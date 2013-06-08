package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-17
 */
public class DataSet extends JPanel implements Comparable <DataSet> {
    public JTextField website;
    public JTextField username;
    public JTextField email;
    public JPasswordField password;
    public JComboBox<String> categoryName;

    private DataSetControl dataSetControl;
    private DataSetJsonNode dataSetJsonNode;
    private String name;

    public DataSet(String name, DataSetJsonNode dataSetJsonNode, Model m){
        String[] categoryNames = new String[m.getCategories().size()];
        int i = 0;
        for(Iterator<Category> iterator = m.getCategories().iterator(); iterator.hasNext();){
            categoryNames[i++] = iterator.next().getName();
        }

        this.setLayout(new GridLayout(0, 2, 2, 2));

        this.add(new JLabel("Website: "));
        this.website = new JTextField();
        this.add(website);

        this.add(new JLabel("Username: "));
        this.username = new JTextField();
        this.add((username));

        this.add(new JLabel("E-Mail: "));
        this.email = new JTextField();
        this.add(email);

        this.add(new JLabel("Passwort: "));
        this.password = new JPasswordField();
        this.add(password);

        this.add(new JLabel("Kategorie"));
        this.categoryName = new JComboBox<String>(categoryNames);
        this.add(categoryName);

        this.name = name;

        this.dataSetJsonNode = dataSetJsonNode;
    }

    public String getName(){
        return this.name;
    }

    public DataSetJsonNode getDataSetJsonNode() {
        return dataSetJsonNode;
    }

    @Override
    protected void paintComponent(Graphics g){
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
    public int compareTo(DataSet o) {
        return this.getName().compareTo(o.getName());
    }
}
