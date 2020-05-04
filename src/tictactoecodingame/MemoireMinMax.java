/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * <p>La classe MémoireMinMax permet de sauvegarder dans un tournoi une copie du plateau où
 * chaque case reçoit un score en fonction des précédentes parties pour affiner le résulat des suivantes.</p>
 * <p>Lorsqu'une partie est jouée toutes les cases qu'il occupe gagnent en poids tandis que celles
 * occupées par le perdant voient leur note diminuer.</p><p>L'algorithme apprend au fur et a mesure des parties
 * mais on ne peut pas l'utiliser dès le début, il faut beaucoup de données sinon la précision de la recherche risque dêtre
 * dégradée par l'imprécision des coefficients.</p><p>Les coeffiencients sont multipliés à la valeur des coups tentés sur une
 * case selon des reglès spécifique pour conserver l'ordre malgré les différences de signes.</p>
 * <p>Cette amélioration est incomptablie avec CodinGame dans la fafonc dont elle est implémentée dans la mesure
 * où elle utilise plusieurs parties et apporte une modification à la classe arbitre pour pouvoir faire un apprentissage
 * progressif dans les parties d'un tournoi.</p>
 */
public class MemoireMinMax {
    private static int mode;
    private static double[][] mem;
    private static int seuil = 100;
    private static int state = 0;
    
    
    /**
     * <div>Méthode permettant de régler la taille de la grille</div>
     * @param grille Grille sur laquelle on joue
     */
    public static void setup(Plateau grille){
        mode = grille.getNbColonnes();
        mem = new double[mode][mode];
    }
    
    public static void setSeuil(int s){
        seuil = s;
    }
    
    public static int getSeuil(){
        return seuil;
    }
    
    /**
     * <div>Fonction d'apprentissage de la mémorisation. On sauvegarde les positions prises par le gagnant et le perdant pour alimenter les coefficients.
     * Si la partie est nulle, elle n'apporte pas d'informations pour la mémorisation. Pour chaque case, on ajoute 1 pour chaque partie gagnée ou ce coup est joué
     * par le gagnant et on retire 1 si ce coup est joué par le perdant. On divise par le total de parties mémorisées.</div>
     * @param grille 
     */
    public static void learn(Plateau grille){
        if(grille.partieNulle()){
            return;
        }
        else{
            //Le nombre de partie analysées est conservé pour garder des coefficients entre -1 et 1
            state++;
            for(int i=0; i<mode; i++){
                for(int j=0; j<mode; j++){
                    Piece piece = grille.getPiece(new Case(j,i));
                    if(piece == null){
                        continue;
                    }
                    else if(piece.getJoueur()==grille.vainqueur()){
                        mem[i][j]=(mem[i][j]*(state-1.0)+1.0)/state;
                    }
                    else{
                        mem[i][j]=(mem[i][j]*(state-1.0)-1.0)/state;
                    }
                }
            }
        }
    }
    
    /**
     * <div>Fonction d'application du coefficient. Cette fonction utilise la note mémorisée pour une case pour moduler la valeur d'un coup.</div>
     * @param coup
     * @param ligne
     * @param colonne
     * @return 
     */
    public static double eval(double coup, int ligne, int colonne){
        //On n'utilise la mémoire des parties précédente qu'apèrs un certain nombre de partie jouées sans quoi les données qu'elle fournit ne sont pas significatives
        if(state < seuil){
            return coup;
        }
        //En foctnion du signe la méthode d'application du coefficient diverge de manière à ne pas arriver à des prédictions contraires
        double note = mem[ligne][colonne];
        if(note*coup < 0){
            return (1.0-Math.abs(note))*coup;
        }
        else{
            return Math.abs(note)*coup;
        }
    }
}
