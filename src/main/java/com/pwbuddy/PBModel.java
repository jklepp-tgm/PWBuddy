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
    private PBRootNode jsonRootNode;

    /** Sollte bei jeder änderung am Dokumenten Modell um 1 inkrementiert werden */
    public static final int JSON_DOCUMENT_VERSION = 2;

    /** Der Dateipfad welcher im Normalfall verwendet werden soll */
    public static final String DEFAULT_JSON_DOCUMENT_PATH = System.getProperty("user.home") + "/.pwbuddy/passwords.json";

    //ToDo node.has und node.is verwenden um unkontrollierte Abstürze zu vermeiden sollte das json Dokument invalid sein
    public PBModel(File file){
        super();
        this.categories = new PriorityQueue<PBCategory>();

        this.jsonRootNode = new PBRootNode(file);

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
        for (JsonNode dataSetNode : dataSetsList) {
            String categoryName = dataSetNode.getStringValue("Category");

            //Wird noch gefunden oder erzeugt.
            PBCategory pbCategory = null;

            //Überprüfen ob die Category des aktuellen DataSet bereits existiert
            boolean namedCategoryExists = false;
            for (PBCategory category : this.categories) {
                if (category.getModel().getName().equals(categoryName)) {
                    namedCategoryExists = true;
                    pbCategory = category;
                    break;
                }
            }

            //Falls die Category noch nicht existiert
            if (!namedCategoryExists) {
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
        this(new File(DEFAULT_JSON_DOCUMENT_PATH));
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
}
