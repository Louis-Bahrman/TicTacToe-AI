/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;

/**
 *
 * @author Théo
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
    
    public AlgoRechercheMinMax(int depth, int d_gen, Joueur joueur1, Joueur joueur2){
        this.depth = depth;
        this.d_gen = d_gen;
        //Cet algo ne marche qu'avec des jeux à deux joueurs
        target = joueur1;
        opponent = joueur2;
    }
    
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
    
    public int getRandGenDepth(int d_gen){
        return d_gen;
    }
    
    public int getDepth(int depth){
        return depth;
    }
    
    @Override
    public Coup meilleurCoup( Plateau _plateau , Joueur _joueur , boolean _ponder ){
        //On part du principe que la partie n'est pas terminée donc qu'il reste au moins un coup
        plateau = _plateau;
        plateau.sauvegardePosition(0);
        if(target != _joueur){
        opponent = target;
        target = _joueur;
        }
        ArbreMinMax explore = new ArbreMinMax();
        builder(explore, target, 0);
        explore.MinMax(0);
        int m = Integer.MIN_VALUE;
        Coup c = null;
        for(int i = 0; i < explore.getfils().size() ; i++){
            int n = explore.getfils().get(i).getvalue();
            if(n > m){
                m = n;
                c = explore.getfils().get(i).getcoup();
            }
        }
        plateau.restaurePosition(0);
        return c;
    }
    
    //Fonction auxiliaire récursive de création de l'arbre des coups possibles
    private void builder(ArbreMinMax t,Joueur currentJoueur, int currentDepth){
        //On commence par mettre le plateau à jour en fonction du coup théorique joué
        if(currentDepth == 0){
            plateau.restaurePosition(0);
        }
        else{
            plateau.restaurePosition(currentDepth-1);
            plateau.joueCoup(t.getcoup());
            plateau.sauvegardePosition(currentDepth);
        }
        //On crée les nouveau noeuds à partir des coups disponible du point de vue du joueur à ce niveau de l'arbre
        if(plateau.partieTerminee()){
            Joueur winner = plateau.vainqueur();
            if(winner == target){
                t.setvalue(1);
            }
            else if(winner == opponent){
                t.setvalue(-1);
            }
            else {
                t.setvalue(0);
            }
        }
        else if (currentDepth == depth){
            int c = Generator.random_tests(plateau, d_gen, target);
            t.setvalue(c);
        }
        else{
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
