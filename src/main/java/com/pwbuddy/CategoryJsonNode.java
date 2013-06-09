package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonStringNode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jakob Klepp
 * @since 2013-06-04
 */
public class CategoryJsonNode extends AccessibleAbstractJsonObject {
    private TreeMap<JsonStringNode, DataSetJsonNode> fields;

    public CategoryJsonNode(){
        this.fields = new TreeMap<JsonStringNode, DataSetJsonNode>();
    }

    /**
     * @param fields JsonNodes welche keine Instanzen von DataSetJsonNode sind werden ignoriert
     */
    public CategoryJsonNode(Map<JsonStringNode, JsonNode> fields, EncryptionCore encryption){
        this();
        for(Map.Entry<JsonStringNode, JsonNode> entry : fields.entrySet()){
            DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(entry.getValue(), encryption);
            this.fields.put(entry.getKey(), dataSetJsonNode);
        }
    }

    /**
     * @param objectNode sollte vom Typ OBJECT sein, Fields mit einem Value welches kein
     *                   DataSetJsonNode ist werden ignoriert
     */
    public CategoryJsonNode(JsonNode objectNode, EncryptionCore encryption){
        this(objectNode.getFields(), encryption);
    }

    /**
     * Fügt der Kategorie-Node eine DataSet-Node hinzu
     *
     * @param dataSetName Name des DataSets
     * @param dataSetJsonNode Node des DataSets
     * @return true wenn erfolgreich hinzugefügt
     */
    public boolean addDataSetNode(String dataSetName, DataSetJsonNode dataSetJsonNode){
        for(JsonStringNode stringNode : this.fields.keySet()){
            if(stringNode.getText().equals(dataSetName)){
                return false;
            }
        }

        return this.fields.put(JsonNodeFactories.string(dataSetName), dataSetJsonNode) != null;
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

    public void flush(Category category){
        this.fields.clear();
        for(DataSet dataSet : category.getDataSets()) {
            this.fields.put(JsonNodeFactories.string(dataSet.toString()), dataSet.getDataSetJsonNode());
        }
    }
}
