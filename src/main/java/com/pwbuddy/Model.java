package com.pwbuddy;
import argo.jdom.*;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author Jakob Klepp, Andreas Willinger
 * @since 2013-04-09
 */
public class Model implements TreeModel {
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
            Category category = new Category(categoryName, categoryNode, this);
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
        return addCategory(new Category(categoryName, new CategoryJsonNode(), this));
    }

    public EncryptionCore getEncryption() {
        return encryption;
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
     * Gibt eine Collection mit allen Kategorien zurück.
     *
     * @return Collection mit allen Kategorien
     */
    public PriorityQueue <Category> getCategories(){
        return categories;
    }

    //<editor-fold desc="TreeModel">
    private MainPanel mainPanel = new MainPanel();

    /**
     * Returns the root of the tree.  Returns <code>null</code>
     * only if the tree has no nodes.
     *
     * @return the root of the tree
     */
    @Override
    public Object getRoot() {
        return mainPanel;
    }

    /**
     * Returns the child of <code>parent</code> at index <code>index</code>
     * in the parent's
     * child array.  <code>parent</code> must be a node previously obtained
     * from this data source. This should not return <code>null</code>
     * if <code>index</code>
     * is a valid index for <code>parent</code> (that is <code>index >= 0 &&
     * index < getChildCount(parent</code>)).
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the child of <code>parent</code> at index <code>index</code>
     */
    @Override
    public Object getChild(Object parent, int index) {
        if(parent == mainPanel){
            return this.categories.toArray()[index];
        } else
        if(parent instanceof Category){
            return ((Category)parent).getDataSets().toArray()[index];
        }
        return null;
    }

    /**
     * Returns the number of children of <code>parent</code>.
     * Returns 0 if the node
     * is a leaf or if it has no children.  <code>parent</code> must be a node
     * previously obtained from this data source.
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the number of children of the node <code>parent</code>
     */
    @Override
    public int getChildCount(Object parent) {
        if(parent == mainPanel){
            return this.categories.size();
        } else
        if(this.categories.contains(parent)) { //Daraus lässt sich auch schließen das es sich um ein Category Objekt handelt
            return ((Category)parent).getDataSets().size();
        }
        return 0;
    }

    /**
     * Returns <code>true</code> if <code>node</code> is a leaf.
     * It is possible for this method to return <code>false</code>
     * even if <code>node</code> has no children.
     * A directory in a filesystem, for example,
     * may contain no files; the node representing
     * the directory is not a leaf, but it also has no children.
     *
     * @param node a node in the tree, obtained from this data source
     * @return true if <code>node</code> is a leaf
     */
    @Override
    public boolean isLeaf(Object node) {
        if(node instanceof DataSet){
            return true;
        }
        return false;
    }

    /**
     * Messaged when the user has altered the value for the item identified
     * by <code>path</code> to <code>newValue</code>.
     * If <code>newValue</code> signifies a truly new value
     * the model should post a <code>treeNodesChanged</code> event.
     *
     * @param path     path to the node that the user has altered
     * @param newValue the new value from the TreeCellEditor
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        //Tu nix
    }

    /**
     * Returns the index of child in parent.  If either <code>parent</code>
     * or <code>child</code> is <code>null</code>, returns -1.
     * If either <code>parent</code> or <code>child</code> don't
     * belong to this tree model, returns -1.
     *
     * @param parent a node in the tree, obtained from this data source
     * @param child  the node we are interested in
     * @return the index of the child in the parent, or -1 if either
     *         <code>child</code> or <code>parent</code> are <code>null</code>
     *         or don't belong to this tree model
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if(parent instanceof Category && ((Category)parent).getDataSets().contains(child)) {
            int posCounter = 0;
            //DataSets mit child vergleichen
            Iterator<DataSet> iterator = ((Category)parent).getDataSets().iterator();
            while(iterator.hasNext() && iterator.next() != child){
                posCounter ++;
            }
            return posCounter;
        } else
        if(parent == mainPanel && this.categories.contains(child)) {
            int posCounter = 0;
            //Categories mit child vergleichen
            Iterator<Category> iterator = this.categories.iterator();
            while(iterator.hasNext() && iterator.next() != child){
                posCounter ++;
            }
            return posCounter;
        }
        return -1;
    }

    /**
     * Adds a listener for the <code>TreeModelEvent</code>
     * posted after the tree changes.
     *
     * @param l the listener to add
     * @see #removeTreeModelListener
     */
    @Override
    public void addTreeModelListener(TreeModelListener l) {
        //Tu nix
    }

    /**
     * Removes a listener previously added with
     * <code>addTreeModelListener</code>.
     *
     * @param l the listener to remove
     * @see #addTreeModelListener
     */
    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        //Tu nix
    }
    //</editor-fold>
}
