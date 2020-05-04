/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;

/**
 * <p>Il s'agit du corps principal de l'algorithme de recherche MinMax.</p><p>
 * L'algorithme permet de renvoyer le meilleur coup à jouer pour un joueur dans 
 * une situation donnée selon l'algoritme du MinMax. </p><p>L'algorithme construit 
 * un arbre des suits de coups possibles à partir de la situation fournie. Cet arbre
 * est construit sur une profondeur donnée et on a ensuite recours à Generator pour 
 * attribuer une certaine valeur  à chaque coup représenté par les feuilles. Cette
 * valeur est une estimation et permet d'éviter un besoin de ressource exponentiel.
 * A chaque niveau de profondeur on remonte successivement la valeur, du minimum quand
 * c'est au tour de l'adversaire ou du maximum quand c'est au tour du joueur cible.
 * Chaque joueur essaye de maximiser son impact tout en minimisant celui de son adversaire.
 * Un fois les valeurs remontée à la racine, on donne le coup avec le score le plus élévé
 * qui semble donc être le meilleur à ce stade.</p><p>Une amélioratoin a été étudiée, elle
 * permet de conserver une carte qui garde en mémoire la qualité des coups en terme de victoire.
 * Cela permettrait de pondérer les coup donnés par l'algo MinMax pour avoir une estimation
 * plus fine des bons coups grâce à l'apprentissage des parties successives.</p><p>
 * Pour que cet algo fonctionne il doit être appelé uniquement s'il reste au moins une action à faire.</p>
 */
public class AlgoRechercheMinMax extends AlgoRecherche{
    
    /*La profondeur de recherche demandée doit être supérieure à 1 et inférieure
    à 98 (On commence à 0 et l'emplacement 99 est utilisée par le générateur), 
    en pratique la croissance est factorielle en mémoire donc en ne dépassera pas 10*/
    private int depth;
    private Plateau plateau;
    private Joueur target;
    private Joueur opponent;
    private int d_gen;
    private boolean mem;
    
    /**
     * <div>Le constructeur principal d'instance de l'algorithme</div>
     * @param depth La profondeur d'étude déterminites des coups possibles. Entre 1 et 98.
     * @param d_gen Le nombre de tests al"atoires réalisés aux feuilles. Supérieur à 1.
     * @param joueur1 Le premier Joueur, il faut garder en mémoire qui est le joueur cible, celui dont on souhaite maximiser l'impact
     * @param joueur2 Son opposant, les deux joueur doivent être connus pour dubpliquer leur jetons
     * @param mem Paramètre indiquant si l'amélioration de mémoire est active.
     */
    public AlgoRechercheMinMax(int depth, int d_gen, Joueur joueur1, Joueur joueur2, boolean mem){
        this.depth = depth;
        this.d_gen = d_gen;
        //Cet algo ne marche qu'avec des jeux à deux joueurs où alterne les tours car on cher à maximiser l'impact d'un joueur cible un tour sur deux en minimisant l'impact de son opposant le reste du temps
        target = joueur1;
        opponent = joueur2;
        this.mem = mem;
    }
    
    
    //Fonctions de réglage des paramètres
    public void setRandGenDepth(int d_gen){
        this.d_gen = d_gen;
    }
    
    public void setDepth(int depth){
        this.depth = depth;
    }
    
    public void setPlayers(Joueur joueur1, Joueur joueur2){
        target = joueur1;
        opponent = joueur2;
    }
    
    public void setMem(boolean mem){
        this.mem = mem;
    }
    
    public int getRandGenDepth(){
        return d_gen;
    }
    
    public int getDepth(){
        return depth;
    }
    
    public boolean getMem(){
        return mem;
    }
    
    @Override
    public Coup meilleurCoup( Plateau _plateau , Joueur _joueur , boolean _ponder ){
        //On part du principe que la partie n'est pas terminée donc qu'il reste au moins un coup sans quoi la méthode ne peux fonctionner car la racine n'a pas de fils
        plateau = _plateau;
        plateau.sauvegardePosition(0);
        //On détermine du point de vue de quel joueur on évalue la valeur du coup
        if(target != _joueur){
        opponent = target;
        target = _joueur;
        }
        /*
        On construit l'arbre des coups récursivement avec une fonction auxiliaire. La même méthode évalue la valeur des feuilles
        on les remonte avec l'algo du MinMax
        */
        ArbreMinMax explore = new ArbreMinMax();
        builder(explore, target, 0);
        explore.MinMax(0);
        //Si voulu, on pondère les coups
        if(mem){
            for(int i = 0; i < explore.getfils().size() ; i++){
                double coup = explore.getfils().get(i).getvalue();
                int ligne = ((CoupTicTacToe)explore.getfils().get(i).getcoup()).getLigne();
                int colonne = ((CoupTicTacToe)explore.getfils().get(i).getcoup()).getColonne();
                explore.getfils().get(i).setvalue(MemoireMinMax.eval(coup, ligne, colonne));
            }
        }
        //On observe les coups de la racine et leur valeur pour choisir au final quel est le meilleur coup pour le joueur cible
        double m = (double)Integer.MIN_VALUE;
        Coup c = null;
        for(int i = 0; i < explore.getfils().size() ; i++){
            double n = explore.getfils().get(i).getvalue();
            if(n > m){
                m = n;
                c = explore.getfils().get(i).getcoup();
            }
        }
        //On restaure le plateau qui a été modifiée pour évaluer la avleur des coups
        plateau.restaurePosition(0);
        return c;
    }
    
    /**
     * <div>Fonction récursive de création et de parcours de l'abre des coups possibles. Le parcours se fait en profondeur et on associe
     * aux feuilles une valeur estimant la qualité du coup que l'on remontera avec l'algo MinMax</div>
     * @param t L'arbre ou le sous-arbre parcouru
     * @param currentJoueur Le joueur concerné à ce niveau de pronfondeur
     * @param currentDepth Permet de savoir si on approche de la fin de partie et si on doit passer au remplissage des noeuds
     */
    private void builder(ArbreMinMax t,Joueur currentJoueur, int currentDepth){
        /*
        A chaque appel récursif on arrive sur un nouveau noeud qui représente un coup. On ne conserve pas les parents donc on peut savoir les coups prcédent.
        Cependant la méthode de parcours en profondeur préfixe implique que les parents on été visité avant et donc on a pu mémoriser les états de plateau correspondant.
        On commence par mettre le plateau à jour en fonction du coup théorique joué
        */
        if(currentDepth == 0){
            plateau.restaurePosition(0);
        }
        else{
            //On parcours l'arbre en profondeur et on stocke toujours l'état du plateau en cours pour pouvoir naviquer rapidement entre les profondeurs
            plateau.restaurePosition(currentDepth-1);
            plateau.joueCoup(t.getcoup());
            plateau.sauvegardePosition(currentDepth);
        }
        //On crée les nouveau noeuds à partir des coups disponible du point de vue du joueur à ce niveau de l'arbre. Il y a 3 cas.
        if(plateau.partieTerminee()){
            //Si la partie est terminée on est sur une feuille et on attribue directement une valeur au coup en fonction du vainqueur de la partie par rapport au joueur cible de l'algo
            Joueur winner = plateau.vainqueur();
            if(winner == target){
                t.setvalue(1.0);
            }
            else if(winner == opponent){
                t.setvalue(-1.0);
            }
            else {
                t.setvalue(0.0);
            }
        }
        else if (currentDepth == depth){
            //Si on atteinte la profondeur d'évaluation demandée on arrete l'eapansion en profondeur et on crée une feuille auquel on associe une valeur estimée en testants des fin de partie aléatoires à partir de ce point
            double c = (double)Generator.random_tests(plateau, d_gen, target);
            t.setvalue(c);
        }
        else{
            /*
            Dans le dernier cas on se situe sur un noeud interne et on crée les nouveau fils à partir des coups possibles.
            On recrée la liste des coups possibles à chaque fois pour que l'algo soit adaptable à d'autres jeu. En effet, les coups possibles
            peuvent être différents pour les deux joueurs à chaque tour en fonction de comment l'adversaire à joué au tour précédent.
            Cela permet notamment un algo unique pour le tictactoe classique et l'ultimate tictactoe.
            */
            ArrayList<Coup> coups = plateau.getListeCoups(currentJoueur);
            ArrayList<ArbreMinMax> fils = new ArrayList<>();
            for(int i=0; i<coups.size();i++){
                ArbreMinMax a = new ArbreMinMax(coups.get(i));
                if(currentJoueur == target){
                    builder(a, opponent,currentDepth + 1);
                }
                else {
                    builder(a, target,currentDepth + 1);
                }
                fils.add(a);
            }
            t.setfils(fils);
        }
    }
}
