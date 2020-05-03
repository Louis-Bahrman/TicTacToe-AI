/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * <div>Cette classe instancie les noeuds d'un arbre. Chaque noeud contient un certain nombre d'informations :</div>
 * <ul>
 * <li>Un coup : Ce coup représente le coup qui <strong>vient</strong> d'être joué.</li>
 * <li>Deux joueurs : Ces joueurs représentent respectivement le joueur qui va jouer, et son adversaire, soit le joueur qui vient
 * de jouer.</li> 
 * <li>Trois entiers : Ces entiers comptent le nombre de victoires, égalités et nombre total de parties jouées passant par ce noeud.
 * Les victoires sont comptées pour le joueur qui vient de jouer, c'est à dire opponent, étant donné que ces valeurs sont uniquement
 * lues depuis le contexte de leur parent dans l'arbre, où opponent devient player.</li>
 * <li>Une liste de Node : Cette liste représente simplement les enfants du noeud.</li>
 * <li>Un booléen : Ce booléen indique si le noeud est résolu. Un noeud résolu est un noeud représentant une partie terminée, ou un
 * noeud dont tous les fils sont résolus.</li>
 * </ul>
 */
public class Node {
    private final Coup coup;
    private final Joueur player;
    private final Joueur opponent;
    private int visits = 0;
    private int wins = 0;
    private int draws = 0;
    private final ArrayList<Node> children;
    private boolean solved = false;
    
    /**
     * <div>Initialisation standard d'un Node</div>
     * @param c Le coup qui vient d'être joué.
     * @param pl Le joueur qui va jouer le prochain coup.
     * @param o Le joueur qui vient de jouer le coup c.
     */
    public Node(Coup c, Joueur pl, Joueur o){
        coup = c;
        player = pl;
        opponent = o;
        children = new ArrayList<>();
    }
    
    //Les accesseurs et modifieurs nécessaires
    public int visits(){
        return visits;
    }
    
    public int wins(){
        return wins;
    }
    
    public int draws(){
        return draws;
    }
    
    public Joueur player(){
        return player;
    }
    
    public Joueur opponent(){
        return opponent;
    }
    
    public ArrayList<Node> children(){
        return children;
    }

    public Coup coup(){
        return coup;
    }
    
    public boolean solved(){
        return solved;
    }
    
    public void solved(boolean s){
        solved = s;
    }
    
    //Les fonctions permettant d'incrémenter le nombre de visites, victoires et égalités.
    public void addVisit(){
        visits++;
    }
    
    public void addWin(){
        wins ++;
    }
    
    public void addDraw(){
        draws++;
    }

    /** <div>Une fonction permettant de calculer le taux de victoire d'un noeud.Ce taux peut prendre en compte les égalités, en fonction
    du coefficient rentré.</div>
    @return Le taux de victoire.
    */
    public double winrate(){
        if(visits == 0){
            return 0;
        }else{
            return (wins + 0.3*draws)/visits;
        }
    }
    
    /** <div>Une fonction permettant de trouver le Node fils avec le meilleur taux de victoire.</div>
     * @return Le Node avec le meilleur taux de victoire de tous les fils de son père.
     */
    public Node nextPlay(){
        Node bestNode = null;
        double winrate = Double.NEGATIVE_INFINITY;
        Iterator<Node> child = children.iterator();
        Node currentNode; double currentWinrate;
        while(child.hasNext()){
            currentNode = child.next();
            currentWinrate = currentNode.winrate();
            if(currentWinrate > winrate){
                winrate = currentWinrate;
                bestNode = currentNode;
            }
        }
        return bestNode;
    }
    
    //Une méthode permettant de visualiser les informations d'un noeud. Surtout utile pendant le débogage.
    @Override
    public String toString(){
        return coup.toString() + "  " + visits + "  " + winrate() * 100;
    }
}
