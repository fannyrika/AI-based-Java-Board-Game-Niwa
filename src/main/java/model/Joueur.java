package main.java.model;

import java.util.ArrayList;

/**
 * Classe qui va représenter un joueur
 */
public class Joueur implements Cloneable{

    /**
     * MAX_PIONS : le nombre de pions maximal qu'un joueur peut avoir (il ne faut pas dépasser 6 parce qu'il n'y a que 6 emplacements autour d'un temple)
     */
    protected static final int MAX_PIONS = 3;
    /**
     * NB_PEARLS_AT_START : le nombre de perles par pions au début de partie
     */
    protected static final int NB_PEARLS_AT_START = 2;

    protected static int ID_STATIC = 0;
    protected int id;

    /**
     * Attributs permettant de définir un joueur
     */
    protected TuileTemple temple;
    protected ArrayList<Pion> pions = new ArrayList<Pion>();
    protected boolean dejaPlacerTemple = false;

    /**
     * Cette perle représente la perle à remettre sur l'un des 2 autres pions après avoir déplacer un pion
     */
    protected Couleurs perleOrphelin;

    public int getID(){return id;}
    public void setID(int i){id=i;}

    /**
     * Constructeur sans argument, permettant d'initialiser un joueur
     */
    public Joueur(){
        this.id = ID_STATIC;
        ID_STATIC++;
        this.temple = new TuileTemple(this);
        initalisePions();
        if(id==0){
            dejaPlacerTemple=true;
        }
    }

    /**
     * Méthode privée, permet d'initialiser les pions du joueur
     */
    private void initalisePions(){
        for (int i = 0; i < MAX_PIONS; i++) {
            Pion p = new Pion(this);
            p.setID(i);
            for (int j = 0; j < NB_PEARLS_AT_START; j++) {
                p.add(Couleurs.values()[i%Couleurs.values().length]);   
            }
            pions.add(p);
        }
    }

    public String toString(){
        /*String str="";
        str+="nom:"+nom+" ";
        str+="id="+id+" ";*/
       /* for(int i=0; i<pions.size(); i++){
        String str="";
        str+="id="+id+" ";
        for(int i=0; i<pions.size(); i++){
            str+=pions.get(i).toString();
            str+=" ";
        }*/
        return "Joueur "+id;
    }

    public ArrayList<Pion> getPions(){ return this.pions; }

    public boolean dejaPlacerTemple(){ return dejaPlacerTemple; }

    public void setDejaPlacerTemple(boolean d){ dejaPlacerTemple=d; }

    public void setTemple(TuileTemple t){ temple=t; }

    public TuileTemple getTemple(){ return temple; }

    /**
     * Méthode pour savoir si un joueur a placé tous ses pions ou non
     * @return true si oui, false sinon
     */
    public boolean placedAllPions(){
        for (Pion pion : pions) {
            if(!pion.isPlaced){
                return false;
            }
        }
        return true;
    }

    //test all the functions
    public static void main(String[] args) {
        Joueur j0 = new Joueur();
        Joueur j1 = new Joueur();
        Joueur j2 = new Joueur();
        System.out.println(j0);
        System.out.println(j1);
        System.out.println(j2);
    }

}
