package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-14
 */
public class PBCategoryView extends JPanel {
    private PBCategory category;
    public PBCategoryView(PBCategory category) {
        this.setBackground(new Color((int)(Math.random()*0xFFFFFF)));
        this.category = category;

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawString(this.category.getName(), 1, 1);
        System.out.println(this.category.getName());
    }
}
