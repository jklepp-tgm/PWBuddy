package com.pwbuddy;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class Control implements ActionListener, TreeSelectionListener {
    Model m;
    View v;

    JComponent lastComp;

    public Control(Model m, View v) {
        this.m = m;
        this.v = v;
        this.lastComp = null;
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.v.repaint();
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if(this.lastComp instanceof Category) {
            Category category = (Category) lastComp;
            category.flush();
        } else
        if(this.lastComp instanceof DataSet) {
            DataSet dataSet = (DataSet) lastComp;
            dataSet.flush();
        }
        JTree source = (JTree)e.getSource();
        this.lastComp = (JComponent)source.getLastSelectedPathComponent();
        this.v.setContentView(this.lastComp);
        this.v.repaint();
        this.v.revalidate();
    }
}
