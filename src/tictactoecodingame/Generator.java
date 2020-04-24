/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * @author Théo
 */
public class Generator {
    public static int random_tests(Plateau plateau, int nb_tests,Joueur target) {
        int c = 0;
        JoueurOrdi player = new JoueurOrdi("player");
        JoueurOrdi opponent = new JoueurOrdi("oppo");
        AlgoRechercheAleatoire alea  = new AlgoRechercheAleatoire( );
        player.setAlgoRecherche(alea);
        opponent.setAlgoRecherche(alea);
        Joueur currentPlayer = player;
        int i=((CoupTicTacToe)plateau.getDernierCoup()).getJeton().getJoueur().getIdJoueur();
        player.forceId(1-i);
        opponent.forceId(i);
        
        Coup coup;
        plateau.sauvegardePosition(99);
        for(i=0; i<nb_tests;i++){
            while (!plateau.partieTerminee()) {
                coup = currentPlayer.joue(plateau);

                plateau.joueCoup(coup);


                if (currentPlayer == player) {
                    currentPlayer = opponent;
                } else {
                    currentPlayer = player;
                }
            }

            Joueur vainqueur = plateau.vainqueur();
            if(vainqueur != null){
                if ( vainqueur.getIdJoueur() == target.getIdJoueur() )
                    c++;
                else if ( vainqueur.getIdJoueur() != target.getIdJoueur() )
                    c--;
            }
            plateau.restaurePosition(99);
        }
        return c;
    }
}
