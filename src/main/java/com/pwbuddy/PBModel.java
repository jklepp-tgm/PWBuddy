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
}
