/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;
import static tictactoecodingame.Generator.random_tests;

/**
 *
 * @author senda
 */
public class ArbreMinMax {
    
    protected int value;
    //Coup théorique joué à ce noeud
    protected Coup coup;
    protected ArrayList<Coup> coups;
    protected ArrayList<ArbreMinMax> fils;
    
    // Les constructeurs :
    
    public ArbreMinMax(){   
    }
    
    public ArbreMinMax(Coup coup){
        this.coup = coup;
    }
    
    public ArbreMinMax(int value, ArrayList coups, ArrayList fils){
        this.value = value;
        this.fils = fils;
        this.coups = coups;
    }
    
    
    public ArbreMinMax(int value, Plateau _plateau, Joueur _joueur){
        this.value = value;
        this.coups = _plateau.getListeCoups(_joueur) ;
        int a = coups.size();
        fils = new ArrayList();
        for(int i = 0; i < a ; i++){
            ArbreMinMax Arbre_i = new ArbreMinMax(0); // Attention ! Ce constructeur initialise donc avec des valeurs nulles en racine !
            fils.add(Arbre_i);
        }
        
    }
    
    public ArbreMinMax(int value){
        this.value = value;
    }

    
    // Les accesseurs :
    public int getvalue(){
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
    
    public ArrayList getcoups(){
        return(coups);
    }
    
    
    //Des choses sans nom :
    public void setvalue(int value){
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
    
    public int Min(){
        int m = 2147483640;
        int a = this.getfils().size();
        for(int i = 0; i < a ; i++){
            int n = this.getfils().get(i).getvalue();
            if(n < m){
                m = n;
            }
        }
        return m;
    }
    
    public int Max(){
        int m = 0;
        int a = this.getfils().size();
        for(int i = 0; i < a ; i++){
            int n = this.getfils().get(i).getvalue();
            if(n > m){
                m = n;
            }
        }
        return m;
    }
    
    public void MinMax(int c){
        // c = compteur 
        // Le compteur doit être initialisé à 0 donc pair -> Max, impair -> Min
        
        if(this.getfils() != null){
            int a = this.getfils().size();
            for(int i = 0; i < a ; i++){
                 this.getfils().get(i).MinMax(c+1);
            }
        }
        
        if(c%2 == 0){
            //On attribue le Min
            int m = this.Min();
            this.setvalue(m);   
            }
        else{
            //On attribue le max
            int m = this.Max();
            this.setvalue(m);
            }
        }
        
    }
