package com.pwbuddy;

import javax.swing.*;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBView extends JScrollPane {
    private PBTree tree;
    public PBView(){
        this.tree = new PBTree();
        this.add(this.tree);
    }
}
