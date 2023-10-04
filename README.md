# 3913_tp1

Dans le dossier principal à partir duquel on exécute le programme il faut avoir le dossier ./jar , ensuite nous pouvons donner tout chemin relatif ou absolu qu'on veut pour effectuer l'analyse du dossier des classes Test java.

exemple:
├── tropcomp_10.csv
├── jar
│   ├── tls.jar
│   ├── tropcomp.jar
│   ├── tassert.jar
│   ├── tloc.jar
├── jfreechart-master
│   ├── ...

commande à exécuter : java -jar ./jar/tropcomp.jar -o ./tropcomp_10.csv ./jfreechart-master/src/test/ 0.1