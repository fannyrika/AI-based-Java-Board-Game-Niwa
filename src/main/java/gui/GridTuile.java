package main.java.gui;

import javax.swing.*;
import java.awt.*;

import main.java.model.Coordonnee;
import main.java.model.Jeu;
import main.java.model.Tuile;
import main.java.model.TuileTemple;

public class GridTuile extends JPanel {

    protected Jeu jeu;
    protected static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Constructeur
     */
    public GridTuile(){
        this.setPreferredSize(screen);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D mainGraphics = (Graphics2D)g;

        // On parcours toutes les tuiles sur le plateau, pour ainsi les dessiner
        for(Tuile t : jeu.getPlateau().getGridTuile().values()){
            if(t instanceof TuileTemple){
                TuileTempleGraphique tuileG = new TuileTempleGraphique((TuileTemple) t, t.getLocationInGridTuile().getX(),t.getLocationInGridTuile().getY(),((TuileTemple)t).getProprietaire());
                tuileG.drawTile(mainGraphics);
            }
            else{
                TuileGraphique tuileG = new TuileGraphique(t, t.getLocationInGridTuile().getX(),t.getLocationInGridTuile().getY());
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gridTuile);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
