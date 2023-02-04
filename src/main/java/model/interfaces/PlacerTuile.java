package main.java.model.interfaces;

import java.util.ArrayList;

import main.java.model.Coordonnee;
import main.java.model.Tuile;

/**
 * Interface qui renvoie une liste de tous les choix possibles de déplacement qu'un pion peut avoir (par rapport à sa position + portes des hexagones)
 */
public interface PlacerTuile {
    public ArrayList<Coordonnee> canPlaceLocations(Tuile t);
}


