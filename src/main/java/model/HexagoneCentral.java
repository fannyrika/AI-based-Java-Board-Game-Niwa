package main.java.model;

/**
 * Class HexagoneCentral extends Hexagone :
 * Pareil qu'un Hexagone basique, mais avec un attribut boolean "temple", qui permet de savoir si l'hexagone (donc la tuile) est un temple ou non
 */
public class HexagoneCentral extends Hexagone {

    /**
     * Attribut boolean temple
     * @warning -> l'attribut ne se défini pas dans le constructeur, mais via la méthode "setTemple(boolean b)"
     */
    protected boolean temple;

    /**
     * Même constructeur que sa classe mère
     */
    public HexagoneCentral(Porte ne, Porte e, Porte se, Porte so, Porte o, Porte no) {
        super(ne, e, se, so, o, no);
    }

    /**
     * Méthode pour définir la valeur de l'attribut "temple"
     */
    public void setTemple(boolean b){
        this.temple = b;
    }

    /**
     * Méthode qui @return la valeur boolean "temple"
     */
    public boolean isTemple(){
        return temple;
    }    
}
