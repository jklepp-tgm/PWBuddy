package com.pwbuddy;

import javax.swing.*;
import java.io.*;
import java.util.Formatter;

/**
 * @author Jakob Klepp
 * @since 2013-04-09
 */
public class PBFrame extends JFrame {
    private PBModel m;
    private PBView v;
    private PBControl c;

    private PBMenuBar menu;
    public PBFrame(){
        this.m = new PBModel(getReader());
        this.v = new PBView(m);
        this.c = new PBControl();

        this.menu = new PBMenuBar();
        this.setJMenuBar(menu);

        this.setContentPane(v);
        this.setSize(650, 450);
        this.setTitle("PWBuddy");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    protected Reader getReader() {
        String filepath = System.getProperty("user.home") + "/.pwbuddy/passwords.json";
        FileReader fileReader = null;
        boolean ersterDurchlauf = true;
        while(fileReader == null){ //Wenn die Datei erst erstellt werden muss soll ein zweiter anlauf versucht werden
            File file = new File(filepath);
            if(file.isFile()){
                if(file.canRead() && file.canWrite()){
                    try {
                        fileReader = new FileReader(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //Kann nicht gelesen oder geschrieben werden
                    //Entsprechende Fehlermeldung ausgeben
                    if(!file.canRead()){
                        System.out.println("Datei: " + filepath + " kann nicht gelesen werden.");
                    }
                    if(!file.canWrite()){
                        System.out.println("Datei: " + filepath + " kann nicht geschrieben werden.");
                    }
                    //Program beenden
                    System.exit(1);
                }
            } else {
                try {
                    //Dateipfad erstellen
                    File path = file.getParentFile();
                    path.mkdirs();
                    //Datei erstellen
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (ersterDurchlauf) {
                    System.out.println("Datei: " + filepath + " existiert nicht. Wird erstellt.");
                } else {
                    System.exit(1);
                }
                ersterDurchlauf = false;
            }
        }

        return fileReader;
    }
}
