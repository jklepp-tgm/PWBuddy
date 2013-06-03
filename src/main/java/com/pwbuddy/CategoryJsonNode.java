package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonStringNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-04
 */
public class CategoryJsonNode extends AccessibleAbstractJsonObject {
    private HashMap <JsonStringNode, JsonNode> fields;

    /**
     * @param fields JsonNodes welche keine Instanzen von DataSetJsonNode sind werden ignoriert
     */
    public CategoryJsonNode(Map<JsonStringNode, JsonNode> fields){
        this.fields = new HashMap<JsonStringNode, JsonNode>();
        for(Map.Entry<JsonStringNode, JsonNode> entry : fields.entrySet()){
            if(entry.getValue() instanceof DataSetJsonNode){
                this.fields.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param objectNode sollte vom Typ OBJECT sein, Fields mit einem Value welches kein
     *                   DataSetJsonNode ist werden ignoriert
     */
    public CategoryJsonNode(JsonNode objectNode){
        this.fields = new HashMap<JsonStringNode, JsonNode>(objectNode.getFields());
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
        return fields;
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
        ArrayList<JsonField> fieldsList = new ArrayList<JsonField>();
        for(Map.Entry<JsonStringNode, JsonNode> entry : this.getFields().entrySet()){
            fieldsList.add(new JsonField(entry.getKey(), entry.getValue()));
        }
        return fieldsList;
    }
}
