package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Tuile :
 * 
 * - Possède un hexagonale qui représente le centre (pouvant être un temple ou non)
 * - Possède une liste d'hexagones simple non-complet de taille 6 qui représentent les 6 hexagones en contact avec l'hexagone centrale
 * 
 * Qu'est ce qu'un hexagone non-complet ?
 * 
 * C'est un hexagone, dont seulement 2 portes sont définis
 */
public class Tuile implements Serializable{

    /**
     * Hexagone centrale
     * +
     * Liste d'hexagones non-complet de taille 6
     */
    protected HexagoneCentral centre = new HexagoneCentral(null, null, null, null, null, null);
    protected Hexagone[] hexagones = new Hexagone[6];

    /**
     * Getteurs
     */
    public HexagoneCentral getHexagoneCentral(){return centre;}
    public Hexagone[] getHexagones(){return hexagones;}

    /**
     * Constructeur qui permet de définir une tuile en fonction de 3 parametres
     * Deux tuiles sont identiques si leurs :
     * - id_type % 2 sont égaux ET
     * - id_couleur % 6 sont égaux ET
     * - id_rotation % 3 sont égaux.
     */
    public Tuile(int id_type, int id_couleur, int id_rotation){
        this.fillTuileByID(id_type, id_couleur, id_rotation);
    }

    /**
     * Constructeur initialisant une tuile aléatoire
     */
    public Tuile(){
        Random r = new Random();
        int typeID = r.nextInt(0, 2);
        int couleurID = r.nextInt(0, 6);
        int rotationID = r.nextInt(0, 3);
        this.fillTuileByID(typeID, couleurID, rotationID);
    }

    /**
     * Constructeur qui donne une tuile en fonction des hexagones qu'elle doit contenir
     */
    public Tuile(HexagoneCentral centre,Hexagone ne, Hexagone e, Hexagone se, Hexagone so, Hexagone o, Hexagone no){
        this.centre = centre;
        hexagones[0] = ne;
        hexagones[1] = e;
        hexagones[2] = se;
        hexagones[3] = so;
        hexagones[4] = o;
        hexagones[5] = no;
    }

    /**
     * Constructeur pour avoir la copie d'une tuile
     * @param tuile La tuile a copiée
     */
    public Tuile(Tuile tuile){
        this.centre = tuile.centre.clone();
        hexagones[0] = tuile.hexagones[0].clone();
        hexagones[1] = tuile.hexagones[1].clone();
        hexagones[2] = tuile.hexagones[2].clone();
        hexagones[3] = tuile.hexagones[3].clone();
        hexagones[4] = tuile.hexagones[4].clone();
        hexagones[5] = tuile.hexagones[5].clone();
    }

    /**
     * Méthode pour remplir les portes d'une tuile à la main
     * @param ne porte Nord-Est de l'hexagone central
     * @param e porte Est de l'hexagone central
     * @param se porte Sud-Est de l'hexagone central
     * @param so porte Sud-Ouest de l'hexagone central
     * @param o porte Ouest de l'hexagone central
     * @param no porte Nord-Ouest de l'hexagone central
     * @param brancheNE porte située sur la branche en haut à droite
     * @param brancheS porte située sur la branche du bas
     * @param brancheNO porte située sur la branche en haut à gauche
     */
    private void fillTuile(Couleurs ne, Couleurs e, Couleurs se, Couleurs so, Couleurs o, Couleurs no, Couleurs brancheNE, Couleurs brancheS, Couleurs brancheNO){
        this.centre = new HexagoneCentral(ne, e, se, so, o, no);
        this.hexagones[0] = new Hexagone(null, null, brancheNE, centre.portes[0], null, null);
        this.hexagones[1] = new Hexagone(null, null, null, null, centre.portes[1], brancheNE);

        this.hexagones[2] = new Hexagone(null, null, null, null, brancheS, centre.portes[2]);
        this.hexagones[3] = new Hexagone(centre.portes[3], brancheS, null, null, null, null);

        this.hexagones[4] = new Hexagone(brancheNO, centre.portes[4], null, null, null, null);
        this.hexagones[5] = new Hexagone(null, null, centre.portes[5], brancheNO, null, null);
    }

    /**
     * Méthode qui permet de remplir les portes d'une tuile en fonction de 3 parametres
     * Deux tuiles sont identiques si leurs :
     * - id_type % 2 sont égaux ET
     * - id_couleur % 6 sont égaux ET
     * - id_rotation % 3 sont égaux.
     */
    private void fillTuileByID(int id_type, int id_couleur, int id_rotation){
        Couleurs couleurs[] = new Couleurs[3];
        if(id_couleur % 6 == 0){
            couleurs[0] = Couleurs.ROUGE;
            couleurs[1] = Couleurs.ORANGE;
            couleurs[2] = Couleurs.VERT;
        }
        else if(id_couleur % 6 == 1){
            couleurs[0] = Couleurs.ROUGE;
            couleurs[1] = Couleurs.VERT;
            couleurs[2] = Couleurs.ORANGE;
        }
        else if(id_couleur % 6 == 2){
            couleurs[0] = Couleurs.ORANGE;
            couleurs[1] = Couleurs.ROUGE;
            couleurs[2] = Couleurs.VERT;
        }
        else if(id_couleur % 6 == 3){
            couleurs[0] = Couleurs.ORANGE;
            couleurs[1] = Couleurs.VERT;
            couleurs[2] = Couleurs.ROUGE;
        }
        else if(id_couleur % 6 == 4){
            couleurs[0] = Couleurs.VERT;
            couleurs[1] = Couleurs.ROUGE;
            couleurs[2] = Couleurs.ORANGE;
        }
        else if(id_couleur % 6 == 5){
            couleurs[0] = Couleurs.VERT;
            couleurs[1] = Couleurs.ORANGE;
            couleurs[2] = Couleurs.ROUGE;
        }

        // Tuiles possédant 2 couleurs sur 3 faces de l'hexagone du centre
        if(id_type % 2 == 0){
            this.fillTuile(couleurs[0], couleurs[0], couleurs[0], couleurs[1], couleurs[1], couleurs[1], couleurs[2], couleurs[2], couleurs[2]);
        }
        // Tuiles possédant 3 couleurs sur 2 faces de l'hexagone du centre
        else{
            this.fillTuile(couleurs[0], couleurs[1], couleurs[2], couleurs[0], couleurs[1], couleurs[2], couleurs[2], couleurs[1], couleurs[0]);
        }

        if(id_rotation % 3 == 1){
            this.rotate();
        }
        else if(id_rotation % 3 == 2){
            this.rotate();
            this.rotate();
        }
    }

    /**
     * Méthode permettant d'avoir toutes les tuiles possibles dans une liste
     * @return La liste contenant toutes les tuiles
     */
    public static ArrayList<Tuile> allTuiles(){
        ArrayList<Tuile> allTuiles = new ArrayList<Tuile>();
        for (int type = 0; type < 2; type++) {
            for (int couleur = 0; couleur < 6; couleur++) {
                for (int rotation = 0; rotation < 3; rotation++) {
                    allTuiles.add(new Tuile(type, couleur, rotation));
                }
            }
        }
        return allTuiles;
    }
    
    /**
     * Méthode qui tourne d'un cran dans le sens horaire la tuile actuelle
     */
    public void rotate(){
        Tuile rotated = getRotation();
        centre = rotated.centre;
        for (int i = 0; i < hexagones.length; i++) {
            hexagones[i] = rotated.hexagones[i];
        }
    }

    /**
     * @return une tuile tournée d'un cran dans le sens horaire
     */
    public Tuile getRotation(){
        return new Tuile(centre.getRotation().getRotation(),hexagones[4].getRotation().getRotation(), hexagones[5].getRotation().getRotation(), hexagones[0].getRotation().getRotation(), hexagones[1].getRotation().getRotation(), hexagones[2].getRotation().getRotation(), hexagones[3].getRotation().getRotation());
    }

    /**
     * Méthode pour savoir les coordonnees du centre de la tuile, dans le repère des tuiles
     * @return la coordonnee voulue
     */
    public Coordonnee getLocationInGridTuile(){
        return centre.getLocationInGridTuile();
    } 

    /**
     * Méthode pour savoir les coordonnees du centre de la tuile, dans le repère des hexagones
     * @return la coordonnee voulue
     */
    public Coordonnee getLocationInGridHexagone(){
        return centre.getLocation();
    }

    /**
     * Méthode pour savoir si la tuile représente un temple ou non
     * @return un boolean qui permet de savoir si c'est un temple ou non
     */
    public boolean isTemple(){
        return centre.isTemple();
    }

    @Override
    public String toString(){
        return getClass().getName()+"@"+Integer.toHexString(hashCode())+" -> "+ toStringPartiel();
    }
    public String toStringPartiel(){
        return "["+centre.toStringPartiel()+","+hexagones[0].toStringPartiel()+","+hexagones[1].toStringPartiel()+","+hexagones[2].toStringPartiel()+","+hexagones[3].toStringPartiel()+","+hexagones[4].toStringPartiel()+","+hexagones[5].toStringPartiel()+"]";
    }
}