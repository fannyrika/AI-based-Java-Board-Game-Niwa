package main.java.model;

import java.util.ArrayList;
import java.util.Random;

public class Jeu {

    /**
     * Liste qui va contenir la liste des joueurs
     * 
     * Pourquoi avoir fait une liste de joueurs et pas un tableau ou autre ?
     * Si nous voulons rajouter un option qui permettra de jouer au jeu avec un nombre variable de joueurs, il est plus facile de passer par une liste.
     */
    protected ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
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
    /**
     * Entier qui permettra de savoir à qui est le tour
     * 
     * @Exemple : 
     * - tour = 0 -> joueur numéro 1 qui joue
     * - tour = 1 -> joueur numéro 2 qui joue
     * etc...
     */
    protected int tour = 0;

    protected static final int NB_TUILES = 12;

    // Méthodes nécessaires pour un jeu de plateau
    public ArrayList<Joueur> getJoueurs(){return joueurs;}
    public Plateau getPlateau(){return plateau;}
    public ArrayList<Tuile> getSac(){return sac;}
    public ArrayList<TuileTemple> getSacTemples(){return sacTemples;}


    /**
     * Constructeur permettant d'initialiser les attributs
     * 
     * @param nb_joueurs -> Le nombre de joueurs que le jeu pourra acueillir
     */
    public Jeu(int nb_joueurs){
        for (int i = 0; i < nb_joueurs; i++) {
            Joueur j = new Joueur();
            joueurs.add(j);             // On ajoute les joueurs dans la liste de joueurs
            sacTemples.add(j.temple);   // On ajoute chaque temple dans la liste des temples
        }
        initSac();
    }

    public void initSac(){
        for (int i = 0; i < NB_TUILES; i++) {
            sac.add(new Tuile());
        }
    }
  
    //Cette fonction permet de piocher une tuile aléatoire dans le sac
    public Tuile piocher(){
        Random r=new Random();
        int n = r.nextInt(sac.size());
        Tuile piocher = sac.get(n);
        sac.remove(n);
        return piocher;
    }

}
