package org.freesideatlanta.msds;



import java.util.ArrayList;
import java.io.*;

public class ChemicalReader {

    String name;
    ArrayList<String> chemicalList = new ArrayList<String>(); // List of all the chemicals. Line number is index + 1
    String line; // String that holds current file line
    BufferedReader bufRead;
    public ChemicalReader(String filename) {
        name = filename;
    }

    public ArrayList<String> getChemicalNames() {
        try {
            FileReader input = new FileReader(name);
            bufRead = new BufferedReader(input);

            System.out.println("Reading starts now ....");
            System.out.println("___________________________________________________________________________________");
            System.out.println();

            line = bufRead.readLine(); // reads teh first line
            getAllChemicals();
            bufRead.close(); // closes the reader
            replaceBadCharacters();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");
        }
        return chemicalList;

    }

    private void replaceBadCharacters() {
// This interates through the arraylist and
// replaces every space with a +

        int size = chemicalList.size(); // variable for the size of the array list

        for (int i = 0; i < size; i++) { // one iteration for every index in teh arraylist
            String a = chemicalList.get(i).replaceAll(" ", "+"); // creates a new variable and replaces teh space with a +
            chemicalList.remove(i); // takes out hte old string at the index
            chemicalList.add(i, a); // inserts the new string
        }

    }

    private void getAllChemicals() {
// This reads each line of the txt document and
// puts each line into seperate index of an
// arraylist.
        try {
        while (line != null) { // while loop that iterates through every line until there isn't one
            System.out.println("chemical: " + line); // prints the chemical name
//            chemicalList.add(line.trim()); // adds it to the arraylist created above
            chemicalList.add(line);
            line = "";
            line = bufRead.readLine(); // next line
        }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");
        }
    }
}
