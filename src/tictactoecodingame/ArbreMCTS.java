/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * @author timot
 */
public class ArbreMCTS {
    private Node root;
    
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
