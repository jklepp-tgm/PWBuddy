package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author jakob
 * @version 2013-04-09
 */
public class PBView extends JPanel{
    private GridBagLayout gridBag;
    private GridBagConstraints con;
    private JPanel content;
    private JScrollPane scrollPane;
    public PBView(){
        super(new GridLayout(1,1));
        this.setBackground(Color.MAGENTA);
        this.gridBag = new GridBagLayout();
        this.con = new GridBagConstraints();
        this.content = new JPanel(gridBag);
        this.scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setBackground(Color.CYAN);
        this.add(scrollPane);

        this.con.gridx = 1;
        this.con.gridy = 1;
        for(int i=0; i<10; ++i){
            JPanel p= new JPanel();
            p.setBackground(new Color((int)Math.random()*0xFFFFFF));
            p.setSize(this.content.getWidth(), 20);
            this.gridBag.setConstraints(p, con);
            //this.content.add(p);
        }
    }


}
