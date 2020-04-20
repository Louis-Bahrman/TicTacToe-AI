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
 */
public class Arbre {
    
    private int value;
    private ArrayList<Coup> coups;
    private ArrayList<Arbre> fils;
    
    // Les constructeurs :
    public Arbre (int value, ArrayList coups, ArrayList fils){
        this.value = value;
        this.fils = fils;
        this.coups = coups;
    }
    
    public Arbre (int value, Plateau _plateau, Joueur _joueur){
        this.value = value;
        this.coups = _plateau.getListeCoups(_joueur) ;
        int a = coups.size();
        fils = new ArrayList();
        for(int i = 0; i < a ; i++){
            Arbre Arbre_i = new Arbre(0); // Attention ! Ce constructeur initialise donc avec des valeurs nulles en racine !
            fils.add(Arbre_i);
        }
        
    }
    
    public Arbre (int value){
        this.value = value;
    }
    
    // Les accesseurs :
    public double getvalue(){
        return(value);
    }
    
    public ArrayList getfils(){
        return(fils);
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
            Arbre fils0 = this.fils.get(0);
            int maxfils = fils0.hauteur();
            for (int i = 1; i < a; i++){
                Arbre next = this.fils.get(i);
                maxfils = Math.max(next.hauteur(),maxfils);
            }
            return(1 + maxfils);   
        }
    }
}
