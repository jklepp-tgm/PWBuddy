package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonStringNode;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jakob Klepp
 * @since 2013-06-04
 */
public class PasswordJsonNode extends AccessibleAbstractJsonObject {
    private String encryptedPassword;
    private String iv;

    private EncryptionCore encryption;

    /** Zugriff verhindern */
    private PasswordJsonNode(){}

    public PasswordJsonNode(JsonNode passwordNode, EncryptionCore encryption) {
        this.encryptedPassword = passwordNode.getStringValue("Crypt");
        this.iv = passwordNode.getStringValue("IV");
        this.encryption = encryption;
    }

    public PasswordJsonNode(char [] password, EncryptionCore encryption) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        this.encryption = encryption;

        //Verschlüsselung des Passworts!
        this.encryptedPassword = encryption.encrypt(new String(password));

        //Verschlüsseltes Passwort und initialisierungs Vektor in die entsprechenden Variablen speichern
        this.iv = encryption.getIV();
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
        fields.put(JsonNodeFactories.string("Crypt"), JsonNodeFactories.string(this.encryptedPassword));
        fields.put(JsonNodeFactories.string("IV"), JsonNodeFactories.string(this.iv));
        return fields;
    }
}
