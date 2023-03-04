package main.java.gui;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.WindowConstants;
import main.java.model.Jeu;

public class TableauDeBord extends JPanel {
    protected Jeu model;
    protected JLabel pioche;
    protected JLabel joueurCourant;
    protected JLabel etapeCourante;
    protected PionGraphique pionSelectione;
    protected JButton boutonQuitter;
    protected JButton boutonRegles;
    TableauDeBord(Jeu model){
        setLayout(new GridLayout(2,1));
        this.model=model;
        this.setBackground(new Color(255, 137, 83, 1));
        this.etapeCourante=new JLabel("");
        this.joueurCourant=new JLabel();
        pioche=new JLabel();
        JPanel boutonPanel=new JPanel();
        boutonPanel.setBackground(Color.WHITE);
        this.setupBoutons();
        boutonPanel.setLayout(new FlowLayout());

        boutonPanel.add(boutonQuitter);
        boutonPanel.add(boutonRegles);
        JPanel instructionPanel=new JPanel();
        instructionPanel.setLayout(new GridLayout(5,1));
        instructionPanel.setBackground(Color.WHITE);
        instructionPanel.add(joueurCourant);
        joueurCourant.setFont(new Font("Arial", Font.PLAIN, 13));
        etapeCourante.setLayout(new BorderLayout());
        etapeCourante.setFont(new Font("Arial", Font.PLAIN, 13));
        instructionPanel.add(etapeCourante);
        instructionPanel.add(pioche);
        this.add(instructionPanel);
        this.add(boutonPanel);

    }
    private void setupBoutons(){
        boutonRegles=new JButton("Règles du jeu");
        boutonRegles.addActionListener(e->{
            ManuelDuJeu mdj=new ManuelDuJeu();
            /*idéalement desactiver le bouton quand un manuel
             * est déjà ouvert et le réactiver quand il est fermé 
             * mais bon là ça ne marche pas
             */
            /*boutonRegles.setEnabled(false);
            mdj.addWindowStateListener(new WindowStateListener() {
                public void windowStateChanged(WindowEvent e) {
                    if (e.getNewState() == WindowEvent.WINDOW_CLOSING) {
                        boutonRegles.setEnabled(true);
                    }
                }
            });
            mdj.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            */
        
        });
        boutonRegles.setFont(new Font("Arial", Font.BOLD, 16)); 
        boutonRegles.setForeground(Color.WHITE);
        boutonRegles.setBackground(Color.BLACK); 
        boutonRegles.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonRegles.setPreferredSize(new Dimension(120, 30));
        boutonQuitter=new JButton("Quitter");
        boutonQuitter.setFont(new Font("Arial", Font.BOLD, 16)); 
        boutonQuitter.setForeground(Color.WHITE); 
        boutonQuitter.setBackground(Color.BLACK);
        boutonQuitter.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonQuitter.setPreferredSize(new Dimension(120, 30));
    }
    protected void setEtapeCourante(String instruction){
        etapeCourante.setText(instruction);
    }
    
protected void setJoueurCourant(String nom){
        joueurCourant.setText(nom);
    }

}
