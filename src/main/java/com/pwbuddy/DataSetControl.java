package com.pwbuddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-05-26
 */
public class DataSetControl implements ActionListener {
    private DataSet dataSet;

    public DataSetControl(DataSet dataSet){
        this.dataSet = dataSet;
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.dataSet.repaint();
    }
}
