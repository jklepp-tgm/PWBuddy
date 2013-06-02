package com.pwbuddy;

import javax.swing.*;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class Frame extends JFrame {
    private Model m;
    private View v;
    private Control c;

    private MenuBar menu;
    public Frame(){
        this.m = new Model();
        this.v = new View(m);
        this.c = new Control(m, v);

        this.v.setControl(c);

        this.menu = new MenuBar();
        this.setJMenuBar(menu);

        this.setContentPane(v);
        this.setSize(650, 450);
        this.setTitle("PWBuddy");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
