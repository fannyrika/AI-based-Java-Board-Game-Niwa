package main.java.model;

import java.util.ArrayList;

/**
 * Classe qui va représenter un joueur
 */
public class Joueur {

    /**
     * MAX_PIONS : le nombre de pions maximal qu'un joueur peut avoir (il ne faut pas dépasser 6 parce qu'il n'y a que 6 emplacements autour d'un temple)
     */
    protected static final int MAX_PIONS = 3;
    protected static final int NB_PEARLS_AT_START = 2;

    /**
     * Attributs permettant de définir un joueur
     */
    protected TuileTemple temple;
    protected ArrayList<Pion> pions=new ArrayList<Pion>();

    /**
     * Cette perle représente la perle à remettre sur l'un des 2 autres pions après avoir déplacer un pion
     */
    protected Couleurs perleOrphelin;

    protected int id;

    /**
     * Constructeur sans argument, permettant d'initialiser un joueur
     */
    public Joueur(){
        this.id=-1;
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

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        String str="";
        str+="id="+id+" ";
        for(int i=0; i<pions.size(); i++){
            str+=pions.get(i).toString();
            str+=" ";
        }
        return str;
    }

    public ArrayList<Pion> getPions(){
        return this.pions;
    }
}
