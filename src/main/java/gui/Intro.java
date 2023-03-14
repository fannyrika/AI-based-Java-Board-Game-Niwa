package main.java.gui;
import java.awt.*;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import main.java.model.Joueur;
import main.java.model.Pion;

public class Intro extends JPanel{
    private Dimension size;
    private JProgressBar j;
    Intro(){
       size=Toolkit.getDefaultToolkit().getScreenSize();
       this.setPreferredSize(size);
       this.setBackground(Color.WHITE);
       JLabel titre=new JLabel("Niwa");
       titre.setFont(new Font("Calibri",Font.BOLD,100)); 
       titre.setForeground(Color.ORANGE);
       this.setLayout(new GridBagLayout());
       GridBagConstraints g=new GridBagConstraints();
       g.gridwidth=3;
       g.gridheight=2;
       g.gridx=2;
       g.gridy=2;
       this.add(titre,g);
       j=new JProgressBar(0,100);
       j.setValue(0);
       j.setStringPainted(true);
       j.setForeground(Color.orange);
       j.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
       j.setBackground(Color.WHITE);
       j.setPreferredSize(new Dimension((int)size.getWidth()/3,(int)size.getHeight()/20));
       g.gridheight=3;
       g.gridy=4;
       this.add(j,g);
       Thread thread = new Thread(new Runnable() {
        public void run() {
            int incr=0;
            while (incr < 100) {
                incr++;
                j.setValue(incr);

                try {
                    Thread.sleep(200); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    thread.start();

    
       repaint();
    }   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        int largeur=(int)size.getWidth();
        int x=largeur/3-2*largeur/30;
        int y=((int)size.getHeight())/6;
        int rayonTete=largeur/30;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        for(int j=0;j<3;j++){
            g2.setStroke(new BasicStroke(rayonTete/6));
            g2.setColor(Color.BLACK);
            g2.drawLine(x+rayonTete,y, x+rayonTete,y-2*rayonTete);
            g2.setStroke(new BasicStroke(rayonTete/10));
            for (int i = 0; i < 3; i++) {
                Color couleurPerle;
                switch ((j+i)%3) {
                    case 0:
                        couleurPerle = Color.RED;
                        break;
                    case 1:
                        couleurPerle = Color.GREEN;
                        break;
                    case 2:
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
        switch (j) {
            case 0: g2.setColor(new Color(122, 174, 230));
            break;
            case 1: g2.setColor(new Color(255, 82, 66));
            break;
            case 2: g2.setColor(new Color(247, 134, 247));
            break;
            case 3: g2.setColor(new Color(255, 138, 202));
            break;
            default: g2.setColor(Color.BLACK);
        }
        g2.fillRoundRect(x+rayonTete/12,y+2*rayonTete,2*rayonTete,4*rayonTete, rayonTete*2, rayonTete*2);
        g2.fillRect(x+rayonTete/12,y+5*rayonTete, 2*rayonTete, rayonTete);
        //g2.setColor(Color.BLACK);
        x+=largeur/6;

}
}

    public static void main(String args[]){
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 Intro a=new Intro();
                 JFrame b=new JFrame();
                b.add(a);
                b.pack();
                b.setVisible(true);
            }
        });
       
    }
}
