package com.pwbuddy;

import javax.swing.*;
import javax.swing.text.PlainDocument;

/**
 * @author Jakob Klepp
 * @since 2013-06-09
 */
public class CustomPasswordField extends JPasswordField {
    public CustomPasswordField(PasswordJsonNode passwordJsonNode) {
        super.setDocument(new CustomPasswordFieldDokument(passwordJsonNode));

    }

    public class CustomPasswordFieldDokument extends PlainDocument {
        public CustomPasswordFieldDokument(PasswordJsonNode passwordJsonNode) {

        }
    }
}
