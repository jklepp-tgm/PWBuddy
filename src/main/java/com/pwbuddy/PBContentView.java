package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-22
 */
public class PBContentView extends JPanel {
    //private GridBagLayout gridBag;
    private PBModel m;

    public PBContentView(PBModel m){
        //this.gridBag = new GridBagLayout();
        this.m = m;
        //this.setLayout(this.gridBag);
        this.setLayout(new PBRowLayout());
        this.setBackground(Color.CYAN);

    }

    public PBModel getModel() {
        return m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Blub");
        int width = this.getWidth();

        this.removeAll();
        /*
        GridBagConstraints con = new GridBagConstraints();
        con.insets = new Insets(1,1,1,1); //Abstand zwischen den Elementen, (oben, unten, links, rechts)
        con.gridx = 0;
        con.gridy = 0;
                        */
        //Über alle categories iterieren und sie dem scrollPane hinzufügen
        for(Iterator<PBCategory> categoryIterator = m.iterator(); categoryIterator.hasNext();){
            PBCategory category = categoryIterator.next();

            //category zum scrollPane hinzufügen
            //this.add(category.getView(), con);
            this.add(category.getView());
            //con.gridy ++;

            //Wenn eine categorie geöffnet ist soll auch ihr Inhalt gezeichnet werden
            if(category.isOpened()){
                for(Iterator<PBDataSet> dataSetIterator = category.iterator(); dataSetIterator.hasNext();){
                    PBDataSet dataSet = dataSetIterator.next();

                    //zum scrollPane hinzufügen
                    //this.add(dataSet.getView(), con);
                    this.add(dataSet.getView());

                    //con.gridy += dataSet.getGridHeight();
                }
            }
        }
        this.validate();
    }
}
