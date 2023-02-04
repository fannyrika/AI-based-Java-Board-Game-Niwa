package main.java.model;

import java.util.ArrayList;
import java.util.Random;

public class Sac {
    /**
     * ensemble des tuiles triviales ( sans le temple )
     */
    protected ArrayList<Tuile> tuiles=new ArrayList<Tuile>();

    //TODO: comment on génere des tuiles (aléatoirement ou avec le nombre de types fixes)
    public Sac(){}

    public boolean estVide(){
        return tuiles.isEmpty();
    }

    public Tuile piocher(){
        System.out.println("------Il reste "+tuiles.size()+" tuiles dans le sac.-------");
        Random rd = new Random();
        // produire un nombre entier aléatoire r, 0 <= r < size
        int n = rd.nextInt(tuiles.size());
        Tuile pieceTarget = tuiles.get(n);
        tuiles.remove(n);
        //debug
        //System.out.println(n);
        return pieceTarget;
    }

}
