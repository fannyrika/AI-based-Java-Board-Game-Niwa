package main.java.model;

public class TuileTemple extends Tuile {

    /**
     * Attribut pour savoir à qui est le temple
     */
    protected Joueur proprietaire;
    protected int type;

    public Joueur getProprietaire(){return proprietaire;}
    
    /**
     * Constructeur initialisant une tuile avec temple
     * +
     * Le propriétaire
     */
    public TuileTemple(Joueur j){
        this.proprietaire = j;
        setTemple(0);
    }

    public TuileTemple(Joueur j, int type){
        this.proprietaire = j;
        setTemple(type);
    }

    /**
     * Les tuiles temple ont toujours les mêmes portes aux mêmes endroits
     * @param t Puisqu'il n'y a que 3 type de temple, l'entier {@code t} définira la tuile suivant la valeur de celui-ci
     */
    private void setTemple(int t){

        this.type = t % 3;

        if(this.type == 0){
            setTempleColors(Couleurs.VERT, Couleurs.ROUGE, Couleurs.ORANGE);
        }
        else if(this.type == 1){
            setTempleColors(Couleurs.ORANGE, Couleurs.VERT, Couleurs.ROUGE);
        }
        else{
            setTempleColors(Couleurs.ROUGE, Couleurs.ORANGE, Couleurs.VERT);
        }
    }

    /**
     * Méthode privée, utile pour la fonction {@code setTemple(int t)}
     * @param a
     * @param b
     * @param c
     */
    private void setTempleColors(Couleurs a, Couleurs b, Couleurs c){
        centre = new HexagoneCentral(a, a, b, b, c, c);

        hexagones[0] = new Hexagone(null, null, a, a, null, null);
        hexagones[1] = new Hexagone(null, null, null, null, a, a);
        hexagones[2] = new Hexagone(null, null, null, null, b, b);
        hexagones[3] = new Hexagone(b, b, null, null, null, null);
        hexagones[4] = new Hexagone(c, c, null, null, null, null);
        hexagones[5] = new Hexagone(null, null, c, c, null, null);

        centre.setTemple(true);
    }



    /**
     * Méthode qui tourne d'un cran dans le sens horaire la tuile actuelle
     */
    @Override
    public void rotate(){
        TuileTemple rotated = getRotation();
        centre = rotated.centre;
        for (int i = 0; i < hexagones.length; i++) {
            hexagones[i] = rotated.hexagones[i];
        }
    }

    /**
     * @return une tuile tournée d'un cran dans le sens horaire
     */
    @Override
    public TuileTemple getRotation(){
        this.type += 1;
        TuileTemple rotated = new TuileTemple(proprietaire, this.type);
        return rotated;
    }

}
