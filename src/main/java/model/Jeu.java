package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import main.java.model.interfaces.MapCreation;

/**
 * La classe qui va gérer le déroulement du jeu
 */
public class Jeu implements MapCreation, Serializable{

    /**
     * Liste qui va contenir la liste des joueurs
     * 
     * Pourquoi avoir fait une liste de joueurs et pas un tableau ou autre ?
     * Si nous voulons rajouter un option qui permettra de jouer au jeu avec un nombre variable de joueurs, il est plus facile de passer par une liste.
     */
    protected ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    protected ArrayList<Joueur> gangeurs = new ArrayList<Joueur>();
    protected ArrayList<Joueur> perdants = new ArrayList<Joueur>();
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
    /**
     * Représente la map sur laquelle le jeu se déroule
     */
    protected MapEtat mapEtat;
    
    /**
     * NB_PEARL_MAX : Représente le nombre de perles qu'un pion peut garder sur sa tête
     */
    public static int NB_PEARL_MAX = 3;
    /**
     * NB_PEARLS_AT_START : le nombre de perles par pions au début de partie
     */
    public static int NB_PEARLS_AT_START = 2;
    /**
     * MAX_PIONS : le nombre de pions qu'un joueur peut avoir (il ne faut pas dépasser 6 parce qu'il n'y a que 6 emplacements autour d'un temple)
     */
    public static int NB_PIONS = 3;

    /**
     * to avoid infinite loop when the game is almost blocked
     */
    protected int almostBlockedCount=0;
    
     /**
     * Constructeur permettant d'initialiser les attributs
     * 
     * @param nb_joueurs -> Le nombre de joueurs que le jeu pourra acueillir
     */
    public Jeu(int nb_joueurs_humain, int nb_joueurs_ia, MapEtat map, int NB_TUILES){
        Joueur.ID_STATIC = 0;
        mapEtat = map;
        if(map == MapEtat.MAP1_2P || map == MapEtat.MAP2_2P){initJoueurs(nb_joueurs_humain, 2-nb_joueurs_humain);}
        else if(map == MapEtat.MAP1_4P || map == MapEtat.MAP2_4P){initJoueurs(nb_joueurs_humain, 4-nb_joueurs_humain);}
        else{initJoueurs(nb_joueurs_humain, nb_joueurs_ia);}

        joueurCourant = joueurs.get(0);
        pionCourant = joueurCourant.pions.get(0);
        initSac(map, NB_TUILES);
        //if there is 2 ai player training each other
        if(map==MapEtat.MAP1_2P && nb_joueurs_humain==0 && nb_joueurs_ia==2){
            initMAP1_2PDefault(this);
            plateau.placerPionForce(joueurs.get(0).pions.get(0), new Coordonnee(5,0));
            plateau.placerPionForce(joueurs.get(0).pions.get(1), new Coordonnee(7,0));
            plateau.placerPionForce(joueurs.get(0).pions.get(2), new Coordonnee(5,-1));

            plateau.placerPionForce(joueurs.get(1).pions.get(0), new Coordonnee(-5,0));
            plateau.placerPionForce(joueurs.get(1).pions.get(1), new Coordonnee(-7,0));
            plateau.placerPionForce(joueurs.get(1).pions.get(2), new Coordonnee(-5,-1));
        }
        //if there is 1 human player and 1 ai player
        else if(map==MapEtat.MAP1_2P && nb_joueurs_humain==1 && nb_joueurs_ia==1){
            initMAP1_2PDefault(this);
            plateau.placerPionForce(joueurs.get(1).pions.get(0), new Coordonnee(-5,0));
            plateau.placerPionForce(joueurs.get(1).pions.get(1), new Coordonnee(-7,0));
            plateau.placerPionForce(joueurs.get(1).pions.get(2), new Coordonnee(-5,-1));
        }
        else{
            initMap(this);
        }
    }
    
    public void initJoueurs(int nb_joueurs_humain, int nb_joueurs_ia){
        for (int i = 0; i < nb_joueurs_humain; i++) {
            JoueurHumain j = new JoueurHumain();
            joueurs.add(j);             // On ajoute les joueurs dans la liste de joueurs
            sacTemples.add(j.temple);   // On ajoute chaque temple dans la liste des temples
        }
        for (int i = 0; i < nb_joueurs_ia; i++) {
            JoueurIA j=null;
            //quand y'a 4 joueurs en total, ia bouge aleatoirement, c-a-d epsilon = 1.0
            if(nb_joueurs_humain+nb_joueurs_ia==4){
                j = new JoueurIA(1.0, 0.5, 0.9);
            }else{//sinon, ia bouge intelligemment, c-a-d epsilon = 0.1(10% de chance de bouger aleatoirement)
                j = new JoueurIA(0.1, 0.5, 0.9);
                j.loadQTable();
            }
            joueurs.add(j);             // On ajoute les joueurs dans la liste de joueurs
            sacTemples.add(j.temple);   // On ajoute chaque temple dans la liste des temples
        }

    }

    /**
     * verifiy if there is 2 ai player training each other
     */
    public boolean isAiTraining(){
        if(joueurs.size() != 2){
            return false;
        }
        for (Joueur j : joueurs) {
            if(j instanceof JoueurHumain){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Permet d'initialiser le sac de tuile
     * @param map -> permet de savoir combien de tuile il y a à créer (par rapport à une map par défaut, ou bien dans le cas ou la map est crée manuellement)
     */
    public void initSac(MapEtat map, int NB_TUILES){
        if(map == MapEtat.MANUEL){
            for (int i = 0; i < NB_TUILES; i++) {
                sac.add(new Tuile());
            }
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

    /**
     * Méthode pour savoir si un joueur a atteint ou non un temple adverse
     * @param j -> Le joueur dont on veut savoir s'il a gagné ou non
     * @return -> true s'il a atteint un temple adverse, false sinon
     */
    public boolean aGagne(Joueur j){
        //System.out.println("inside aGagne");
        for (Pion p : j.getPions()){            // On regarde chaque pion du joueur
            if(p.getLocation() != null){        // On vérifie que le pion est placé
                Hexagone emplacement = plateau.getGridHexagone().get(p.getLocation());        // On regarde sur quel hexagone le pion est posé
                if(emplacement instanceof HexagoneCentral){                     // On vérifie qu'il est bien sur un hexagone central
                    if(((HexagoneCentral) emplacement).isTemple()){             // On vérifie que c'est un temple
                        //System.out.println("p.getLocation() = "+p.getLocation());
                        //System.out.println("j.temple.getLocationInGridHexagone() = "+j.temple.getLocationInGridHexagone());
                        if(!p.getLocation().equals(j.temple.getLocationInGridHexagone())){    // On exclue le cas où le pion est dans sa propre base
                            System.out.println("Le joueur "+j.id+" a gagné");
                            setGagneur(j);
                            //find the player who lost, that means find the propriety of the temple
                            for (Joueur joueur : joueurs) {
                                if(joueur.temple.getLocationInGridHexagone().equals(p.getLocation())){
                                    setPerdant(joueur);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void verifyIfPlayerBlocked(){
        System.out.println("------------------------");
        System.out.println("verifyIfPlayerBlocked");
        System.out.println("------------------------");
        int nbBlockedPion = 0;
        Pion pionCourant = getPionCourant();
        for (Pion pionReal : getJoueurCourant().getPions()) {
            setPionCourant(pionReal);
            //case 1: pion is blocked
            if (getPlateau().canMoveLocations(getPionCourant())==null){
                System.out.println("Pion is blocked");
                nbBlockedPion++;
            }
        }
        //all pions are blocked
        if (nbBlockedPion==getJoueurCourant().getPions().size()){
            if(isAiTraining()){
            //dont eliminate the player
                setPerdant(joueurCourant);
            }else{
                perdants.add(joueurCourant);
            }
        }
        //set back the pion courant
        setPionCourant(pionCourant);
    }

    /**
     * method for eliminating a player
     * @return
     */
    public void eliminerJoueur(Joueur loser){
        joueurs.remove(loser);
        //eliminate the loser's pions on the board
        for (Pion p : loser.getPions()) {
            if(p!= null){
                if(p.getLocation()!=null){
                    plateau.removePion(p.getLocation());
                }
            }
        }
        if(joueurs.size()==1){
            jeuEtat=JeuEtat.GAME_OVER;
        }
    }

    /**
     * method for getting the opponent base location
     * @return the opponent base location
     */
    public Coordonnee getOpponentBase(){
        //in the case of 2 players, just return the base of a different player
        return joueurs.get((joueurCourant.id+1)%2).getTemple().getLocationInGridHexagone();
    }

    public double getReward() {
        if (jeuEtat==JeuEtat.GAME_OVER) {
            if (gangeurs.get(0) == joueurCourant) {
                return 100.0;  // If the current player wins, the reward is 100
            } else {
                return -100.0; // If the current player loses, the reward is -100
            }
        } 
        //else return 0.0;
        else {
            // Calculate the Manhattan distance between the current player's closest character and the opponent's base
            double minDistance = Double.MAX_VALUE;
            Coordonnee opponentBase = getOpponentBase();
            //System.out.println("opponent base: " + opponentBase);
            for (Pion pion : getJoueurCourant().getPions()) {
                if (pion.isPlaced()) {
                    Coordonnee pionLocation = pion.getLocation();
                    double distance = Math.abs(pionLocation.getX() - opponentBase.getX()) + Math.abs(pionLocation.getY() - opponentBase.getY());
                    minDistance = Math.min(minDistance, distance);
                }
            }
            System.out.println("minDistance: " + minDistance);
            System.out.println("distance: " + ((JoueurIA) joueurCourant).getDistance());
            // Calculate the reward based on the distance
            double reward = 5*( ((JoueurIA) joueurCourant).getDistance() - minDistance ) ;
            ((JoueurIA) joueurCourant).setDistance(minDistance);
            return reward;
        }
    }    
    
    /**
     * method terminating the game
     */
    public void gameOver(){
        jeuEtat=JeuEtat.GAME_OVER;
        ////print qtable
        //for (Joueur j : joueurs) {
        //    if(j instanceof JoueurIA){
        //        ((JoueurIA) j).printQTable();
        //    }
        //}
        //save QTable of each player
        if(joueurs.size()==2)
            {for (Joueur j : joueurs) {
                if(j instanceof JoueurIA){
                    ((JoueurIA) j).saveQTable();
                }
            }}
        //to do: afficher les info....
    }
    
    //setters
    public void setJoueurCourant(Joueur j){ joueurCourant=j;}
    public void setPionCourant(Pion p){ pionCourant=p;}
    //set perdant
    public void setPerdant(Joueur j){ 
        if(!perdants.contains(j))
            perdants.add(j);}
    public void setGagneur(Joueur j){ 
        if(!gangeurs.contains(j))
            gangeurs.add(j);}
    public void setTuileCourante(Tuile t){ tuileCourante=t; }
    public void setJeuEtat(JeuEtat j){ jeuEtat=j;}
    public void setDernierEtat(JeuEtat d){ dernierEtat = d;}
    //getters
    public JeuEtat getJeuEtat(){ return jeuEtat; }
    public MapEtat getMapEtat(){ return mapEtat; }
    public JeuEtat getDernierEtat(){ return dernierEtat;}
    // Méthodes nécessaires pour un jeu de plateau
    public ArrayList<Joueur> getJoueurs(){return joueurs;}
    public Plateau getPlateau(){return plateau;}
    public ArrayList<Tuile> getSac(){return sac;}
    public ArrayList<TuileTemple> getSacTemples(){return sacTemples;}
    public ArrayList<Joueur> getGagneurs(){ return gangeurs; }
    public ArrayList<Joueur> getPerdants(){ return perdants; }
    public Joueur getJoueurCourant() { return joueurCourant; }
    public Tuile getTuileCourant(){ return tuileCourante; }
    public Pion getPionCourant(){ return pionCourant; }
    public TuileTemple popTemple(){
        TuileTemple temple = sacTemples.get(sacTemples.size()-1);
        sacTemples.remove(sacTemples.size()-1);
        return temple;
    }

    public void resetAlmostBlockedCount(){almostBlockedCount=0;}
    public void incrementAlmostBlockedCount(){almostBlockedCount++;}
    public int getAlmostBlockedCount(){return almostBlockedCount;}

    @Override
    public boolean equals(Object obj) {
        //compare the gridPion of the two states, checking that each pion is in the same location(Coordonnee)
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Jeu other = (Jeu) obj;
        for (Joueur j : joueurs) {
            for (int i=0; i<j.getPions().size(); i++) {
                Pion p=j.getPions().get(i);
                Pion p2=other.getJoueurs().get(j.id).getPions().get(i);

                if (!p.equals(p2)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (Joueur j : joueurs) {
            s += j.toString();
        }
        return s;
    }

    /**
     * Méthode pour savoir si la partie est une partie par défaut ou non, c'est-à-dire :
     * - Nombre de perles max = 3
     * - Nombre de perles de départ = 2
     * - Nombre de pions = 3
     * @return true si c'est une partie par défaut, false si c'est personalisé
     */
    public static boolean isDefaultGame(){
        return NB_PEARL_MAX == 3 && NB_PEARLS_AT_START == 2 && NB_PIONS == 3;
    }

}