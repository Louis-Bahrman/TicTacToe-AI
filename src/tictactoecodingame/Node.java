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
    private Coup coup;
    private final Joueur player;
    private final Joueur opponent;
    private int visits = 0;
    private int wins = 0;
    private final ArrayList<Node> children;
    
    public Node(Coup c, Joueur pl, Joueur o){
        coup = c;
        player = pl;
        opponent = o;
        children = new ArrayList<>();
    }
    
    public int visits(){
        return visits;
    }
    
    public int wins(){
        return wins;
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
    
    public void coup(Coup c){
        coup = c;
    }
       
    public void addVisit(){
        visits++;
    }
    
    public void addWin(){
        wins++;
    }
    
    public double winrate(){
        if(visits == 0){
            return 0;
        }else{
            return wins/visits;
        }
    }
     
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
}
