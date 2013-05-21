package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-17
 */
public class PBDataSetView extends JPanel {
    private PBDataSet dataSet;
    public PBDataSetView(PBDataSet dataSet){
        this.dataSet = dataSet;
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawString(this.dataSet.getName(), 1, 1);
        System.out.println(this.dataSet.getName());
    }
}
