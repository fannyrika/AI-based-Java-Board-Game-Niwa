package main.java.gui;

import java.awt.*;

import main.java.model.Couleurs;
import main.java.model.Tuile;

public class TuileGraphique extends Polygon {

    protected Tuile tuile;
    protected static final int radius = 50; 
    protected Point center;
    protected Color color;
    
    public TuileGraphique(Tuile tuile, int x, int y){
        this.tuile = tuile;
        if(x%2 == 0){
            this.center = new Point(radius*x*5/2,-3*radius*y);
            color = Color.WHITE;
        }
        else{
            color = Color.GRAY;
            this.center = new Point(radius*x*5/2,-3*radius*y - 3*radius/2);
        }
        
        center.setLocation(center.x + GridTuile.screen.getWidth()/2,center.y + GridTuile.screen.getHeight()/2);     // On place le (0,0) au centre de la page
    }

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

    private void drawLineColor(Graphics g, Couleurs c, int x1, int y1, int x2, int y2){
        switch (c) {
            case ROUGE:
                g.setColor(Color.RED);
                break;
            case ORANGE:
                g.setColor(Color.ORANGE);
                break;
            case VERT:
                g.setColor(Color.GREEN);
                break;
        }
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawTile(Graphics g){
        // On trace chaque face de la tuile
        Point[] bas = givePoints(center);
        for (int i = 1; i < 6; i++){
            this.addPoint(bas[i].x,bas[i].y);
        }

        Point centreGauche = new Point(this.xpoints[this.npoints-1],this.ypoints[this.npoints-1]-radius);
        Point[] gauche = givePoints(centreGauche);
        for (int i = 3; i < 6; i++){
            this.addPoint(gauche[i].x,gauche[i].y);
        }
        for (int i = 0; i < 2; i++){
            this.addPoint(gauche[i].x,gauche[i].y);
        }

        Point centreDroite = new Point(bas[1].x,bas[1].y-radius);
        Point[] droite = givePoints(centreDroite);
        for (int i = 0; i < 3; i++){
            this.addPoint(droite[i].x,droite[i].y);
        }
        g.setColor(color);
        g.fillPolygon(this);

        // On trace les portes
        ((Graphics2D) g).setStroke(new BasicStroke(radius/6));
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[2], this.xpoints[0], this.ypoints[0], center.x, center.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[3], this.xpoints[5], this.ypoints[5], center.x, center.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[4], this.xpoints[5], this.ypoints[5], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[5], this.xpoints[9], this.ypoints[9], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[0], this.xpoints[9], this.ypoints[9], centreDroite.x, centreDroite.y);
        drawLineColor(g, tuile.getHexagoneCentral().getPortes()[1], this.xpoints[0], this.ypoints[0], centreDroite.x, centreDroite.y);

        drawLineColor(g, tuile.getHexagones()[2].getPortes()[4], this.xpoints[2], this.ypoints[2], center.x, center.y);
        drawLineColor(g, tuile.getHexagones()[4].getPortes()[0], this.xpoints[7], this.ypoints[7], centreGauche.x, centreGauche.y);
        drawLineColor(g, tuile.getHexagones()[0].getPortes()[2], this.xpoints[11], this.ypoints[11], centreDroite.x, centreDroite.y);


        // On trace les bordures noires ici
        g.setColor(Color.BLACK);
        for (int i = 0; i < this.npoints; i++) {
            if(i == this.npoints - 1){
                g.drawLine(this.xpoints[i], this.ypoints[i], this.xpoints[0], this.ypoints[0]);
            }
            else{
                g.drawLine(this.xpoints[i], this.ypoints[i], this.xpoints[i+1], this.ypoints[i+1]);
            }
        }

        ((Graphics2D) g).setStroke(new BasicStroke());
    }
}
