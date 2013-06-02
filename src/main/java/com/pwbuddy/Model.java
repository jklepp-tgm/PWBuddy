package com.pwbuddy;
import argo.jdom.*;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class Model {
    private PriorityQueue<Category> categories;
    private RootNode jsonRootNode;

    /** Sollte bei jeder änderung am Dokumenten Modell um 1 inkrementiert werden */
    public static final int JSON_DOCUMENT_VERSION = 2;

    /** Der Dateipfad welcher im Normalfall verwendet werden soll */
    public static final String DEFAULT_JSON_DOCUMENT_PATH = System.getProperty("user.home") + "/.pwbuddy/passwords.json";

    public Model(File file){
        this.categories = new PriorityQueue<Category>();

        this.jsonRootNode = new RootNode(file);

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
            Category pbCategory = null;

            //Überprüfen ob die Category des aktuellen DataSet bereits existiert
            boolean namedCategoryExists = false;
            for (Category category : this.categories) {
                if (category.getModel().getName().equals(categoryName)) {
                    namedCategoryExists = true;
                    pbCategory = category;
                    break;
                }
            }

            //Falls die Category noch nicht existiert
            if (!namedCategoryExists) {
                //Category hinzufügen
                pbCategory = new Category(categoryName);
                this.addCategory(pbCategory);
            }

            //DataSet Objekt erzeugen und zur passenden Category hinzufügen
            String dataSetName = dataSetNode.getStringValue("Title");
            DataSet pbDataSet = new DataSet(dataSetName);
            pbCategory.getModel().add(pbDataSet);
        }
    }

    public Model(){
        this(new File(DEFAULT_JSON_DOCUMENT_PATH));
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param category Kategorie
     * @return Erfolgreich? Ein möglicher grund für Misserfolg ist wenn eine entsprechende Kategorie bereits besteht
     */
    protected boolean addCategory(Category category){
        return this.categories.add(category);
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param categoryName Name der Kategorie
     * @return Erfolgreich? Ein möglicher grund für Misserfolg ist wenn eine entsprechende Kategorie bereits besteht
     */
    public boolean addCategory(String categoryName){
        return addCategory(new Category(categoryName));
    }

    /**
     * Fragt den Benutzer nach den Namen für eine Neue Kategorie und versucht sie zu erstellen.
     *
     * @return Erfolgreich?
     */
    public boolean addCategoryFromUserInput(){
        String categoryName = javax.swing.JOptionPane.showInputDialog("Name der Kategorie die erstellt werden soll?");
        if(categoryName != null){
            return this.addCategory(categoryName);
        }
        return false;
    }

    /**
     * Fragt den Benutzer nach Eingaben und versucht daraus einen Datensatz zu erstellen
     *
     * @return Erfolgreich
     */
    public boolean addDataSetFromUserInput(){
        DataSetInputPanel inputPanel = new DataSetInputPanel();
        javax.swing.JOptionPane.showConfirmDialog(
                null,
                inputPanel,
                "Eingabe DataSet",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        String dataSetName = "";
        String categoryName = "";
        for(JLabel label : inputPanel.getInputs().keySet()){
            if(label.getText().equals("Website")){
                dataSetName = inputPanel.getInputs().get(label).getText();
            } else
            if(label.getText().equals("Category")){
                categoryName = inputPanel.getInputs().get(label).getText();
            }
            if(!dataSetName.equals("") && !categoryName.equals("")){
                break;
            }
        }

        if (dataSetName.equals("") || categoryName.equals("")) {
            return false;
        }

        Category category = null;
        for(Category tempCategory : this.categories){
            if(tempCategory.getModel().getName().equals(categoryName)){
                category = tempCategory;
                break;
            }
        }

        if(category == null){
            category = new Category(categoryName);
            this.categories.add(category);
        }

        DataSet dataSet = new DataSet(dataSetName, inputPanel.getInputs());
        return category.getModel().add(dataSet);
    }

    /**
     * Gibt ein Array mit allen Kategorien zurück.
     *
     * @return Array mit allen Kategorien
     */
    public Iterator<Category> iterator(){
        return categories.iterator();
    }
}
