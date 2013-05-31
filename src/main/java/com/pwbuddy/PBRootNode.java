package com.pwbuddy;

import argo.jdom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Eine JsonRootNode an den Bedarf von PWBuddy zugeschnitten.
 * Dementsprechend sind viele der Methoden hardcoded
 *
 * @author Jakob Klepp
 * @since 2013-05-31
 */
public class PBRootNode extends AccessibleJsonNode {
    /** Soll nicht verwendet werden */
    protected PBRootNode(){}

    /**
     * @param file Daten werden aus diesen File eingelesen und in ebendieses geschrieben.
     */
    public PBRootNode(File file){

    }

    /**
     * @return OBJECT
     */
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.OBJECT;
    }

    /**
     * k.T.
     * @return false
     */
    @Override
    public boolean hasText() {
        return false;
    }

    /**
     * @return the text associated with this node
     * @throws IllegalStateException if hasText() returns false, indicating this type of node doesn't have text.
     */
    @Override
    public String getText() {
        throw new IllegalStateException("Nope, kein Text");
    }

    /**
     * Ja hats wenn auch vielleicht 0
     * @return true
     */
    @Override
    public boolean hasFields() {
        return true;
    }

    /**
     * Gets the fields associated with this node as a map of name to value.  Note that JSON permits
     * duplicated keys in an object, though in practice this is rare, and in this case, this method
     * will return a map containing a single entry of each unique key.
     *
     * @return the fields associated with this node
     * @throws IllegalStateException if hasFields() returns false, indicating this type of node doesn't support fields.
     */
    @Override
    public Map<JsonStringNode, JsonNode> getFields() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the fields associated with this node as a list of {@code JsonFields}.  This method allows
     * the retrieval of all fields in an object even when the fields have duplicate keys.  This method
     * also preserves the order of the fields.
     *
     * @return the fields associated with this node
     * @throws IllegalStateException if hasFields() returns false, indicating this type of node doesn't support fields.
     */
    @Override
    public List<JsonField> getFieldList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Ja hats wenn auch vielleicht 0
     * @return true
     */
    @Override
    public boolean hasElements() {
        return true;
    }

    /**
     * Keine Garantie für Ordnung oder Unordnung
     *
     * @return the elements associated with this node
     * @throws IllegalStateException if hasElements() returns false, indicating this type of node doesn't support elements.
     */
    @Override
    public List<JsonNode> getElements() {
        ArrayList <JsonNode> nodes= new ArrayList<JsonNode>();
        for(Map.Entry <JsonStringNode, JsonNode> entry : this.getFields().entrySet()) {
            nodes.add(entry.getValue());
        }
        return nodes;
    }
}
