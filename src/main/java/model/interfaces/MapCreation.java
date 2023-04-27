package main.java.model.interfaces;

import java.util.ArrayList;

import main.java.model.Coordonnee;
import main.java.model.Jeu;
import main.java.model.JeuEtat;
import main.java.model.MapEtat;
import main.java.model.Plateau;
import main.java.model.Tuile;
import main.java.model.TuileTemple;

public interface MapCreation {

    /**
     * Liste de coordonnées où les tuiles blanches doivent être placées pour la MAP1_2P
     */
    public Coordonnee[] MAP1_2PCoordonnees = 
    {new Coordonnee(0, 0),new Coordonnee(0, 1),new Coordonnee(1, 0),new Coordonnee(1, -1),new Coordonnee(0, -1),new Coordonnee(-1, -1),new Coordonnee(-1, 0),
    new Coordonnee(2, 1),new Coordonnee(3, 0),new Coordonnee(3, -1),new Coordonnee(2, -1),
    new Coordonnee(-2, 1),new Coordonnee(-3, 0),new Coordonnee(-3, -1),new Coordonnee(-2, -1)};

    /**
     * Liste de coordonnées où les tuiles blanches doivent être placées pour la MAP2_2P
     */
    public Coordonnee[] MAP2_2PCoordonnees =
    {new Coordonnee(0, 0),new Coordonnee(1, 0),new Coordonnee(1, -1),new Coordonnee( 0, -1),new Coordonnee( 0, -2),new Coordonnee( 1, -3),new Coordonnee( 2, -2),
    new Coordonnee( 2, 1),new Coordonnee( 3, 0),new Coordonnee( 3, -1),new Coordonnee( 3, -2),new Coordonnee( -1, 0),new Coordonnee( 0, 1),new Coordonnee( -1, -1),
    new Coordonnee( 0, 2),new Coordonnee( -1, 2),new Coordonnee( -2, 2),new Coordonnee( -2, -1),new Coordonnee( -3, -1),new Coordonnee( -3, 0),new Coordonnee( -3, 1)};

    /**
     * Liste de coordonnées où les tuiles blanches doivent être placées pour la MAP1_4P
     */
    public Coordonnee[] MAP1_4PCoordonnees =
    {new Coordonnee( 0, 0),new Coordonnee( 0, 1),new Coordonnee( 1, 0),new Coordonnee( 1, -1),new Coordonnee( 0, -1),new Coordonnee( -1, -1),new Coordonnee( -1, 0),
    new Coordonnee( 2, 1),new Coordonnee( 3, 0),new Coordonnee( 3, -1),new Coordonnee( 2, -1),new Coordonnee( -2, 1),new Coordonnee( -3, 0),new Coordonnee( -3, -1),
    new Coordonnee( -2, -1),new Coordonnee( -1, 1),new Coordonnee( 1, 1),new Coordonnee( -1, 2),new Coordonnee( 1, 2),new Coordonnee( 0, 3),new Coordonnee( -1, -2),
    new Coordonnee( 1, -2),new Coordonnee( -1, -3),new Coordonnee( 1,-3),new Coordonnee( 0, -3)};

    /**
     * Liste de coordonnées où les tuiles blanches doivent être placées pour la MAP2_4P
     */
    public Coordonnee[] MAP2_4PCoordonnees =
    {new Coordonnee( -1, 0),new Coordonnee( 0, 1),new Coordonnee( 1, 0),new Coordonnee( 1, -1),new Coordonnee( 0, -1),new Coordonnee( -1, -1),new Coordonnee( -2, 2),
    new Coordonnee( -1, 1),new Coordonnee( 0, 2),new Coordonnee( 2, 1),new Coordonnee( 2, 0),new Coordonnee( 2, -2),new Coordonnee( 1, -2),new Coordonnee( 0, -2),
    new Coordonnee( -2, -1),new Coordonnee( -2, 0)};

    /**
     * Méthode pour initialiser un plateau par rapport au paramètre donné
     * @param jeu Le model sur lequel on effectue le travail
     */
    public default void initMap(Jeu jeu){
        jeu.setJeuEtat(JeuEtat.PLACING_START_PION);
        Plateau plateau = jeu.getPlateau();
        ArrayList<TuileTemple> sacTemples = jeu.getSacTemples();
        if(jeu.getMapEtat() == MapEtat.MAP1_2P){

            for (Coordonnee c : MAP1_2PCoordonnees) {
                plateau.placeTuileForce(new Tuile(), c);
            }

            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);
        }
        else if(jeu.getMapEtat() == MapEtat.MAP2_2P){

            for (Coordonnee c : MAP2_2PCoordonnees) {
                plateau.placeTuileForce(new Tuile(), c);
            }

            plateau.placeTuileForce(sacTemples.get(0), 2, -1);
            sacTemples.remove(0);

            plateau.placeTuileForce(sacTemples.get(0), -2, 1);
            sacTemples.remove(0);
        }
        else if(jeu.getMapEtat() == MapEtat.MAP1_4P){

            for (Coordonnee c : MAP1_4PCoordonnees) {
                plateau.placeTuileForce(new Tuile(), c);
            }

            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);

            plateau.placeTuileForce(sacTemples.get(0), 0, 2);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 0, -2);
            sacTemples.remove(0);
        }
        else if(jeu.getMapEtat() == MapEtat.MAP2_4P){

            for (Coordonnee c : MAP2_4PCoordonnees) {
                plateau.placeTuileForce(new Tuile(), c);
            }

            plateau.placeTuileForce(sacTemples.get(0), -2, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 1, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 2, -1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -1, -2);
            sacTemples.remove(0);
        }
        else{   // map == MapEtat.MANUEL
            jeu.setJeuEtat(JeuEtat.CHOOSING_TUILE_LOCATION);
            jeu.setTuileCourante(sacTemples.get(0));
            sacTemples.remove(0);
            plateau.placeTuileForce(jeu.getTuileCourant(), 0, 0);
        }
    }

    /**
    * Notre but est de faire en sorte que la map soit toujours identique lorsqu'on fait face à une IA, mais en faisant en sorte que les tuiles posées sur le plateau ne se ressemble pas trop entre eux.
    * Pour cela, nous avons une liste "allTuiles" contenant les 36 tuiles différentes possibles.
    * Nous allons parcourir cette liste, en faisant des sauts de longueur {@code jump = 25}, qui est premier avec 36 {@code (PGCD(36,25) = 1)} pour justement éviter des possibles répétitions.
    */
    public default void initMAP1_2PDefault(Jeu jeu){
        jeu.setJeuEtat(JeuEtat.PLACING_START_PION);
        Plateau plateau = jeu.getPlateau();
        ArrayList<TuileTemple> sacTemples = jeu.getSacTemples();
        
        ArrayList<Tuile> allTuiles = Tuile.allTuiles();
        int size = allTuiles.size();

        // PGCD(36, 25) = 1
        int jump = 25;
        
        if(jeu.getMapEtat() == MapEtat.MAP1_2P){
            int index = 0;
            for (Coordonnee c : MAP1_2PCoordonnees) {
                plateau.placeTuileForce(new Tuile(allTuiles.get(index)), c);
                index = (index + jump)%size;
            }
            
            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);
        }
    }
}
