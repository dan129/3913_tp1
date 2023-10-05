Équipe: Carlos Antonio Paz et Dan Munteanu
Git: https://github.com/dan129/3913_tp1

Documention:
Dans le dossier principal à partir duquel on exécute le programme, il faut avoir le dossier ./jar , ensuite nous pouvons donner tout chemin relatif ou absolu qu'on veut pour effectuer l'analyse du dossier des classes Test java.

exemple:
├── tropcomp_10.csv
├── jar
│ ├── tropcomp.jar
│ ├── tassert.jar
│ ├── tloc.jar
│ ├── jar
│ │ ├── tls.jar
├── jfreechart-master
│ ├── ...

commande à exécuter : java -jar ./jar/tropcomp.jar -o ./tropcomp_10.csv ./jfreechart-master/src/test/ 0.1

Pour TLS,
-Pour identifier si un fichier .java est une classe test, on verifie si le nom du fichier se termine par Test.java.
-Lorsque Tassert vaut 0, on assume une valeur de TCMP de 0 pour automatiquement disqualifier tropcomp (prends un pourcentage des valeurs les plus elevés)

appel avec sortie à la ligne de commande : java -jar <chemin-de : tls.jar> <chemin-de-l'entrée> 

appel avec sortie dans un fichier .csv : java -jar <chemin-de : tls.jar> -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée>
- On assume que le fichier .csv existe déjà et que le chemin relatif ou absolue de ce fichier est fournit en argument.

Tropcomp:
-Pour que tropcomp.jar fonctionne correctement, celui-ci doit se retrouver dans le même dossier que qu'un dossier nommé jar avec tls.jar dedans
-Le seuil fournit doit être en format double tel qu'entre 0.0 et 1.0.
-On prends tout ce qui est plus grand que le seuil pour trouver les classes potiellement suspectes. Donc, si le seuil est 20% et on a 7 classe, 7*20%= 1,4. Donc, au maximum 1 classe est suspecte.

appel : java -jar <chemin-de : tropcomp.jar> <chemin-de-l'entrée> <seuil>  pour produire la sortie à la ligne de commande

appel avec sortie dans un fichier .csv: java -jar <chemin-de : tropcomp.jar> -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée> <seuil>
- On assume que le fichier .csv existe déjà et que le chemin relatif ou absolue de ce fichier est fournit en argument.
 
