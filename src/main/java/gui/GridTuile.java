package main.java.gui;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;

import main.java.model.Coordonnee;
import main.java.model.Jeu;
import main.java.model.Tuile;
import main.java.model.TuileTemple;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import main.java.gui.TuileGraphique.Circle;
import main.java.model.*;

public class GridTuile extends JPanel implements KeyListener, MouseInputListener {

    /**
     * Le model sur lequel le graphique se base
     */
    protected Jeu model;
    /**
     * Certainement temporaire, représente la taille de l'écran
     */
    protected static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Décalage des tuiles sur le graphique en x
     */
    protected static int dx = 0;
    /**
     * Décalage des tuiles sur le graphique en y
     */
    protected static int dy = 0;

    /**
     * Représente la distance de déplacement lorsqu'on glisse le plateau (+ on l'augmente, plus le déplacement est rapide)
     */
    protected static final int DISTANCE_DEPLACEMENT = 10;
    /**
     * Représente la vitesse à laquelle le zoom/dezoom est fait (+ on l'augmente, plus le zoom/dezoom est rapide)
     */
    protected static final int DISTANCE_ZOOM = 10;

    protected ArrayList<TuileGraphique> tuilesGraphique = new ArrayList<TuileGraphique>();

    /**
     * Constructeur
     * @param m
     */
    public GridTuile(Jeu m){
        this.model=m;
        this.setPreferredSize(screen);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(new ComponentAdapter() {                  // Ce bout de code sert à remettre le plateau au centre lorsqu'on resize la fenetre
            public void componentResized(ComponentEvent componentEvent) {
                GridTuile.dx = 0;
                GridTuile.dy = 0;
                screen = GridTuile.this.getSize();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D mainGraphics = (Graphics2D)g;

        tuilesGraphique.clear();

        // On parcours toutes les tuiles sur le plateau, pour ainsi les dessiner
        for(Tuile t : model.getPlateau().getGridTuile().values()){
            int x = t.getLocationInGridTuile().getX();
            int y = t.getLocationInGridTuile().getY();
            if(t instanceof TuileTemple){
                TuileTempleGraphique tuileG = new TuileTempleGraphique((TuileTemple) t, x,y,((TuileTemple)t).getProprietaire());
                tuileG.drawTile(mainGraphics);
                tuilesGraphique.add(tuileG);
            }
            else{
                TuileGraphique tuileG = new TuileGraphique(t, x,y);
                tuileG.drawTile(mainGraphics);
                tuilesGraphique.add(tuileG);
            }
        }

        //TODO: On parcours toutes les pions sur le plateau, pour ainsi les dessiner

        //test pion graphique
        PionGraphique pionGraphique = new PionGraphique(new Pion(new Joueur()), 0, 0, 3);
        pionGraphique.draw(mainGraphics);
    }

    /**
     * Méthode privée permettant de piocher une tuile dans le model et de la placer (utile seulement pour les tests)
     * @param x -> Coordonnee x
     * @param y -> Coordonnee y
     */
    private void piocheAndPlace(int x, int y){
        model.getPlateau().placeTuileBrutForce(model.piocher(), new Coordonnee(x, y));
    }

    /**
     * Méthode permettant de déplacer graphiquement le plateau d'un certain x et y
     * @param dx -> Déplacemenent en x
     * @param dy -> Déplacemenent en y
     */
    public void glisserVers(int dx, int dy){
        GridTuile.dx += dx;
        GridTuile.dy -= dy;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(model.getJeuEtat()==JeuEtat.CHANGING_VIEW){
        switch (e.getKeyChar()) {
            case 's':   // gauche
                glisserVers(-DISTANCE_DEPLACEMENT, 0);
                break;
            case 'f':   // droite
                glisserVers(DISTANCE_DEPLACEMENT, 0);
                break;
            case 'd':   // bas
                glisserVers(0, -DISTANCE_DEPLACEMENT);
                break;
            case 'e':   // haut
                glisserVers(0, DISTANCE_DEPLACEMENT);
                break;
            case '+':   // zoom
                TuileGraphique.zoom(DISTANCE_ZOOM);
                break;
            case '_':   // dezoom
                TuileGraphique.zoom(-DISTANCE_ZOOM);
            default:
                break;
        }}
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clic = e.getPoint();
        for (TuileGraphique t : tuilesGraphique) {  
            for (Circle c : t.cercles) {
                if(c.contains(clic)){
                    System.out.println(c.getLocationInGridHexagone());
                    Pion pionChoisi=model.getPlateau().getGridPion().get(c.getLocationInGridHexagone());
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                        if(pionChoisi!=null){
                            model.setPionCourant(pionChoisi);
                            model.setJeuEtat(JeuEtat.CONTINUE);
                        }
                    }
                    else if(model.getJeuEtat()==JeuEtat.PLACING_PION){
                        //TODO:programmez ici pour effacer tous les cercles
                        Coordonnee depart = model.getPionCourant().getLocation();
                        ArrayList<Coordonnee> locationsPossible = model.getPlateau().canMoveLocations(model.getPionCourant());
                        if(locationsPossible.contains(c.getLocationInGridHexagone())){
                            model.getPlateau().getGridPion().remove(depart);
                            model.getPlateau().placerPionForce(model.getPionCourant(), c.getLocationInGridHexagone());
                            model.getPionCourant().setLocation(c.getLocationInGridHexagone());
                            repaint();
                            model.setJeuEtat(JeuEtat.CONTINUE);
                        }
                    }
                    else if(model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                        //TODO:programmez ici pour effacer tous les cercles
                        if(pionChoisi!=null){
                            model.getPionCourant().passPerleTo(pionChoisi);
                            //renouveler la vue des 2 pions
                            model.getPlateau().getGridPion().remove(pionChoisi.getLocation());
                            model.getPlateau().placerPionForce(pionChoisi,pionChoisi.getLocation());

                            model.getPlateau().getGridPion().remove(model.getPionCourant().getLocation());
                            model.getPlateau().placerPionForce(model.getPionCourant(), model.getPionCourant().getLocation());
                            repaint();

                            model.setJeuEtat(JeuEtat.CONTINUE);
                        }
                    }

                    return;
                    }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        Point position = e.getPoint();
        for (TuileGraphique t : tuilesGraphique) {
            for (Circle c : t.cercles) {
                if(c.contains(position)){
                    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    return;
                }
            }
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Plateau Tuiles");

        // On effectue nos tests :
        // On initialise le model avec 2 joueurs
        Jeu model =  new Jeu(2);
        GridTuile gridTuile = new GridTuile(model);
        // On place quelques tuiles
        gridTuile.piocheAndPlace(0, 0);
        gridTuile.piocheAndPlace(1, 0);
        gridTuile.piocheAndPlace(0, 1);
        gridTuile.piocheAndPlace(-1, 0);
        gridTuile.piocheAndPlace(0, -1);
        // On place le temple du J1
        Tuile tuile = gridTuile.model.getSacTemples().get(0);
        tuile.rotate();
        gridTuile.model.getPlateau().placeTuileBrutForce(gridTuile.model.getSacTemples().get(0), new Coordonnee(-1, -1));
        // On place le temple du J2
        //gridTuile.model.getPlateau().placeTuileBrutForce(gridTuile.model.getSacTemples().get(1), new Coordonnee(1, -1));

        frame.addKeyListener(gridTuile);
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) { 
            frame.requestFocus();	
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gridTuile);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
