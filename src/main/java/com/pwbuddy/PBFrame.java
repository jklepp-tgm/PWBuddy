package com.pwbuddy;

import javax.swing.*;
import java.io.File;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBFrame extends JFrame {
    private PBModel m;
    private PBView v;
    private PBControl c;

    private PBMenuBar menu;
    public PBFrame(){
        this.m = new PBModel(new File("./pwds"));
        this.v = new PBView(m);
        this.c = new PBControl();

        this.menu = new PBMenuBar();
        this.setJMenuBar(menu);

        this.pack();
        this.setVisible(true);

        this.setContentPane(v);
        this.setSize(650, 450);
        this.setTitle("PWBuddy");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
