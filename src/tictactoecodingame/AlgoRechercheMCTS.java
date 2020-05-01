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
    int maxIterations;
    
    public AlgoRechercheMCTS(Joueur player, Joueur opponent, int m){
        search = new ArbreMCTS(player, opponent);
        maxIterations = m;
    }
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        Node root = search.root();
        _plateau.sauvegardePosition(0);
        int iterations = 0;
        Random seed = new Random();
        while(iterations < maxIterations){
            iterations++;
            Node nextNode = selection(root);
            if(!_plateau.partieTerminee()){
                expansion(nextNode, _plateau);
            }
            if(!nextNode.children().isEmpty()){
                nextNode = nextNode.children().get(seed.nextInt(nextNode.children().size()));
            }
            Joueur winner = simulate(nextNode, _plateau);
            update(winner, nextNode);
            _plateau.restaurePosition(0);
        }
        Node nextPlay = root.nextPlay();
        search.root(nextPlay);
        return nextPlay.coup();
    }
    
    private Node selection(Node root){
        Node currentNode = root;
        while(!(currentNode.children().isEmpty())){
            currentNode = Fraction.bestChild(currentNode);
        }
        return currentNode;
    }
    
    private void expansion(Node leaf, Plateau leafPlateau){
        ArrayList<Coup> coups = leafPlateau.getListeCoups(leaf.player());
        Iterator<Coup> coup = coups.iterator();
        Coup currentCoup;
        while(coup.hasNext()){
            currentCoup = coup.next();
            Node newLeaf = new Node(currentCoup, leaf.opponent(), leaf.player());
            leaf.children().add(newLeaf);
        }
    }
    
    private Joueur simulate(Node node, Plateau board){
        Joueur p1 = node.player();Joueur p2 = node.opponent();
        Joueur currentPlayer = node.player();
        Random seed = new Random();
        Coup coup;
        ArrayList<Coup> coups;
        while(!board.partieTerminee()){
            coups = board.getListeCoups(currentPlayer);
            coup = coups.get(seed.nextInt(coups.size()));
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
            if(currentNode.player().equals(winner)){
                currentNode.addWin();
            }
            currentNode = currentNode.parent();
        }
    }
    
}
