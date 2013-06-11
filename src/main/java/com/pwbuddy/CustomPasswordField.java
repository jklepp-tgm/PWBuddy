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
    private char defaultEchoChar;

    public CustomPasswordField(PasswordJsonNode passwordJsonNode) {
        this.passwordJsonNode = passwordJsonNode;

        char [] passwordChars = this.passwordJsonNode.getPassword();
        this.setText(new String(passwordChars));

        this.showPassword = false;
        this.defaultEchoChar = this.getEchoChar();

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

    public void savePassword() {
        char [] passwordChars = this.getPassword();
        passwordJsonNode.setPassword(passwordChars);
    }

    public class PasswordDocumentListener implements DocumentListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            CustomPasswordField.this.savePassword();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            CustomPasswordField.this.savePassword();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            CustomPasswordField.this.savePassword();
        }
    }

    public class PasswordMouseListener extends MouseAdapter {
        /**
         * {@inheritDoc}
         */
        public void mousePressed(MouseEvent e) {
            if(e.getComponent().equals(CustomPasswordField.this)) {
                CustomPasswordField.this.showPassword = !CustomPasswordField.this.showPassword;
                CustomPasswordField.this.setEchoChar(CustomPasswordField.this.showPassword?0:CustomPasswordField.this.defaultEchoChar);
            }
            CustomPasswordField.this.savePassword();
            CustomPasswordField.this.revalidate();
            CustomPasswordField.this.repaint();
        }
    }
}
