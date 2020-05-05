# Algorithme MinMax et Monte Carlo
## Objectif

Notre objectif a été d’implémenter l’algorithme MinMax et l’algorithme MonteCarlo Tree Search sur le jeu TicTacToe et d'Ultimate TicTacToe.
Les consignes qui nous ont été forunies sont dans le fichier consignes.md et le planning dans Project-monitoring.md.

## Explications du code

Le fonctionnement détaillée de chaque classe ajoutée et de ses méthodes est donnée dans la doc. On explique ici le rôle des clesses ajoutées et comment l'utilisateur peut jouer ou tester les IA.

### Player

Cette classe contient la méthode **main** du programme. Elle crée un joueur «*humain*» ainsi qu’un joueur «*Ordinateur*». L’ordinateur joue les coups de manière aléatoire.
Regardez en détail le contenu de la classe **Player**. La création de l’IA ( *joueurOrdi* ) nécessite  d’appeler la méthode **setAlgoRecherche** en lui passant en paramètre une instance d’une classe qui implémente l’interface **AlgoRecherche**. Dans notre cas c'est la classe **AlgoRechercheAleatoire** qui implémente cette interface. Elle implémente donc la méthode suivante :

```java
public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder)
```

On doit passer à cette méthode le plateau du jeu ( *ici GrilleTicTacToe3x3* ) , le joueur à qui c'est le tour de jouer et un dernier paramètre qui indique si l'ordinateur peut analyser la position lorsque c'est à son adversaire de jouer.

L'algorithme aléatoire récupère la liste des coups possibles à partir de la position courante et en choisit un au hasard.

```java
Random rnd;

public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {    

    ArrayList<Coup> coups = _plateau.getListeCoups(_joueur);   
    return coups.get(rnd.nextInt( coups.size()));
}
```
### Arbitre

Cette classe forunit les méthodes pour réaliser des parties et des tournois en local.

### GrilleTicTacToe3x3

Cette classe représente la grille du jeu TicTacToe de dimension 3x3 et l'ensemble des méthodes pour jouer dessus, vérifier la validité des coups ou l'état de la partie.

### GrilleTicTacToe9x9

Cette classe représente la grille du jeu Ultimate TicTacToe de dimension 9x9 et l'ensemble des méthodes pour jouer dessus, vérifier la validité des coups ou l'état de la partie.

### JoueurOrdi

Cette classe implémente un joueur IA caractérisé par un ID auto-incrémenté et un algo de recherche que l'on peut choisir.

### JoueurHumain

Cette classe implémente un joueur Humain caractérisé par un ID auto-incrémenté pour permettre à un opérateur de jouer.

### ArbreMinMax

Cette classe implémente la structure d'arbre, qui est une structure récursive, destinée à la prévision et l'évaluation de coups par l'algorithme du MinMax.
Elle contient les méthodes pour construire un noeud, référencer ses fils, un ou plusieurs coup et une note évaluant ces coups ainsi que les méthodes nécéssaires à l'algorithme MinMax.

### ArbreMCTS

Cette classe implémente la structure d'arbre, qui est une structure récursive, destinée à la prévision et l'évaluation de coups par l'algorithme MCTS.
Elle contient les méthodes pour construire un noeud, référencer ses fils, un ou plusieurs coup et une note évaluant ces coups ainsi que les méthodes nécéssaires à l'algorithme MCTS.


### AlgoRechercheMinMax

### AlgoRechercheMCTS

### MemoireMinMax

Modif sur la classe arbitre

### Generator

## Tester l'IA en local

La classe Player actuelle vous permet de jouer contre votre IA. Vous pouvez  modifier facilement cette classe afin de faire jouer deux IA l’une contre l’autre.

## Tester l'IA sur Codingame

Afin de tester votre IA sur *Codingame*, il vous faudra modifier le contenu de la classe **Player** pour être compatible avec le mode de fonctionnement de la plateforme, à savoir récupérer les données en entrée ( code déjà fourni par le site ) et retourner sur la sortie standard votre coup. La classe **Arbitre** ne sera plus nécessaire puisque se sera la plateforme Codingame qui fera office d'arbitre.  La classe **Player** contient également la version pour le bon fonctionnement de votre IA sur Codingame. Il suffit de dé-commenter la partie *Codingame* et commenter le jeu en local


Pour générer un seul fichier à partir de l’ensemble de vos classes  vous utiliserez le **builder.jar** en effectuant les commandes suivantes.

Lancez l’invite de commande **cmd.exe** et  tapez les commandes suivantes :
```bash 
# C est la lettre du disque dur où se trouve votre projet 
C: 

# Se positionner dans le répertoire src du projet
# exemple cd c:\netbean\TicTacToeCodingame\src
cd <nom_du_répertoire source>  

# Lancer le builder.jar
# Cette commande va générer un fichier Player.java dans le répertoire courant.
# C’est ce fichier qu’il faudra poster sur codingame.

java –jar builder.jar .\tictactoecodingame\Player.java 
```
## Amélioration des algorithmes

### Amélioration MCTS

### Amélioration MinMax



