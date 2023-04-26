package main.java.model;

import java.util.ArrayList;

/**
 * Classe qui va représenter un joueur
 */
public class Joueur implements Cloneable{

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
        for (int i = 0; i < Jeu.NB_PIONS; i++) {
            Pion p = new Pion(this);
            p.setID(i);
            for (int j = 0; j < Jeu.NB_PEARLS_AT_START; j++) {
                p.add(Couleurs.values()[i%Couleurs.values().length]);   
            }
            pions.add(p);
        }
    }

    public String toString(){
        /*
        String str="";
        str+="id="+id+" ";
        for(int i=0; i<pions.size(); i++){
            str+=pions.get(i).toString();
            str+=" ";
        }
        */
        String strCouleur = "";
        switch (id) {
            case 0:
                strCouleur = "Bleu";
                break;
            case 1:
                strCouleur = "Rouge";
                break;
            case 2:
                strCouleur = "Rose";
                break;
            case 3:
                strCouleur = "Jaune";
                break;
            default:
                strCouleur = "inconnu";
                break;
        }
        return "Joueur "+strCouleur;
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
