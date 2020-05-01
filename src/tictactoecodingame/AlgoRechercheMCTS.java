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
    private final ArbreMCTS search;
    private final int maxIterations;
    private final Random seed;
    private ArrayList<Integer> chemin;
    private final double coeff;
    
    public AlgoRechercheMCTS(Joueur player, Joueur opponent, int m, double c){
        search = new ArbreMCTS(player, opponent);
        maxIterations = m;
        seed = new Random();
        coeff = c;
    }
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {

        Coup lastPlayed = _plateau.getDernierCoup();
        if(search.root().coup() == null){
            search.root().coup(lastPlayed);
            expansion(search.root(), _plateau);
        }
        Node tmp = null;
        Iterator<Node> children = search.root().children().iterator();
        if(lastPlayed != null){
            while(children.hasNext()){
                tmp = children.next();
                if(tmp.coup().equals(lastPlayed)){
                    search.root(tmp);
                }
            }
        }
        Node root = search.root();
        _plateau.sauvegardePosition(0);
        int iterations = 0;
        int trivial = 0;
        while(iterations < maxIterations){
            iterations++;
            chemin = new ArrayList<>();
            Node nextNode = selection(root, _plateau);
            if(!_plateau.partieTerminee()){
                expansion(nextNode, _plateau);
                trivial++;
            }
            if(!nextNode.children().isEmpty()){
                nextNode = nextNode.children().get(seed.nextInt(nextNode.children().size()));
                _plateau.joueCoup(nextNode.coup());
            }
            Joueur winner = simulate(nextNode, _plateau);
            backPropagation(root, winner);
            _plateau.restaurePosition(0);
        }
        Node nextPlay = root.nextPlay();
        search.root(nextPlay);
        return nextPlay.coup();
    }
    
    public Node selection(Node n, Plateau pl){
        if (n.children().isEmpty()){
	    return n;
        }
        else {
            double valMax=Double.NEGATIVE_INFINITY;
            int indiceSelection=0;
            ArrayList<Node> f = n.children();
            double val;
            for(int i = 0; i < f.size(); i++){
                Node nf = f.get(i);
                if(nf.visits() == 0){
                    val = Double.MAX_VALUE;
                }else{
                    val = (nf.wins()/nf.visits())+coeff*Math.sqrt(Math.log(n.visits())/nf.visits());
                }
                if (val>valMax){
                    indiceSelection=i;
                    valMax=val;
                }
            }
            Node noeudSelectionne=f.get(indiceSelection);
	    pl.joueCoup(noeudSelectionne.coup());
            chemin.add(indiceSelection);
            return selection(noeudSelectionne, pl);
        }       
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
    
    public void backPropagation(Node n, Joueur gagnant){
        Node currentNode = n; int index = 0;
        do{
            currentNode.addVisit();
            if (currentNode.player().equals(gagnant)){
                currentNode.addWin();
            }
            currentNode = currentNode.children().get(chemin.get(index));
            index++;
        }while(index < chemin.size());
        currentNode.addVisit();
        if (currentNode.player().equals(gagnant)){
            currentNode.addWin();
        }
    }
    
}
