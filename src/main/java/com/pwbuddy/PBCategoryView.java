package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author jakob
 * @version 2013-05-14
 */
public class PBCategoryView extends JPanel {
    private PBCategory categorie;
    public PBCategoryView(PBCategory categorie) {
        this.setBackground(new Color((int)(Math.random()*0xFFFFFF)));


    }
    protected void paintComponent(Graphics g) {
        g.drawString(categorie.getName(), 1, 1);
        System.out.println(categorie.getName());
    }
}
