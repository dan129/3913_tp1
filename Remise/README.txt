Équipe: Carlos Antonio Paz et Dan Munteanu
Git: https://github.com/dan129/3913_tp1

Documention:
Dans le dossier principal à partir duquel on exécute le programme il faut avoir le dossier ./jar , ensuite nous pouvons donner tout chemin relatif ou absolu qu'on veut pour effectuer l'analyse du dossier des classes Test java.

exemple:
├── tropcomp_10.csv
├── jar
│ ├── tls.jar
│ ├── tropcomp.jar
│ ├── tassert.jar
│ ├── tloc.jar
├── jfreechart-master
│ ├── ...

commande à exécuter : java -jar ./jar/tropcomp.jar -o ./tropcomp_10.csv ./jfreechart-master/src/test/ 0.1

Pour TLS,
- Pour identification si un fichier .java est une classe test, on ne fait que verifier si le nom du fichier se termine par Test.java.
-Pour un Tassert de 0, on assume une valeur de TCMP de 0 pour automatiquement disqualifier tropcomp (prend un pourcentage des valeurs les plus elevés)

appel : java -jar <chemin-de : tls.jar> <chemin-de-l'entrée> pour produire la sortie à la ligne de commande

appel : java -jar <chemin-de : tls.jar> -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée> pour modifier et inclure les réponses dans un fichier .csv
- on assume qu'on fichier .csv existe déja et le chemin relatif ou absolue de ce fichier doit être fournit entrée.(Si le fichier .csv n'existe, le programme n'est pas conçue pour en crée un)


Pour tropcomp,

-Pour que tropcomp.jar fonctionne correctement, celui-ci doit se retrouver dans le même dossier que qu'un dossier nommé jar avec tls.jar dedans
-le seuil doit être en format double. (c'est-à-dire on s'entend a recevoir des entrée entre 0.0 et 1.0)
- arrondis pour trouver les classes potiellement suspect donc si le seuil est 20% et on a 7 classe, 7*20%= 1,4 . Donc au maximum une classe suspecte. alors que pour 8 classe et seuil de 20%. 8 *20% = 1,6 . Donc 2 classes potentiellement suspecte


appel : java -jar <chemin-de : tropcomp.jar> <chemin-de-l'entrée> <seuil>  pour produire la sortie à la ligne de commande

appel : java -jar <chemin-de : tropcomp.jar> -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée> <seuil> pour modifier et inclure les réponses dans un fichier .csv
- on assume qu'on fichier .csv existe déja et le chemin relatif ou absolue de ce fichier doit être fournit entrée.(Si le fichier .csv n'existe, le programme n'est pas conçue pour en crée un)
 