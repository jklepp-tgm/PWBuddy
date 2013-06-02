package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * ToDo vielleicht Felder dynamisch gestalten
 * @author Jakob Klepp
 * @since 2013-06-02
 */
public class DataSetInputPanel extends JPanel {
    private HashMap<JLabel, JTextField> inputs;

    public DataSetInputPanel(){
        this(new HashSet<String>() {{
            this.add("Website");
            this.add("Username");
            this.add("Password");
            this.add("E-Mail");
            this.add("Category");
        }});
    }

    public DataSetInputPanel(Set<String> inputNames){
        this.inputs = new HashMap<JLabel, JTextField>();
        this.setLayout(new GridLayout(0, 2, 2, 2));
        for(String inputName : inputNames){
            JLabel label = new JLabel(inputName + ": ");
            JTextField textField;
            if(inputName.toLowerCase().startsWith("password") || inputName.toLowerCase().endsWith("password")){
                textField = new JPasswordField();
            } else {
                textField = new JTextField();
            }
            this.add(label);
            this.add(textField);
            this.inputs.put(label, textField);
        }
    }

    public HashMap<JLabel, JTextField> getInputs() {
        return inputs;
    }
}
