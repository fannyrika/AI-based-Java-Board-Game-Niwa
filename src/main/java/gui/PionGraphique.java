package main.java.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.*;
import java.awt.BasicStroke;

import main.java.gui.TuileGraphique.Circle;
import main.java.model.Coordonnee;
import main.java.model.Joueur;
import main.java.model.Pion;

public class PionGraphique {

    private Pion pion;
    private Color color;
    private int x,y;
    protected static final double PION_RADIUS_RATIO = 0.15;
    protected static int rayonTete = (int) (TuileGraphique.radius*PION_RADIUS_RATIO);
    protected Point centre;
    /**
     * @param pion le pion que l'on souhaite représenter
     * @param x la position du point le plus en haut à droite de sa tete dans l'axe des x
     * @param y la position du point le plus en haut à droite de sa tete dans l'axe des y
     * @param rayonTete la taille du rayon de la tete
     */
    public PionGraphique(Pion pion) {
        int px = pion.getLocation().getX();
        int py = pion.getLocation().getY();
        Circle c = GridTuile.allCircles.get(new Coordonnee(px, py));
        //print px and py
        //System.out.println("px: " + px + " py: " + py);
        this.x = c.getX()-TuileGraphique.radius/6;
        this.y = c.getY()-TuileGraphique.radius/3;
        this.pion = pion;
        this.color = this.colorByID(pion.getProprietaire());
        this.centre = new Point(x-rayonTete/2,y+rayonTete/2);
        
    }
    

    public Pion getPion() {
        return pion;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public int getRayonTete(){
        return rayonTete;
    }

    public void setPosition(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void setPosition(Coordonnee co){
        this.x=co.getX();
        this.y=co.getY();
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //le baton sur lequel tiennent les perles
        g2.setStroke(new BasicStroke(rayonTete/6));
    	g2.setColor(Color.BLACK);
        g2.drawLine(x+rayonTete,y, x+rayonTete,y-2*rayonTete);
        g2.setStroke(new BasicStroke(rayonTete/10));
        //on récupère le nombre de perles puis on les dessine sur le baton
        int numPerles=pion.size();
        for (int i = 0; i < numPerles; i++) {
            Color couleurPerle;
            switch (pion.get(i)) {
                case ROUGE:
                    couleurPerle = Color.RED;
                    break;
                case VERT:
                    couleurPerle = Color.GREEN;
                    break;
                case ORANGE:
                    couleurPerle = Color.ORANGE;
                    break;
                default:
                    couleurPerle = Color.BLACK;
                    break;
            }
            g2.setColor(couleurPerle);
            g2.fillOval(x+rayonTete-rayonTete/4, y-(rayonTete/2)*(i+1),rayonTete/2,rayonTete/2);
        
        }       
        //tete
        g2.setColor(new Color(255, 177, 110));
        g2.fillOval(x, y,rayonTete*2, rayonTete*2);
        //cheveux
        g2.setColor(Color.BLACK);
        g2.fillArc(x,y, rayonTete*2, rayonTete,0,180);
        //oeil droit
        g2.setStroke(new BasicStroke(rayonTete/12));
        g2.drawLine(x+rayonTete/3, y+6*rayonTete/7, x+rayonTete/3+rayonTete/4, y+6*rayonTete/7);
        //oeil gauche
        g2.drawLine(x+rayonTete/3+rayonTete, y+6*rayonTete/7, x+rayonTete/3+rayonTete/4+rayonTete, y+6*rayonTete/7);
        //nez
        g2.fillOval(x+rayonTete,y+rayonTete+rayonTete/5,rayonTete/8, rayonTete/6);
        g2.setColor(Color.red);
        //bouche
        g2.fillArc(x+rayonTete-rayonTete/5,y+rayonTete+rayonTete/4, rayonTete/2, rayonTete/2,180,180);
        //le corps 
        g2.setColor(color);
        g2.fillRoundRect(x+rayonTete/12,y+2*rayonTete,2*rayonTete,4*rayonTete, rayonTete*2, rayonTete*2);
        g2.fillRect(x+rayonTete/12,y+5*rayonTete, 2*rayonTete, rayonTete);
       
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        
    }
    /**
     * Méthode privée qui permet de donner une robe de couleur au pion par rapport à l'ID du joueur propriètaire 
     * ( c'est juste un doublon de la méthode homonyme dans TuileTempleGraphique)
     * @param j -> Le joueur
     * @return -> La couleur associée au joueur
     */
	private Color colorByID(Joueur j){
        switch (j.getID()%4) {
            case 0: return new Color(122, 174, 230);
            case 1: return new Color(255, 82, 66);
            case 2: return new Color(247, 134, 247);
            case 3: return new Color(255, 138, 202);
            default: return Color.BLACK;
        }
    }
}

