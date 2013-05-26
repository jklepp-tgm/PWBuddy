package com.pwbuddy;

import java.awt.*;
import java.util.HashMap;

/**
 * @author Jakob Klepp
 * @since 2013-05-23
 */
public class PBRowLayout implements LayoutManager {
    /**
     * @see #addLayoutComponent(String, java.awt.Component)
     */
    private HashMap <Component, String> compNameMap;

    public PBRowLayout(){
        this.compNameMap = new HashMap<Component, String>();
    }

    /**
     * If the layout manager uses a per-component string,
     * adds the component <code>comp</code> to the layout,
     * associating it
     * with the string specified by <code>name</code>.
     *
     * @see java.awt.Container#add(java.awt.Component, Object)
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        this.compNameMap.put(comp, name); //ToDo Überlegen ob das hier wirklich sinnvoll ist, gleiches @removeLayoutComponent
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        this.compNameMap.remove(comp);
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param target the container to be laid out
     * @see #minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(Container target) {
        int height, width;

        width = 0;

        height = 0;
        for(Component component : target.getComponents()){
            height += component.getPreferredSize().height;

            //Bevorzugt ist die prferredSize des targets, wenn die minimumSize einer der componenten aber größer ist
            // wird diese als Breite genommen.
            if(component.getPreferredSize().width > width){
                width = component.getPreferredSize().width;
            }
        }

        return new Dimension(width, height);
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param target the component to be laid out
     * @see #preferredLayoutSize
     */
    @Override
    public Dimension minimumLayoutSize(Container target) {
        int height, width;

        width = 0;

        height = 0;
        for(Component component : target.getComponents()){
            height += component.getMinimumSize().height;

            if(component.getMinimumSize().width > width){
                width = component.getMinimumSize().width;
            }
        }

        return new Dimension(width, height);
    }

    /**
     * Lays out the specified container.
     *
     * @param target the container to be laid out
     */
    @Override
    public void layoutContainer(Container target) {
        int yPos = 0;
        Dimension dimension = this.preferredLayoutSize(target);
        System.out.println(dimension);
        for (Component component : target.getComponents()){
            component.setBounds(0, yPos, dimension.width, dimension.height);
            yPos += component.getPreferredSize().height;
        }
    }
}
