/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freesideatlanta.msds;

import java.io.*;
/**
 *
 * @author Pranav Shenoy
 */
public class MsdsWriter {
    FileWriter fstream;
    BufferedWriter out;
    public MsdsWriter () {
        try {
            fstream = new FileWriter("output.txt");
            out = new BufferedWriter(fstream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void write(String chemical, String text) {
        try {
            out.append(chemical);
            out.newLine();
            out.append("__________");
            out.newLine();
            out.append(text);
            out.newLine();
            out.newLine();
            out.newLine();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void close() {
        try {
        out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
