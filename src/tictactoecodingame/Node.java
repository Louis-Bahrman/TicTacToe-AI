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
    Coup coup;
    Joueur player;
    Joueur opponent;
    int visits = 0;
    int wins = 0;
    ArrayList<Node> children;
    
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
    
    public void addVisit(){
        visits++;
    }
    
    public void addWin(){
        wins++;
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
