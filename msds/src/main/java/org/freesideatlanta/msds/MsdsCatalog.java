package org.freesideatlanta.msds;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.io.*;

public class MsdsCatalog {

    HttpClient client;
    String URLhere = "http://hazard.com/msds/gn.cgi?query=";
    ArrayList<String> errorsHere;
    MSDS chemicalsMSDS;
    String body;
    String edittedBody;

    public MsdsCatalog() {
        client = new DefaultHttpClient(); // start the client
        //URLhere = "http://hazard.com/msds/gn.cgi?query="; // this is the generic part of the URL common to every site
        errorsHere = new ArrayList<String>();

    }

    public MSDS query(String chemical) {
        try {
            URLhere = "http://hazard.com/msds/gn.cgi?query=";
            chemicalsMSDS = new MSDS(chemical);
            System.out.println("Next chemical" + "\n" + "Chemical: " + chemical);
            URLhere = URLhere + chemical;
            HttpGet method = new HttpGet(URLhere);// inserts URL to the method
            System.out.println(URLhere);

            HttpResponse response = client.execute(method); // gets a response from teh URL
            HttpEntity entity = response.getEntity(); // creates an entity
            body = EntityUtils.toString(entity); // converts the entity to a string and ads it to the body

            boolean hasJtBakerDb = (body.indexOf("jtbaker.com") >= 0);
            boolean hasSafetyCard = (body.indexOf("mf/cards/file") >= 0);
            boolean hasFileCard = (body.indexOf("href=f") >= 0);

            if (hasJtBakerDb || hasSafetyCard || hasFileCard) {
                // affirmative case
                System.out.println("No Errors");
                chemicalsMSDS = getMSDS();
            } else {
                // negative case
                System.out.println("ERROR! THIS CHEMICAL IS NOT FOUND ON THE DATABASE!");
                System.out.println("THIS WILL BE ADDED TO THE ERROR LIST");
                errorsHere.add(chemical);
            }




        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");

        }
        return chemicalsMSDS;
    }

    private MSDS getMSDS() {
        boolean hasSafetyCard = (body.indexOf("mf/cards/file") >= 0);
        boolean hasFisher = (body.indexOf("fscim") >= 0);

        if (hasSafetyCard == true) {
            chemicalsMSDS = retrieveSafetyCard();
        } else if (hasFisher == true) {
            chemicalsMSDS = retrieveFisher();
        } else {
            chemicalsMSDS = retrieveMSDS();
        }


        return chemicalsMSDS;
    }

    private MSDS retrieveSafetyCard() {
        try {
            Document abc = Jsoup.connect(URLhere).get();
            Elements links = abc.select("a[href]");

            for (int i = 0; i < links.size(); i++) {
                boolean isSafetyCard = (links.get(i).html().indexOf("Safety Card") >= 0);
                if (isSafetyCard) {
                    String newURL = links.get(i).attr("abs:href");
                    HttpGet method = new HttpGet(newURL);
                    HttpResponse response = client.execute(method);
                    HttpEntity entity = response.getEntity();
                    String MSDSq = EntityUtils.toString(entity);
                    Document doc_one = Jsoup.parse(MSDSq);
                    MSDSq = doc_one.body().text();
                    chemicalsMSDS.changeText(MSDSq);
                    i = links.size() + 10;
                } else {
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");
        }
        return chemicalsMSDS;

    }

    private MSDS retrieveFisher() {
        try {
            Document abc = Jsoup.connect(URLhere).get();
            Elements links = abc.select("a[href]");
            for (int i = 0; i < links.size(); i ++) {
                boolean isFisher = (links.get(i).html().indexOf("Fisher ") >= 0);
                if (isFisher == true) {
                    String newURL = links.get(i).attr("abs:href");
                    HttpGet method = new HttpGet(newURL);
                    HttpResponse response = client.execute(method);
                    HttpEntity entity = response.getEntity();
                    String MSDSq = EntityUtils.toString(entity);
                    Document doc_one = Jsoup.parse(MSDSq);
                    MSDSq = doc_one.body().text();
                    chemicalsMSDS.changeText(MSDSq);
                    i = links.size() + 10;
                } else {
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");
        }
        return chemicalsMSDS;
    }

    private MSDS retrieveMSDS() {
        try {
            Document abc = Jsoup.connect(URLhere).get();
            Elements links = abc.select("a[href]");
            for (int i = 0; i < links.size(); i ++) {
                boolean isJtBaker = (links.get(i).html().indexOf("Mallinckrodt Baker ") >= 0);
                boolean isErrors = (links.get(i).attr("abs:href").indexOf("msds/errors.html") >= 0);
                boolean isSearch = (links.get(i).attr("abs:href").indexOf("msds/search.html") >= 0);
                boolean isArchive = (links.get(i).attr("abs:href").indexOf("msds/index.php") >= 0);
                if (isJtBaker == true || isErrors == true || isSearch == true || isArchive == true) {
                } else {
                    String newURL = links.get(i).attr("abs:href");
                    HttpGet method = new HttpGet(newURL);
                    HttpResponse response = client.execute(method);
                    HttpEntity entity = response.getEntity();
                    String MSDSq = EntityUtils.toString(entity);
                    Document doc_one = Jsoup.parse(MSDSq);
                    MSDSq = doc_one.body().text();
                    chemicalsMSDS.changeText(MSDSq);
                    i = links.size() + 10;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error! u suck at this ");

        }
        return chemicalsMSDS;
    }

    private String deleteString(int beginIndex, int endIndex, String a) {
        String toBeDeleted = a.substring(beginIndex, endIndex);
        return a.replace(toBeDeleted, "");


    }
}
