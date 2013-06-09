package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Jakob Klepp
 * @since 2013-05-17
 */
public class DataSet extends JPanel implements Comparable <DataSet> {
    public JTextField name;
    public JTextField website;
    public JTextField username;
    public JTextField email;
    public JPasswordField password;
    public JComboBox<String> categoryName;
    private DataSetJsonNode dataSetJsonNode;

    public DataSet(String name, DataSetJsonNode dataSetJsonNode, Model m){
        String[] categoryNames = new String[m.getCategories().size()];
        int i = 0;
        for(Iterator<Category> iterator = m.getCategories().iterator(); iterator.hasNext();){
            categoryNames[i++] = iterator.next().toString();
        }

        this.dataSetJsonNode = dataSetJsonNode;

        this.setLayout(new GridLayout(0, 2, 2, 2));

        this.add(new JLabel("Name: "));
        this.name = new JTextField(dataSetJsonNode.name);
        this.add(this.name);

        this.add(new JLabel("Website: "));
        this.website = new JTextField(dataSetJsonNode.website);
        this.add(this.website);

        this.add(new JLabel("Username: "));
        this.username = new JTextField(dataSetJsonNode.username);
        this.add(this.username);

        this.add(new JLabel("E-Mail: "));
        this.email = new JTextField(dataSetJsonNode.email);
        this.add(this.email);

        this.add(new JLabel("Passwort: "));
        this.password = new JPasswordField(); //ToDo aus PasswordJsonNode setzten
        this.add(this.password);

        this.add(new JLabel("Kategorie"));
        this.categoryName = new JComboBox<String>(categoryNames);
        this.add(this.categoryName);
    }

    public DataSetJsonNode getDataSetJsonNode() {
        return dataSetJsonNode;
    }

    public void flush(){
        this.dataSetJsonNode.name = this.name.getText();
        this.dataSetJsonNode.website = this.website.getText();
        this.dataSetJsonNode.username = this.username.getText();
        this.dataSetJsonNode.email = this.email.getText();
        //ToDo this.password in PasswordJsonNode speichern
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
        return this.toString().compareTo(o.toString());
    }

    /**
     * Für JTree
     * @return Name des DataSet
     */
    public String toString(){
        return this.name.getText();
    }
}
