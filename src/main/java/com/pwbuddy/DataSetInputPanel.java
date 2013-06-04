package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * ToDo vielleicht Felder dynamisch gestalten
 * @author Jakob Klepp
 * @since 2013-06-02
 */
public class DataSetInputPanel extends JPanel {
    public JTextField websiteF;
    public JTextField usernameF;
    public JTextField emailF;
    public JPasswordField passwordF;
    public JComboBox<String> categoryF;

    public DataSetInputPanel(String [] categoryNames){
        this.setLayout(new GridLayout(0, 2, 2, 2));

        this.add(new JLabel("Website: "));
        this.websiteF = new JTextField();
        this.add(websiteF);

        this.add(new JLabel("Username: "));
        this.usernameF = new JTextField();
        this.add((usernameF));

        this.add(new JLabel("E-Mail: "));
        this.emailF = new JTextField();
        this.add(emailF);

        this.add(new JLabel("Passwort: "));
        this.passwordF = new JPasswordField();
        this.add(passwordF);

        this.add(new JLabel("Kategorie"));
        this.categoryF = new JComboBox<String>(categoryNames);
        this.add(categoryF);
    }
}
