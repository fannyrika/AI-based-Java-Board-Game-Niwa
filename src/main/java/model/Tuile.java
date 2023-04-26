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
     * Attribut privé, utile pour la création des portes des hexagones
     */
    private ArrayList<Couleurs> portesDispo;

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
     * Constructeur initialisant une tuile sans temple
     */
    public Tuile(){
        setPortesDispo();
        setHexagoneCentral();
        setHexagoneNonComplet();
    }

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
     * Méthode privée, pour remplir la liste de couleurs disponible pour une seule tuile (3 rouge, 3 vert, et 3 orange)
     */
    private void setPortesDispo(){
        portesDispo = new ArrayList<Couleurs>();
        for (Couleurs p : Couleurs.values()){
            for (int i = 0; i < Couleurs.values().length; i++) {
                portesDispo.add(p);
            }
        }
    }

    /**
     * Méthode privée pour initialiser l'hexagone centrale
     */
    private void setHexagoneCentral(){
        Random r = new Random();
        if(r.nextInt(2) == 0){                                      // Cas ou : l'hexagone central a seulement 2 couleurs chacun sur 3 faces 
            Couleurs p1 = Couleurs.values()[r.nextInt(Couleurs.values().length)];
            Couleurs p2 = p1;
            while (p2 == p1) {
                p2 = Couleurs.values()[r.nextInt(Couleurs.values().length)];
            }
            for (int i = 0; i < hexagones.length/2; i++) {
                portesDispo.remove(p1);
                portesDispo.remove(p2);
            }

            int debutP1 = r.nextInt(hexagones.length);
            for (int i = 0; i < hexagones.length; i++) {
                if(i+debutP1 >= hexagones.length){
                    if(i < hexagones.length/2){
                        centre.portes[i+debutP1 - hexagones.length] = p1;
                    }
                    else{
                        centre.portes[i+debutP1 - hexagones.length] = p2;
                    }
                }
                else{
                    if(i < hexagones.length/2){
                        centre.portes[i+debutP1] = p1;
                    }
                    else{
                        centre.portes[i+debutP1] = p2;
                    }
                }
            }
        }
        else{           // Cas ou : l'hexagone central a les 3 couleurs chacun sur 2 faces (mais jamais 2 couleurs cote à cote)
            ArrayList<Couleurs> portes = new ArrayList<Couleurs>();
            portes.add(Couleurs.ROUGE);portes.add(Couleurs.VERT);portes.add(Couleurs.ORANGE);
            Couleurs p1 = portes.get(r.nextInt(portes.size()));
            portes.remove(p1);
            Couleurs p2 = portes.get(r.nextInt(portes.size()));
            portes.remove(p2);
            Couleurs p3 = portes.get(r.nextInt(portes.size()));
            portes.remove(p3);

            for (int i = 0; i < hexagones.length/3; i++) {
                portesDispo.remove(p1);
                portesDispo.remove(p2);
                portesDispo.remove(p3);
            }

            int debutP1 = r.nextInt(hexagones.length);
            for (int i = 0; i < hexagones.length; i++) {
                if(i+debutP1 >= hexagones.length){
                    if(i%3==0){
                        centre.portes[i+debutP1 - hexagones.length] = p1;
                    }
                    else if(i%3==1){
                        centre.portes[i+debutP1 - hexagones.length] = p2;
                    }
                    else{
                        centre.portes[i+debutP1 - hexagones.length] = p3;
                    }
                }
                else{
                    if(i%3==0){
                        centre.portes[i+debutP1] = p1;
                    }
                    else if(i%3==1){
                        centre.portes[i+debutP1] = p2;
                    }
                    else{
                        centre.portes[i+debutP1] = p3;
                    }
                }
            }
        }
    }

    private void setHexagoneNonComplet(){
        Random r = new Random();
        Couleurs p1 = null;
        Couleurs p2 = null;
        Couleurs p3 = null;

        while (p1 == null) {
            Couleurs p = portesDispo.get(r.nextInt(portesDispo.size()));
            if(p != centre.portes[0] && p != centre.portes[1]){
                p1 = p;
                portesDispo.remove(p1);
            }
        }

        p2 = portesDispo.get(0);
        if(p2 != centre.portes[2] && p2 != centre.portes[3]){
            portesDispo.remove(p2);
            p3 = portesDispo.get(0);
            portesDispo.remove(p3);
        }
        else{
            p3 = p2;
            portesDispo.remove(p3);
            p2 = portesDispo.get(0);
            portesDispo.remove(p2);
        }

        hexagones[0] = new Hexagone(null, null, p1, centre.portes[0], null, null);
        hexagones[1] = new Hexagone(null, null, null, null, centre.portes[1], p1);

        hexagones[2] = new Hexagone(null, null, null, null, p2, centre.portes[2]);
        hexagones[3] = new Hexagone(centre.portes[3], p2, null, null, null, null);

        hexagones[4] = new Hexagone(p3, centre.portes[4], null, null, null, null);
        hexagones[5] = new Hexagone(null, null, centre.portes[5], p3, null, null);
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
