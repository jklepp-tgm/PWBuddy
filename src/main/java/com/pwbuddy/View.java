package com.pwbuddy;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @author Andreas Willinger
 * @since 2013-04-09
 */
public class View extends JPanel{
    private Model m;
    private Control c;
    private JPanel contentView;
    private JButton addCategoryButton;
    private JTree tree;

    public View(Model m){
        super(new GridLayout());
        this.m = m;

        JPanel categoriesPanel = new JPanel(new BorderLayout());

        tree = new JTree();
        tree.setModel(m);
        tree.addTreeSelectionListener(c);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.addCategoryButton = new JButton("+ Kategorie");
        categoriesPanel.add(this.addCategoryButton, BorderLayout.SOUTH);

        JScrollPane treeScroll = new JScrollPane(tree);
        categoriesPanel.add(treeScroll, BorderLayout.CENTER);

        this.contentView = new JPanel(new GridLayout(1,1));
        JScrollPane contentScroll = new JScrollPane(contentView);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoriesPanel, contentScroll);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        this.add(splitPane);
    }

    public void setControl(Control control) {
        this.tree.removeTreeSelectionListener(this.c);
        this.addCategoryButton.removeActionListener(this.c);
        this.c = control;
        this.tree.addTreeSelectionListener(this.c);
        this.addCategoryButton.addActionListener(this.c);
    }

    public void setContentView(JComponent panel){
        this.contentView.removeAll();
        this.contentView.add(panel);
    }

    public JButton getAddCategoryButton() {
        return addCategoryButton;
    }

    public JTree getTree() {
        return tree;
    }

    @Override
    protected void paintComponent(Graphics g){

    }
}
