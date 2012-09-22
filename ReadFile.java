import java.util.ArrayList;
import java.io.*;

public class ReadFile {

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
//                System.out.println(lineCount + ": " + line);
                line = bufRead.readLine();
                chemicalList.add(line);
                lineCount++;
            }

            for (Object i : chemicalList) {
            System.out.println(i);
            }

            bufRead.close();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ReadFile filename\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
