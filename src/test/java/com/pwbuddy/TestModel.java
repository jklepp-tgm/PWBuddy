package com.pwbuddy;

import java.io.File;

/**
 * @author Jakob Klepp
 * @since 2013-05-21
 */
public class TestModel extends PBModel {

    public TestModel(){
        super(new File("."));

        PBCategory category = new PBCategory("Kategorie 01");
        category.add(new PBDataSet("Data 01.01"));
        category.add(new PBDataSet("Data 01.02"));
        super.addCategory(category);
    }
}
