package com.pwbuddy;

import java.io.File;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBModel {
    private TreeMap<String, PBCategorie> categories;

    public PBModel(File pwFile){
        //TODO File einlese
        //TODO ... parsen
        categories = new TreeMap<String, PBCategorie>();
    }
    /**
     * Fügrt eine Kategorie hinzu
     * @param categorieName
     */
    public void addCategorie(String categorieName){

    }

    /**
     * Gibt ein Set mit den Namen aller Kategorien zurück.
     * @return Set mit den Namen aller Kategorien
     */
    public Set<String> getCategorieNames(){
        return categories.keySet();
    }

    public PBCategorie getCategorie(String categorieName){
        return categories.get(categorieName);
    }
}
