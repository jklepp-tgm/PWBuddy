package com.pwbuddy;
import argo.format.PrettyJsonFormatter;
import argo.jdom.*;
import argo.saj.InvalidSyntaxException;

import java.io.*;
import java.util.Iterator;
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
    public static final int JSON_DOCUMENT_VERSION = 1;

    /** Der Dateipfad welcher im Normalfall verwendet werden soll */
    public static final String DEFAULT_JSON_DOCUMENT_PATH = System.getProperty("user.home") + "/.pwbuddy/passwords.json";

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

        JsonNode dataSetsNode = this.jsonRootNode.getNode("DataSets");
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

    /**
     * Gibt ein Array mit allen Kategorien zurück.
     *
     * @return Array mit allen Kategorien
     */
    public Iterator<PBCategory> iterator(){
        return categories.iterator();
    }

    public static FileReader getDefaultReader(){
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

    public static BufferedWriter getDefaultWriter(){
        //sicherstellen das die Datei existiert
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
        builder.withField("id_incrementer", JsonNodeBuilders.aNumberBuilder("1"));

        JsonRootNode node = builder.build();
        return node;
    }
}
