package org.freesideatlanta.msds;

//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.params.HttpMethodParams;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.params.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;

import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class App {

    public static void main(String[] args) {

        try {

			// TODO: Refactor into a (class) method
            FileReader input = new FileReader(args[0]);
            BufferedReader bufRead = new BufferedReader(input);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            ArrayList<String> chemicalList = new ArrayList<String>();    // List of all the chemicals. Line number is index + 1

            line = bufRead.readLine();

            while (line != null) {
				System.out.println("chemical: " + line);
                chemicalList.add(line.trim());
                lineCount++;
                line = bufRead.readLine();
            }

            bufRead.close();



            HttpClient client = new DefaultHttpClient();
            String URLhere = "http://www.commonchemistry.org/search.aspx";

            for (String a : chemicalList) {
                HttpPost method = new HttpPost(URLhere);
				HttpParams params = new BasicHttpParams();
				params.setParameter("terms", a);

                HttpResponse response = client.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				String body = EntityUtils.toString(entity);

				System.out.println("body: " + body);

				/*
				InputStream in = new ByteArrayInputStream(body.getBytes());

                String name = a.replaceAll("\\s", "_") + ".txt";
                OutputStream out = new FileOutputStream(name);
                out = new BufferedOutputStream(out);
                byte[] buf = new byte[8192];
                int len = 0;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.close();
				*/
            }

		} catch (HttpResponseException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStatusCode());
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ReadFile filename\n");
        } catch (IOException e) {
            System.out.println("error! u suck at this");
            e.printStackTrace();
        }
    }
}
