package main.java.gui;

import java.awt.*;

import main.java.model.Coordonnee;
import main.java.model.Couleurs;
import main.java.model.Tuile;
import main.java.model.interfaces.HexagoneAutour;

public class TuileGraphique extends Polygon {

    public static class Circle {

        protected int x;
        protected int y;
        protected static int circleRadius = (int)(TuileGraphique.radius * HITBOX_CIRCLE_RADIUS_RATIO);
        protected Coordonnee locationInGridHexagone;

        /**
         * Constructeur permettant de "simuler" un cercle, avec comme centre (x,y) et comme rayon "radius"
         * @param x -> centre x
         * @param y -> centre y
         * @param radius -> rayon du cercle
         */
        public Circle(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Circle(double x, double y){
            this((int)x,(int)y);
        }

        public Circle(Point p){
            this((int)p.getX(),(int)p.getY());
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public static int getCircleRadius(){
            return circleRadius;
        }

        public boolean contains(Point p){
            int x2 = (int)p.getX();
            int y2 = (int)p.getY();  
            double distance = Math.sqrt((x2-this.x)*(x2-this.x) + (y2-this.y)*(y2-this.y));  
            return distance < circleRadius; 
        }

        public boolean contains(int x, int y){
            return contains(new Point(x,y));
        }

        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawOval((x-circleRadius/2), (y-circleRadius/2), circleRadius, circleRadius);
        }

        public void setLocationInGridHexagone(Coordonnee c){
            this.locationInGridHexagone = c;
        }

        public Coordonnee getLocationInGridHexagone(){
            return locationInGridHexagone;
        }
    }

    /**
     * La tuile qu'on veut représenter graphiquement
     */
    protected Tuile tuile;
    /**
     * Le rayon des hexagones (grossièrement : la taille des tuiles)
     */
    protected static int radius = 50;

    /**
     * Le rayon minimal autorisé
     */
    protected static final int RADIUS_MIN = 20;

    /**
     * Le rayon maximal autorisé
     */
    protected static final int RADIUS_MAX = 100;

    /**
     * Représente le centre de l'hexagone du bas de la tuile
     */
    /*
     * Graphiquement, le centreBas est représenté par la lettre B, centreGauche par G, et centreDroite par D, et le centre par C  :
     * 
     *            #               # 
     *       #         #     #         #
     *            G               D     
     *       #         #     #         # 
     *            #       C       # 
     *                    #    
     *               #         #
     *                    B     
     *               #         #
     *                    #
     */
    protected Point centre;
    protected Point centreBas;
    protected Point centreGauche;
    protected Point centreDroite;
    /**
     * La couleur qu'aura l'intérieur de la tuile
     */
    protected Color color;

    /**
     * Représente le ratio du rayon des cercles par rapport au rayon des tuiles, pour comprendre, exemple tout simple :
     * Si HITBOX_CIRCLE_RADIUS_RATIO = 0.5 , alors le rayon des cercles sera égale à : (rayon des tuiles) * 0.5 , ou pour faire simple, la moitié du rayon des tuiles 
     */
    protected static final double HITBOX_CIRCLE_RADIUS_RATIO = 0.5;
    protected Circle[] cercles = new Circle[7];
    /**
     * Boolean pour savoir si on trace les cercles ou non
     */
    protected static final boolean DRAW_CIRCLE = false;
    
    /**
     * Constructeur d'une tuile graphique
     * @param tuile -> La tuile à représenter
     * @param x -> La coordonnee en x
     * @param y -> La coordonnee en y
     */
    public TuileGraphique(Tuile tuile, int x, int y){
        this.tuile = tuile;
        color = Color.WHITE;
        if(x%2 == 0){           // Pour pouvoir placer la tuile correctement par rapport à la parité du x
            this.centreBas = new Point(radius*x*5/2,-3*radius*y);
        }
        else{
            this.centreBas = new Point(radius*x*5/2,-3*radius*y - 3*radius/2);
        }
        centreBas.setLocation(centreBas.x + GridTuile.screen.getWidth()/2 + GridTuile.dx,centreBas.y + GridTuile.screen.getHeight()/2 + GridTuile.dy+radius);     // On place le (0,0) au centre de la page
        centre = givePoints(centreBas)[0];
        centreGauche = givePoints(new Point(centreBas.x,centreBas.y-radius))[5];
        centreDroite = givePoints(new Point(centreBas.x,centreBas.y-radius))[1];
        // On défini les cercles
        defineCercles();
    }

    /**
     * Méthode privée pour définir les cercles (pour les emplacements des pions)
     */
    private void defineCercles(){
        Coordonnee HexagoneCentrale = tuile.getLocationInGridHexagone();
        Coordonnee[] HexagonesAutour = HexagoneAutour.get(HexagoneCentrale);

        cercles[0] = new Circle(centre);
        cercles[0].setLocationInGridHexagone(HexagoneCentrale);

        cercles[1] = new Circle(centreDroite.getX(),centreDroite.getY()-radius);cercles[1].setLocationInGridHexagone(HexagonesAutour[0]);
        cercles[2] = new Circle(centre.getX()+2*radius - Circle.circleRadius*0.7,centre.getY());cercles[2].setLocationInGridHexagone(HexagonesAutour[1]);
        cercles[3] = new Circle(centreDroite.getX(),centreDroite.getY()+2*radius);cercles[3].setLocationInGridHexagone(HexagonesAutour[2]);

        cercles[4] = new Circle(centreGauche.getX(),centreGauche.getY()+2*radius);cercles[4].setLocationInGridHexagone(HexagonesAutour[3]);
        cercles[5] = new Circle(centre.getX()-2*radius + Circle.circleRadius*0.7,centre.getY());cercles[5].setLocationInGridHexagone(HexagonesAutour[4]);
        cercles[6] = new Circle(centreGauche.getX(),centreGauche.getY()-radius);cercles[6].setLocationInGridHexagone(HexagonesAutour[5]);
    }

    /**
     * Méthode privée permettant de récuperer les 6 points autour d'un centre permettant de définir un hexagone
     * @param p -> Le centre
     * @return -> Un tableau contenant les 6 points autour du centre
     */
    /*
     * Voici chaque points[i], avec p étant le centre :
     * 
     *                    0
     *               5         1
     *                    p     
     *               4         2
     *                    3
     */
    private Point[] givePoints(Point p){
        Point[] points = new Point[6];
        int index = 0;
        for (int i = 3; i < 9; i++) {
            points[index] = new Point(
                (int) (p.x + radius * Math.cos(i * 2 * Math.PI / 6 + Math.PI/2)),
                (int) (p.y + radius * Math.sin(i * 2 * Math.PI / 6 + Math.PI/2))
            );
            index++;
        }
        return points;
    }

    /**
     * Méthode privée, permettant de tracer un trait d'une certaine couleur (utilisée pour tracer les portes de couleurs)
     * @param g -> Le graphique sur lequel on dessine
     * @param c -> La couleur du trait
     * @param x1 -> x de départ
     * @param y1 -> y de départ
     * @param x2 -> x d'arrivée
     * @param y2 -> y d'arrivée
     */
    private void drawLineColor(Graphics g, Couleurs c, int x1, int y1, int x2, int y2){
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(radius/6));
        //g.drawLine(x1, y1, x2, y2);
        switch (c) {
            case ROUGE:
                g.setColor(Color.RED);
                break;
            case ORANGE:
                g.setColor(new Color(255, 166, 77));
                break;
            case VERT:
                g.setColor(new Color(147, 255, 84));
                break;
        }
        ((Graphics2D) g).setStroke(new BasicStroke(radius/10));
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Méthode pour tracer la tuile dans son intégralité
     * @param g -> Le graphique sur lequel on dessine
     */
    public void drawTile(Graphics gr){
        // On trace chaque face de la tuile
        Graphics2D g=(Graphics2D)gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Point[] bas = givePoints(centreBas);
        for (int i = 1; i < 6; i++){
            this.addPoint(bas[i].x,bas[i].y);
        }

        Point[] gauche = givePoints(centreGauche);
        for (int i = 3; i < 6; i++){
            this.addPoint(gauche[i].x,gauche[i].y);
        }
        for (int i = 0; i < 2; i++){
            this.addPoint(gauche[i].x,gauche[i].y);
        }

        Point[] droite = givePoints(centreDroite);
        for (int i = 0; i < 3; i++){
            this.addPoint(droite[i].x,droite[i].y);
        }
        GradientPaint gradient = new GradientPaint(this.getBounds().x,this.getBounds().y,color,this.getBounds().x+5*radius,this.getBounds().y,new Color(166, 172, 173));
        g.setPaint(gradient);
        //g.setColor(color);
        g.fillPolygon(this);
        // On trace les portes
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[2], this.xpoints[0], this.ypoints[0], centreBas.x, centreBas.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[3], this.xpoints[5], this.ypoints[5], centreBas.x, centreBas.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[4], this.xpoints[5], this.ypoints[5], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[5], this.xpoints[9], this.ypoints[9], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[0], this.xpoints[9], this.ypoints[9], centreDroite.x, centreDroite.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[1], this.xpoints[0], this.ypoints[0], centreDroite.x, centreDroite.y);

        drawLineColor(g, tuile.getHexagones()[2].getPortes()[4], this.xpoints[2], this.ypoints[2], centreBas.x, centreBas.y);
        drawLineColor(g, tuile.getHexagones()[4].getPortes()[0], this.xpoints[7], this.ypoints[7], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagones()[0].getPortes()[2], this.xpoints[11], this.ypoints[11], centreDroite.x, centreDroite.y);


        // On trace les bordures noires ici
        g.setColor(Color.BLACK);
        
       /* for (int i = 0; i < this.npoints; i++) {
            if(i == this.npoints - 1){
                g.drawLine(this.xpoints[i], this.ypoints[i], this.xpoints[0], this.ypoints[0]);
            }
            else{
                g.drawLine(this.xpoints[i], this.ypoints[i], this.xpoints[i+1], this.ypoints[i+1]);
            }
        }*/

        ((Graphics2D) g).setStroke(new BasicStroke());  // On remet l'épaisseur des traits à sa valeur d'origine

        if(DRAW_CIRCLE){
            for (Circle c : cercles) {
                c.draw(g);
            }
        }
    }

    /**
     * Méthode pour pouvoir changer la valeur de "radius" correctement par rapport à {@value}RADIUS_MIN et {@value}RADIUS_MAX
     * @param newRadius -> Nouveau rayon
     */
    public static void setRadius(int newRadius){
        radius = newRadius;
        if(radius < RADIUS_MIN){
            radius = RADIUS_MIN;
        }
        if(radius > RADIUS_MAX){
            radius = RADIUS_MAX;
        }

        Circle.circleRadius = (int)(TuileGraphique.radius * HITBOX_CIRCLE_RADIUS_RATIO);
        PionGraphique.rayonTete = (int) (TuileGraphique.radius*PionGraphique.PION_RADIUS_RATIO);
    }

    /**
     * Méthode permettant de zoomer et dezoomer (modifier la valeur de "radius")
     * @param zoom -> Valeur de zoomage/dezoomage
     */
    public static void zoom(int zoom){
        setRadius(radius+zoom);
    }
}
