/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;

/**
 *
 * <p>Structure d'arbre pour l'algoritme MinMax.</p><p>Cette structure est récursive et peut être un noeud ou un élément vide.
 * Les noeuds référencent un ou plusieurs coups, une valeur évaluant la qualité dues coups et une liste pointant vers d'autrs noeuds qui sont 
 * ses fils.</p>
 */
public class ArbreMinMax {
    
    //La valeur est stockée dans un double pour povoir est modulée facilement par l'amélioration de mémorisation
    protected double value;
    //Coup théorique joué à ce noeud
    protected Coup coup;
    //Si on fait plusieurs coups
    protected ArrayList<Coup> coups;
    protected ArrayList<ArbreMinMax> fils;
    
    // Les constructeurs :
    
    //L'abre vide est représenté par des attributs vides
    public ArbreMinMax(){   
    }
    
    public ArbreMinMax(Coup coup){
        this.coup = coup;
    }
    
    public ArbreMinMax(double value, ArrayList coups, ArrayList fils){
        this.value = value;
        this.fils = fils;
        this.coups = coups;
    }
    
    
    public ArbreMinMax(double value, Plateau _plateau, Joueur _joueur){
        this.value = value;
        this.coups = _plateau.getListeCoups(_joueur) ;
        int a = coups.size();
        fils = new ArrayList();
        for(int i = 0; i < a ; i++){
            ArbreMinMax Arbre_i = new ArbreMinMax(0); // Attention ! Ce constructeur initialise donc avec des valeurs nulles en racine !
            fils.add(Arbre_i);
        }
        
    }
    
    public ArbreMinMax(double value){
        this.value = value;
    }

    
    // Les accesseurs :
    public double getvalue(){
        return(value);
    }
    
    public ArrayList<ArbreMinMax> getfils(){
        if(fils != null){
            return(fils);
        }
        else{
            return null;
        }
    }
    
    public Coup getcoup(){
        return coup;
    }
    
    public ArrayList<Coup> getcoups(){
        return(coups);
    }
    
    
    //Les méthodes de réglage des attributs :
    public void setvalue(double value){
        this.value = value;
    }
    
    public void setfils(ArrayList fils){
        this.fils = fils;
    }
    
    public void setcoups(ArrayList coups){
        this.coups = coups;
    }
    
    //Fonctions auxiliaires :
    public boolean estFeuille(){
        return(fils == null);
    }
    
    public boolean estNoeud(){
        return(fils != null);
    }
    
    public int hauteur(){
        if(this.estFeuille()){
            return 1;
        }
        else{
            int a = this.fils.size();
            ArbreMinMax fils0 = this.fils.get(0);
            int maxfils = fils0.hauteur();
            for (int i = 1; i < a; i++){
                ArbreMinMax next = this.fils.get(i);
                maxfils = Math.max(next.hauteur(),maxfils);
            }
            return(1 + maxfils);   
        }
    }
    
    //fonctions auxiliaires pour l'algo MinMax
    
    /**
     * <div>Fonction déterminant la valeur minimum parmi les noeuds fils</div>
     * @return 
     */
    public double Min(){
        double m = (double)Integer.MAX_VALUE;
        int a = this.getfils().size();
        for(int i = 0; i < a ; i++){
            double n = this.getfils().get(i).getvalue();
            if(n < m){
                m = n;
            }
        }
        return m;
    }
    
    /**
     * <div>Fonction déterminant la valeur maximum parmi les noeuds fils</div>
     * @return 
     */
    public double Max(){
        double m = (double)Integer.MIN_VALUE;
        int a = this.getfils().size();
        for(int i = 0; i < a ; i++){
            double n = this.getfils().get(i).getvalue();
            if(n > m){
                m = n;
            }
        }
        return m;
    }
    
    /**
     * <div>Algorithme du Minmax. L'algo fonctione récursivement, on mémorise la profondeur à laquelle on se situe
     * pour savoir si on remonte la valeur maximale où minimale. A la racine on veut toukours le maximum puisqu'on évalue du point
     * de vue du joeuru cible. Ensuite la parité de la profondeur permet déterminer le joueur qui s'apprêtre à jouer</div>
     * @param c Porofndeur en cours dans le parcours de l'arbre
     */
    public void MinMax(int c){
        // c = compteur 
        // Le compteur doit être initialisé à 0 donc pair -> Max, impair -> Min
        
        if(!this.estFeuille()){
            int a = this.getfils().size();
            for(int i = 0; i < a ; i++){
                 this.getfils().get(i).MinMax(c+1);
            }
            if(c%2 == 0){
                //On attribue le max
                double m = this.Max();
                this.setvalue(m);   
            }
            else{
                //On attribue le min
                double m = this.Min();
                this.setvalue(m);
            }
        }
    }
    
}
