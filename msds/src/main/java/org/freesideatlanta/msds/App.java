package org.freesideatlanta.msds;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
            ArrayList<String> chemicalList = new ArrayList<String>();    // List of all the chemicals. Line number is index + 1

            line = bufRead.readLine();
            chemicalList.add(line);
            lineCount++;

            while (line != null) {
                line = bufRead.readLine();
                chemicalList.add(line.trim());
                lineCount++;
            }

            bufRead.close();







            HttpClient client = new HttpClient();
            String URLhere = "http://www.commonchemistry.org/search.aspx";


            for (String a : chemicalList) {
                PostMethod amethod = new PostMethod(URLhere);
                amethod.addParameter("terms", a);



                int statusCode = client.executeMethod(amethod);

                if (statusCode != HttpStatus.SC_OK) {
                    System.err.println("Method failed: " + amethod.getStatusLine());
                }



                InputStream in = amethod.getResponseBodyAsStream();



                String name = a.replaceAll("\\s", "_") + ".txt";
                OutputStream out = new FileOutputStream(name);
                out = new BufferedOutputStream(out);
                byte[] buf = new byte[8192];
                int len = 0;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.close();
            }



        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ReadFile filename\n");


        } catch (IOException e) {
            System.out.println("error! u suck at this");
            e.printStackTrace();


        }
    }
}
