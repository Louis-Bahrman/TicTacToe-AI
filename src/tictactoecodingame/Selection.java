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
public class Selection {
    ArrayList<Integer> chemin;
    
    public Selection(){
        chemin=new ArrayList<>();
    }
    
    
    public Selection(double c, ArbreMCTS a){
        Selection s=new Selection();
        chemin=SelectionAux(c, a, s, 0).chemin;
    }
    
    private Selection SelectionAux(double c, ArbreMCTS a, Selection s, int nbSimulPere){
        if (a.estFeuille()){
            return s;
        }
        else {
            double coefMax=0;
            int indiceSelection=0;
            ArrayList<ArbreMCTS> f=a.getfils();
            for(int i=0;i<f.size();i++){
                ArbreMCTS arbreTeste = f.get(i);
                double coef =arbreTeste.getvalue()+c*Math.sqrt(Math.log(nbSimulPere)/arbreTeste.getFraction().getDenominateur());
                if (coef>coefMax){
                    indiceSelection=i;
                    coefMax=coef;
                }
            }
            ArbreMCTS arbreSelectionne=f.get(indiceSelection);
            int nbSimulPereNew=arbreSelectionne.getFraction().getDenominateur();
            s.chemin.add(indiceSelection);
            return SelectionAux(c,arbreSelectionne,s,nbSimulPereNew);
        }
    }
}
