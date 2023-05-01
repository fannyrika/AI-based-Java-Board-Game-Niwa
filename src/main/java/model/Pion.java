package main.java.model;

import java.io.Serializable;
import java.util.Stack;

/**
 * Pourquoi étendre la classe "Stack" ?
 * - Tout simplement parce que le systeme de stockage de perles que possède le pion est la même qu'une pile (dans notre cas : une pile de Couleurs)
 * 
 * En faisant étendre la classe "Stack", plus besoin de redéfinir les méthodes telles que : add(), pop(), peek(), empty(), ...
 */
public class Pion extends Stack<Couleurs> implements Serializable{

    /**
     * Attributs permettant de définir un pion
     */
    protected Joueur proprietaire;
    protected Coordonnee location;
    protected boolean isPlaced;
    private int id=0;
    //protected static int ID_STATIC = 0;

    /**
     * Constructeur permettant d'initialiser le pion
     * @param j -> Proprietaire du pion
     */
    public Pion(Joueur j){
        this.proprietaire = j;
        //this.id = ID_STATIC;
        //ID_STATIC++;
    }

    
    /**
     * deep copy constructor(partially)
     * @param p -> pion to copy
     */
    public Pion(Pion p){
        this.clear();
        this.addAll(p);
        if(p.location != null){
            this.location=new Coordonnee(p.location);
        }
        //else{
        //    throw new IllegalArgumentException("Pion location is null");
        //}
        this.id = p.id;
    }

    /*
     * Méthodes getteurs
     */
    public Joueur getProprietaire(){return proprietaire;}
    public Coordonnee getLocation(){return location;}
    public boolean isPlaced(){return isPlaced;}

    /*
     * Méthodes setteurs
     */
    public void setLocation(Coordonnee c){
        this.location = c;
        isPlaced = true;
    }
    public void setProprietaire(Joueur j){
        this.proprietaire = j;
    }

    public void setLocation(int x, int y){setLocation(new Coordonnee(x, y));}

    /**
     * Méthode pour faire une passe de perle d'un pion à l'autre
     * @param p -> pion qui recoit la perle
     * @return true si le pion actuel possède une perle à donner, false sinon
     */
    public boolean passPerleTo(Pion p){
        if(!empty() && this != p && p.proprietaire == this.proprietaire){
            p.add(pop());
            return true;
        }
        return false;
    }

    public void setIsPlaced(boolean b){
        this.isPlaced = b;
    }

    @Override
    public String toString() {
        return super.toString()+"-id:"+id+"-location:"+location+"-isPlaced:"+isPlaced;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Pion){
            Pion p = (Pion) o;
            //compare all attributes
            // Check if both locations are null
            if (p.location == null && this.location == null) {
                return true;
            }
            // Check if only one location is null
            if (p.location == null || this.location == null) {
                return false;
            }
            if(p.id == this.id 
            && p.location.equals(this.location) && this.containsAll(p) && p.containsAll(this)){
                return true;
            }
        }
        return false;
    }

    //test the equals method
    public static void main(String[] args) {
        Joueur j1 = new Joueur();
        Joueur j2 = new Joueur();
        Pion p1 = new Pion(j1);
        Pion p2 = new Pion(j2);
        p1.add(Couleurs.ROUGE);
        p1.add(Couleurs.ROUGE);
        p2.add(Couleurs.ROUGE);
        p2.add(Couleurs.ORANGE);
        p1.setLocation(new Coordonnee(0, 0));
        p2.setLocation(new Coordonnee(0, 1));

        System.out.println(p1.equals(p2));
    }

    //getID
    public int getID() {
        return id;
    }

    //setID
    public void setID(int id) {
        this.id = id;
    }
}
