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
            	ligne = ligne.trim();
                //ignorer lignes vides et commentaires
                if (ligne.isEmpty() || ligne.startsWith("//"))
                    continue;
                
                if(!ligne.startsWith("/*"))
                    count++;

                //check si la ligne contient un commentaire sur plusieurs lignes
                if (ligne.contains("/*"))
                    while (!ligne.contains("*/"))
                        ligne = (buffer.readLine()).trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}