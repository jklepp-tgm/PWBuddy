package com.pwbuddy;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author Jakob Klepp
 * @since 2013-05-17
 */
public class PBDataSet extends JPanel implements Comparable <PBDataSet>, IPBObservable<PBDataSet>{
    private PBDataSetModel dataSetModel;
    private JToggleButton toggle;
    private PBDataSetControl dataSetControl;
    private JLabel label;

    private HashSet <IPBObserver<PBDataSet>> observers;
    public PBDataSet(String name){
        this.dataSetModel = new PBDataSetModel(this, name);
        this.setLayout(new BorderLayout());

        this.label = new JLabel(".");
        this.add(label, BorderLayout.SOUTH);
        this.label.setPreferredSize(new Dimension(1,1));

        this.toggle = new JToggleButton(this.getModel().getName());
        this.add(toggle, BorderLayout.CENTER);

        this.dataSetControl = new PBDataSetControl(this);
        this.toggle.addActionListener(this.dataSetControl);

        this.observers = new HashSet<IPBObserver<PBDataSet>>();
    }

    public PBDataSetModel getModel() {
        return dataSetModel;
    }

    public JToggleButton getToggle() {
        return toggle;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Container contentView = SwingUtilities.getAncestorOfClass(PBContentView.class, this); //etwas unschön
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
    public int compareTo(PBDataSet o) {
        return this.getModel().compareTo(o.getModel());
    }

    /**
     * Einen Observer hinzufügen
     *
     * @param observer welcher hinzugefügt werden soll
     */
    @Override
    public void addPBObserver(IPBObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Einen Observer entfernen
     *
     * @param observer welcher entfernt werden soll
     */
    @Override
    public void removePBObserver(IPBObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Alle Observer über ein Ereignis benachrichtigen
     *
     * @param eventType
     */
    @Override
    public void notifyPBObservers(IPBObserver.PBEventType eventType) {
        for(IPBObserver<PBDataSet> observer : this.observers){
            observer.eventOcurred(this, eventType);
        }
    }
}
