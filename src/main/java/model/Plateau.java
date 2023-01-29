package main.java.model;

import java.util.HashMap;

import main.java.model.interfaces.HexagoneAutour;
import main.java.model.interfaces.TuilesAutour;

public class Plateau {

    /**
     * Attributs permettant de définir un plateau
     */
    protected HashMap<Coordonnee,Tuile> gridTuile;
    protected HashMap<Coordonnee,Hexagone> gridHexagone;
    protected HashMap<Coordonnee,Pion> gridPion;

    /**
     * Constructeur sans arguments, initialisant les attributs
     */
    public Plateau(){
        this.gridTuile = new HashMap<Coordonnee,Tuile>();
        this.gridHexagone = new HashMap<Coordonnee,Hexagone>();
    }

    /**
     * Méthode pour placer une tuile à une certaine coordonnee de manière forcée
     * c'est-à-dire que la tuile se placera sur la coordonnee, sans vérifier les contraintes de placement (sauf s'il y a déjà une tuile à cette emplacement)
     * ( exemple : la tuile se placera même s'il n'y a pas de tuile autour d'elle avec qui se coller)
     * 
     * @param t -> Tuile à placée
     * @param c -> Coordonnee dans le gridTuile
     * 
     * @return true si la tuile a été placée
     * @return false sinon
     */
    public boolean placeTuileForce(Tuile t, Coordonnee c){
        if(gridTuile.containsKey(c)){return false;}
        gridTuile.put(c, t);
        placerHexagones(t, new Coordonnee(c.x*3, c.y));
        return true;
    }

    public boolean placeTuileForce(Tuile t, int x, int y){
        return placeTuileForce(t, new Coordonnee(x, y));
    }

    /**
     * Méthode de placement la plus puissante, elle ignore toutes les contraintes et place la tuile quoiqu'il arrive
     * 
     * @param t -> la tuile a placée
     * @param c -> les coordonees dans le gridTuile
     * @return forcément true
     */
    public boolean placeTuileBrutForce(Tuile t, Coordonnee c){
        if(gridTuile.containsKey(c)){
            gridTuile.remove(c);
        }
        placeTuileForce(t, c);
        return true;
    }

    /**
     * Méthode privée, permettant de placer/d'imprimer tous les hexagones d'une tuile dans le gridHexagone
     * 
     * @param t -> La tuile qu'on place, pour récupérer ses hexagones
     * @param ghc -> la variable "ghc" représente les coordonnées de l'hexagone centrale dans le gridHexagone, et non pas dans le gridTuile
     */
    private void placerHexagones(Tuile t, Coordonnee ghc){
        t.centre.setLocation(ghc);
        gridHexagone.put(ghc, t.centre.copy());
        Coordonnee[] ha = HexagoneAutour.get(ghc);
        for (int i = 0; i < t.hexagones.length; i++) {
            tamponHexagone(t.hexagones[i], ha[i]);
        }
    }

    /**
     * Méthode privée, permettant d'imprimer les portes de l'hexagone @param h sur la coordonnee @param c dans le gridHexagone
     * Si l'emplacement aux coordonnees indiqué n'est pas occupé, on "put" simplement une copie de @param h à cet emplacement
     * 
     * @param h -> Hexagone dont les portes sont imprimées sur le gridHexagone
     * @param c -> Coordonnee dans le gridHexagone
     */
    private void tamponHexagone(Hexagone h, Coordonnee c){
        if(gridHexagone.containsKey(c)){
            Hexagone old = gridHexagone.get(c);
            old.tamponBy(h);
        }
        else{
            gridHexagone.put(c, h.copy());
        }
    }

    /**
     * Même méthode que placeTuileForce(), mais en prenant en compte les contraintes de placement
     * exemple : la tuile ne se place que si elle est collée au moins à une autre tuile
     * @return true si la tuile a été placée
     * @return false sinon
     */
    public boolean placeTuileContraint(Tuile t, Coordonnee c){
        return placeTuileContraint(t, c.x, c.y);
    }

    public boolean placeTuileContraint(Tuile t, int x, int y){
        Coordonnee[] ta = TuilesAutour.get(new Coordonnee(x, y));
        if(gridTuile.containsKey(ta[0]) || gridTuile.containsKey(ta[1]) || gridTuile.containsKey(ta[2]) || gridTuile.containsKey(ta[3]) || gridTuile.containsKey(ta[4]) || gridTuile.containsKey(ta[5])){
            placeTuileForce(t, x, y);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour placer de force un pion à un certain encdroit (sauf si l'endroit est occupé)
     * @param p -> pion à placer
     * @param c -> endroit où placer le pion
     * @return true si le pion a été placer, false sinon (parce que l'endroit est occupé)
     */
    public boolean placerPionForce(Pion p, Coordonnee c){
        if(!gridPion.containsKey(c)){
            gridPion.remove(p.getLocation());
            p.setLocation(c);
            gridPion.put(c, p);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour placer un pion autour du temple du proprietaire avant que la partie commence
     * @param p -> Le pion à placer
     * @param c -> L'endroit où placer le pion
     * @return -> true si le pion est placé (la location du pion est donc mise à jour), false sinon (pion trop loin du temple OU emplacement occupé)
     */
    public boolean placeStartPion(Pion p, Coordonnee c){
        Coordonnee t = p.getProprietaire().temple.getLocationInGridHexagone();
        Coordonnee[] autourTemple = HexagoneAutour.get(t);
        for (Coordonnee autour : autourTemple) {
            if(c.equals(autour)){
                return placerPionForce(p, c);
            }
        }
        return false;
    }    
}