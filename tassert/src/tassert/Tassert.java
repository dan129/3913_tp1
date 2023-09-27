package tassert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tassert {

    public static void main(String[] args) {
        if (args.length != 1)
        {
            System.out.println("Veuillez inscrire en parametres la source du programme");
            return;
        }

        String path = args[0];
        System.out.println("TASSERT: " + tassert(path));
    }

    private static int tassert(String path) {
        int count = 0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            String line;
            
            while ((line = buffer.readLine()) != null) {
            	String[] fragments1 = line.split("assert(.*\\()");
            	String[] fragments2 = line.split("fail\\(");
                count += fragments1.length-1 + fragments2.length-1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}