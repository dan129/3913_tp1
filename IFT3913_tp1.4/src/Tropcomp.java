import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/*
 * A faire 
 * - arranger les dossier .jar pour que la methode appel fonctionne pour tls.jar
 * -tester appel a 2 argument
 * 
 * toute la fonction pour lappel a 4 argument
 */


public class Tropcomp {
	
	public static class ProprieteFichier {
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

	public Tropcomp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length < 2 || args.length == 3 || args.length > 4) {
			System.err.println("Mauvais nbr arguments. Veuillez soit passez un"
					+ " argument (le chemin d'accès d'un dossier qui contient"
					+ " du code test java) /n ou trois arguments (tls -o <chemin-à-la"
					+ "-sortie.csv> <chemin-de-l'entrée>)");
		}
		
		// liste des resultats
		List<ProprieteFichier> liste = new ArrayList<>();
		List<ProprieteFichier> listeClasseSuspecte = new ArrayList<>();
		
		// si argument utilise verifier que c'est le chemin d'acces d'un dossier 
		if(args.length == 2) {
			String arg = args[0];
			Path chemin = Paths.get(arg);
			
			//tester si le deuxieme argument est un numero
			String arg2 = args[1];
			
			try {
			double seuil = Double.parseDouble(arg2);
			if(!(validerArgs(chemin,1))) {
				System.exit(1);
			}
			
			appelTLSUnArg(arg, seuil, liste);
			trouverListeClasseSuspecte(seuil, liste, listeClasseSuspecte);
			imprimer(listeClasseSuspecte);
			
			} catch(NumberFormatException e) {
				System.err.println("Le deuxieme argument doit etre un numero qui"
						+ " represente le seuil(pourcentage sans le signe)");
				System.exit(1);
				
			}
			
		}
		
		
		// si trois argument utilisee, verifier la bonne syntaxe
		if(args.length == 4) {
			if( !(args[0].equalsIgnoreCase("-o")) ) {
				System.err.println("Si vous voulez un fichier csv en sortie utilisez la forme (tls -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée>)");
				System.exit(1);
			}
			String argSortie = args[1];
			String argEntree = args[2];
			
			Path cheminSortie = Paths.get(argSortie);
			Path cheminEntree = Paths.get(argEntree);
			
			//tester si le deuxieme argument est un numero
			String arg4 = args[3];
			
			try {
			double seuil = Double.parseDouble(arg4);
			} catch(NumberFormatException e) {
				System.err.println("Le deuxieme argument doit etre un numero qui"
						+ " represente le seuil(pourcentage sans le signe)");
				System.exit(1);
				
			}
			
			if(!(validerArgs(cheminSortie,2))) {
				System.exit(1);
			}
			
			if(!(validerArgs(cheminEntree,3))) {
				System.exit(1);
			}
			
			
			
		}

	}
	
	private static void imprimer(List<ProprieteFichier> liste) {
		// TODO Auto-generated method stub
		for(ProprieteFichier proprietes : liste) {

			System.out.println(proprietes.cheminDuFichier + ", " + proprietes.nomDuPaquet + ", " + proprietes.nomDeLaClasse + ", " + proprietes.tloc + ", " + proprietes.tassert + ", " + proprietes.tcmp);
			
		}
	}
	
	private static void trouverListeClasseSuspecte(double seuil, List<Tropcomp.ProprieteFichier> liste,
			List<Tropcomp.ProprieteFichier> listeClasseSuspecte) {
		
		// TODO Auto-generated method stub
		List<ProprieteFichier> listeSuspecteTloc = new ArrayList<>();
		List<ProprieteFichier> listeSuspecteTcmp = new ArrayList<>();
		
		
		//trier la liste par ordre croissant de TLOC
		liste.sort((a, b) -> Integer.compare(a.getTloc(), b.getTloc()));
		
		//trouver liste des classes potentielement suspect en considerant TLOC
		int longueur = liste.size();
		double nbClasseSuspecte = longueur * seuil;
		int pivot = longueur - (int) Math.round(nbClasseSuspecte);
		//pour garantir une classe suspect si le seuil est trop faible
		if(pivot == longueur) {
			pivot--;
		}
		
		//trouver la liste de classes potentiellement suspectes selon TLOC 
		for(int i = pivot; i < longueur; i++) {
			listeSuspecteTloc.add(liste.get(i));
		}
		//ajouter a liste des classes potentielement suspect toutes classes ayant exactement le mm TLOC que le pivot 
		int iterateur = 1;
		int pivotTemporaire = pivot;
		// si pivot - 1 = un nombre bnegatif atteint la fin de la liste don on arrete
		if( !((pivotTemporaire -1) < 0) ) {
			while(liste.get(pivot - iterateur) == liste.get(pivot)) {
				listeSuspecteTloc.add(liste.get(pivot - iterateur));
				iterateur--;
				pivotTemporaire--;
				if(pivotTemporaire < 0 ) {
					break;
				}
			}
			
		}
		
		
		//trouver la liste de classes potentiellement suspectes selon Tcmp
		//trier la liste par ordre croissant de Tcmp
		liste.sort((a, b) -> Double.compare(a.getTcmp(),b.getTcmp()));
		
		for(int i = pivot; i < longueur; i++) {
			listeSuspecteTcmp.add(liste.get(i));
		}
		//ajouter a liste des classes potentielement suspect toutes classes ayant exactement le mm Tcmp que le pivot 
	    iterateur = 1; // reinitialise notre iterateur
	    pivotTemporaire = pivot; //reinitialiser 
		// si pivot - 1 = un nombre bnegatif atteint la fin de la liste don on arrete
		if( !((pivotTemporaire -1) < 0) ) {
			while(liste.get(pivot - iterateur) == liste.get(pivot)) {
				listeSuspecteTcmp.add(liste.get(pivot - iterateur));
				iterateur--;
				pivotTemporaire--;
				if(pivotTemporaire < 0 ) {
					break;
				}
			}
			
		}
		
		for(int i = 0; i < listeSuspecteTloc.size(); i++) {
			
			// si element de la liste TLCO se retrouve aussi de la liste TCMP alors on rajoute cette element dans liste des classes suspectes
			if(listeSuspecteTcmp.contains(listeSuspecteTloc.get(i))) {
				listeClasseSuspecte.add(listeSuspecteTloc.get(i));
			}
		}
		
	}

	private static void appelTLSUnArg(String arg, double seuil, List<Tropcomp.ProprieteFichier> liste) {
		// TODO Auto-generated method stub
		
		
		//pour appel le fichier .jar
		ProcessBuilder createurProcessus = new ProcessBuilder("java", "-jar","\"C:\\Users\\4carl\\eclipse-workspace\\IFT3913_tp1.4\\src\\tls.jar\"" );
		//pour ajouter les args
		createurProcessus.command().addAll(List.of(arg));
		try {
			//pour lancer lappel
			Process processus = createurProcessus.start();
			
			//pour recevoir la sortie de TLS.jar
			InputStream entree = processus.getInputStream();
			BufferedReader lecteur = new BufferedReader(new InputStreamReader(entree));
			String ligne;
			ligne = lecteur.readLine();
			
			while(ligne != null) {
				
				ligne.trim();
				//separer les elements du string par les espaces
				String proprietes[] = ligne.split(",");
				int tloc;
				int tassert;
				double tcmp;
				//tester la sortie de tls.jar
				if(proprietes.length == 6) {
					
					try {
						tloc = Integer.parseInt(proprietes[3].trim());
						tassert= Integer.parseInt(proprietes[4].trim());
						tcmp = Double.parseDouble(proprietes[5].trim());
						
						liste.add(new ProprieteFichier(proprietes[0].trim(), proprietes[1].trim(), proprietes[2].trim(), tloc, tassert, tcmp));
					} catch (NumberFormatException e) {
						System.err.println("la sortie de TLS.jar pour TLOC et/ou Tassert et/ou Tcmp est dans le mauvais format");
					}
				}else {
					System.err.println("Sortie de TLS.jar n'a pas le nombre arguments attendus");
					System.exit(1);
				}
				
				//pour iterer
				ligne = lecteur.readLine();
			}
			
			// attendre que le processus soit fini
			try {
				processus.waitFor();
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

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

}
