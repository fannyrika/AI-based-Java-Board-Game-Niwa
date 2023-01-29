package main.java.model;

import main.java.model.Hexagone.Porte;

public class TuileTemple extends Tuile {

    /**
     * Attribut pour savoir à qui est le temple
     */
    protected Joueur proprietaire;
    
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
        centre = new HexagoneCentral(Porte.VERT, Porte.VERT, Porte.ROUGE, Porte.ROUGE, Porte.ORANGE, Porte.ORANGE);

        hexagones[0] = new Hexagone(null, null, Porte.VERT, Porte.VERT, null, null);
        hexagones[1] = new Hexagone(null, null, null, null, Porte.VERT, Porte.VERT);
        hexagones[2] = new Hexagone(null, null, null, null, Porte.ROUGE, Porte.ROUGE);
        hexagones[3] = new Hexagone(Porte.ROUGE, Porte.ROUGE, null, null, null, null);
        hexagones[4] = new Hexagone(Porte.ORANGE, Porte.ORANGE, null, null, null, null);
        hexagones[5] = new Hexagone(null, null, Porte.ORANGE, Porte.ORANGE, null, null);

        centre.setTemple(true);
    }

}
