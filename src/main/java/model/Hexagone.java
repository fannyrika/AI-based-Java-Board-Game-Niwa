package main.java.model;

/**
 * Class Hexagone :
 * 
 * - Possède une énumération pour définir les "portes de couleurs" (pouvant être ROUGE, VERT, ou ORANGE)
 * - Possède une liste de taille 6 pour définir ces 6 portes (on commence par la porte en haut à droite, et on lis le reste dans le sens horaire)
 * Exemple : 
 *  portes[0] -> la porte en haut à droite
 *  portes[1] -> la porte à droite
 *  portes[2] -> la porte en bas à droite
 *  portes[3] -> la porte en bas à gauche
 *  etc...
 */
public class Hexagone implements Copy<Hexagone> {
    
    /**
     * Enumération pour les 3 différentes couleurs de portes possible
     * +
     * La liste contenant ces portes (de taille 6 pour chaque face d'un hexagone)
     */
    protected enum Couleurs {ROUGE, VERT, ORANGE}
    protected Couleurs[] portes;

    /**
     * Constructeur qui défini les 6 portes de l'hexagone avec les arguments (donnés dans le sens horaire, et en partant de la porte en haut à droite)
     */
    public Hexagone(Couleurs ne, Couleurs e, Couleurs se, Couleurs so, Couleurs o, Couleurs no){
        portes = new Couleurs[6];
        portes[0] = ne;
        portes[1] = e;
        portes[2] = se;
        portes[3] = so;
        portes[4] = o;
        portes[5] = no;
    }

    /**
     * Méthode qui tourne la tuile actuelle dans le sens horaire
     */
    public void rotate(){
        Couleurs tmp = portes[5];
        portes[5] = portes[4];
        portes[4] = portes[3];
        portes[3] = portes[2];
        portes[2] = portes[1];
        portes[1] = portes[0];
        portes[0] = tmp;
    }

    /**
     * Méthode qui permet "d'imprimer"/"de tamponner" les portes != null de l'hexagone "h" sur l'hexagone actuel
     * 
     * @param h -> L'hexagone qui va imprimer ses portes sur l'hexagone actuel
     */
    public void tamponBy(Hexagone h){
        for (int i = 0; i < portes.length; i++) {
            if(h.portes[i] != null){
                this.portes[i] = h.portes[i];
            }
        }
    }

    /**
     * @return une tuile qui est la tuile actuelle mais tournée d'un cran dans le sens horaire
     */
    public Hexagone getRotation(){
        return new Hexagone(portes[5], portes[0], portes[1], portes[2], portes[3], portes[4]);
    }

    /**
     * Méthode qui : @return une copie de l'hexagone actuel (copie de ses portes)
     */
    @Override
    public Hexagone copy(){
        return new Hexagone(portes[0], portes[1], portes[2], portes[3], portes[4],portes[5]);
    }

    @Override
    public String toString(){
        return getClass().getName()+"@"+Integer.toHexString(hashCode())+" -> "+toStringPartiel();
    }
    public String toStringPartiel(){
        return "["+portes[0]+","+portes[1]+","+portes[2]+","+portes[3]+","+portes[4]+","+portes[5]+"]";
    }
}