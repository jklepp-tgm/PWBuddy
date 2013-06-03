package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonNode;
import argo.jdom.JsonStringNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-04
 */
public class CategoryJsonNode extends AccessibleAbstractJsonObject {
    private HashMap <JsonStringNode, DataSetJsonNode> fields;

    public CategoryJsonNode(){
        this.fields = new HashMap<JsonStringNode, DataSetJsonNode>();
    }

    /**
     * @param fields JsonNodes welche keine Instanzen von DataSetJsonNode sind werden ignoriert
     */
    public CategoryJsonNode(Map<JsonStringNode, JsonNode> fields){
        this();
        for(Map.Entry<JsonStringNode, JsonNode> entry : fields.entrySet()){
            DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(entry.getValue());
            fields.put(entry.getKey(), dataSetJsonNode);
        }
    }

    /**
     * @param objectNode sollte vom Typ OBJECT sein, Fields mit einem Value welches kein
     *                   DataSetJsonNode ist werden ignoriert
     */
    public CategoryJsonNode(JsonNode objectNode){
        this(objectNode.getFields());
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
        HashMap <JsonStringNode, JsonNode> fields = new HashMap<JsonStringNode, JsonNode>();
        for(Map.Entry<JsonStringNode, DataSetJsonNode> entry : this.fields.entrySet()){
            fields.put(entry.getKey(), entry.getValue());
        }
        return fields;
    }
}
