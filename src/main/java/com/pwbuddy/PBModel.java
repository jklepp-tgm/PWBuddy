package com.pwbuddy;
import argo.format.PrettyJsonFormatter;
import argo.jdom.*;
import argo.saj.InvalidSyntaxException;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class PBModel {
    private PriorityQueue<PBCategory> categories;
    private JsonRootNode jsonRootNode;

    private Reader reader;
    private Writer writer;

    /** Sollte bei jeder änderung am Dokumenten Modell um 1 inkrementiert werden */
    public static final int JSON_DOCUMENT_VERSION = 2;

    /** Der Dateipfad welcher im Normalfall verwendet werden soll */
    public static final String DEFAULT_JSON_DOCUMENT_PATH = System.getProperty("user.home") + "/.pwbuddy/passwords.json";

    //ToDo node.has und node.is verwenden um unkontrollierte Abstürze zu vermeiden sollte das json Dokument invalid sein
    public PBModel(Reader reader, Writer writer){
        this.categories = new PriorityQueue<PBCategory>();

        this.reader = reader;
        this.writer = writer;

        JdomParser jdomParser = new JdomParser();

        //Überprüfen ob Json gültig ist
        try{
            //json Objekt aus Reader laden
            this.jsonRootNode = jdomParser.parse(this.reader);
        } catch (InvalidSyntaxException e) {
            //json ungültig, erstelle backup des aktuellen json Dokument und erstelle eine valide json Struktur
            System.out.println("Json Dokument hat eine ungültige Syntax. Ein neues Dokument wird erstellt.");
            //TODO json Backupen

            //default json Dokument erstellen.
            this.jsonRootNode = getDefaultJsonDocument();
        } catch (IOException e) {
            //Wenn der Reader Probleme macht
            e.printStackTrace();
            System.exit(1);
        }

        //Zustand des Json Dokuments in Datei schreiben
        PrettyJsonFormatter jsonFormatter = new PrettyJsonFormatter();
        String output = jsonFormatter.format(jsonRootNode);
        try {
            this.writer.write(output);
            this.writer.flush();
            System.out.println(output);
        } catch (IOException e) {
            //Im falle eines Problematischen writers
            e.printStackTrace();
        }

        //TODO lösen von Versionskompatiblitätsproblemen
        //Version des Json Dokuments mit der unterstützten Version vergleichen
        if(Integer.parseInt(this.jsonRootNode.getNumberValue("Version")) != JSON_DOCUMENT_VERSION){
            //Andere Version
            System.out.println("Json Dokument mit den Passwörtern hat nicht die unterstützte Version.");
            System.exit(1);
        }

        //Existierende DataSets auslesen
        JsonNode dataSetsNode = this.jsonRootNode.getNode("DataSets");
        List <JsonNode> dataSetsList = dataSetsNode.getArrayNode();

        //Über nodes in dataSetsNode iterieren um DataSet und Category Objekte zu erzeugen
        for(Iterator<JsonNode> dataSetsIterator = dataSetsList.iterator(); dataSetsIterator.hasNext(); ){
            JsonNode dataSetNode = dataSetsIterator.next();
            String categoryName = dataSetNode.getStringValue("Category");

            //Wird noch gefunden oder erzeugt.
            PBCategory pbCategory = null;

            //Überprüfen ob die Category des aktuellen DataSet bereits existiert
            boolean namedCategoryExists = false;
            for(Iterator <PBCategory> categoryIterator = this.categories.iterator(); categoryIterator.hasNext();){
                PBCategory category = categoryIterator.next();
                if(category.getModel().getName().equals(categoryName)){
                    namedCategoryExists = true;
                    pbCategory = category;
                    break;
                }
            }

            //Falls die Category noch nicht existiert
            if(!namedCategoryExists){
                //Category hinzufügen
                pbCategory = new PBCategory(categoryName);
                this.addCategory(pbCategory);
            }

            //DataSet Objekt erzeugen und zur passenden Category hinzufügen
            String dataSetName = dataSetNode.getStringValue("Title");
            PBDataSet pbDataSet = new PBDataSet(dataSetName);
            pbCategory.getModel().add(pbDataSet);
        }
    }

    public PBModel(){
        this(getDefaultReader(), getDefaultWriter());
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param category Kategorie
     */
    protected void addCategory(PBCategory category){
        this.categories.add(category);
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param categoryName Name der Kategorie
     */
    public void addCategory(String categoryName){
        addCategory(new PBCategory(categoryName));
    }

    protected synchronized String elementID(){
        int id = Integer.parseInt(this.jsonRootNode.getNumberValue("id_incrementer"));
        String elementID = "id_" + id + "/";
        ++id;
        //Todo id änderung zurück ins json dokument schreiben
        return elementID;
    }

    /**
     * Gibt ein Array mit allen Kategorien zurück.
     *
     * @return Array mit allen Kategorien
     */
    public Iterator<PBCategory> iterator(){
        return categories.iterator();
    }

    public static Reader getDefaultReader(){
        String filepath = DEFAULT_JSON_DOCUMENT_PATH;
        FileReader fileReader = null;
        boolean ersterDurchlauf = true;
        while(fileReader == null){ //Wenn die Datei erst erstellt werden muss soll ein zweiter anlauf versucht werden
            File file = new File(filepath);
            if(file.isFile()){
                if(file.canRead() && file.canWrite()){
                    try {
                        fileReader = new FileReader(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //Kann nicht gelesen oder geschrieben werden
                    //Entsprechende Fehlermeldung ausgeben
                    if(!file.canRead()){
                        System.out.println("Datei: " + filepath + " kann nicht gelesen werden.");
                    }
                    if(!file.canWrite()){
                        System.out.println("Datei: " + filepath + " kann nicht geschrieben werden.");
                    }
                    //Program beenden
                    System.exit(1);
                }
            } else {
                try {
                    //Dateipfad erstellen
                    File path = file.getParentFile();
                    path.mkdirs();
                    //Datei erstellen
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (ersterDurchlauf) {
                    System.out.println("Datei: " + filepath + " existiert nicht. Wird erstellt.");
                } else {
                    System.exit(1);
                }
                ersterDurchlauf = false;
            }
        }

        return fileReader;
    }


    public static Writer getDefaultWriter(){
        //sicherstellen das die Datei existiert und sie den ansprüchen entsprechend zugreifbar ist.
        getDefaultReader();
        try {
            FileWriter fileWriter = new FileWriter(DEFAULT_JSON_DOCUMENT_PATH);
            return new BufferedWriter(fileWriter);
        } catch (IOException e) {
            //sollte nicht passieren
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /**
     * Erstellt die Struktur eines Jsondokuments für den Fall das
     * ein Dokuent eine ungültige Struktur hat.
     *
     * @return Gültiges Json Dokument mit der benötigten Struktur;
     */
    public static JsonRootNode getDefaultJsonDocument(){
        JsonObjectNodeBuilder builder;
        builder = JsonNodeBuilders.anObjectBuilder();

        builder.withField("DataSets", JsonNodeBuilders.anArrayBuilder());
        builder.withField("Version", JsonNodeBuilders.aNumberBuilder("" + JSON_DOCUMENT_VERSION));

        JsonRootNode node = builder.build();
        return node;
    }
}
