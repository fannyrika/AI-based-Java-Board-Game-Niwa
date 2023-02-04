package main.java.model;

import java.util.ArrayList;

/**
 * pour gérer le déroulement du jeu
 */
public class Jeu {
    protected ArrayList<Joueur> joueurs = null;
    /**
     * joueurs qui sont déjà arrivés au temple d'un autre joueur
     */
    protected ArrayList<Joueur> gangeurs = null;
    protected Joueur joueurCourant;
    protected boolean shouldContinue=false;

    protected Plateau plateau;
    protected Tuile tuileCourante;
    protected Tuile tuileInit = null;
    protected Sac sac;
    
    protected Pion pionCourant = null;

    //public Jeu(ArrayList<Joueur> joueurs){
    //    this.joueurs=joueurs;
    //}

    public Jeu(int nbJoueurs){
        joueurs=new ArrayList<Joueur>();
        shouldContinue=false;
        for(int i=0; i<nbJoueurs; i++){
            joueurs.add(new Joueur());
        }
        for(int i=0;i<joueurs.size();i++){
            joueurs.get(i).setId(i);
        }

        sac = new Sac();
        plateau=new Plateau();
        tuileInit=sac.piocher();
    }

    //setters and getters
    public void setJoueurCourant(Joueur j){
        joueurCourant=j;
    }

    public void setGagneur(Joueur j){
        gangeurs.add(j);
    }

    public void setTuileCourante(Tuile t){
        tuileCourante=t;
    }

    public void setShouldContinue(boolean s){
        shouldContinue=s;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public ArrayList<Joueur> getGagneurs(){
        return gangeurs;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public boolean shouldContinue(){
        return shouldContinue;
    }

    public Plateau getPlateau(){
        return plateau;
    }
    
    public Tuile getTuileCourant(){
        return tuileCourante;
    }

    public Tuile getTuileInit(){
        return tuileInit;
    }

    public Sac getSac(){
        return sac;
    }

    public Pion getPionCourant(){
        return pionCourant;
    }

    public static void main(String[] args) {
        Jeu jeu=new Jeu(3);
        for(int i=0; i<3; i++){
            System.out.println(jeu.joueurs.get(i));
        }
    }
}
