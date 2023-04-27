package main.java.model;
import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
public class Tuile extends JPanel{
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // Je l'ai juste mis là pour le test du main, mais peut être virer
    private HashMap<Hexagone, Point> tuile = new HashMap<Hexagone, Point>(); // Afin de placer les 7 hexagones composant la tuile
    private int radius;
    private Graphics2D g2d;

    /*
     * Une tuile sera sous la forme suivante :
     * 
     *                    0               0
     *               5         1     5         1
     *                    A               B
     *               4         2     4         2
     *                    3               3
     * 
     *            0               0               0
     *       5         1     5         1     5         1
     *            C               D               E
     *       4         2     4         2     4         2
     *            3               3               3
     * 
     *                    0               0
     *               5         1     5         1
     *                    F               G
     *               4         2     4         2
     *                    3               3
     * 
     */

    public Tuile(){
        this.radius = 70;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                    int tmp = (y % 2 == 0)? radius - 10 : 0;
                    int z = x * (radius / 4) * 7 + tmp + 90;
                    int w = (int) (y * (radius / 4) * 6.05) + 90;

                    int tmpY = (y % 2 == 0)? y + 1 : y - 1;
                    if((x != 2 || y != 0) && (x != 2 || y != 2)){ 
                        Hexagone hex = Hexagone.drawHexagone(g2d, x, tmpY, z, w);
                        tuile.put(hex, new Point(x, tmpY));
                    }
            }
        }
        this.g2d = g2d; // Je suis pas sûre pour ça
    }
    
    public static void main(String[] args){
        JFrame f = new JFrame();
        Tuile p = new Tuile();
        f.setSize(p.screen);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.CENTER);
        f.getContentPane().add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
