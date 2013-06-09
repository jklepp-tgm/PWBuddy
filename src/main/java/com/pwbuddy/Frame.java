package com.pwbuddy;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        this.setSize(420, 195);
        this.setTitle("PWBuddy");

        //Programm "sicher" beenden
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             */
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                m.flushAll();
                dispose();
            }
        });
        this.setVisible(true);
    }
}
