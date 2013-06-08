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

    public Control(Model m, View v) {
        this.m = m;
        this.v = v;
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
        System.out.println(e);
        if(e.getSource() instanceof JComponent){
            this.v.setContentView((JComponent)e.getSource());
            this.v.repaint();
        }
    }
}
