package org.freesideatlanta.msds;

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


	    // Start

	    System.out.println("Starting ....");
	    System.out.println();
	    System.out.println();
	    System.out.println("___________________________________________________________________________________");
	    System.out.println();
	    System.out.println();
	    System.out.println();




	    // This reads each line of the txt document and
	    // puts each line into seperate index of an 
	    // arraylist.

            String line; 								// String that holds current file line
            int lineCount = 0;								// Line number of count
            ArrayList<String> chemicalList = new ArrayList<String>();    		// List of all the chemicals. Line number is index + 1

            line = bufRead.readLine();							// reads teh first line

            while (line != null) {							// while loop that iterates through every line until there isn't one
                System.out.println("chemical: " + line);				// prints the chemical name
                chemicalList.add(line.trim());						// adds it to the arraylist created above
                lineCount++;								// interates the line count although it is a useless and unneeded variable
                line = bufRead.readLine();						// next line
            }

            bufRead.close();								// closes the reader







	    // This interates through the arraylist and
	    // replaces every space with a +

            int size = chemicalList.size();						// variable for the size of the array list
	    for (int i = 0; i < size; i ++) {						// one iteration for every index in teh arraylist
                String a = chemicalList.get(i).replace(" ", "+");			// creates a new variable and replaces teh space with a +
                chemicalList.remove(i);							// takes out hte old string at the index
                chemicalList.add(i, a);							// inserts the new string
            }





	    // this was my verification taht the above program worked
	    // simply prints all the new values for the arraylist

	    for (String a : chemicalList) {
		System.out.println(a);
	    }







	    // This is where the program actually accesses the site with the chemical data sheets

            HttpClient client = new DefaultHttpClient();				// start the client
            String URLhere = "http://hazard.com/msds/gn.cgi?query=";			// this is the generic part of the URL common to every site


	    ArrayList<String> errorsHere = new ArrayList<String>();


            for (String a : chemicalList) {						// iterate through every chemmical


		// Starting a new chemical

		System.out.println();
		System.out.println();
		System.out.println("next:");
		System.out.println();
		System.out.print("Chemical: ");
		System.out.println(a);
		System.out.println();
		System.out.println();
		System.out.println("_______________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println();


		String thisIsTheNewURL = URLhere + a;					// makes a new string with the actual URL
                HttpGet method = new HttpGet(thisIsTheNewURL);				// inserts URL to the method


                HttpResponse response = client.execute(method);				// gets a response from teh URL
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();				// creates an entity
                String body = EntityUtils.toString(entity);				// converts the entity to a string and ads it to the body

                System.out.println("body: " + body);					// prints all of the body


		int indexOfResults_one = body.indexOf("href=h");
		int indexOfResults_two = body.indexOf("href=f");
		int indexOfResults_three = body.indexOf("href=m");

		if (indexOfResults_one == -1 || indexOfResults_two == -1 || indexOfResults_three == -1) {
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("ERROR! THIS CHEMICAL IS NOT FOUND ON THE DATABASE!");
			System.out.println();
			System.out.println("THIS WILL BE ADDED TO THE ERROR LIST");
			System.out.println();
			System.out.println();

			errorsHere.add(a);
			
		} else {

			System.out.println("No Errors");

		}




            }



	    if (errorsHere.size() == 0) {

		System.out.println();
		System.out.println("CONGRATS! No errors!");
		System.out.println("kthxbai");

	    } else {

		System.out.println();
		System.out.println();
		System.out.println("Here are all the errors: ");
		for (String error : errorsHere) {
			System.out.println(error);
		}
		System.out.println("\n \n kthxbai");

	    }








	// all the exceptions!

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
