/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;


/**
 *
 * @author senda
 
public class AlgoRecherMinMax1 extends AlgoRecherche {
    ArrayList ListCoup;
    
    /*public AlgoRecherMinMax1() {
              
    }
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        ListCoup = _plateau.getListeCoups(_joueur);
        
        
        return ;
    }
}*/

/* L'idée c'est :
- Créer un arbre de coups :
    On récupère les coups dispo
    On fait toutes les possibilités d'enchaînement de coups (<N, <MaxCoupsRestants)
- On applique MinMax
- On rend le meilleur coup selon notre algorythme
*/