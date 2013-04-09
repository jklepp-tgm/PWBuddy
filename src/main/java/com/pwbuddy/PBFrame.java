package com.pwbuddy;

import javax.swing.*;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBFrame extends JFrame {
    private PBModel m;
    private PBView v;
    private PBControl c;
    public PBFrame(){
        this.m = new PBModel();
        this.v = new PBView();
        this.c = new PBControl();

        this.setContentPane(v);
        this.setSize(650, 450);
        this.setVisible(true);
    }
}
