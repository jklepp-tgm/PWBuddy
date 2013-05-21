package com.pwbuddy;

import java.io.File;
import java.util.Set;

/**
 * @author jakob
 * @version 2013-05-21
 */
public class TestModel extends PBModel {

    public TestModel(){
        super(new File("."));

        PBCategory category = new PBCategory();
        category.add(new PBDataSet());
        category.add(new PBDataSet());
        super.addCategory("Kategorie 01" ,category);
    }
}
