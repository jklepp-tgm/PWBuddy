package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-06-02
 */
public class DataSetIOPanel extends JPanel {
    public JTextField website;
    public JTextField username;
    public JTextField email;
    public JPasswordField password;
    public JComboBox<String> categoryName;

    public DataSetIOPanel(Model m){
        String[] categoryNames = new String[m.getCategories().size()];
        int i = 0;
        for(Iterator <Category> iterator = m.getCategories().iterator(); iterator.hasNext();){
            categoryNames[i++] = iterator.next().getName();
        }

        this.setLayout(new GridLayout(0, 2, 2, 2));

        this.add(new JLabel("Website: "));
        this.website = new JTextField();
        this.add(website);

        this.add(new JLabel("Username: "));
        this.username = new JTextField();
        this.add((username));

        this.add(new JLabel("E-Mail: "));
        this.email = new JTextField();
        this.add(email);

        this.add(new JLabel("Passwort: "));
        this.password = new JPasswordField();
        this.add(password);

        this.add(new JLabel("Kategorie"));
        this.categoryName = new JComboBox<String>(categoryNames);
        this.add(categoryName);
    }
}
