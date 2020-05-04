/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * <p>La classe Generator n'a qu'une méthode qui permet d'explorer un certain nombre
 * de fins de partie de manière aléatoire à partir d'un certain point et du poitn de
 * vue d'un joueur donné.</p><p> La méthode renvoie un entier qui permet d'estimer le taux de
 * victoire pour le joueur concerné à partir de ce point.</p><p> Pour chaque fin gagnante pour
 * le joueur cible, la méthode compte +1, pour chque nul 0 et pour chaque défaite -1.
 * C'est la somme de ces valeurs qui est renvoyée en sortie. Cette méthode s'appuie sur
 * le code d'origine et notamment AlgoRechercheAleatoire. En effet, la méthode récupère un
 * plateau en cours et simule deux joueurs IA ayant les même jetons que les joueurs concernés.
 * On leur associe alors l'algo de recherche aleatoire et on laisse la partie se terminer.
 * On relève le vainqueur pour ajuster le score et on remet le plateau au point d'étude
 * avant de recommencer le nombre de fois demandé.</p>
 * 
 */
public class Generator {
    /**
     * <div>Méthode de tests aléatoires à répétition de la classe</div>
     * @param plateau Le plateau en cours à partit duquel on débute les simulations.
     * @param nb_tests Le nombre de tests aléatoires voulus
     * @param target Le joueur de référence pour lequel on comtpe positivement les victoires.
     */
    public static int random_tests(Plateau plateau, int nb_tests,Joueur target) {
        int c = 0;
        //Simulation de deux joueurs IA
        JoueurOrdi player = new JoueurOrdi("player");
        JoueurOrdi opponent = new JoueurOrdi("oppo");
        AlgoRechercheAleatoire alea  = new AlgoRechercheAleatoire( );
        player.setAlgoRecherche(alea);
        opponent.setAlgoRecherche(alea);
        Joueur currentPlayer = player;
        //On détermine qui du joueur cible où de son opposant à la main au début<.
        int i=((CoupTicTacToe)plateau.getDernierCoup()).getJeton().getJoueur().getIdJoueur();
        player.forceId(1-i);
        opponent.forceId(i);
        
        Coup coup;
        //On mémorise la position du plateau à partir du point d'étude pour le réitialiser à chaque nouevelle simultaion
        plateau.sauvegardePosition(99);
        for(i=0; i<nb_tests;i++){
            while (!plateau.partieTerminee()) {
                //Tant que la partie n'est pas terminée on joue un coup aléatoirement
                coup = currentPlayer.joue(plateau);

                plateau.joueCoup(coup);


                if (currentPlayer == player) {
                    currentPlayer = opponent;
                } else {
                    currentPlayer = player;
                }
            }

            Joueur vainqueur = plateau.vainqueur();
            //Si le joueur cible est vainqueur on incrémente l'évaluation, s'il perd on al décrémente, si le match est nul on ne fait rien
            if(vainqueur != null){
                if ( vainqueur.getIdJoueur() == target.getIdJoueur() )
                    c++;
                else if ( vainqueur.getIdJoueur() != target.getIdJoueur() )
                    c--;
            }
            //On utilise la postion 99 des sauvegardes car les premières emplacement sont souvent utilisés
            plateau.restaurePosition(99);
        }
        return c;
    }
}
