package main.java.model;

import java.util.ArrayList;

import main.java.model.Hexagone.Couleurs;

/**
 * Classe qui va représenter un joueur
 */
public class Joueur {

    protected static final int MAX_PIONS = 3;
    protected static final int NB_PEARLS_AT_START = 2;

    /**
     * Attributs permettant de définir un joueur
     */
    protected TuileTemple temple;
    protected ArrayList<Pion> pions;

    /**
     * Constructeur sans argument, permettant d'initialiser un joueur
     */
    public Joueur(){
        this.temple = new TuileTemple(this);
        initalisePions();
    }

    /**
     * Méthode privée, permet d'initialiser les pions du joueur
     */
    private void initalisePions(){
        for (int i = 0; i < MAX_PIONS; i++) {
            Pion p = new Pion(this);
            for (int j = 0; j < NB_PEARLS_AT_START; j++) {
                p.add(Couleurs.values()[i%Couleurs.values().length]);   
            }
            pions.add(p);
        }
    }
    
}
