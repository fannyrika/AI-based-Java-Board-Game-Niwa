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
    protected Coordonnee location;

    /**
     * Même constructeur que sa classe mère
     */
    public HexagoneCentral(Couleurs ne, Couleurs e, Couleurs se, Couleurs so, Couleurs o, Couleurs no) {
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

    @Override
    public HexagoneCentral copy(){
        HexagoneCentral copy = new HexagoneCentral(portes[0], portes[1], portes[2], portes[3], portes[4],portes[5]);
        copy.setLocation(location);
        return copy;
    }

    /**
     * @return une tuile qui est la tuile actuelle mais tournée d'un cran dans le sens horaire
     */
    @Override
    public HexagoneCentral getRotation(){
        return new HexagoneCentral(portes[5], portes[0], portes[1], portes[2], portes[3], portes[4]);
    }

    public void setLocation(Coordonnee c){this.location = c;}
    public void setLocation(int x, int y){this.location = new Coordonnee(x, y);}
    public Coordonnee getLocation(){return location;}
}
