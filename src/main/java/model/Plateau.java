package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.model.interfaces.DeplacementPion;
import main.java.model.interfaces.HexagoneAutour;
import main.java.model.interfaces.TuilesAutour;

public class Plateau implements DeplacementPion {

    /**
     * Attributs permettant de définir un plateau
     */
    protected HashMap<Coordonnee,Tuile> gridTuile;
    protected HashMap<Coordonnee,Hexagone> gridHexagone;
    protected HashMap<Coordonnee,Pion> gridPion;

    public HashMap<Coordonnee,Tuile> getGridTuile(){return gridTuile;}
    public HashMap<Coordonnee,Hexagone> getGridHexagone(){return gridHexagone;}
    public HashMap<Coordonnee,Pion> getGridPion(){return gridPion;}

    /**
     * Constructeur sans arguments, initialisant les attributs
     */
    public Plateau(){
        this.gridTuile = new HashMap<Coordonnee,Tuile>();
        this.gridHexagone = new HashMap<Coordonnee,Hexagone>();
        this.gridPion = new HashMap<Coordonnee,Pion>();
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
        return placeTuileForce(t, c);
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
            return placeTuileForce(t, x, y);
        }
        return false;
    }

    /**
     * Méthode pour placer de force un pion à un certain endroit (sauf si l'endroit est occupé)
     * @param p -> pion à placer
     * @param c -> endroit où placer le pion
     * @return true si le pion a été placé, false sinon (parce que l'endroit est occupé)
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
     * Méthode pour placer un pion autour du temple de son proprietaire avant que la partie commence
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

    /**
     * Méthode privée, toujours dans le sens horaire :
     * @param p -> pion à déplacer
     * @param direction
     * | 0 -> NE
     * | 1 -> E
     * | 2 -> SE
     * | 3 -> SO
     * | 4 -> O
     * | 5 -> NO
     */
    private ArrayList<Coordonnee> canMoveLocationsDirection(Pion p, Coordonnee position, int direction) {
        if(direction > 5){return new ArrayList<Coordonnee>();}        // N'est pas censé arriver si le jeu est fait correctement

        Coordonnee[] choix = HexagoneAutour.get(position);

        if(!gridHexagone.containsKey(choix[direction])){return new ArrayList<Coordonnee>();}      // CAS 0 : Le pion veut aller hors du plateau -> on return une liste vide

        if(p.empty()){    // CAS 1 : Le pion n'a pas de perle
            if(gridPion.containsKey(choix[direction])){     // CAS 1.1 : il y a un personnage sur sa direction -> il doit donc sauter
                Coordonnee[] choixApres = HexagoneAutour.get(choix[direction]);
                Coordonnee c1;
                Coordonnee c2;
                Coordonnee c3;
                if(direction == 0){
                    c1 = choixApres[5];
                    c2 = choixApres[0];
                    c3 = choixApres[1];
                }
                else{
                    c1 = choixApres[direction-1];
                    c2 = choixApres[direction];
                    c3 = choixApres[direction+1];
                }

                if(gridPion.containsKey(c1) && gridPion.containsKey(c2) && gridPion.containsKey(c3)){       // CAS 1.1.1 : Les 3 emplacements derriere sont occupées également, il faut sauter + loin
                    return canMoveLocationsDirection(p, choix[direction], direction);
                }
                else{       // CAS 1.1.2 : Il y a au moins un des emplacements derrière non-occupées
                    ArrayList<Coordonnee> arrives = new ArrayList<Coordonnee>();
                    if(!gridPion.containsKey(c1)){arrives.add(c1);}
                    if(!gridPion.containsKey(c2)){arrives.add(c2);}
                    if(!gridPion.containsKey(c3)){arrives.add(c3);}
                    return arrives;
                }
            }
            else{                                           // CAS 1.2 : il n'y a pas de personnage sur sa direction, il ne peut donc rien faire -> on return une liste vide
                return new ArrayList<Coordonnee>();
            }
        }
        else{           // CAS 2 : Le pion a au moins une perle
            if(gridHexagone.get(position).portes[direction] == p.peek()){   // CAS 2.1 : La couleur de la perle match avec celle de la porte
                ArrayList<Coordonnee> arrive = new ArrayList<Coordonnee>();
                arrive.add(choix[direction]);
                return arrive;
            }
            else{       // CAS 2.2 : La couleur de la perle ne match pas avec la porte -> on return une liste vide
                return new ArrayList<Coordonnee>();
            }
        }
    }

    /**
     * Override de la méthode de l'interface "DeplacementPion" (choix de déplacement d'un pion)
     * @param p -> Pion à déplacer
     */
    @Override
    public ArrayList<Coordonnee> canMoveLocations(Pion p){
        ArrayList<Coordonnee> possibilites = canMoveLocationsDirection(p, p.getLocation(), 0);
        for (int i = 1; i < 6; i++) {
            possibilites.addAll(canMoveLocationsDirection(p, p.getLocation(), i));
        }
        return possibilites;
    }

    /**
     * Méthode permettant de connaître les emplacements libres autour d'une coordonnee
     * @param t -> La tuile
     * @return une liste de coordonnee contenant les places libres
     */
    public ArrayList<Coordonnee> canPlaceLocations(Coordonnee c) {
        Coordonnee[] tuilesAutours = TuilesAutour.get(c);
        ArrayList<Coordonnee> locationsPossibles = new ArrayList<Coordonnee>();
        for(int i=0; i<tuilesAutours.length; i++){
            if(!gridTuile.containsKey(tuilesAutours[i])){
                locationsPossibles.add(tuilesAutours[i]);
            }
        }
        return locationsPossibles;
    }

    public ArrayList<Coordonnee> canPlaceLocations(int x, int y){
        return canPlaceLocations(new Coordonnee(x, y));
    }

    /**
     * Méthode permettant de retirer une tuile déjà posée sur le plateau
     * @Warning -> On ne retire pas simplement la tuile, il faut aussi mettre à jour le gridHexagone pour que le tout coincide
     * @param c -> La coordonnee de la tuile à retirer
     * @return true si retirée avec succes, false sinon
     */
    public boolean removeTuileBrutForce(Coordonnee c) {
        if(!gridTuile.containsKey(c)){return false;}

        Tuile tuile = gridTuile.get(c);
        Coordonnee[] hexagonesAutour = HexagoneAutour.get(tuile.getLocationInGridHexagone());
        gridHexagone.remove(tuile.getLocationInGridHexagone());     // On retire complétement l'hexagone centrale quoiqu'il arrive
        
        // On s'occupe de l'hexagone NE
        gridHexagone.get(hexagonesAutour[0]).portes[2] = null;
        gridHexagone.get(hexagonesAutour[0]).portes[3] = null;
        if(gridHexagone.get(hexagonesAutour[0]).isNull()){gridHexagone.remove(hexagonesAutour[0]);}

        // On s'occupe de l'hexagone E
        gridHexagone.get(hexagonesAutour[1]).portes[4] = null;
        gridHexagone.get(hexagonesAutour[1]).portes[5] = null;
        if(gridHexagone.get(hexagonesAutour[1]).isNull()){gridHexagone.remove(hexagonesAutour[1]);}

        // On s'occupe de l'hexagone SE
        gridHexagone.get(hexagonesAutour[2]).portes[4] = null;
        gridHexagone.get(hexagonesAutour[2]).portes[5] = null;
        if(gridHexagone.get(hexagonesAutour[2]).isNull()){gridHexagone.remove(hexagonesAutour[2]);}

        // On s'occupe de l'hexagone SO
        gridHexagone.get(hexagonesAutour[3]).portes[0] = null;
        gridHexagone.get(hexagonesAutour[3]).portes[1] = null;
        if(gridHexagone.get(hexagonesAutour[3]).isNull()){gridHexagone.remove(hexagonesAutour[3]);}

        // On s'occupe de l'hexagone O
        gridHexagone.get(hexagonesAutour[4]).portes[0] = null;
        gridHexagone.get(hexagonesAutour[4]).portes[1] = null;
        if(gridHexagone.get(hexagonesAutour[4]).isNull()){gridHexagone.remove(hexagonesAutour[4]);}

        // On s'occupe de l'hexagone NO
        gridHexagone.get(hexagonesAutour[5]).portes[2] = null;
        gridHexagone.get(hexagonesAutour[5]).portes[3] = null;
        if(gridHexagone.get(hexagonesAutour[5]).isNull()){gridHexagone.remove(hexagonesAutour[5]);}

        gridTuile.remove(c);
        return true;
    }
}