package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-22
 */
public class ContentView extends JPanel {
    private Model m;

    public ContentView(Model m){
        this.m = m;
        this.setLayout(new RowLayout());
    }

    public Model getModel() {
        return m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.removeAll();

        //Über alle categories iterieren und sie dem scrollPane hinzufügen
        for(Iterator<Category> categoryIterator = m.iterator(); categoryIterator.hasNext();){
            Category category = categoryIterator.next();

            //category zum scrollPane hinzufügen
            this.add(category);

            //Wenn eine categorie geöffnet ist soll auch ihr Inhalt gezeichnet werden
            if(category.getModel().isOpened()){
                for (DataSet dataSet : category.getModel()) {
                    //zum scrollPane hinzufügen
                    this.add(dataSet);
                }
            }
            if(categoryIterator.hasNext()){
                JSeparator separator = new JSeparator();
                separator.setPreferredSize(new Dimension(this.getSize().width, 6));
                this.add(separator);
            }
        }
        this.validate();
    }
}
