package main.java.gui;

import javax.swing.*;
import java.awt.*;

import main.java.model.Coordonnee;
import main.java.model.Jeu;
import main.java.model.Tuile;

public class GridTuile extends JPanel {

    protected Jeu jeu;
    protected static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public GridTuile(){
        this.setPreferredSize(screen);
        jeu =  new Jeu(2);
        piocheAndPlace(0, 0);
        piocheAndPlace(1, 0);
        piocheAndPlace(0, 1);
        piocheAndPlace(-1, 0);
        piocheAndPlace(0, -1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D mainGraphics = (Graphics2D)g;

        for(Tuile t : jeu.getPlateau().getGridTuile().values()){
            TuileGraphique tuileG = new TuileGraphique(t, t.getLocationInGridTuile().getX(),t.getLocationInGridTuile().getY());
            tuileG.drawTile(mainGraphics);
        }
    }

    private void piocheAndPlace(int x, int y){
        jeu.getPlateau().placeTuileBrutForce(jeu.piocher(), new Coordonnee(x, y));
    }




    public static void main(String[] args) {

        JFrame frame = new JFrame("Draw Tiles");
        GridTuile gridTuile = new GridTuile();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gridTuile);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
