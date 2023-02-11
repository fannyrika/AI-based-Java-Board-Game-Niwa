package main.java.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classes qui gerer le deroulement du jeu
 */
public class Jeu {

    /**
     * Liste qui va contenir la liste des joueurs
     * 
     * Pourquoi avoir fait une liste de joueurs et pas un tableau ou autre ?
     * Si nous voulons rajouter un option qui permettra de jouer au jeu avec un nombre variable de joueurs, il est plus facile de passer par une liste.
     */
    protected ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    protected ArrayList<Joueur> gangeurs = new ArrayList<Joueur>();
    protected Joueur joueurCourant = new Joueur();
    /**
     * Le jeu nécessite un plateau pour jouer
     */
    protected Plateau plateau = new Plateau();
    /**
     * Représente le sac de tuile (hors tuile temple)
     */
    protected ArrayList<Tuile> sac = new ArrayList<Tuile>();
    /**
     * Représente le sac des temples
     */
    protected ArrayList<TuileTemple> sacTemples = new ArrayList<TuileTemple>();
    protected Tuile tuileCourante = new Tuile();
    
    protected Pion pionCourant = new Pion(joueurCourant);

    protected JeuEtat jeuEtat;
    protected JeuEtat dernierEtat;
    
    //remplac�� par l'attribut id de joueur
    /**
     * Entier qui permettra de savoir à qui est le tour
     * 
     * @Exemple : 
     * - tour = 0 -> joueur numéro 1 qui joue
     * - tour = 1 -> joueur numéro 2 qui joue
     * etc...
     */
    //protected int tour = 0;
    
    protected static final int NB_TUILES = 12;
    
     /**
     * Constructeur permettant d'initialiser les attributs
     * 
     * @param nb_joueurs -> Le nombre de joueurs que le jeu pourra acueillir
     */
    public Jeu(int nb_joueurs){
    	jeuEtat = JeuEtat.CHOOSING_TUILE_LOCATION;
        for (int i = 0; i < nb_joueurs; i++) {
            Joueur j = new Joueur();
            joueurs.add(j);             // On ajoute les joueurs dans la liste de joueurs
            sacTemples.add(j.temple);   // On ajoute chaque temple dans la liste des temples
        }
        initSac();
        tuileCourante=sacTemples.get(0);
        plateau.placeTuileForce(tuileCourante, 0, 0);
    }
    
    
    public void initSac(){
        for (int i = 0; i < NB_TUILES; i++) {
            sac.add(new Tuile());
        }
    }
    
    //Cette fonction permet de piocher une tuile aléatoire dans le sac
    public Tuile piocher(){
        Random r=new Random();
        //int n = r.nextInt(0,sac.size());
        int n = r.nextInt(sac.size());
        Tuile piocher = sac.get(n);
        sac.remove(n);
        return piocher;
    }
    
    //setters
    public void setJoueurCourant(Joueur j){ joueurCourant=j;}
    public void setPionCourant(Pion p){ pionCourant=p;}
    public void setGagneur(Joueur j){ gangeurs.add(j);}
    public void setTuileCourante(Tuile t){ tuileCourante=t; }
    public void setJeuEtat(JeuEtat j){ jeuEtat=j;}
    public void setDernierEtat(JeuEtat d){ dernierEtat = d;}
    //getters
    public JeuEtat getJeuEtat(){ return jeuEtat; }
    public JeuEtat getDernierEtat(){ return dernierEtat;}
    // Méthodes nécessaires pour un jeu de plateau
    public ArrayList<Joueur> getJoueurs(){return joueurs;}
    public Plateau getPlateau(){return plateau;}
    public ArrayList<Tuile> getSac(){return sac;}
    public ArrayList<TuileTemple> getSacTemples(){return sacTemples;}
    public ArrayList<Joueur> getGagneurs(){ return gangeurs; }
    public Joueur getJoueurCourant() { return joueurCourant; }
    public Tuile getTuileCourant(){ return tuileCourante; }
    public Pion getPionCourant(){ return pionCourant; }

    public static void main(String[] args) {
        Jeu jeu=new Jeu(3);
        for(int i=0; i<3; i++){
            System.out.println(jeu.joueurs.get(i));
        }
    }
}
