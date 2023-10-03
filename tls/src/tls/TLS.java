import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class TLS {
	
	// clsses utiliser pour stocker les infos sur les fichiers
	public static class ProprieteFichier{
		private String cheminDuFichier;
		private String nomDuPaquet;
		private String nomDeLaClasse;
		private int tloc;
		private int tassert;
		private double tcmp;
		
		public ProprieteFichier(String cheminDuFichier, String nomDuPaquet, String nomDeLaClasse, int tloc, int tassert, double tcmp ) {
			this.cheminDuFichier = cheminDuFichier;
			this.nomDuPaquet = nomDuPaquet;
			this.nomDeLaClasse = nomDeLaClasse;
			this.tloc= tloc;
			this.tassert = tassert;
			this.tcmp = tcmp;			
		}
		
		public String getCheminDuFichier() {
			return cheminDuFichier;
		}
		
		public String getNomDuPaquet() {
			return nomDuPaquet;
		}
		
		public String getnomDeLaClasse() {
			return nomDeLaClasse;
		}
		
		public int getTloc() {
			return tloc;
		}
		
		public int getTassert() {
			return tassert;
		}
		
		public double getTcmp() {
			return tcmp;
		}			
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length < 1 || args.length == 2 || args.length > 3) {
			System.err.println("Mauvais nbr arguments. Veuillez soit passez un"
					+ " argument (le chemin d'accès d'un dossier qui contient"
					+ " du code test java) /n ou trois arguments (tls -o <chemin-à-la"
					+ "-sortie.csv> <chemin-de-l'entrée>)");
		}
		
		List<ProprieteFichier> liste = new ArrayList<>();
		
		
		// si argument utilise verifier que c'est le chemin d'acces d'un dossier 
		if(args.length == 1) {
			String arg = args[0];
			Path chemin = Paths.get(arg);
			
			if(!(validerArgs(chemin,1))) {
				System.exit(1);
			}
			
			File dossier = new File(arg);
			String nomDossierSource = dossier.getName();
			testerDossier(dossier, liste, nomDossierSource);
			imprimer(liste);
		}
		
		// si trois argument utilisee, verifier la bonne syntaxe
		if(args.length == 3) {
			if( !(args[0].equalsIgnoreCase("-o")) ) {
				System.err.println("Si vous voulez un fichier csv en sortie utilisez la forme (tls -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée>)");
				System.exit(1);
			}
			String argSortie = args[1];
			String argEntree = args[2];
			
			Path cheminSortie = Paths.get(argSortie);
			Path cheminEntree = Paths.get(argEntree);
			
			if(!(validerArgs(cheminSortie,2))) {
				System.exit(1);
			}
			
			if(!(validerArgs(cheminEntree,3))) {
				System.exit(1);
			}
			
			File dossier = new File(args[2]);
			String nomDossierSource = dossier.getName();
			testerDossier(dossier, liste, nomDossierSource);
			creerCSV(argSortie, liste);
			
			
		}
		
		

	}
	

	//methode utiliser pour valider les arguments recu par lutilisateur
	private static boolean validerArgs(Path chemin, int cas) {
		// TODO Auto-generated method stub
		switch(cas) {
		case 1:
			if(Files.exists(chemin)) {
				if(!(Files.isDirectory(chemin))) {
					System.err.println("Veuillez entree en argument le chemin d'accès"
							+ " d'un dossier qui contient du code test java");
					return false;
				}
				
			} else {
				System.err.println("Le chemin n'existe pas");
				return false;
			}
			return true;
			
		case 2:
			if(Files.exists(chemin)) {
				if(!(Files.isRegularFile(chemin))) {
					System.err.println("Veuillez entree comme 2e argument le "
							+ "chemin de sortie du fichier csv");
					return false;
				}
				if( !(chemin.getFileName().toString().toLowerCase().endsWith(".csv")) ) {
					System.err.println("le 2e argument entree ne coresspond pas a un chemin dun fichier .csv");
					return false;
				}
				
			} else {
				System.err.println("Le chemin n'existe pas");
				return false;
			}
			return true;
			
		case 3:
			if(Files.exists(chemin)) {
				if(!(Files.isDirectory(chemin))) {
					System.err.println("Veuillez entree comme 3e argument le "
							+ "chemin d'acces d'un dossier qui contient du"
							+ " code test java");
					return false;
				}
				
			} else {
				System.err.println("Le chemin n'existe pas");
				return false;
			}
			return true;
			
		default:
			System.err.println("erreur dans le processus de validation des arguments");
			return false;
		}
		
	}
	
	//methode utiliser par parcourir notre projet et trouver les fichiers classes test 
	private static void testerDossier(File dossier , List<ProprieteFichier> liste, String nomDossierSource) {
		// TODO Auto-generated method stub
		// si c'est un dossier, on rapelle la fonction recursivement pour traverser le sous dossier 
		if(dossier.isDirectory()) {
			
			File[] fichiers = dossier.listFiles();
			
			if(fichiers != null) {
				
				for(File fichier : fichiers) {
					testerDossier(fichier, liste, nomDossierSource);
					
				}
			}
		// si c'est un fichier 
		} else {
			// si cest une classe test (ce test prend en consideration que le projet respect la convention regardant le nom dune classe test)
			if(dossier.getName().endsWith("Test.java")) {
				testerFichier(dossier,liste, nomDossierSource);
			}
		}	
	}
	
	//methode utiliser pour trouver les infos desirees par rapport au fichiers et les stocker dans une liste
	private static void testerFichier(File fichier, List<ProprieteFichier> liste, String nomDossierSource) {
		// TODO Auto-generated method stub
		int tloc = calculerTLOC(fichier);
		int tassert = calculerTassert(fichier);
		double tcmp = 0.0;
		if(tassert == 0) {
			tcmp = tloc;
		}else {
			tcmp = (double) tloc/tassert;
		}
		
		String nomDuChemin =  fichier.getName();
		String nomDuPaquet="";
		String nomDeLaClasse= fichier.getName();
		
		
		//pour trouver le chemin relatif a partir du dossier originale
		File parent =fichier.getParentFile();
		
		while(parent != null) {
			
			if(!(parent.getName().equals(nomDossierSource))) {
				nomDuChemin = parent.getName() + "\\"+ nomDuChemin;
			}else {
				
				nomDuChemin = ".\\" + nomDuChemin;
				break;
			}
			
			// remonter dun autre palier 
			parent = parent.getParentFile();
		}
		
		
		
		//pour trouver le nom du paquet
		try(BufferedReader lecteur = new BufferedReader(new FileReader(fichier))){
			
			String ligne = lecteur.readLine();
			String paquet = "^package\\s+([\\w.]+);$"; // regex pour retrouver le nom du paquet 
			int debutDuNom = "package ".length();
			
			while( ligne != null) {
				
				if(ligne.matches(paquet)) {
					nomDuPaquet = ligne.substring(debutDuNom, ligne.length()-1);
					break;
				}
				
				
				//pour passer a la prochaine ligne
				ligne = lecteur.readLine();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("problem avec la lecture du fichier");
			e.printStackTrace();
			
		} 
		
		liste.add(new ProprieteFichier(nomDuChemin, nomDuPaquet, nomDeLaClasse, tloc, tassert, tcmp));
			
	}
	
	//methode utiliser retourner le tassert du fichier
	private static int calculerTassert(File fichier) {
		int count = 0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(fichier))) {
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
	

	//methode utiliser retourner le tloc
	private static int calculerTLOC(File fichierATester) {
	
		int count = 0;

        try(BufferedReader buffer = new BufferedReader(new FileReader(fichierATester))) {
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
			
	//methode utiliser pour imprimer la liste des fichiers analyses
	private static void imprimer(List<ProprieteFichier> liste) {
		// TODO Auto-generated method stub
		
		if(liste.isEmpty()) {
			System.out.println("Aucune classe suspecte dans ce projet");
		}else {
			
			for(ProprieteFichier proprietes : liste) {

				System.out.println(proprietes.cheminDuFichier + ", " + proprietes.nomDuPaquet + ", " + proprietes.nomDeLaClasse + ", " + proprietes.tloc + ", " + proprietes.tassert + ", " + proprietes.tcmp);
				
			}
			
		}
		
	}
	
	// methode utiliser pour modifier le fichier .csv recu par lutilisateur
	private static void creerCSV(String argSortie, List<TLS.ProprieteFichier> liste) {
		// TODO Auto-generated method stub
		
		try (FileWriter resultats = new FileWriter(argSortie)) {
			
			resultats.append("chemin du fichier, nom du paquet, nom de la classe, tloc de la classe, tassert de la classe, tcmp");
			resultats.append("\n");
			
			for(ProprieteFichier proprites : liste) {
				resultats.append(proprites.cheminDuFichier + ", " + proprites.nomDuPaquet + ", " + proprites.nomDeLaClasse + ", " + proprites.tloc + ", " + proprites.tassert + ", " + proprites.tcmp);
				resultats.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Erreur dans la modification du fichier CSV");
			e.printStackTrace();
		}
		
		System.out.println("csv file modifie avec succes. Si des classes TEST ont ete trouvees, elles ont ete rajoutees au fichier csv");
		
		
	}

}
