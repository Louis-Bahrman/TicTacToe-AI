/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * <p>Après implémentation et tests de l'algorithme MCTS, il est intéressant de remarquer que cette implémentation de l'algorithme
 * ne joue pas "parfaitement". En effet, celà se remarque surtout dans le Tic Tac Toe 3x3, que notre algorithme jouera toujours
 * comme premier coup le centre, même quand il a résolu le jeu (voir si dessous).
 * Ce coup est en effet celui qui admet le plus grand taux de victoire, mais n'est pas le meilleur. En effet, ouvrir dans un coin permets
 * de garantir une victoire où une égalité. Une faiblesse de notre implémentation est liée au fait que le meilleur coup en terme de
 * taux de victoire n'est pas nécessairement le véritable "meilleur coup".</p>
 * <p>Cette classe est le coeur de l'implémentation de l'algorithme MCTS. Elle contient donc une certaine quantité de données :</p>
 * <ul>
 * <li>Une instance d'arbre : Avant tout, l'algorithme a besoin d'un arbre pour pouvoir fonctionner.</li>
 * <li>Un entier : Cet entier représente le nombre d'itérations de l'algorithme qui sera faite à chaque appel de meilleurCoup.</li>
 * <li>Un coefficient réel : Cette valeur corresponds au coefficient d'exploration dans la formule de L'Upper Confidence Bound applied to Trees.
 * Sa valeur standard est sqrt(2).</li>
 * <li>Un générateur de nombre aléatoires : Ce générateur est nécessaire pour simuler aléatoirement des parties. Une amélioration qui peut être
 * apportée à l'algorithme est d'utiliser une heuristique pour simuler les parties plus intelligement.</li>
 * <li>La liste de Nodes chemin : Cette liste permet de tracer les différents noeuds qui ont étés suivis à chaque itération de l'algorithme.
 * Celà n'est pas strictement nécessaire, mais simplifie grandement le travail de mise à jour de l'arbre après la simulation.</li>
 * <li>Un booléen : Cet attribut, ainsi que tous ceux qui suivent, ne font pas partie de l'implémentation de base de l'algorithme. Ces 
 * données sont utiles à la mise en place d'un système de cache, permettant à l'algorithme de retenir les données traitées dans une partie
 * pour les prochaines. Cela amène entre autre à la résolution du Tic Tac Toe 3x3, avec suffisamment de parties. 
 * <strong>ATTENTION : cette fonctionnalité est à éviter pour un grand nombre de simulations de parties 9x9, car la consommation en 
 * mémoire est importante.</strong></li>
 * <li>Le Node firstRoot : Ce node permets de sauvegarder la racine qui corresponds à un jeu vide entre différentes parties, étant donné que la 
 * racine de l'arbre en soi est modifiée dans notre implémentation. Ce Node pointe à la même adresse mémoire que le Node à l'initialisation
 * de l'arbre, il est donc mis à jour quand ce dernier l'est.</li>
 * <li>Un tableau de Node : Ce tableau stocke, au même titre que <strong>firstRoot</strong>, les calculs déjà faits par l'algorithme. Celui ci prends 
 * la forme d'un tableau car il stocke les différents arbres après que l'adversaire aie joué son premier coup. Le tableau a les mêmes
 * dimensions que la grille de jeu. Cette donnée n'est pas strictement nécessaire. En effet, une optimisation de l'algorithme permettrait de
 * réutiliser <strong>firstRoot</strong>, car les données sont les mêmes, avec les joueurs inversés. Cela offrirait une division d'espace
 * mémoire utilisé par cette fonctionnalité de 2, ainsi qu'une augmentation de remplissage de ces informations de 2 (ca se voit facilement
 * avec le Tic Tac Toe 3x3 : le jeu est résolu 2 fois).</li>
 * <li>Le Node trueRoot : Ce Node est utile pour la génération du cache partie après partie. Il pointe vers le Node avec lequel il a commencé
 * la partie, nous permettant de mettre à jour l'intégralité de l'arbre, même en étant profondément dedans. Cet attribut ne serait pas 
 * nécessaire avec l'optimisation décrite précédemment.</li>
 * <li>La liste de node cachedChemin : Cette liste corresponds au chemin "invisible" à effectuer dans la mise à jour de l'arbre, 
 * dans le cas où on veut générer un cache. En effet, en temps normal, l'algorithme descends dans l'arbre de jeu, et ne mets à jour que
 * les informations utiles, c'est à dire celles directement en dessous. Mais si on veut générer un cache correct, il faut systématiquement
 * mettre à jour l'intégralité de l'arbre.</li>
 * </ul>
 */
public class AlgoRechercheMCTS extends AlgoRecherche {
    private final ArbreMCTS search;
    private final int maxIterations;
    private final double coeff;
    private final Random seed;
    private ArrayList<Node> chemin;
    private final boolean cache;
    private final Node firstRoot;
    private final Node[][] rootTable;
    private Node trueRoot;
    private ArrayList<Node> cachedChemin;
    
    /**
     * <div>Initialisation d'une instance de l'algorithme.</div>
     * @param player Le joueur ordi jouant avec cette instance de l'algorithme.
     * @param opponent Son adversaire.
     * @param m Le nombre d'itérations à effectuer par recherche de coup.
     * @param c Le coefficient à utiliser dans L'UCT.
     * @param line La longueur des lignes du plateau de jeu (utile pour cette implémentation du cache uniquement).
     * @param column La longueur des colonnes du plateau de jeu (utile pour cette implémentation du cache uniquement).
     * @param storeCache Le booléen indiquant si l'utilisateur veut que l'algorithme utilise un cache.
     */
    public AlgoRechercheMCTS(Joueur player, Joueur opponent, int m, double c, int line, int column, boolean storeCache){
        search = new ArbreMCTS(player, opponent);
        maxIterations = m;
        seed = new Random();
        coeff = c;
        firstRoot = search.root();
        rootTable = new Node[line][column];
        cache = storeCache;
    }
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        /*
        Le début de cette méthode sert deux fonctions : Si on est en plein milieu d'une partie, il prends en compte le coup adverse et 
        descends dans l'arbre en conséquence (lorsqu'on compte au moins 2 jetons dans la grille, on est "en plein milieu d'une partie").
        Si on utilise la même instance de l'algorithme pour jouer plusieurs parties, il se remets à zéro (lorsqu'on a au plus 1 jeton dans
        la grille). Si l'utilisateur demande un cache, l'algorithme n'oublie pas ses données, sinon oui. Dans le cas où l'algorithme compte
        0 jetons, il déduit que c'est à lui de commencer, sinon qu'il joue en 2eme.
        */
        //Compte du nombre de jetons dans la grille, s'arrêtant à 2 (pas besoin d'en savoir plus).
        CoupTicTacToe lastPlayed = (CoupTicTacToe) _plateau.getDernierCoup(); Node tmp;
        Iterator<Node> children;
        int nbJeton = 0;
        for(int i = 0; i < _plateau.getNbLignes(); i++){
            for(int j = 0; j < _plateau.getNbColonnes(); j++){
                if(_plateau.getPiece(new Case(j, i)) != null){
                    nbJeton++;
                    if(nbJeton > 1){
                        break;
                    }
                }
            }
        }
        switch(nbJeton){
            case 0:
                if(cache){
                    //Appel du cache déjà existant et réinitialisation du chemin en cache
                    search.root(firstRoot);
                    trueRoot = firstRoot;
                    cachedChemin = new ArrayList<>();
                }else{
                    //Remise à zéro de l'algorithme 
                    Joueur opponent;
                    if(search.root().player() == _joueur){
                        opponent = search.root().opponent();
                    }else{
                        opponent = search.root().player();
                    }
                    search.root(new Node(null, _joueur, opponent));
                }
                break;
            case 1:
                if(cache){
                    //Appel du cache déjà existant et réinitialisation du chemin en cache
                    int i = lastPlayed.getLigne();
                    int j = lastPlayed.getColonne();
                    if(rootTable[i][j] == null){
                        Joueur opponent;
                        if(search.root().player() == _joueur){
                            opponent = search.root().opponent();
                        }else{
                            opponent = search.root().player();
                        }
                        rootTable[i][j] = new Node(lastPlayed, _joueur, opponent);
                    }
                    search.root(rootTable[i][j]);  
                    trueRoot = rootTable[i][j];
                    cachedChemin = new ArrayList<>();
                }else{
                    //Remise à zéro de l'algorithme
                    Joueur opponent;
                    if(search.root().player() == _joueur){
                        opponent = search.root().opponent();
                    }else{
                        opponent = search.root().player();
                    }
                    search.root(new Node(lastPlayed, _joueur, opponent));
                }
                break;
            default:
                //Descente dans l'arbre suivant le coup adverse.
                children = search.root().children().iterator();
                while(children.hasNext()){
                    tmp = children.next();
                    if(tmp.coup().equals(lastPlayed)){
                        search.root(tmp);
                        //Ralonge du chemin en cache avec le Node représentant le coup adverse, si demandé.
                        if(cache){
                            cachedChemin.add(tmp);
                        }
                        break;
                    }
                }
        }
        //Initialisation de l'algorithme.
        Node root = search.root();
        Node nextNode;Joueur winner;
        _plateau.sauvegardePosition(0);
        int iterations = 0;
        while(iterations < maxIterations){
            if(root.solved()){
                /*
                Cas extrême, qui ne s'applique que au 3x3 : Si la racine de l'arbre est résolue, soit l'intégralité du jeu, l'algorithme
                ne s'exécute plus.
                */
                break;
            }else{
                /*
                Si l'utilisateur demande un cache, le chemin de mise à jour dans chaque itération
                commence toujours par le chemin en cache. Sinon il est vidé.
                */
                chemin = new ArrayList<>();
                if(cache){
                    chemin.addAll(cachedChemin);
                }else{
                    chemin.add(root);
                }
                /*
                Sélection de l'algorithme. La valeur null est une valeur particulière renvoyée intentionellement,
                celà est expliquée dans la fonction en question
                */
                nextNode = selection(root, _plateau);
                if(nextNode == null){
                    _plateau.restaurePosition(0);
                }else{
                    //Si la sélection ne renvoie pas un null, l'itération compte comme une vraie itération de l'algorithme.
                    iterations++;
                    //L'expansion est effectuée avec la simulation, à cette étape.
                    winner = simulate(nextNode, _plateau);
                    /*
                    Si l'utilisateur demande un cache, le retour est fait sur l'intégralité de l'arbre initial. Sinon, seulement sur le
                    sous arbre en cours.
                    */
                    if(cache){
                        backPropagation(winner, trueRoot);
                    }else{
                        backPropagation(winner, root);
                    }
                    //Remise à zéro du plateau à la fin d'une itération.
                    _plateau.restaurePosition(0);            
                }
            }
        }
        //Sélection du noeud optimal
        Node nextPlay = root.nextPlay(); 
        //Ralonge du chemin en cache avec le Node représentant notre coup, si demandé.
        if(cache){
            cachedChemin.add(nextPlay);
        }
        search.root(nextPlay);
        return nextPlay.coup();
    }
    
    /**
     * <div>Cette méthode exécute la partie sélection de l'algorithme.</div>
     * @param n Le noeud père dont on va sélectionner un des fils.
     * @param pl Le plateau représentant l'état du jeu en arrivant sur le noeud père. Il sera mis à jour après la sélection d'un fils.
     * @return Le Node sélectionné
     */
    private Node selection(Node n, Plateau pl){
        //Condition d'arrêt de la récursivité.
        if (n.children().isEmpty()){
	    return n;
        }
        else {
            //Initialisation de la sélection.
            double valMax=Double.NEGATIVE_INFINITY;
            double val;boolean allSolved = true;
            Node selection = null;
            Iterator<Node> nodes = n.children().iterator();
            while(nodes.hasNext()){
                Node nf = nodes.next();
                //La sélection ne considèrera que les noeuds non-résolus.
                if(!(nf.solved())){
                    allSolved = false;
                    if(nf.visits() == 0){
                        /*
                        Si le noeud est à 0 visites, on force l'algorithme à le visiter. Ce forcage est nécessaire au bon fonctionnement
                        de l'algorithme, dans notre implémentation, si on veut que l'algorithme fonctionne comme voulu.
                        */
                        val = Double.MAX_VALUE;
                    }else{
                        //Cas par défaut : Application de l'UCT
                        val = nf.winrate()+coeff*Math.sqrt(Math.log(n.visits())/nf.visits());
                    }
                    if (val>valMax){
                        selection=nf;
                        valMax=val;
                    }
                }
            }
            if(allSolved){
                /*
                Cette condition apparait si tous les fils de n sont résolus (voir Node.java pour définition). On mets à jour n et on 
                saute l'itération de l'algorithme en cours.
                */
                n.solved(true);
                return null;
            }else{
                //Cas standard : mise à jour du plateau et récursion de l'algorithme.
                pl.joueCoup(selection.coup());
                chemin.add(selection);
                return selection(selection, pl);
            }
        }       
    }
    
    /**
     * <div>Cette méthode traite l'extension d'une feuille, en fonction des coups disponibles.</div>
     * @param leaf Le noeud à étendre.
     * @param leafPlateau Le plateau représentant l'état du jeu en arrivant sur le noeud leaf.
     */
    private void expansion(Node leaf, Plateau leafPlateau){
        ArrayList<Coup> coups = leafPlateau.getListeCoups(leaf.player());
        Iterator<Coup> coup = coups.iterator();
        Coup currentCoup;
        while(coup.hasNext()){
            //Ajout de chaque fils correspondant à un des coups possible à partir du Node
            currentCoup = coup.next();
            Node newLeaf = new Node(currentCoup, leaf.opponent(), leaf.player());
            leaf.children().add(newLeaf);
        }
    }
    
    
    /**
     * <div>Cette méthode simule au hasard le déroulement d'une partie. Il est pertinent de remarquer que l'extension se fait pendant
     * la simulation. Celà implique entre autre que tous les états de jeu rencontrés pendant la simulation sont ajoutés à l'arbre et
     * étendus. Celà n'est pas conforme à l'énoncé théorique de l'algorithme, mais préférable dans cette implémentation, surtout dans
     * le cadre de l'utilisation du cache. En effet, l'algorithme théorique a tendance à compter certaine victoires en double 
     * (1 victoire par noeud étendu, pour être exact). Cette implémentation remédie à ce problème, et permets d'avoir un cache représentant
     * exactement le jeu (nous avons testé cette affirmation pour le 3x3, et retrouvé les valeurs issues de la résolution du jeu).</div>
     * @param node Le noeud à partir duquel simuler.
     * @param board Le plateau représentant l'état du jeu en arrivant dans ce noeud.
     * @return L'objet Joueur gagnant cette simulation.
     */
    private Joueur simulate(Node node, Plateau board){
        if(!board.partieTerminee()){
            //Extension du noeud, choix d'un fils au hasard, mise à jour du plateau et récursion de l'algorithme.
            expansion(node, board);
            int index = seed.nextInt(node.children().size());
            Node nextMove = node.children().get(index);
            chemin.add(nextMove);
            board.joueCoup(nextMove.coup());
            return simulate(nextMove, board);
        }else{
            //Condition d'arrêt de la récursion
            return board.vainqueur();
        }
    }
    
    /**
     * <div>Cette méthode gère la mise à jour des notes de l'arbre.</div>
     * @param gagnant Le joueur gagnant de la simulation effectué précédemment.
     * @param n Le noeud à partir duquel mettre à jour (n est toujours soit la racine relative de l'arbre, soit sa racine absolue).
     */
    private void backPropagation(Joueur gagnant, Node n){
        Node currentNode = n;
        Iterator<Node> followChemin = chemin.iterator();
        //Distinction légère entre les égalités et les victoires : tout le monde marque une égalité  si elle arrive 
        if(gagnant == null){
            currentNode.addVisit();
            currentNode.addDraw(); 
            while(followChemin.hasNext()){
                currentNode = followChemin.next();
                currentNode.addVisit();
                currentNode.addDraw();
            }
        }else{
            currentNode.addVisit();
            if (currentNode.opponent().equals(gagnant)){
                currentNode.addWin();
            }  
            while(followChemin.hasNext()){
                currentNode = followChemin.next();
                currentNode.addVisit();
                if (currentNode.opponent().equals(gagnant)){
                    currentNode.addWin();
                }
            }
        }
        //A la fin de l'algorithme, dans notre implémentation, currentNode est toujours une feuille représentant une partie finie, et est donc résolu.
        currentNode.solved(true);
    }
}
