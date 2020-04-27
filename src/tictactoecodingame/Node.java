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
 * @author timot
 */
public class Node {
    Plateau board;
    Joueur player;
    Joueur opponent;
    int visits = 0;
    int wins = 0;
    Node parent;
    ArrayList<Node> children;
    
    public Node(Plateau b, Joueur pl, Joueur o, Node pr){
        board = b;
        player = pl;
        opponent = o;
        parent = pr;
        children = new ArrayList<>();
    }
    
    public Node(Plateau b){
        board = b;
    }
    
    public ArrayList<Coup> getCoups(){
        return board.getListeCoups(player);
    }
    
    public Node parent(){
        return parent;
    }
    
    public int visits(){
        return visits;
    }
    
    public int wins(){
        return wins;
    }
    
    public Plateau board(){
        return board;
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
    
    public void addVisit(){
        visits++;
    }
    
    public void addWin(){
        wins++;
    }
    
    public void board(Plateau p){
        board = p;
    }
    
    public Node nextPlay(){
        Node bestNode = null;
        double winrate = 0;
        Iterator<Node> child = children.iterator();
        Node currentNode; double currentWinrate;
        while(child.hasNext()){
            currentNode = child.next();
            if(currentNode.visits == 0 ){
                currentWinrate = 0;
            }else{
                currentWinrate = currentNode.wins() / currentNode.visits();
            }
            if( currentWinrate > winrate){
                winrate = currentWinrate;
                bestNode = currentNode;
            }
        }
        return bestNode;
    }
}
