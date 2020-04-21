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
<<<<<<< HEAD:src/tictactoecodingame/ArbreMCTS.java
public class ArbreMCTS {
    
    private Fraction value;
    private ArrayList<Coup> coups;
    private ArrayList<ArbreMCTS> fils;
    
    // Les constructeurs :
    public ArbreMCTS (Fraction value, ArrayList coups, ArrayList fils){
=======
public class Arbre {
    public static int N = 5;
    protected Fraction value;
    protected ArrayList<Coup> coups;
    protected ArrayList<Arbre> fils;
    
    // Les constructeurs :
    
    public Arbre(){   
    }
    
    public Arbre (Fraction value, ArrayList coups, ArrayList fils){
>>>>>>> 354446df7750de3c8bc24e57f0a58c2fce1347bf:src/tictactoecodingame/Arbre.java
        this.value = value;
        this.fils = fils;
        this.coups = coups;
    }
    
<<<<<<< HEAD:src/tictactoecodingame/ArbreMCTS.java
    public ArbreMCTS (int den, int num, ArrayList coups, ArrayList fils){
=======
    public Arbre (int num, int den, ArrayList coups, ArrayList fils){
>>>>>>> 354446df7750de3c8bc24e57f0a58c2fce1347bf:src/tictactoecodingame/Arbre.java
        this.value.den = den;
        this.value.num = num;
        this.fils = fils;
        this.coups = coups;
    }
    
    public ArbreMCTS (Fraction value, Plateau _plateau, Joueur _joueur){
        this.value = value;
        this.coups = _plateau.getListeCoups(_joueur) ;
        int a = coups.size();
        fils = new ArrayList();
        for(int i = 0; i < a ; i++){
            ArbreMCTS Arbre_i = new ArbreMCTS(new Fraction()); // Attention ! Ce constructeur initialise donc avec des valeurs nulles en racine !
            fils.add(Arbre_i);
        }
        
    }
    
    public ArbreMCTS (Fraction value){
        this.value = value;
    }
    
<<<<<<< HEAD:src/tictactoecodingame/ArbreMCTS.java
    public ArbreMCTS (int den, int num){
=======
    public Arbre (int num, int den){
>>>>>>> 354446df7750de3c8bc24e57f0a58c2fce1347bf:src/tictactoecodingame/Arbre.java
        this.value.den = den;
        this.value.num = num;
    }
    
    // Les accesseurs :
    public Fraction getFraction(){
        return value;
    }
    
    public double getvalue(){
        return(value.getNote());
    }
    
    public ArrayList<Arbre> getfils(){
        return(fils);
    }
       
    public ArrayList getcoups(){
        return(coups);
    }
    
    public Fraction getFrac(){
        return(this.value);
    }
    
    //Des choses sans nom :
    public void setvalue(Fraction value){
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
            ArbreMCTS fils0 = this.fils.get(0);
            int maxfils = fils0.hauteur();
            for (int i = 1; i < a; i++){
                ArbreMCTS next = this.fils.get(i);
                maxfils = Math.max(next.hauteur(),maxfils);
            }
            return(1 + maxfils);   
        }
    }
}
