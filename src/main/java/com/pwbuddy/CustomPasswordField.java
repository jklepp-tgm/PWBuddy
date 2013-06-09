package com.pwbuddy;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Jakob Klepp
 * @since 2013-06-09
 */
public class CustomPasswordField extends JPasswordField {
    private PasswordJsonNode passwordJsonNode;

    public CustomPasswordField(PasswordJsonNode passwordJsonNode) {
        super(new String(passwordJsonNode.getPassword()));

        this.passwordJsonNode = passwordJsonNode;

        super.getDocument().addDocumentListener(new PasswordDocumentListener());
    }

    public class PasswordDocumentListener implements DocumentListener {

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            passwordJsonNode.setPassword(getPassword());
        }
    }
}
