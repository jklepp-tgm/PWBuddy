package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-17
 */
public class DataSet extends JPanel implements Comparable <DataSet> {
    public JTextField name;
    public JTextField website;
    public JTextField username;
    public JTextField email;
    public JPasswordField password;
    public JComboBox<String> categoryName;
    private DataSetJsonNode dataSetJsonNode;

    public DataSet(String name, DataSetJsonNode dataSetJsonNode, Model m){
        String[] categoryNames = new String[m.getCategories().size()];
        int i = 0;
        for(Iterator<Category> iterator = m.getCategories().iterator(); iterator.hasNext();){
            categoryNames[i++] = iterator.next().toString();
        }

        this.dataSetJsonNode = dataSetJsonNode;

        this.setLayout(new GridLayout(0, 2, 2, 2));

        this.add(new JLabel("Name: "));
        this.name = new JTextField(dataSetJsonNode.name);
        this.add(this.name);

        this.add(new JLabel("Website: "));
        this.website = new JTextField(dataSetJsonNode.website);
        this.add(this.website);

        this.add(new JLabel("Username: "));
        this.username = new JTextField(dataSetJsonNode.username);
        this.add(this.username);

        this.add(new JLabel("E-Mail: "));
        this.email = new JTextField(dataSetJsonNode.email);
        this.add(this.email);

        this.add(new JLabel("Passwort: "));
        this.password = new CustomPasswordField(this.dataSetJsonNode.password);
        this.add(this.password);

        this.add(new JLabel("Kategorie"));
        this.categoryName = new JComboBox<String>(categoryNames);
        this.add(this.categoryName);
    }

    public DataSetJsonNode getDataSetJsonNode() {
        return dataSetJsonNode;
    }

    public void flush(){
        this.dataSetJsonNode.name = this.name.getText();
        this.dataSetJsonNode.website = this.website.getText();
        this.dataSetJsonNode.username = this.username.getText();
        this.dataSetJsonNode.email = this.email.getText();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(DataSet o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     * Für JTree
     * @return Name des DataSet
     */
    public String toString(){
        return this.name.getText();
    }
}
