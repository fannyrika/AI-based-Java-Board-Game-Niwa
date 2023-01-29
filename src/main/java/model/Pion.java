package main.java.model;

import java.util.Stack;
import main.java.model.Hexagone.Couleurs;

/**
 * Pourquoi étendre la classe "Stack" ?
 * - Tout simplement parce que le systeme de stockage de perles que possède le pion est la même qu'une pile (dans notre cas : une pile de Couleurs)
 * 
 * En faisant étendre la classe "Stack", plus besoin de redéfinir les méthodes telles que : add(), pop(), peek(), empty(), ...
 */
public class Pion extends Stack<Couleurs> {

    /**
     * Attributs permettant de définir un pion
     */
    protected Joueur proprietaire;
    protected Coordonnee location;

    /**
     * Constructeur permettant d'initialiser le pion
     * @param j -> Proprietaire du pion
     */
    public Pion(Joueur j){
        this.proprietaire = j;
    }

    /*
     * Méthodes getteurs
     */
    public Joueur getProprietaire(){return proprietaire;}
    public Coordonnee getLocation(){return location;}

    /*
     * Méthodes setteurs
     */
    public void setLocation(Coordonnee c){this.location = c;}
    public void setLocation(int x, int y){this.location = new Coordonnee(x, y);}
}
