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
 *
 * @author timot
 */
public class AlgoRechercheMCTS extends AlgoRecherche {
    ArbreMCTS search;
    
    public AlgoRechercheMCTS(Joueur player, Joueur opponent){
        search = new ArbreMCTS(player, opponent);
    }
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        search = new ArbreMCTS(search.root().player(), search.root().opponent());
        Node root = search.root();
        root.board(_plateau);
        int iterations = 0;
        Random seed = new Random();
        while(iterations < 1){
            iterations++;
            Node nextNode = selection(root);
            if(!nextNode.board().partieTerminee()){
                expansion(nextNode);
            }
            if(!nextNode.children().isEmpty()){
                nextNode = nextNode.children().get(seed.nextInt(nextNode.children().size()));
            }
            Joueur winner = simulate(nextNode);
            update(winner, nextNode);
        }
        
        Node nextPlay = root.nextPlay();
        search.root(nextPlay);
        return nextPlay.board().getDernierCoup();
    }
    
    private Node selection(Node root){
        Node currentNode = root;
        while(!(currentNode.children().isEmpty())){
            currentNode = Fraction.bestChild(currentNode);
        }
        return currentNode;
    }
    
    private void expansion(Node leaf){
        ArrayList<Coup> coups = leaf.getCoups();
        Iterator<Coup> coup = coups.iterator();
        Plateau leafPlateau = (new Node(leaf.board(), null, null, null)).board();
        Coup currentCoup;
        while(coup.hasNext()){
            currentCoup = coup.next();
            leafPlateau.joueCoup(currentCoup);
            Node newLeaf = new Node(leafPlateau, leaf.opponent(), leaf.player(), leaf);
            leafPlateau.annuleDernierCoup();
            leaf.children().add(newLeaf);
        }
    }
    
    private Joueur simulate(Node node){
        Node tmp = new Node(node.board(), node.player(), node.opponent(), node.parent());
        Plateau board = tmp.board();
        Joueur p1 = node.player();Joueur p2 = node.opponent();
        Joueur currentPlayer = node.player();
        Random seed = new Random();
        while(!board.partieTerminee()){
            ArrayList<Coup> coups = board.getListeCoups(currentPlayer);
            Coup coup = coups.get(seed.nextInt(coups.size()));
            board.joueCoup(coup);
            if(currentPlayer.equals(p1)){
                currentPlayer = p2;
            }else{
                currentPlayer = p1;
            }
        }
        return board.vainqueur();
    }
    
    private void update(Joueur winner, Node node){
        Node currentNode = node;
        while(currentNode != null){
            currentNode.addVisit();
            if(currentNode.player() == winner){
                currentNode.addWin();
            }
            currentNode = currentNode.parent();
        }
    }
    
}
