package com.pwbuddy;

import java.io.File;
import java.util.Set;

/**
 * @author jakob
 * @version 2013-05-21
 */
public class TestModel extends PBModel {
    //gekillt
    private TestModel(File pwFile) {
        super(pwFile);
    }

    public TestModel(){
        super(new File("."));
    }
}
