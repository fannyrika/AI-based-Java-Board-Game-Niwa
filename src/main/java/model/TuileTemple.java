package main.java.model;

public class TuileTemple extends Tuile {

    /**
     * Attribut pour savoir à qui est le temple
     */
    protected Joueur proprietaire;

    public Joueur getProprietaire(){return proprietaire;}
    
    /**
     * Constructeur initialisant une tuile avec temple
     * +
     * Le propriétaire
     */
    public TuileTemple(Joueur j){
        this.proprietaire = j;
        setTemple();
    }

    /**
     * Les tuiles temple ont toujours les mêmes portes aux mêmes endroits
     */
    private void setTemple(){
        centre = new HexagoneCentral(Couleurs.VERT, Couleurs.VERT, Couleurs.ROUGE, Couleurs.ROUGE, Couleurs.ORANGE, Couleurs.ORANGE);

        hexagones[0] = new Hexagone(null, null, Couleurs.VERT, Couleurs.VERT, null, null);
        hexagones[1] = new Hexagone(null, null, null, null, Couleurs.VERT, Couleurs.VERT);
        hexagones[2] = new Hexagone(null, null, null, null, Couleurs.ROUGE, Couleurs.ROUGE);
        hexagones[3] = new Hexagone(Couleurs.ROUGE, Couleurs.ROUGE, null, null, null, null);
        hexagones[4] = new Hexagone(Couleurs.ORANGE, Couleurs.ORANGE, null, null, null, null);
        hexagones[5] = new Hexagone(null, null, Couleurs.ORANGE, Couleurs.ORANGE, null, null);

        centre.setTemple(true);
    }

}
