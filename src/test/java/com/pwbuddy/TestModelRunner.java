package com.pwbuddy;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * @author Jakob Klepp
 * @since 2013-05-21
 */
public class TestModelRunner {
    public static void main(String [] args){
        JFrame frame = new JFrame();
        PBModel m = null;
        try {
            m = new TestModel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        PBView v = new PBView(m);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(v);
        frame.setSize(650, 450);
        frame.setTitle("PWBuddy _-_ TestModelRunner");
        frame.setVisible(true);
    }
}
