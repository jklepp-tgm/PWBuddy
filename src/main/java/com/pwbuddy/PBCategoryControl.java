package com.pwbuddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-05-26
 */
public class PBCategoryControl implements ActionListener{
    private PBCategory category;

    public PBCategoryControl(PBCategory category){
        this.category = category;
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.category.getToggle().equals(e.getSource())){
            this.category.getModel().setOpened(this.category.getToggle().isSelected());
            System.out.println(this.category.getModel().getName());
            /*if(this.category.getToggle().isSelected()){
                this.category.getToggle().setText("-");
            } else {
                this.category.getToggle().setText("+");
            }*/
        }
    }
}
