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
        super(new GridLayout(1, 1));
        this.gridBag = new GridBagLayout();
        this.con = new GridBagConstraints();
        this.content = new JPanel(gridBag);
        this.content.setBackground(Color.CYAN);

        this.scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);

        this.con.insets = new Insets(1,1,1,1); //Abstand zwischen den Elementen, (oben, unten, links, rechts)
        this.con.gridx = 1;
        this.con.gridy = 1;
        this.con.gridwidth = this.content.getWidth();
        this.con.weightx = 100;

        Dimension d = new Dimension(this.content.getWidth(), 20);
        d = new Dimension(620, 100);
        System.out.println(d);
        for(int i=0; i<10; ++i){
            JPanel p= new JPanel();
            p.setPreferredSize(d);
            p.setBackground(new Color((int)(Math.random()*0xFFFFCC)));
            //JButton p = new JButton(Math.random()+"");
            this.con.gridy ++;
            this.content.add(p, con);
        }
    }


}
