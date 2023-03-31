package main.java.model;

/**
 * La classe Coordonnee va servir pour définir le plateau
 */
public class Coordonnee {

    /**
     * Attributs permettant de définir une coordonnee
     */
    protected int x;
    protected int y;

    /**
     * Constructeur prenant un "x" et "y", et les affectant aux attributs
     */
    public Coordonnee(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * deep copy constructor
     */
    public Coordonnee(Coordonnee c){
        this.x = c.x;
        this.y = c.y;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    
    /*
     * Pourquoi redéfinir equals() et hashCode() ?
     * 
     * Puisque le plateau est représenté par une HashMap, il faut impérativement redéfinir les méthodes "equals()" et "hashCode()"
     * 
     * Imaginons que dans votre HashMap, vous avez une clé de type Coordonnee avec un x = 1, et un y = 2 (on nommera cette objet o1).
     * Pour rechercher donc s'il y a une tuile en x = 1, et y = 2, il faudra donc recréer un objet de type Coordonnee (qu'on nommera o2), avec comme attributs x = 1, et y = 2,
     * puis faire une recherche dans notre HashMap.
     * Le soucis, c'est que l'objet o1 et o2 ne désignent pas vers le même objet, donc la recherche ne donnera rien, alors qu'il existe bel et bien une tuile en x = 1, y = 2.
     * 
     * C'est donc pour ça qu'il faut comparer les Coordonnees par rapport aux "x" et "y", et non pas par rapport aux pointeurs.
     */

    /**
     * Méthode qui @return true si les deux references pointent vers le même objet ou qu'ils ont les mêmes "x" et "y"
     */
    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        if(this == o){
            return true;
        }
        Coordonnee c = (Coordonnee) o;
        return this.x == c.x && this.y == c.y;
    }
    @Override
    public int hashCode(){
        int hash = 17;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        return hash;
    }

    @Override
    public String toString(){
        return getClass().getName()+"@"+Integer.toHexString(hashCode())+" -> [x = "+x+", y = "+y+"]";
    }
    
}