package main.java.model.interfaces;

import java.util.ArrayList;

import main.java.model.Jeu;
import main.java.model.JeuEtat;
import main.java.model.MapEtat;
import main.java.model.Plateau;
import main.java.model.Tuile;
import main.java.model.TuileTemple;

public interface MapCreation {

    /**
     * Méthode pour initialiser un plateau par rapport au paramètre donné
     * @param map -> Permet de savoir quel plateau initialiser
     */
    public default void initMap(Jeu jeu, MapEtat map){
        jeu.setJeuEtat(JeuEtat.PLACING_START_PION);
        Plateau plateau = jeu.getPlateau();
        ArrayList<TuileTemple> sacTemples = jeu.getSacTemples();
        if(map == MapEtat.MAP1_2P){
            plateau.placeTuileForce(new Tuile(), 0, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), -1, -1);
            plateau.placeTuileForce(new Tuile(), -1, 0);

            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 3, 0);
            plateau.placeTuileForce(new Tuile(), 3, -1);
            plateau.placeTuileForce(new Tuile(), 2, -1);

            plateau.placeTuileForce(new Tuile(), -2, 1);
            plateau.placeTuileForce(new Tuile(), -3, 0);
            plateau.placeTuileForce(new Tuile(), -3, -1);
            plateau.placeTuileForce(new Tuile(), -2, -1);

            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);
        }
        else if(map == MapEtat.MAP2_2P){
            plateau.placeTuileForce(new Tuile(), 0, 0);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(sacTemples.get(0), 2, -1);
            sacTemples.remove(0);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), 0, -2);
            plateau.placeTuileForce(new Tuile(), 1, -3);
            plateau.placeTuileForce(new Tuile(), 2, -2);
            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 3, 0);
            plateau.placeTuileForce(new Tuile(), 3, -1);
            plateau.placeTuileForce(new Tuile(), 3, -2);
            
            plateau.placeTuileForce(new Tuile(), -1, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), -1, -1);
            plateau.placeTuileForce(sacTemples.get(0), -2, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(new Tuile(), 0, 2);
            plateau.placeTuileForce(new Tuile(), -1, 2);
            plateau.placeTuileForce(new Tuile(), -2, 2);
            plateau.placeTuileForce(new Tuile(), -2, -1);
            plateau.placeTuileForce(new Tuile(), -3, -1);
            plateau.placeTuileForce(new Tuile(), -3, 0);
            plateau.placeTuileForce(new Tuile(), -3, 1);
        }
        else if(map == MapEtat.MAP1_4P){
            plateau.placeTuileForce(new Tuile(), 0, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), -1, -1);
            plateau.placeTuileForce(new Tuile(), -1, 0);

            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 3, 0);
            plateau.placeTuileForce(new Tuile(), 3, -1);
            plateau.placeTuileForce(new Tuile(), 2, -1);

            plateau.placeTuileForce(new Tuile(), -2, 1);
            plateau.placeTuileForce(new Tuile(), -3, 0);
            plateau.placeTuileForce(new Tuile(), -3, -1);
            plateau.placeTuileForce(new Tuile(), -2, -1);

            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);

            plateau.placeTuileForce(sacTemples.get(0), 0, 2);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 0, -2);
            sacTemples.remove(0);

            plateau.placeTuileForce(new Tuile(), -1, 1);
            plateau.placeTuileForce(new Tuile(), 1, 1);
            plateau.placeTuileForce(new Tuile(), -1, 2);
            plateau.placeTuileForce(new Tuile(), 1, 2);
            plateau.placeTuileForce(new Tuile(), 0, 3);

            plateau.placeTuileForce(new Tuile(), -1, -2);
            plateau.placeTuileForce(new Tuile(), 1, -2);
            plateau.placeTuileForce(new Tuile(), -1, -3);
            plateau.placeTuileForce(new Tuile(), 1,-3);
            plateau.placeTuileForce(new Tuile(), 0, -3);
        }
        else if(map == MapEtat.MAP2_4P){
            plateau.placeTuileForce(new Tuile(), -1, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), -1, -1);

            plateau.placeTuileForce(sacTemples.get(0), -2, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 1, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), 2, -1);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -1, -2);
            sacTemples.remove(0);

            plateau.placeTuileForce(new Tuile(), -2, 2);
            plateau.placeTuileForce(new Tuile(), -1, 1);
            plateau.placeTuileForce(new Tuile(), 0, 2);
            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 2, 0);
            plateau.placeTuileForce(new Tuile(), 2, -2);
            plateau.placeTuileForce(new Tuile(), 1, -2);
            plateau.placeTuileForce(new Tuile(), 0, -2);
            plateau.placeTuileForce(new Tuile(), -2, -1);
            plateau.placeTuileForce(new Tuile(), -2, 0);
        }
        else{   // map == MapEtat.MANUEL
            jeu.setJeuEtat(JeuEtat.CHOOSING_TUILE_LOCATION);
            jeu.setTuileCourante(sacTemples.get(0));
            sacTemples.remove(0);
            plateau.placeTuileForce(jeu.getTuileCourant(), 0, 0);
        }
    }
}
