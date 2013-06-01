package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jakob Klepp
 * @since 2013-06-02
 */
public class PBDataSetInputPanel extends JPanel {
    private HashMap<String, JTextField> inputs;

    public PBDataSetInputPanel(){
        this(new HashSet<String>() {{
            this.add("Website");
            this.add("Username");
            this.add("Password");
            this.add("E-Mail");
        }});
    }

    public PBDataSetInputPanel(Set<String> inputNames){
        this.inputs = new HashMap<String, JTextField>();
        this.setLayout(new GridLayout(0, 2, 2, 2));
        for(String inputName : inputNames){
            JTextField textField;
            if(inputName.toLowerCase().startsWith("password") || inputName.toLowerCase().endsWith("password")){
                textField = new JPasswordField();
            } else {
                textField = new JTextField();
            }
            this.add(new JLabel(inputName + ": "));
            this.add(textField);
            this.inputs.put(inputName, textField);
        }
    }

    public HashMap<String, JTextField> getInputs() {
        return inputs;
    }
}
