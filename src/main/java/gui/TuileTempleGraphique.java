package main.java.gui;

import java.awt.*;

import main.java.model.Joueur;
import main.java.model.TuileTemple;

public class TuileTempleGraphique extends TuileGraphique{

    /**
     * Constructeur pour une tuileTemple graphique
     * @param tuile -> La tuile représentée
     * @param x -> La coordonnee en x
     * @param y -> La coordonnee en y
     * @param j -> Le joueur à qui est la tuileTemple
     */
    public TuileTempleGraphique(TuileTemple tuile, int x, int y, Joueur j) {
        super(tuile, x, y);
        this.color = colorByID(j);
    }

    /**
     * Méthode privée qui permet de donner une couleur par rapport à l'ID d'un joueur
     * @param j -> Le joueur
     * @return -> La couleur associée au joueur
     */
    private Color colorByID(Joueur j){
        switch (j.getID()%4) {
            case 0: return new Color(140, 217, 255);
            case 1: return new Color(255, 82, 66);
            case 2: return Color.MAGENTA;
            case 3: return Color.PINK;
            default: return Color.BLACK;
        }
    }
}
