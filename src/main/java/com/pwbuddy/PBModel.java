package com.pwbuddy;

import java.io.File;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBModel {
    private TreeMap<String, PBCategory> categories;

    public PBModel(File pwFile){
        //TODO File einlese
        //TODO ... parsen
        categories = new TreeMap<String, PBCategory>();
    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param categoryName Name der Kategorie
     * @param category Kategorie
     */
    protected void addCategory(String categoryName, PBCategory category){

    }

    /**
     * Fügt eine Kategorie hinzu
     *
     * @param categoryName Name der Kategorie
     */
    public void addCategory(String categoryName){
        addCategory(categoryName, new PBCategory());
    }

    /**
     * Gibt ein Set mit den Namen aller Kategorien zurück.
     *
     * @return Set mit den Namen aller Kategorien
     */
    public Set<String> getCategoryNames(){
        return categories.keySet();
    }

    /**
     * Ermöglicht es ein Kategorie Objekt anhand des Namens zu erhalten.
     *
     * @param categoryName Name der Kategorie
     * @return Kategorie Objekt
     */
    public PBCategory getCategory(String categoryName){
        return categories.get(categoryName);
    }
}
