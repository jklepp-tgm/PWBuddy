package com.pwbuddy;
import argo.jdom.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class Model {
    private PriorityQueue<Category> categories;
    private RootNode jsonRootNode;

    /** Sollte bei jeder änderung am Dokumenten Modell um 1 inkrementiert werden */
    public static final int JSON_DOCUMENT_VERSION = 4;

    /** Der Dateipfad welcher im Normalfall verwendet werden soll */
    public static final String DEFAULT_JSON_DOCUMENT_PATH = System.getProperty("user.home") + "/.pwbuddy/passwords.json";
    private EncryptionCore encryption;

    public Model(File file){
        this.categories = new PriorityQueue<Category>();

        this.encryption = new EncryptionCore(new String(this.getPasswordFromUser()));

        this.jsonRootNode = new RootNode(file);

        //TODO lösen von Versionskompatiblitätsproblemen
        //Version des Json Dokuments mit der unterstützten Version vergleichen
        if(Integer.parseInt(this.jsonRootNode.getNumberValue("Version")) != JSON_DOCUMENT_VERSION){
            //Andere Version
            System.out.println("Json Dokument mit den Passwörtern hat nicht die unterstützte Version.");
            System.exit(1);
        }

        //Existierende Categories auslesen
        RootNode.CategoriesObject categoriesObject = (RootNode.CategoriesObject) this.jsonRootNode.getNode("Categories");

        //Über über Categories iterieren
        for (JsonField categoryField : categoriesObject.getFieldList()) {
            CategoryJsonNode categoryNode = new CategoryJsonNode(categoryField.getValue(), this.encryption);
            String categoryName = categoryField.getName().getText();

            //Category erzeugen.
            Category category = new Category(categoryName, categoryNode);
        }

        //Json Baum alle 5 Sekunden sichern
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jsonRootNode.flush();
            }
        }).start();
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
        return addCategory(new Category(categoryName, new CategoryJsonNode()));
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
        String[] categoryNames = new String[this.categories.size()];
        int i = 0;
        for(Category category : this.categories){
            categoryNames[i++] = category.getModel().getName();
        }
        DataSetInputPanel inputPanel = new DataSetInputPanel(categoryNames);
        javax.swing.JOptionPane.showConfirmDialog(
                null,
                inputPanel,
                "Eingabe DataSet",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        String dataSetName = inputPanel.websiteF.getText();
        String categoryName = (String)inputPanel.categoryF.getSelectedItem();

        Category category = null;
        for(Category tempCategory : this.categories){
            if(tempCategory.getModel().getName().equals(categoryName)){
                category = tempCategory;
                break;
            }
        }

        DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(
                inputPanel.websiteF.getText(),
                inputPanel.usernameF.getText(),
                inputPanel.emailF.getText(),
                new PasswordJsonNode(inputPanel.passwordF.getPassword(), this.encryption),
                this.encryption
        );

        DataSet dataSet = new DataSet(dataSetName, dataSetJsonNode);
        return category.getModel().add(dataSet);
    }

    public char[] getPasswordFromUser(){
        JPanel askForPassword = new JPanel();
        askForPassword.setLayout(new GridLayout(0, 2, 2, 2));
        JPasswordField passwordField = new JPasswordField();
        askForPassword.add(new JLabel("Masterpasswort: "));
        askForPassword.add(passwordField);


        javax.swing.JOptionPane.showConfirmDialog(
                null,
                askForPassword,
                "Eingabe DataSet",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        return passwordField.getPassword();
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
