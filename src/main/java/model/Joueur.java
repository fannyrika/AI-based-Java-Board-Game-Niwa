package main.java.model;

public class Joueur {
    private static String nom;
    private int id;
    private int nbrHexagone;
    public boolean aGagne;
    //on remplacera ca par des tuiles
    // ou voir si on fait pas un sac comme -> public SacTuiles tuiles

    public Joueur(String n, int i, int h){
        nom = n;
        i = id;
        nbrHexagone = h;
    }

    public String getName(){
        return nom;
    }

    public int getID(){
        return id;
    }

    public int getNbrHexagone(){
        return nbrHexagone;
    }

    public void setGagne(){
        aGagne = true;
    }
}
