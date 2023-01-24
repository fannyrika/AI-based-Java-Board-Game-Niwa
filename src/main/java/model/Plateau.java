package main.java.model;

import java.util.HashMap;

public class Plateau {

    /*
     * Attributs permettant de définir un plateau
     */
    protected HashMap<Coordonnee,Tuile> plateau;

    /*
     * Constructeur sans arguments, initialisant les attributs
     */
    public Plateau(){
        this.plateau = new HashMap<Coordonnee,Tuile>();
    }

    /**
     * Méthode pour placer une tuile à une certaine coordonnee de manière forcée
     * c'est-à-dire que la tuile se placera sur la coordonnee, sans vérifier les contraintes de placement (sauf s'il y a déjà une tuile à cette emplacement)
     * ( exemple : la tuile se placera même s'il n'y a pas de tuile autour d'elle avec qui se coller )
     * 
     * @return true si la tuile a été placée
     * @return false sinon
     */
    public boolean placeTuileForce(Tuile t, Coordonnee c){
        if(plateau.containsKey(c)){return false;}
        plateau.put(c, t);
        return true;
    }
    public boolean placeTuileForce(Tuile t, int x, int y){
        return placeTuileForce(t, new Coordonnee(x, y));
    }

    /**
     * Même méthode qu'au dessus, mais en prenant en compte les contraintes de placement
     * exemple : la tuile ne se place que si elle est collée au moins à une autre tuile
     * @return true si la tuile a été placée
     * @return false sinon
     */
    public boolean placeTuileContraint(Tuile t, Coordonnee c){
        return placeTuileContraint(t, c.x, c.y);
    }
    public boolean placeTuileContraint(Tuile t, int x, int y){
        Coordonnee haut = new Coordonnee(x, y+1);
        Coordonnee bas = new Coordonnee(x, y-1);
        Coordonnee droite = new Coordonnee(x+1, y);
        Coordonnee gauche = new Coordonnee(x-1, y);
        if(plateau.containsKey(haut) || plateau.containsKey(bas) || plateau.containsKey(droite) || plateau.containsKey(gauche)){
            placeTuileForce(t, x, y);
            return true;
        }
        return false;
    }

    /*
     * Méthode pour savoir si l'emplacement sur la coordonnee en argument est occupé ou non
     */
    public boolean isOccupee(Coordonnee c){
        return plateau.containsKey(c);
    }

    /*
     * Idem, mais avec deux entiers directement
     */
    public boolean isOccupee(int x, int y){
        return isOccupee(new Coordonnee(x, y));
    }
    
}
