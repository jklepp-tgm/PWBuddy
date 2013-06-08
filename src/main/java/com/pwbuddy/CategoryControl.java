package com.pwbuddy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jakob Klepp
 * @since 2013-05-26
 */
public class CategoryControl implements ActionListener{
    private Category category;

    public CategoryControl(Category category){
        this.category = category;
    }


    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
