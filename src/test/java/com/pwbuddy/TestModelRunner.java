package com.pwbuddy;

import javax.swing.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-21
 */
public class TestModelRunner {
    public static void main(String [] args){
        JFrame frame = new JFrame();
        PBModel m = new TestModel();
        PBView v = new PBView(m);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(v);
        frame.setSize(650, 450);
        frame.setTitle("PWBuddy _-_ TestModelRunner");
        frame.setVisible(true);
        System.out.println("_-_1_-_");
    }
}
