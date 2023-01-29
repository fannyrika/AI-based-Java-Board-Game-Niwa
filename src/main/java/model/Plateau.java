package main.java.model;

import java.util.HashMap;

public class Plateau {

    /**
     * Attributs permettant de définir un plateau
     */
    protected HashMap<Coordonnee,Tuile> gridTuile;
    protected HashMap<Coordonnee,Hexagone> gridHexagone;

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
     * Méthode privée, permettant de placer tous les hexagones d'une tuile dans le gridHexagone
     * 
     * @param t -> La tuile qu'on place, pour récupérer ses hexagones
     * @param ghc -> la variable "ghc" représente les coordonnées de l'hexagone centrale dans le gridHexagone, et non pas dans le gridTuile
     */
    private void placerHexagones(Tuile t, Coordonnee ghc){
        gridHexagone.put(ghc, t.centre.copy());
        int x = ghc.x;
        int y = ghc.y;
        if(ghc.x % 2 == 0){
            tamponHexagone(t.hexagones[0], new Coordonnee(x+1, y));
            tamponHexagone(t.hexagones[1], new Coordonnee(x+2, y));
            tamponHexagone(t.hexagones[2], new Coordonnee(x+1, y-1));
            tamponHexagone(t.hexagones[3], new Coordonnee(x-1, y-1));
            tamponHexagone(t.hexagones[4], new Coordonnee(x-2, y));
            tamponHexagone(t.hexagones[5], new Coordonnee(x-1, y));
        }
        else{
            tamponHexagone(t.hexagones[0], new Coordonnee(x+1, y+1));
            tamponHexagone(t.hexagones[1], new Coordonnee(x+2, y));
            tamponHexagone(t.hexagones[2], new Coordonnee(x+1, y));
            tamponHexagone(t.hexagones[3], new Coordonnee(x-1, y));
            tamponHexagone(t.hexagones[4], new Coordonnee(x-2, y));
            tamponHexagone(t.hexagones[5], new Coordonnee(x-1, y+1));
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
            Hexagone placed = gridHexagone.get(c);
            placed.tamponBy(h);
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
        Coordonnee n = null;
        Coordonnee ne = null;
        Coordonnee se = null;
        Coordonnee s = null;
        Coordonnee so = null;
        Coordonnee no = null;
        if(x%2==0){
            n = new Coordonnee(x, y+1);
            ne = new Coordonnee(x+1, y);
            se = new Coordonnee(x+1, y-1);
            s = new Coordonnee(x, y-1);
            so = new Coordonnee(x-1, y-1);
            no = new Coordonnee(x-1, y);
        }
        else{
            n = new Coordonnee(x, y+1);
            ne = new Coordonnee(x+1, y+1);
            se = new Coordonnee(x+1, y);
            s = new Coordonnee(x, y-1);
            so = new Coordonnee(x-1, y);
            no = new Coordonnee(x-1, y+1);
        }
        if(gridTuile.containsKey(n) || gridTuile.containsKey(ne) || gridTuile.containsKey(se) || gridTuile.containsKey(s) || gridTuile.containsKey(so) || gridTuile.containsKey(no)){
            placeTuileForce(t, x, y);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour savoir si l'emplacement sur la coordonnee en argument est occupé ou non
     * 
     * @param c -> Coordonnee dans le gridTuile
     */
    public boolean isOccupee(Coordonnee c){
        return gridTuile.containsKey(c);
    }

    /**
     * Idem, mais avec deux entiers directement
     */
    public boolean isOccupee(int x, int y){
        return isOccupee(new Coordonnee(x, y));
    }
    
}
