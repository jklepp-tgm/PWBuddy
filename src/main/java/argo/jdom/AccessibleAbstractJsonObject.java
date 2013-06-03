package argo.jdom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-01
 */
public abstract class AccessibleAbstractJsonObject extends AbstractJsonObject {
    public AccessibleAbstractJsonObject(){}

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
