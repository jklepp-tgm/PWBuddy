package com.pwbuddy;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class View extends JPanel{
    private Model m;
    private Control c;
    private JButton addCategoryButton;

    public View(Model m){
        super(new GridLayout());
        this.m = m;

        JPanel categoriesPanel = new JPanel(new BorderLayout());

        JTree tree = new JTree();
        tree.addTreeSelectionListener(c);
        categoriesPanel.add(tree, BorderLayout.CENTER);

        this.addCategoryButton = new JButton("+ Kategorie");
        categoriesPanel.add(this.addCategoryButton, BorderLayout.SOUTH);

        JScrollPane treeScroll = new JScrollPane(categoriesPanel);
        treeScroll.setBackground(Color.CYAN);

        ContentView contentView = new ContentView(m);
        JScrollPane contentScroll = new JScrollPane(contentView);
        contentScroll.setBackground(Color.BLUE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, contentScroll);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        this.add(splitPane);
    }

    public void setControl(Control control) {
        this.c = control;
    }
}
