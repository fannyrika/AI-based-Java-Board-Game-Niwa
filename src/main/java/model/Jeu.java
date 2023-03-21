package main.java.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classe qui va gérer le déroulement du jeu
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
    /**
     * Représente le joueur courant, mis sur le premier joueur lorsque l'objet "Jeu" est crée
     */
    protected Joueur joueurCourant;
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
    
    /**
     * Représente le pion courant, mis sur le premier pion du premier joueur lorsque l'objet "Jeu" est crée
     */
    protected Pion pionCourant;

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
    
    protected static final int NB_TUILES = 3;
    
     /**
     * Constructeur permettant d'initialiser les attributs
     * 
     * @param nb_joueurs -> Le nombre de joueurs que le jeu pourra acueillir
     */
    public Jeu(int nb_joueurs, MapEtat map){
        if(map == MapEtat.MAP1_2P || map == MapEtat.MAP2_2P){initJoueurs(2);}
        else if(map == MapEtat.MAP1_4P || map == MapEtat.MAP2_4P){initJoueurs(4);}
        else{initJoueurs(nb_joueurs);}
        joueurCourant = joueurs.get(0);
        pionCourant = joueurCourant.pions.get(0);
        initSac(map);
        initMap(map);
    }

    public Jeu(ArrayList<Joueur> j, ArrayList<TuileTemple> s){
        joueurs=j;
        sacTemples=s;

        joueurCourant = joueurs.get(0);
        pionCourant = joueurCourant.pions.get(0);
        initSac();
        tuileCourante=sacTemples.get(0);
        sacTemples.remove(0);
        plateau.placeTuileForce(tuileCourante, 0, 0);
    }

    public void initJoueurs(int nb_j){
        for (int i = 0; i < nb_j; i++) {
            Joueur j = new Joueur("j0");
            joueurs.add(j);             // On ajoute les joueurs dans la liste de joueurs
            sacTemples.add(j.temple);   // On ajoute chaque temple dans la liste des temples
        }
    }
    
    /**
     * Permet d'initialiser le sac de tuile
     * @param map -> permet de savoir combien de tuile il y a à créer (par rapport à une map par défaut, ou bien dans le cas ou la map est crée manuellement)
     */
    public void initSac(MapEtat map){
        if(map == MapEtat.MANUEL){
            for (int i = 0; i < NB_TUILES; i++) {
                sac.add(new Tuile());
            }
        }
    }

    /**
     * Permet d'initialiser le sac dans le cas ou le plateau doit être créer manuellement
     */
    public void initSac(){
        initSac(MapEtat.MANUEL);
    }

    /**
     * Méthode pour initialiser un plateau par rapport au paramètre donné
     * @param map -> Permet de savoir quel plateau initialiser
     */
    public void initMap(MapEtat map){
        jeuEtat = JeuEtat.PLACING_START_PION;
        if(map == MapEtat.MAP1_2P){
            plateau.placeTuileForce(new Tuile(), 0, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), -1, -1);
            plateau.placeTuileForce(new Tuile(), -1, 0);

            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 3, 0);
            plateau.placeTuileForce(new Tuile(), 3, -1);
            plateau.placeTuileForce(new Tuile(), 2, -1);

            plateau.placeTuileForce(new Tuile(), -2, 1);
            plateau.placeTuileForce(new Tuile(), -3, 0);
            plateau.placeTuileForce(new Tuile(), -3, -1);
            plateau.placeTuileForce(new Tuile(), -2, -1);

            plateau.placeTuileForce(sacTemples.get(0), 2, 0);
            sacTemples.remove(0);
            plateau.placeTuileForce(sacTemples.get(0), -2, 0);
            sacTemples.remove(0);
        }
        else if(map == MapEtat.MAP2_2P){
            plateau.placeTuileForce(new Tuile(), 0, 0);
            plateau.placeTuileForce(new Tuile(), 1, 0);
            plateau.placeTuileForce(new Tuile(), 1, -1);
            plateau.placeTuileForce(sacTemples.get(0), 2, -1);
            sacTemples.remove(0);
            plateau.placeTuileForce(new Tuile(), 0, -1);
            plateau.placeTuileForce(new Tuile(), 0, -2);
            plateau.placeTuileForce(new Tuile(), 1, -3);
            plateau.placeTuileForce(new Tuile(), 2, -2);
            plateau.placeTuileForce(new Tuile(), 2, 1);
            plateau.placeTuileForce(new Tuile(), 3, 0);
            plateau.placeTuileForce(new Tuile(), 3, -1);
            plateau.placeTuileForce(new Tuile(), 3, -2);
            
            plateau.placeTuileForce(new Tuile(), -1, 0);
            plateau.placeTuileForce(new Tuile(), 0, 1);
            plateau.placeTuileForce(new Tuile(), -1, -1);
            plateau.placeTuileForce(sacTemples.get(0), -2, 1);
            sacTemples.remove(0);
            plateau.placeTuileForce(new Tuile(), 0, 2);
            plateau.placeTuileForce(new Tuile(), -1, 2);
            plateau.placeTuileForce(new Tuile(), -2, 2);
            plateau.placeTuileForce(new Tuile(), -2, -1);
            plateau.placeTuileForce(new Tuile(), -3, -1);
            plateau.placeTuileForce(new Tuile(), -3, 0);
            plateau.placeTuileForce(new Tuile(), -3, 1);


        }
        else{   // map == MapEtat.MANUEL
            jeuEtat = JeuEtat.CHOOSING_TUILE_LOCATION;
            tuileCourante=sacTemples.get(0);
            sacTemples.remove(0);
            plateau.placeTuileForce(tuileCourante, 0, 0);
        }
    }
    
    /**
     * Cette fonction permet de piocher une tuile aléatoire dans le sac
     * @return la tuile piochée dans le sac
     */
    public Tuile piocher(){
        Random r=new Random();
        //int n = r.nextInt(0,sac.size());
        int n = r.nextInt(sac.size());
        Tuile piocher = sac.get(n);
        sac.remove(n);
        return piocher;
    }

    /**
     * Méthode pour savoir si tous les pions ont été placés ou non
     * @return true si oui, false sinon
     */
    public boolean allPionsPlaced(){
        for (Joueur j : joueurs) {
            if(!j.placedAllPions()){
                return false;
            }
        }
        return true;
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
    public TuileTemple popTemple(){
        TuileTemple temple = sacTemples.get(sacTemples.size()-1);
        sacTemples.remove(sacTemples.size()-1);
        return temple;
    }
}