/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freesideatlanta.msds;

/**
 *
 * @author Pranav Shenoy
 */
public class MSDS {
    String name;
    String MSDStext;
    public MSDS (String a) {
        name = a;
    }
    
    public String getText () {
        return MSDStext;
    }
    
    public void changeText(String a) {
        MSDStext = a;
    }
}
