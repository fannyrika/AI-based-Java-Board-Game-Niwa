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
    //protected boolean shouldContinue=false;

    protected Plateau plateau;
    protected Tuile tuileCourante;
    protected Tuile tuileInit = null;
    protected Sac sac;
    protected ArrayList<TuileTemple> tuileTemples = null;
    
    protected Pion pionCourant = null;

    protected JeuEtat jeuEtat;

    //public Jeu(ArrayList<Joueur> joueurs){
    //    this.joueurs=joueurs;
    //}

    public Jeu(int nbJoueurs){
        joueurs=new ArrayList<Joueur>();
        jeuEtat = JeuEtat.CHOOSING_TUILE_LOCATION;
        for(int i=0; i<nbJoueurs; i++){
            joueurs.add(new Joueur());
        }
        for(int i=0;i<joueurs.size();i++){
            joueurs.get(i).setId(i);
            tuileTemples.add(new TuileTemple(joueurs.get(i)));
        }

        sac = new Sac();
        plateau=new Plateau();
        tuileInit=tuileTemples.get(0);
        //TODO: placer les tuiles avec temple selon le nombre du joueurs
    }

    //setters and getters
    public void setJoueurCourant(Joueur j){
        joueurCourant=j;
    }

    public void setPionCourant(Pion p){
        pionCourant=p;
    }

    public void setGagneur(Joueur j){
        gangeurs.add(j);
    }

    public void setTuileCourante(Tuile t){
        tuileCourante=t;
    }

    public void setJeuEtat(JeuEtat j){
        jeuEtat=j;
    }

    //getters

    public JeuEtat getJeuEtat(){
        return jeuEtat;
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
