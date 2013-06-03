package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonNode;
import argo.jdom.JsonStringNode;

import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-04
 */
public class PasswordJsonNode extends AccessibleAbstractJsonObject {
    public PasswordJsonNode(JsonNode password) {
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
}
