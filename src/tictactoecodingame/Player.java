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
        
        //JoueurHumain humain = new JoueurHumain("Humain");  
        //JoueurOrdi joueurOrdiRnd = new JoueurOrdi("OrdiRnd");
        JoueurOrdi joueurOrdiMCTS1000 = new JoueurOrdi("OrdiMCTS1000");
        JoueurOrdi joueurOrdiMCTS5000 = new JoueurOrdi("OrdiMCTS5000");
        //JoueurOrdi joueurOrdiMCTS10000 = new JoueurOrdi("OrdiMCTS10000");
        double coeff = Math.sqrt(2);
        
        GrilleTicTacToe3x3 grille = new GrilleTicTacToe3x3();
        /*
        AlgoRechercheAleatoire aleaRnd = new AlgoRechercheAleatoire();
        joueurOrdiRnd.setAlgoRecherche(aleaRnd);
        */
        AlgoRechercheMCTS mcts1000 = new AlgoRechercheMCTS(joueurOrdiMCTS1000, joueurOrdiMCTS5000, 1000, coeff, grille.getNbLignes(), grille.getNbColonnes(), true);
        joueurOrdiMCTS1000.setAlgoRecherche(mcts1000);
        
        AlgoRechercheMCTS mcts5000 = new AlgoRechercheMCTS(joueurOrdiMCTS5000, joueurOrdiMCTS1000, 5000, coeff, grille.getNbLignes(), grille.getNbColonnes(), true);
        joueurOrdiMCTS5000.setAlgoRecherche(mcts5000);
        
        /*
        AlgoRechercheMCTS mcts10000  = new AlgoRechercheMCTS(joueurOrdiMCTS10000, joueurOrdiMCTS1000, 10000,coeff, grille.getNbLignes(), grille.getNbColonnes(), false);
        joueurOrdiMCTS10000.setAlgoRecherche(mcts10000);                              
        */

        Arbitre a = new Arbitre(grille,  joueurOrdiMCTS1000, joueurOrdiMCTS5000);
        //a.startNewGame(true);    // Demarre une partie en affichant la grille du jeu
       
        // Pour lancer un tournoi de 100 parties en affichant la grille du jeu
        
        int[] results = {0,0,0};Joueur winner;
        for(int i = 0; i < 100; i++){
            System.out.println("Round " + i);
            winner = a.startTournament(100 , false);            
            if(winner != null){
                 if(winner.equals(joueurOrdiMCTS1000)){
                     results[0]++;
                 }else{
                     results[1]++;
                 }           
            }else{
                results[2]++;
            }
        }
        System.out.println();
        
        System.out.println("1000 vs 5000");
        System.out.println("Ordi MCTS 1000 :" + results[0]);
        System.out.println("Ordi MCTS 5000 :" + results[1]);
        System.out.println("Draws :" + results[2]);
        
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