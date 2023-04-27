package main.java.model;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Hexagone extends Polygon{
    private static Color[] frontieres = new Color[6]; // 6 chaines correspondant aux 6 frontières entourant les formes du polygône
    // Si on fait 3 frontieres en croissement : ca sera donc un tab de taille 3
    private Point[] points = new Point[6]; // 6 points, donc les coordonnées des coins de l'hexagone
    
    /*
     *      0
     *  5       1
     *      *
     *  4       2
     *      3  
     * Representation des points de l'hexagone selon l'indice
     */

    private BufferedImage image; // Une image de fond si on vient à choisir d'y mettre une image et pas des graphiques
    private Point center; // centre de l'hexagone, donc coordonnées de l'hexagone en lui-même
    private int radius = 70; // Taille de l'hexagone
    private int rotation = 90; // Rotation du polygone de 90 afin d'avoir la pointe vers le haut
    private Graphics2D g2d; // Correspond à la representation graphique de l'hexagone 
                            // (mais je sais pas si c'est nécessaire de le garder)

    // Les coordonnées de l'hexagone est le Point center
    // Mais nous avons aussi la possibilité de trouver les coordonnées de chaques coins de l'hexagone
    public Hexagone(Point center) {
        npoints = 6;
        xpoints = new int[6];
        ypoints = new int[6];

        this.center = center;        
        updatePoints();

        // On definit image directement dans le constructeur
        /*
        try{
            image = ImageIO.read(getClass().getResource("hexagone.png"));  // bien fixer le chemin vers l'image
        }catch(IOException e){
            e.printStackTrace();
        }
        */
    }

    // Afin de permettre aux hexagones de bien former une grille, on pourra accèder à leurs taille
    public int getRadius(){
        return radius;
    }
    
    public Point getCenter(){
        return center;
    }

    // Correspond à la frontière des points x et x + 1, sauf si x = 5, alors il s'agit de la frontière x et 0 
    public Color getFrontiere(int x){
        return frontieres[x];
    }

    // Les trois fonctions suivantes servent à former les 6 points de l'hexagone :
    private double findAngle(double fraction) {
        return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
    }

    private Point findPoint(double angle) {
        int x = (int) (center.x + Math.cos(angle) * radius);
        int y = (int) (center.y + Math.sin(angle) * radius);
        
        return new Point(x, y);
    }
    
    protected void updatePoints() {
        for (int p = 0; p < 6; p++) {
            double angle = findAngle((double) p / 6);
            Point point = findPoint(angle);
            xpoints[p] = point.x;
            ypoints[p] = point.y;
            points[p] = point;
        }
    }

    // Détermine et dessine les couleurs des frontières comme étant les six côtés de l'hexagone 
    // et on les affecte au tableau correspondant 
    public static void drawLine(Graphics2D g, Point x, Point y, int indice){
        int tmp = (int) (Math.random() * 3);
        Color[] frontieresBis = {Color.RED, Color.YELLOW, Color.BLUE};
       
        g.setColor(frontieresBis[tmp]);
        frontieres[indice] = frontieresBis[tmp]; // Affecte la couleur choisit au tableau de frontières grâce à indice
        Stroke stroke = new BasicStroke(2f); // Définit l'épaisseur de la ligne
        g.setStroke(stroke);
        // On dessine la ligne entre le point i et i+1 selon les éléments dans points
        g.drawLine((int) x.getX(), (int) x.getY(), (int) y.getX(), (int) y.getY());        
    }
    
    // Renvoie l'hexagone dont la representation a été créer afin de pouvoir le placer dans la HashMap
    public static Hexagone drawHexagone(Graphics2D g, int x, int y, int z, int w) {
        // Les coordonnées (x, y) dans la HashMap sont les arguments x et y
        Hexagone hexagone = new Hexagone(new Point(z, w));

        g.setColor(new Color(0xFFFFFF)); // Couleur de remplissage de l'hexagone
        g.fillPolygon(hexagone);
        //g.setColor(Color.BLACK); //Couleur des lignes noires
        g.drawPolygon(hexagone); // Dessine le polygône
        for(int i = 0; i < 6; i++){
            if(i < 5) drawLine(g, hexagone.points[i], hexagone.points[i + 1], i);
            else drawLine(g, hexagone.points[5], hexagone.points[0], 5); // Afin de relier les points 5 et 0
        }
         /*
         * Si on veut faire les 3 lignes en croissement:
         *  for(int i = 0; i < 3; i++){
         *      drawLine(g, hexagone.points[0], hexagone.center, i);
         *      drawLine(g, hexagone.points[2], hexagone.center, i);
         *      drawLine(g, hexagone.points[4], hexagone.center, i);
         *  }
         */

        //Afin d'afficher les coordonnées, on utilisera les deux lignes suivantes : 
        g.setColor(Color.BLACK);
        g.drawString("("+ x + ", " + y + ")", z, w);
       
        hexagone.g2d = g;

        return hexagone;
    }

    // Fonction de test afin de permettre l'ajout d'une image en fond de l'hexagone
    public static Hexagone drawHexagone(Graphics2D g, int x, int y, int z, int w, boolean image) {
        // Les coordonnées (x, y) dans la HashMap sont les arguments x et y
        Hexagone hexagone = new Hexagone(new Point(z, w));

        if(!image) g.setColor(new Color(0xFFFFFF)); // Couleur de remplissage de l'hexagone
        else{

        }
        g.fillPolygon(hexagone);
        //g.setColor(Color.BLACK); //Couleur des lignes noires
        g.drawPolygon(hexagone); // Dessine le polygône
        for(int i = 0; i < 6; i++){
            if(i < 5) drawLine(g, hexagone.points[i], hexagone.points[i + 1], i);
            else drawLine(g, hexagone.points[5], hexagone.points[0], 5); // Afin de relier les points 5 et 0
        }
        
        //Afin d'afficher les coordonnées, on utilisera les deux lignes suivantes : 
        g.setColor(Color.BLACK);
        g.drawString(x + " : " + y, z, w);
       
        hexagone.g2d = g;

        return hexagone;
    }
}
