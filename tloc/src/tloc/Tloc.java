package tloc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tloc {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Veuillez inscrire en parametres la source du programme");
            return;
        }

        String path = args[0];
        System.out.println("TLOC: " + tloc(path));
    }

    private static int tloc(String path) {
        int count = 0;

        try(BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            String ligne;

            while ((ligne = buffer.readLine()) != null) 
            {
                //ignorer lignes vides et commentaires
                if (ligne.trim().isEmpty() || ligne.trim().startsWith("//") || ligne.trim().startsWith("/*"))
                    continue;

                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}