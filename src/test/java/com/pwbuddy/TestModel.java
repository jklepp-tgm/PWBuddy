package com.pwbuddy;

import java.io.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-21
 */
public class TestModel extends PBModel {

    public TestModel() throws FileNotFoundException {
        super(new FileReader(new File(System.getProperty("user.home") + "/.pwbuddy/testmodel/passwords.json")));
        PBCategory category;

        category = new PBCategory("Kategorie 01");
        category.getModel().add(new PBDataSet("Data 01.01"));
        category.getModel().add(new PBDataSet("Data 01.02"));
        category.getModel().setOpened(true);
        super.addCategory(category);

        category = new PBCategory("Kategorie 02");
        category.getModel().add(new PBDataSet("Data 02.01"));
        category.getModel().add(new PBDataSet("Data 02.02"));
        category.getModel().add(new PBDataSet("Data 02.03"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 03");
        category.getModel().add(new PBDataSet("Data 03.01"));
        category.getModel().add(new PBDataSet("Data 03.02"));
        category.getModel().setOpened(true);
        super.addCategory(category);

        category = new PBCategory("Kategorie 04");
        category.getModel().add(new PBDataSet("Data 04.01"));
        category.getModel().add(new PBDataSet("Data 04.02"));
        category.getModel().add(new PBDataSet("Data 04.03"));
        category.getModel().add(new PBDataSet("Data 04.04"));
        category.getModel().add(new PBDataSet("Data 04.05"));
        category.getModel().add(new PBDataSet("Data 04.06"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 05");
        super.addCategory(category);

        category = new PBCategory("Kategorie 06");
        category.getModel().add(new PBDataSet("Data 06.01"));
        category.getModel().add(new PBDataSet("Data 06.02"));
        category.getModel().add(new PBDataSet("Data 06.03"));
        category.getModel().add(new PBDataSet("Data 06.04"));
        category.getModel().setOpened(true);
        super.addCategory(category);

        category = new PBCategory("Kategorie 07");
        category.getModel().add(new PBDataSet("Data 07.01"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 08");
        category.getModel().add(new PBDataSet("Data 08.01"));
        category.getModel().add(new PBDataSet("Data 08.02"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 09");
        category.getModel().add(new PBDataSet("Data 09.01"));
        category.getModel().add(new PBDataSet("Data 09.02"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 10");
        category.getModel().add(new PBDataSet("Data 10.01"));
        category.getModel().add(new PBDataSet("Data 10.02"));
        category.getModel().add(new PBDataSet("Data 10.03"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 11");
        category.getModel().add(new PBDataSet("Data 11.01"));
        category.getModel().add(new PBDataSet("Data 11.02"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 12");
        category.getModel().add(new PBDataSet("Data 12.01"));
        category.getModel().add(new PBDataSet("Data 12.02"));
        category.getModel().add(new PBDataSet("Data 12.03"));
        category.getModel().add(new PBDataSet("Data 12.04"));
        category.getModel().add(new PBDataSet("Data 12.05"));
        category.getModel().add(new PBDataSet("Data 12.06"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 13");
        category.getModel().setOpened(true);
        super.addCategory(category);

        category = new PBCategory("Kategorie 14");
        category.getModel().add(new PBDataSet("Data 14.01"));
        category.getModel().add(new PBDataSet("Data 14.02"));
        category.getModel().add(new PBDataSet("Data 14.03"));
        category.getModel().add(new PBDataSet("Data 14.04"));
        super.addCategory(category);

        category = new PBCategory("Kategorie 15");
        category.getModel().add(new PBDataSet("Data 15.01"));
        category.getModel().setOpened(true);
        super.addCategory(category);

        category = new PBCategory("Kategorie 16");
        category.getModel().add(new PBDataSet("Data 16.01"));
        category.getModel().add(new PBDataSet("Data 16.02"));
        category.getModel().setOpened(true);
        super.addCategory(category);
    }
}
