/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;

/**
 *
 * @author louis
 */
public class Fraction {
    
    public static double frctValue(int parentVisit, int visits, int wins){
        if(visits == 0){
            return Integer.MAX_VALUE;
        }
        return (wins/visits) * 1.41 * Math.sqrt(Math.log(parentVisit) / visits);
    }
    
    public static Node bestChild(Node root){
        int parentVisit = root.visits();
        int maxIndex = 0;
        double maxScore = 0;
        ArrayList<Node> children = root.children();
        double currentScore; Node currentNode;
        for(int i = 0; i < children.size(); i++){
            currentNode = children.get(i);
            currentScore = frctValue(parentVisit, currentNode.visits(), currentNode.wins());
            if(currentScore > maxScore){
                maxScore = currentScore;
                maxIndex = i;
            }
        }
        return children.get(maxIndex);
    }
}
