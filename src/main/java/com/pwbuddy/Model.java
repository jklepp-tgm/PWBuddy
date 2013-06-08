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

        char [] passwordChars = new char[0];
        do {
            passwordChars = this.getPasswordFromUser();
            if(passwordChars == null){
                System.out.println("Passworteingabe wurde abgebrochen, PWBuddy wird beendet.");
                System.exit(0);
            }
            if(passwordChars.length == 0){
                javax.swing.JOptionPane.showMessageDialog(null, "Passwort ungültig, bitte geben sie was ein.");
            }
        } while (passwordChars.length == 0);

        this.encryption = new EncryptionCore(new String(passwordChars));

        this.jsonRootNode = new RootNode(file);

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
            Category category = new Category(categoryName, categoryNode, this.encryption);
            this.categories.add(category);
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
        return this.categories.add(category) && this.jsonRootNode.addCategoryNode(category.getName(), category.getCategoryJsonNode());
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param categoryName Name der Kategorie
     * @return Erfolgreich? Ein möglicher grund für Misserfolg ist wenn eine entsprechende Kategorie bereits besteht
     */
    public boolean addCategory(String categoryName){
        return addCategory(new Category(categoryName, new CategoryJsonNode(), this.encryption));
    }

    public char[] getPasswordFromUser(){
        JPanel askForPassword = new JPanel();
        askForPassword.setLayout(new GridLayout(0, 2, 2, 2));
        JPasswordField passwordField = new JPasswordField();
        askForPassword.add(new JLabel("Masterpasswort: "));
        askForPassword.add(passwordField);


        int status = javax.swing.JOptionPane.showConfirmDialog(
                null,
                askForPassword,
                "Masterpasswort eingeben",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(status != 0){ //0 ... Ok Button
            return null;
        }

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
