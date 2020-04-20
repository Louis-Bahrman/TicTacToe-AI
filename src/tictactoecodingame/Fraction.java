/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

/**
 *
 * @author louis
 */
public class Fraction {
    int num;
    int den;
    
    public Fraction(int n, int d){
        num=n;
        den=d;
    }
    
    public Fraction(){
        num=0;
        den=0;
    }
    
    public Fraction(int n){
        num=n;
        den=1;
    }
    
    public int getScore(){
        return num;
    }
            
    public double getNote(){
        try{
            return num/den;
        }
        catch(ArithmeticException e){return 1.;}
    }
    
    public int getNumerateur(){
        return num;
    }
    
    public int getDenominateur(){return den;}
    
    public void setDenominateur(int d){den=d;}
    
    public void setNumerateur(int n){
        num=n;
    }
    
    public void incrementeNumerateur(){num+=1;}
    
    public void incrementeDenominateur(){den+=1;}
}
