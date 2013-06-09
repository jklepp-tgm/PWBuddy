package com.pwbuddy;

import argo.jdom.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-02
 */
public class DataSetJsonNode extends AccessibleAbstractJsonObject {
    //Benötigte Felder
    public String name;
    public String website;
    public String username;
    public String email;
    public PasswordJsonNode password;

    public DataSetJsonNode(String name, String website, String username, String email, JsonNode password, EncryptionCore encryption){
        this.name = name;
        this.website = website;
        this.username = username;
        this.email = email;
        this.password = new PasswordJsonNode(password, encryption);
    }

    public DataSetJsonNode(JsonNode json, EncryptionCore encryption){
        this(json.getStringValue("Name"),
                json.getStringValue("Website"),
                json.getStringValue("Username"),
                json.getStringValue("eMail"),
                json.getNode("Password"),
                encryption
        );
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
        HashMap<JsonStringNode, JsonNode> fields = new HashMap<JsonStringNode, JsonNode>();
        fields.put(JsonNodeFactories.string("Name"), JsonNodeFactories.string(this.name));
        fields.put(JsonNodeFactories.string("Website"), JsonNodeFactories.string(this.website));
        fields.put(JsonNodeFactories.string("Username"), JsonNodeFactories.string(this.username));
        fields.put(JsonNodeFactories.string("eMail"), JsonNodeFactories.string(this.email));
        fields.put(JsonNodeFactories.string("Password"), this.password);
        return fields;
    }
}
