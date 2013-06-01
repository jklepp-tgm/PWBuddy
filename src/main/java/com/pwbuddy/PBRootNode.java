package com.pwbuddy;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.*;
import argo.saj.InvalidSyntaxException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Eine JsonRootNode an den Bedarf von PWBuddy zugeschnitten.
 * Dementsprechend sind viele der Methoden hardcoded
 *
 * @author Jakob Klepp
 * @since 2013-05-31
 */
public class PBRootNode extends AccessibleJsonRootNode {
    private HashMap <JsonStringNode, JsonNode> fields;

    private JsonFormatter jsonFormatter;

    private File file;

    /**
     * Es gibt viele Schreibzugriffe deshalb sollte nicht jedes mal ein neuer writer erstellt werden müssen
     * Wird von writerStringToFile erzeugt und benutzt
     */
    private BufferedWriter bufferedWriter;

    /** Soll nicht verwendet werden */
    protected PBRootNode(){}

    /**
     * @param file Daten werden aus diesen File eingelesen und in ebendieses geschrieben.
     */
    public PBRootNode(File file){
        this.fields = new HashMap<JsonStringNode, JsonNode>();

        this.file = file;

        this.jsonFormatter = new PrettyJsonFormatter();

        //Eine mögliche no such file exception soll vermerkt werden
        //Sie währe ein Grund eine neue Datei zu erstellen
        boolean noSuchFile = false;

        String jsonString = null;
        try {
            jsonString = readFileContent(file);
        } catch (NoSuchFileException e){
            noSuchFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonRootNode defaultRootNode = this.getDefaultJsonDocument();
        JsonRootNode rootNode;



        if(jsonString == null || jsonString.equals("") || noSuchFile){
            rootNode = defaultRootNode;
        } else {
            JdomParser jdomParser = new JdomParser();
            try {
                rootNode = jdomParser.parse(jsonString);
            } catch (InvalidSyntaxException e) {
                //e.printStackTrace();
                Timestamp tstamp = new Timestamp(System.currentTimeMillis());
                File newFile = new File(this.file.getAbsolutePath() + "." + tstamp.getTime());
                System.out.println("Json Dokument ungültig, default Dokument wird verwendet. Aktuelles Dokument wir nach " + newFile + "verschoben.");
                try {
                    Files.move(Paths.get(this.file.toURI()), Paths.get(newFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                rootNode = defaultRootNode;
            }
        }

        this.fields.putAll(rootNode.getFields());

        this.flush();
    }

    /**
     * Schreibt Json in File
     *
     * ToDo herausfinden warum anstatt des richtigen Jsondokuments nur {} geschrieben wird
     * ToDo fixen
     */
    public void flush(){
        String json = this.jsonFormatter.format(this);
        try {
            this.writeStringToFile(this.file, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schreibt den Inhalt von string in file
     *
     * @param file Zieldatei
     * @param string Zeug was geschrieben werden soll.
     * @throws IOException Wenn die Datei nicht beschrieben werden kann | die Datei ein Verzeichnis ist |
     * die Datei nicht existiert und es Probleme beim erstellen gibt
     */
    private void writeStringToFile(File file, String string) throws IOException {
        if(!file.exists()){
            //Dateipfad erstellen
            File path = file.getParentFile();
            path.mkdirs();
            //Datei erstellen
            file.createNewFile();
        }

        if(!file.canWrite()){
            throw new AccessDeniedException(file.getAbsolutePath());
        }

        if(file.isDirectory()){
            throw new IOException(file.getAbsolutePath() + " ist ein Verzeichnis.");
        }

        //String --> File; Wenn es sich beim file um this.file handelt soll der default writer verwendet werden
        BufferedWriter writer = null;
        if(file == this.file){
            if(this.bufferedWriter == null){
                FileWriter fileWriter = new FileWriter(file);
                this.bufferedWriter = new BufferedWriter(fileWriter);
            }
            writer = this.bufferedWriter;
        } else {
            FileWriter fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
        }
        writer.write(string);
        writer.flush();

        if(file != this.file){
            writer.close();
        }
    }

    /**
     * Liest den Inhalt von file aus
     *
     * @param file Datei welche ausgelesen werden soll
     * @return Inhalt von file
     * @throws IOException Wenn die Datei nicht existiert | sie ein Verzeichnis ist | nicht gelesen werden kann
     */
    private String readFileContent(File file) throws IOException {
        if(!file.exists()){
            throw new NoSuchFileException(file.getAbsolutePath());
        }

        if(file.isDirectory()){
            throw new IOException(file.getAbsolutePath() + " ist ein Verzeichnis.");
        }

        if(!file.canRead()){
            throw new AccessDeniedException(file.getAbsolutePath());
        }

        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        String fileContent = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();

        return fileContent;
    }

    /**
     * Erstellt die Struktur eines Jsondokuments für den Fall das
     * ein Dokuent eine ungültige Struktur hat.
     *
     * @return Gültiges Json Dokument mit der benötigten Struktur;
     */
    public JsonRootNode getDefaultJsonDocument(){
        JsonObjectNodeBuilder builder;
        builder = JsonNodeBuilders.anObjectBuilder();

        builder.withField("DataSets", JsonNodeBuilders.anArrayBuilder());
        builder.withField("Version", JsonNodeBuilders.aNumberBuilder("" + PBModel.JSON_DOCUMENT_VERSION));

        JsonRootNode node = builder.build();
        return node;
    }

    /**
     * @return OBJECT
     */
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.OBJECT;
    }

    /**
     * k.T.
     * @return false
     */
    @Override
    public boolean hasText() {
        return false;
    }

    /**
     * @return the text associated with this node
     * @throws IllegalStateException if hasText() returns false, indicating this type of node doesn't have text.
     */
    @Override
    public String getText() {
        throw new IllegalStateException("Nope, kein Text");
    }

    /**
     * Ja hats wenn auch vielleicht 0
     * @return true
     */
    @Override
    public boolean hasFields() {
        return true;
    }

    /**
     * Gets the fields associated with this node as a map of name to value.  Note that JSON permits
     * duplicated keys in an object, though in practice this is rare, and in this case, this method
     * will return a map containing a single entry of each unique key.
     *
     * @return the fields associated with this node
     * @throws IllegalStateException if hasFields() returns false, indicating this type of node doesn't support fields.
     */
    @Override
    public Map<JsonStringNode, JsonNode> getFields() {
        return this.fields;
    }

    /**
     * Gets the fields associated with this node as a list of {@code JsonFields}.  This method allows
     * the retrieval of all fields in an object even when the fields have duplicate keys.  This method
     * also preserves the order of the fields.
     *
     * @return the fields associated with this node
     * @throws IllegalStateException if hasFields() returns false, indicating this type of node doesn't support fields.
     */
    @Override
    public List<JsonField> getFieldList() {
        ArrayList <JsonField> fieldList = new ArrayList<JsonField>();

        //Fields aus den Map.Entries zusammensetzten und zur fieldList hinzufügen
        for(Map.Entry <JsonStringNode, JsonNode> fieldEntry : this.getFields().entrySet()){
            fieldList.add(new JsonField(fieldEntry.getKey(), fieldEntry.getValue()));
        }
        return fieldList;
    }

    /**
     * Ja hats wenn auch vielleicht 0
     * @return true
     */
    @Override
    public boolean hasElements() {
        return true;
    }

    /**
     * Keine Garantie für Ordnung oder Unordnung
     *
     * @return the elements associated with this node
     * @throws IllegalStateException if hasElements() returns false, indicating this type of node doesn't support elements.
     */
    @Override
    public List<JsonNode> getElements() {
        ArrayList <JsonNode> nodes= new ArrayList<JsonNode>();
        for(Map.Entry <JsonStringNode, JsonNode> entry : this.getFields().entrySet()) {
            nodes.add(entry.getValue());
        }
        return nodes;
    }
}
