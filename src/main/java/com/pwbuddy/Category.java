package com.pwbuddy;

import argo.jdom.JsonField;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-14
 */
public class Category extends JPanel implements Comparable<Category>{
    private CategoryModel categoryModel;
    private JToggleButton toggle;
    private CategoryControl categoryControl;
    private CategoryJsonNode categoryJsonNode;
    public Category(String name, CategoryJsonNode categoryJsonNode, EncryptionCore encryption) {
        this.categoryModel = new CategoryModel(this, name);
        this.categoryJsonNode = categoryJsonNode;

        //DataSets aus CategoryJsonNode auslesen
        for(JsonField field : this.categoryJsonNode.getFieldList()){
            String dataSetName = field.getName().getText();
            DataSetJsonNode dataSetJsonNode = new DataSetJsonNode(field.getValue(), encryption);

            DataSet dataSet = new DataSet(dataSetName, dataSetJsonNode);
            this.getModel().add(dataSet); //#addDataSet(DataSet) könnte Probleme machen
        }

        //Grafisches zeug
        this.setLayout(new BorderLayout());

        this.toggle = new JToggleButton(this.getModel().getName());
        this.toggle.setBackground(Color.LIGHT_GRAY);
        this.add(this.toggle, BorderLayout.CENTER);

        this.categoryControl = new CategoryControl(this);
        this.toggle.addActionListener(this.categoryControl);
    }

    public CategoryModel getModel() {
        return categoryModel;
    }

    public JToggleButton getToggle() {
        return toggle;
    }

    public String getName() {
        return this.getModel().getName();
    }

    public CategoryJsonNode getCategoryJsonNode() {
        return categoryJsonNode;
    }

    public boolean addDataSet(DataSet dataSet){
        return this.getModel().add(dataSet) && this.getCategoryJsonNode().addDataSetNode(dataSet.getName(), dataSet.getDataSetJsonNode());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Container contentView = SwingUtilities.getAncestorOfClass(ContentView.class, this); //etwas unschön
        if(contentView != null){
            contentView.revalidate();
            contentView.repaint();
        }
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p/>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p/>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p/>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p/>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p/>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Category o) {
        return this.getModel().compareTo(o.getModel());
    }
}
