package com.pwbuddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-05-26
 */
public class PBDataSetControl implements ActionListener {
    private PBDataSet dataSet;

    public PBDataSetControl(PBDataSet dataSet){
        this.dataSet = dataSet;
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.dataSet.getToggle().equals(e.getSource())){
            this.dataSet.getModel().setOpened(this.dataSet.getToggle().isSelected());
            if(this.dataSet.getToggle().isSelected()){
                this.dataSet.getToggle().setText("-");
            } else {
                this.dataSet.getToggle().setText("+");
            }
        }
        this.dataSet.repaint();
    }
}
