package com.pwbuddy;

import argo.jdom.JsonField;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;

/**
 * ToDO boolean opened in json speichern und auslesen
 * @author Jakob Klepp
 * @author Andreas Willinger
 * @since 2013-05-14
 */
public class Category extends JPanel implements Comparable<Category>{
    public JTextField categoryName;
    public JTextField dataSetName;
    public JButton createDataSet;

    private PriorityQueue<DataSet> dataSets;
    private CategoryControl categoryControl;
    private CategoryJsonNode categoryJsonNode;

    private Model m;

    public Category(String name, CategoryJsonNode categoryJsonNode, Model m) {
        //GUI
        this.setLayout(new TableLayout(new double[][]{
                {TableLayout.FILL, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, TableLayout.FILL},
                {30, TableLayout.PREFERRED, 30, TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 30}
        }));

        this.add(new JLabel("Name: "), new TableLayoutConstraints(1, 1, 1, 1));
        this.categoryName = new JTextField();
        this.add(this.categoryName, new TableLayoutConstraints(3, 1, 3, 1));

        this.add(new JSeparator(), new TableLayoutConstraints(1, 2, 3, 2, TableLayoutConstants.CENTER, TableLayoutConstants.CENTER));

        this.add(new JLabel("Datensatz Name: "), new TableLayoutConstraints(1, 3, 1, 3));
        this.dataSetName = new JTextField();
        this.add(this.dataSetName, new TableLayoutConstraints(3, 3, 3, 3));

        this.createDataSet = new JButton("Datensatz erstellen");
        this.createDataSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Category.this.dataSetName.getText().equals("")){
                    PasswordJsonNode passwordJsonNode = new PasswordJsonNode(new char[0], Category.this.m.getEncryption());
                    DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(Category.this.dataSetName.getText(), "", "", "", passwordJsonNode, Category.this.m.getEncryption());
                    DataSet dataSet = new DataSet("", dataSetJsonNode, Category.this.m);
                    dataSet.categoryName.setSelectedItem(Category.this.toString());
                    Category.this.addDataSet(dataSet);
                    Container contentView = SwingUtilities.getAncestorOfClass(View.class, Category.this);
                    if(contentView != null){
                        View view = (View) contentView;
                        view.setContentView(dataSet);
                        contentView.revalidate();
                        contentView.repaint();
                    }
                    Category.this.dataSetName.setText("");
                }
            }
        });
        this.add(this.createDataSet, new TableLayoutConstraints(1, 5, 3, 5));

        this.categoryName.setText(name);
        this.m = m;
        this.categoryJsonNode = categoryJsonNode;
        this.dataSets = new PriorityQueue<DataSet>();

        //DataSets aus CategoryJsonNode auslesen
        for(JsonField field : this.categoryJsonNode.getFieldList()){
            String dataSetName = field.getName().getText();
            DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(field.getValue(), m.getEncryption());

            DataSet dataSet = new DataSet(dataSetName, dataSetJsonNode, m);
            this.dataSets.add(dataSet); //#addDataSet(DataSet) könnte Probleme machen
        }
    }

    public PriorityQueue<DataSet> getDataSets() {
        return dataSets;
    }

    public CategoryJsonNode getCategoryJsonNode() {
        return categoryJsonNode;
    }

    public boolean addDataSet(DataSet dataSet){
        return this.dataSets.add(dataSet) && this.getCategoryJsonNode().addDataSetNode(dataSet.toString(), dataSet.getDataSetJsonNode());
    }

    public void flush(){
        this.m.flush(); //Sollte den Namen auch auf anderen stellen updaten
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Category o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     * Für JTree
     * @return Name der Category
     */
    public String toString(){
        return this.categoryName.getText();
    }
}
