package main.java.model.interfaces;

import main.java.model.Coordonnee;

public interface HexagoneAutour {
    public static Coordonnee[] get(Coordonnee c){
        int x = c.getX();
        int y = c.getY();

        Coordonnee ne;
        Coordonnee e;
        Coordonnee se;
        Coordonnee so;
        Coordonnee o;
        Coordonnee no;

        if(x%2==0){
            ne = new Coordonnee(x+1, y);
            e = new Coordonnee(x+2, y);
            se = new Coordonnee(x+1, y-1);
            so = new Coordonnee(x-1, y-1);
            o = new Coordonnee(x-2, y);
            no = new Coordonnee(x-1, y);
        }
        else{
            ne = new Coordonnee(x+1, y+1);
            e = new Coordonnee(x+2, y);
            se = new Coordonnee(x+1, y);
            so = new Coordonnee(x-1, y);
            o = new Coordonnee(x-2, y);
            no = new Coordonnee(x-1, y+1);
        }
        Coordonnee[] autour = {ne,e,se,so,o,no};
        return autour;
    }
}
