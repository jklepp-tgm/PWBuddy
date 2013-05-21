package com.pwbuddy;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBModel {
    private PriorityQueue<PBCategory> categories;

    public PBModel(File pwFile){
        //TODO File einlese
        //TODO ... parsen
        this.categories = new PriorityQueue<PBCategory>();
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
     * Gibt ein Array mit allen Kategorien zurück.
     *
     * @return Array mit allen Kategorien
     */
    public PBCategory[] getCategories(){
        return (PBCategory[]) this.categories.toArray();
    }
}
