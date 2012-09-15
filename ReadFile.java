
import java.io.*;

public class ReadFile {

    public static void main(String[] args) {
        try {

            FileReader input = new FileReader(args[0]);
            BufferedReader bufRead = new BufferedReader(input);

            String line; 	// String that holds current file line
            int count = 0;	// Line number of count 

            line = bufRead.readLine();
            count++;

            while (line != null) {
                System.out.println(count + ": " + line);
                line = bufRead.readLine();
                count++;
            }

            bufRead.close();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ReadFile filename\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
