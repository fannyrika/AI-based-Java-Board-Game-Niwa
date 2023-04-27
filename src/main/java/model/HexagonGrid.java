package main.java.model;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class HexagonGrid extends JPanel {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // Je l'ai juste mis là pour le test du main, mais peut être virer
    private int rows; // Nbr de lignes
    private int columns; // Nbr de colonnes
    private int radius; //Taille de l'hexagone mais à voir si ya moyen de changer ça
    HashMap<Hexagone, Point> grid = new HashMap<Hexagone, Point>();
    // C'est un JPanel qui contient les dessins d'hexagones
    // On verifieras que le dessin de ruche ne depasse pas la taille du JPanel concerné (mas je l'ai pas encore fait)
    public HexagonGrid(int rows, int columns, int radius){
        this.rows = rows;
        this.columns = columns;
        this.radius = radius;
    }

    @Override
    // Remplis le contenu de la HashMap mais aussi du JPanel alias HexagonalGrid afin d'avoir une "ruche" un grid de 'rows' lignes et 'columns' colonnes
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(int x = 0; x < columns; x++){
            for(int y = 0; y < rows; y++){
                // tmp sert à permettre le décalage entre lignes paires et impaires
                int tmp = (y % 2 == 0)? radius - 10 : 0;
                // z sert à déterminer la position de l'hexagone dans l'axe des x
                int z = x * (radius / 4) * 7 + tmp + 90;
                // w sert à déterminer la position de l'hexagone dans l'axe des y
                int w = (int) (y * (radius / 4) * 6.05) + 90;
                // dans les calculs du z et w, les deux '+90' serve à centrer les hexagones pour que dès le premier, on puisse en voir l'entiereté
                // et pas un quart de la forme MAIS A XCHANGER SELON LA TAILLE DE L'HEXAGONE
                // On fait appelle à la fonction drawHexagone() qui fixera l'affiche de l'hexagone et créera l'objet Hexagone
                
                int tmpY = (y % 2 == 0)? y + 1 : y - 1;
                Hexagone a = Hexagone.drawHexagone(g2d, x, tmpY, z, w);
                // On ajoute l'hexagone et les coordonnées de l'hexagone dans grid
                grid.put(a, new Point(x, tmpY));
                //// Changer les coordonnées afin d'avoir les lignes impairs au dessus (avec l'alinéa) et les paires en dessous (sans l'alinéa)
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel panel = new JPanel();
        HexagonGrid p = new HexagonGrid(10, 10, 70);
        f.setSize(p.screen);
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.CENTER);
        f.getContentPane().add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}