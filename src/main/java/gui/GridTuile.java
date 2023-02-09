package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;

import main.java.model.Coordonnee;
import main.java.model.Jeu;
import main.java.model.Tuile;
import main.java.model.TuileTemple;

public class GridTuile extends JPanel implements KeyListener {

    /**
     * Le jeu sur lequel le graphique se base
     */
    protected Jeu jeu;
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

    /**
     * Constructeur
     */
    public GridTuile(){
        this.setPreferredSize(screen);
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

        // On parcours toutes les tuiles sur le plateau, pour ainsi les dessiner
        for(Tuile t : jeu.getPlateau().getGridTuile().values()){
            int x = t.getLocationInGridTuile().getX();
            int y = t.getLocationInGridTuile().getY();
            if(t instanceof TuileTemple){
                TuileTempleGraphique tuileG = new TuileTempleGraphique((TuileTemple) t, x,y,((TuileTemple)t).getProprietaire());
                tuileG.drawTile(mainGraphics);
            }
            else{
                TuileGraphique tuileG = new TuileGraphique(t, x,y);
                tuileG.drawTile(mainGraphics);
            }
        }
    }

    /**
     * Méthode permettant de piocher une tuile dans le jeu et de la placer
     * @param x -> Coordonnee x
     * @param y -> Coordonnee y
     */
    public void piocheAndPlace(int x, int y){
        jeu.getPlateau().placeTuileBrutForce(jeu.piocher(), new Coordonnee(x, y));
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
        switch (e.getKeyChar()) {
            case 'q':   // gauche
                glisserVers(-DISTANCE_DEPLACEMENT, 0);
                break;
            case 'd':   // droite
                glisserVers(DISTANCE_DEPLACEMENT, 0);
                break;
            case 's':   // bas
                glisserVers(0, -DISTANCE_DEPLACEMENT);
                break;
            case 'z':   // haut
                glisserVers(0, DISTANCE_DEPLACEMENT);
                break;
            case 'u':   // zoom
                TuileGraphique.zoom(DISTANCE_ZOOM);
                break;
            case 'j':   // dezoom
                TuileGraphique.zoom(-DISTANCE_ZOOM);
            default:
                break;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {

        JFrame frame = new JFrame("Draw Tiles");
        GridTuile gridTuile = new GridTuile();

        // On effectue nos tests :
        // On initialise le jeu avec 2 joueurs
        gridTuile.jeu =  new Jeu(2);
        // On place quelques tuiles
        gridTuile.piocheAndPlace(0, 0);
        gridTuile.piocheAndPlace(1, 0);
        gridTuile.piocheAndPlace(0, 1);
        gridTuile.piocheAndPlace(-1, 0);
        gridTuile.piocheAndPlace(0, -1);
        // On place le temple du J1
        gridTuile.jeu.getPlateau().placeTuileBrutForce(gridTuile.jeu.getSacTemples().get(0), new Coordonnee(-1, -1));
        // On place le temple du J2
        gridTuile.jeu.getPlateau().placeTuileBrutForce(gridTuile.jeu.getSacTemples().get(1), new Coordonnee(2, 1));

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
