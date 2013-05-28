package com.pwbuddy;
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

    public PBModel(Reader reader){
        this.categories = new PriorityQueue<PBCategory>();

        //JsonRootNode erzeugen
        JdomParser jdomParser = new JdomParser();
        try {
            this.jsonRootNode = jdomParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }

        JsonNode categoriesNode = this.jsonRootNode.getNode("categories");
        JsonNode dataSetsNode = this.jsonRootNode.getNode("datasets");
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

    public static Reader getDefaultReader(){
        String filepath = System.getProperty("user.home") + "/.pwbuddy/passwords.json";
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
}
