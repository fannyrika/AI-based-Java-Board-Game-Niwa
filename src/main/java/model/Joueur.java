package main.java.model;

import java.util.ArrayList;

public class Joueur {
    public int id;
    public ArrayList<Pion> pions;

    public Joueur(){
        this.id=-1;
        this.pions=new ArrayList<Pion>();
    }
    
    public void setId(int id){
        this.id=id;
        for(int i=0;i<3;i++){
            Pion newPion = new Pion(id);
            newPion.setId(i);
            pions.add(newPion);
        }
    }

    public int getId(){
        return id;
    }

    //TODO:
    //public Pion choisirUnPion()
    
    //public void placerPion()

}

