package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class PBView extends JPanel{
    private PBModel m;


    private PBContentView content;
    private JScrollPane scrollPane;

    public PBView(PBModel m){
        super(new GridLayout(1, 1));
        this.m = m;

        this.content = new PBContentView(m);

        this.scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        content.repaint();
    }
}
