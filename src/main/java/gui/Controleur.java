package main.java.gui;

import main.java.model.*;

public class Controleur implements ControleurInterface{
    private Jeu modifiedModel;
    private JeuVue vueActuel;
    //private Tuile pieceTmp;
    //public int rowTmp, colTmp, rotationTmp;

    public Controleur(Jeu m, JeuVue v){
        this.modifiedModel=m;
        this.vueActuel=v;
    }

}
