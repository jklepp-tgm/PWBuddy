package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-22
 */
public class PBContentView extends JPanel {
    private PBModel m;

    public PBContentView(PBModel m){
        this.m = m;
        this.setLayout(new PBRowLayout());
        this.setBackground(Color.CYAN);

    }

    public PBModel getModel() {
        return m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.removeAll();

        //Über alle categories iterieren und sie dem scrollPane hinzufügen
        for(Iterator<PBCategory> categoryIterator = m.iterator(); categoryIterator.hasNext();){
            PBCategory category = categoryIterator.next();

            //category zum scrollPane hinzufügen
            this.add(category);

            //Wenn eine categorie geöffnet ist soll auch ihr Inhalt gezeichnet werden
            if(category.getModel().isOpened()){
                for(Iterator<PBDataSet> dataSetIterator = category.getModel().iterator(); dataSetIterator.hasNext();){
                    PBDataSet dataSet = dataSetIterator.next();

                    //zum scrollPane hinzufügen
                    this.add(dataSet);
                }
            }
            if(categoryIterator.hasNext()){
                JSeparator separator = new JSeparator();
                separator.setPreferredSize(new Dimension(this.getSize().width, 3));
                this.add(separator);
            }
        }
        this.validate();
    }
}
