package main.java.model.interfaces;

import java.awt.Color;

import main.java.model.Couleurs;

public interface ColorsSwitcher {
    public static Couleurs toCouleurs(Color c){
        if(c == Color.RED){return Couleurs.ROUGE;}
        if(c == Color.ORANGE){return Couleurs.ORANGE;}
        if(c == Color.GREEN){return Couleurs.VERT;}
        else{return null;}
    }

    public static Color toColor(Couleurs c){
        if(c == Couleurs.ROUGE){return Color.RED;}
        if(c == Couleurs.ORANGE){return Color.ORANGE;}
        if(c == Couleurs.VERT){return Color.GREEN;}
        else{return null;}
    }
}
