package tictactoecodingame;

/**
 *
 * @author franck
 */

    /*--------------------------------------------------------*/
    /*                 Version jeu en local                   */
    /*--------------------------------------------------------*/
public class Player {

    public static void main(String args[]) {
        
        /*
        Il faut choisir deux joueurs parmi les possibilités ci-dessous car l'id est différent pour chaque joueur et il
        fixe le jeton d'où la nécéssité d'en avoir deux distincts.
        */
        // JoueurHumain humain1 = new JoueurHumain("Humain1");
        // JoueurHumain humain2 = new JoueurHumain("Humain2");
        JoueurOrdi joueurOrdi1 = new JoueurOrdi("Ordi1");
        JoueurOrdi joueurOrdi2 = new JoueurOrdi("Ordi2");
        
        //Grille de tictactoe classique
        GrilleTicTacToe3x3 grille = new GrilleTicTacToe3x3();
        
        //Grille d'ultimate tictactoe
        //GrilleTicTacToe9x9 grille = new GrilleTicTacToe9x9();
       
        
        AlgoRechercheMinMax minmax  = new AlgoRechercheMinMax(3, 10, joueurOrdi1, joueurOrdi2, true);
        AlgoRechercheMCTS mcts1000 = new AlgoRechercheMCTS(joueurOrdi2, joueurOrdi1, 1000, Math.sqrt(2), grille.getNbLignes(), grille.getNbColonnes(), false);
        
        joueurOrdi1.setAlgoRecherche(minmax);
        joueurOrdi2.setAlgoRecherche(mcts1000);
             
         

        Arbitre a = new Arbitre(grille, joueurOrdi1 , joueurOrdi2);

        //Arbitre a = new Arbitre(grille, joueurOrdi , humain );
       
        //a.startNewGame(true);    // Demarre une partie en affichant la grille du jeu
       
       // Pour lancer un tournooi de 1000 parties sans afficher la grille du jeu
        a.startTournament(1000 , false);
        
    }
}

    /*--------------------------------------------------------*/
    /*                 Version Codin game                     */
    /*--------------------------------------------------------*/

    /*
    import java.util.Scanner;



    class Player {

       public static void main(String args[]) {

            Scanner in = new Scanner(System.in);

            CoupTicTacToe3x3 coup;
            JoueurHumain adversaire = new JoueurHumain("Adversaire");
            JoueurOrdi joueurOrdi = new JoueurOrdi("Ordi");

            AlgoRechercheAleatoire alea  = new AlgoRechercheAleatoire( );   // L'ordinateur joue au hasard
            joueurOrdi.setAlgoRecherche(alea);

            GrilleTicTacToe3x3 grille = new GrilleTicTacToe3x3();
            grille.init();


            while (true) {
                int opponentRow = in.nextInt();
                int opponentCol = in.nextInt();
                int validActionCount = in.nextInt();
                for (int i = 0; i < validActionCount; i++) {
                    int row = in.nextInt();
                    int col = in.nextInt();
                }
                if ( opponentCol != -1  ) {
                    coup = new CoupTicTacToe3x3(opponentCol, opponentRow, new Jeton(adversaire));
                    grille.joueCoup(coup);
                }

                coup = (CoupTicTacToe3x3) joueurOrdi.joue(grille);
                grille.joueCoup(coup);
                System.out.println(coup.getLigne() + " " + coup.getColonne() ); 
                System.out.flush();
            }
       }
    
}
*/