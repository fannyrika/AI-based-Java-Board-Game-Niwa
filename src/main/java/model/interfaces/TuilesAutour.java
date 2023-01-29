package main.java.model.interfaces;

import main.java.model.Coordonnee;

/**
 * Interface qui va permettre de donner toutes les coordonnées autour d'une tuile (6 coordonnées)
 * - En partant du nord et dans le sens horaire
 */
public interface TuilesAutour {
    public static Coordonnee[] get(Coordonnee c){
        int x = c.getX();
        int y = c.getY();

        Coordonnee n;
        Coordonnee ne;
        Coordonnee se;
        Coordonnee s;
        Coordonnee so;
        Coordonnee no;

        if(x%2==0){
            n = new Coordonnee(x, y+1);
            ne = new Coordonnee(x+1, y);
            se = new Coordonnee(x+1, y-1);
            s = new Coordonnee(x, y-1);
            so = new Coordonnee(x-1, y-1);
            no = new Coordonnee(x-1, y);
        }
        else{
            n = new Coordonnee(x, y+1);
            ne = new Coordonnee(x+1, y+1);
            se = new Coordonnee(x+1, y);
            s = new Coordonnee(x, y-1);
            so = new Coordonnee(x-1, y);
            no = new Coordonnee(x-1, y+1);
        }
        Coordonnee[] autour = {n,ne,se,s,so,no};
        return autour;
    }
}
