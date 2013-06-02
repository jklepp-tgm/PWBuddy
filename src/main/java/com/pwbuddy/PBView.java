package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class PBView extends JPanel{
    private PBModel m;

    //Enhält Categories und DataSets
    private PBContentView content;

    //Macht content scrollbar
    private JScrollPane scrollPane;

    //Bietet Bedienelemente zum hinzufügen und entfernen von Categories und Elementen
    private JPanel addElementPanel;
    private JButton addCategoryButton;
    private JButton addDataSetButton;

    public PBView(PBModel m){
        super(new BorderLayout());
        this.m = m;

        this.content = new PBContentView(m);

        this.addElementPanel = new JPanel(new GridLayout(1, 3));
        this.addCategoryButton = new JButton("Kategorie");
        this.addElementPanel.add(this.addCategoryButton);
        final Image imageObject = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/org/freedesktop/tango/22x22/actions/document-new.png"));
        //Bild zeichnen und zum addElementPanel hinzufügen
        this.addElementPanel.add(new JPanel(){
            @Override
        protected void paintComponent(Graphics g){
                g.drawImage(imageObject, 0, 0, null);
            }
        });
        this.addDataSetButton = new JButton("Datensatz");
        this.addElementPanel.add(addDataSetButton);

        this.add(this.addElementPanel, BorderLayout.SOUTH);

        this.scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
