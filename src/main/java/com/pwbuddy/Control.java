package com.pwbuddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class Control implements ActionListener{
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
        if(e.getSource().equals(v.getAddCategoryButton())){
            m.addCategoryFromUserInput();
        } else
        if(e.getSource().equals(v.getAddDataSetButton())){
            m.addDataSetFromUserInput();
        }
        this.v.repaint();
    }
}
