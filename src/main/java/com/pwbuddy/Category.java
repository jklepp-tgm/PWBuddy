package com.pwbuddy;

import argo.jdom.JsonField;

import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

/**
 * @author Jakob Klepp
 * @since 2013-05-14
 */
public class Category extends CategoryIOPanel implements Comparable<Category>{
    private PriorityQueue<DataSet> dataSets;
    private CategoryControl categoryControl;
    private CategoryJsonNode categoryJsonNode;
    private String name;

    public Category(String name, CategoryJsonNode categoryJsonNode, Model m) {
        super(m);
        this.name = name;
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

    public String getName() {
        return this.name;
    }

    public CategoryJsonNode getCategoryJsonNode() {
        return categoryJsonNode;
    }

    public boolean addDataSet(DataSet dataSet){
        return this.dataSets.add(dataSet) && this.getCategoryJsonNode().addDataSetNode(dataSet.getName(), dataSet.getDataSetJsonNode());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        return this.name.compareTo(o.getName());
    }
}
