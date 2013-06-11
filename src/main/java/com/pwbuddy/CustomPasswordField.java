package com.pwbuddy;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jakob Klepp
 * @since 2013-06-09
 */
public class CustomPasswordField extends JPasswordField {
    private PasswordJsonNode passwordJsonNode;
    private boolean showPassword;

    public CustomPasswordField(PasswordJsonNode passwordJsonNode) {
        super(new String(passwordJsonNode.getPassword()));

        this.passwordJsonNode = passwordJsonNode;

        this.showPassword = false;

        this.getDocument().addDocumentListener(new PasswordDocumentListener());
        this.addMouseListener(new PasswordMouseListener());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getEchoChar() {
        if(showPassword)
            return 0; //Zeichen anzeigen! siehe JPasswordField#getEchoChar()
        return super.getEchoChar();
    }

    public class PasswordDocumentListener implements DocumentListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertUpdate(DocumentEvent e) {}

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeUpdate(DocumentEvent e) {}

        /**
         * {@inheritDoc}
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            passwordJsonNode.setPassword(getPassword());
        }
    }

    public class PasswordMouseListener extends MouseAdapter {
        /**
         * {@inheritDoc}
         */
        public void mousePressed(MouseEvent e) {
            if(e.getComponent().equals(CustomPasswordField.this)) {
                CustomPasswordField.this.showPassword = !CustomPasswordField.this.showPassword;
            }
        }
    }
}
