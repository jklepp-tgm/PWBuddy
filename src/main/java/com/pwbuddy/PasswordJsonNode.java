package com.pwbuddy;

import argo.jdom.AccessibleAbstractJsonObject;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonStringNode;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
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

    public PasswordJsonNode(JsonNode passwordNode, EncryptionCore encryption) {
        this.encryptedPassword = passwordNode.getStringValue("Crypt");
        this.iv = passwordNode.getStringValue("IV");
        this.encryption = encryption;
    }

    public PasswordJsonNode(char [] password, EncryptionCore encryption) {
        this.encryption = encryption;

        //Verschlüsselung des Passworts!
        try {
            this.encryptedPassword = encryption.encrypt(new String(password));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        //Verschlüsseltes Passwort und initialisierungs Vektor in die entsprechenden Variablen speichern
        this.iv = encryption.getIV();
    }

    public void setPassword(char [] password) {
        try {
            this.encryptedPassword = encryption.encrypt(new String(password));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        //Verschlüsseltes Passwort und initialisierungs Vektor in die entsprechenden Variablen speichern
        this.iv = encryption.getIV();
    }

    public char[] getPassword() {
        char [] password = null;
        try {
            password = this.encryption.decrypt(this.encryptedPassword, this.iv).toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return password;
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
