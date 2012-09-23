package org.freesideatlanta.msds;

import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class App {

    public static void main(String[] args) {

        try {

            FileReader input = new FileReader(args[0]);
            BufferedReader bufRead = new BufferedReader(input);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            ArrayList chemicalList = new ArrayList();    // List of all the chemicals. Line number is index + 1

            line = bufRead.readLine();
            chemicalList.add(line);
            lineCount++;

            while (line != null) {
                line = bufRead.readLine();
                chemicalList.add(line);
                lineCount++;
            }

            bufRead.close();




            URL url = new URL("http://google.com");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();



            // SEARCH CHEMICAL




            InputStream in = httpCon.getInputStream();
            OutputStream out = new FileOutputStream("file.txt");
            out = new BufferedOutputStream(out);
            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();




        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ReadFile filename\n");


        } catch (IOException e) {
            System.out.println("error! u suck at this");
            e.printStackTrace();


        }
    }
}
