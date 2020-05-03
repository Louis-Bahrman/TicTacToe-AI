/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * <div>Cette classe englobe la structure de noeud en un arbre, et permets d'agir sur sa racine.</div>
 */
public class ArbreMCTS {
    private Node root;
    
    /**
     * <div>Initialisation d'un arbre : La racine représente un plateau vide.</div>
     * @param pl Le joueur qui va jouer.
     * @param o  Le joueur qui vient de jouer.
     */
    public ArbreMCTS(Joueur pl, Joueur o){
        root = new Node(null, pl, o);
    }
    
    public Node root(){
        return root;
    }
    
    public void root(Node r){
        root = r;
    }
}
