package main.java.model;

import java.util.Stack;

public class Pion{
    public int ownerId;
    private int id;
    private Stack<Couleur> tete = new Stack<Couleur>();
    private Coordonnee location=null;

    public Pion(int o){
        ownerId=o;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    //sur chaque pion, le joueur dispose 2 perles de meme couleur
    public void initialiserTete(Couleur c){
        tete.add(c);
        tete.add(c);
    }

    public void setLocation( Coordonnee newLocation ){
        location=newLocation;
    }

    public Coordonnee getLocation(){
        return location;
    }

}