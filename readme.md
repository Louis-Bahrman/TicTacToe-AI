# Algorithme MinMax et Monte Carlo
## Objectif

Notre objectif a été d’implémenter l’algorithme MinMax et l’algorithme MonteCarlo Tree Search sur le jeu TicTacToe et d'Ultimate TicTacToe.
Les consignes qui nous ont été forunies sont dans le fichier consignes.md et le planning dans Project-monitoring.md.

## Explications du code

Le fonctionnement détaillée de chaque classe ajoutée et de ses méthodes est donnée dans la doc. On explique ici le rôle des classes ajoutées et comment l'utilisateur peut jouer ou tester les IA.

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
Elle contient des méthodes pour construire des arbres à partir de la classe Node qui enregistre toute les informations sur les joueurs, le coup joué à ce point, l'évaluation du taux de victoire ou encore le coup précédent.

### AlgoRechercheMinMax

Cette classe implémente l'algorithme du MinMax et hérite d'AlgoRecherche. Elle permet de renvoyer le meilleur coup du point de vue d'un joeur à un certain niveau de la partie selon cet algo.
Plusieurs paramètres comme la profondeur d'évaluation déterministe, le nombre de tests aléatoire réalisés pour les estimations ou encore l'activation d'une amélioration de l'algorithme par mémorisation sont réglables.

### AlgoRechercheMCTS

Cette classe implémente l'algorithme du MinMax et hérite d'AlgoRecherche. Elle permet de renvoyer le meilleur coup du point de vue d'un joeur à un certain niveau de la partie selon cet algo.
Les paramètres de cette algo comme le niveau d'exploration peuvent être choisis à l'instanciation.

### MemoireMinMax

Cette implémente une amélioration de l'agorithme du MinMax en mémorisant des coefficiants évaluant la qualités des coups basés sur les parties précédemment jouées dans un tournoi.
Une modification de la classe arbitre y est associé pour déclencher l'apprentissage à chaque partie jouée.

### Generator

Cette classe implémente la notation d'une position du point de vue d'un joueur par estimation en testant des fins de parties aléatoires depuis cette position.

### Player

Cette classe contient la méthode **main** du programme. Elle permet d'effectuer des parties ou torunois en local. Les paramètres se règle avec les classes précédemment présentées :
*  Il faut créer deux joueurs distincts selon la composition souhaitée. Soit des JoueurHumain si vous souhaitez participer, soit des JoueurOrdi pour faire jouer les IA ou effectuer des tests
*  Il faut choisir le plateau de jeu selon le jeu souhaité parmi les deux grilles de TicTacToe et d'ultimate TicTacToe
*  Il faut ensuite paramétrer les algo recherche voulu pour les IA parmi le MinMax, le MCTS ou l'algo aléatoire et fixer leurs paramètres
*  Enfin, il faut créer un arbitre avec le plateau et les joueurs et demander une partie ou un tournoi avec affichage désactivable


## Tester l'IA en local

La classe Player permet de réaliser des tests en local.

## Tester l'IA sur Codingame

Afin de tester les IA sur *Codingame*, il vous faudra modifier le contenu de la classe **Player** pour être compatible avec le mode de fonctionnement de la plateforme, à savoir récupérer les données en entrée ( code déjà fourni par le site ) et retourner sur la sortie standard votre coup. La classe **Arbitre** ne sera plus nécessaire puisque se sera la plateforme Codingame qui fera office d'arbitre.  La classe **Player** contient également la version pour le bon fonctionnement de votre IA sur Codingame. Il suffit de dé-commenter la partie *Codingame* et commenter le jeu en local.
L'amélioration de l'algo MinMax sera nécésairement désactivée.


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

L'amélioration proposée de l'algo MCTS proposée consiste en une conservation des statistiques d'évalution estimées pour les coups d'une partie sur l'autre, elle est intégrée à la classe mais chère en mémoire.

### Amélioration MinMax

l'amélioration proposée de l'algo MinMax et donnée par la classe MémoireMinMax. Une grille garde en mémoire au long d'un tournoi un coefficients évaluants si la position est souvent gagnante ou perdante.
Ce coefficient pondère ensuite, après un nombre suffisant de parties jouées pour avoir une estimation correcte, les évalutions de valeur des coups par l'algo.

