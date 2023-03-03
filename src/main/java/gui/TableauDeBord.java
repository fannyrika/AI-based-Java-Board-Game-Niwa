package main.java.gui;

import java.awt.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
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
        this.setBackground(Color.LIGHT_GRAY);
        this.etapeCourante=new JLabel("");
        this.joueurCourant=new JLabel();
        pioche=new JLabel();
        etapeCourante.setLayout(new BorderLayout());
        JPanel boutonPanel=new JPanel();
        this.setupBoutons();
        boutonPanel.setLayout(new FlowLayout());
        boutonPanel.add(boutonQuitter);
        boutonPanel.add(boutonRegles);
        JPanel instructionPanel=new JPanel();
        instructionPanel.setLayout(new GridLayout(5,1));
        instructionPanel.add(joueurCourant);
        instructionPanel.add(etapeCourante);
        instructionPanel.add(pioche);
        this.add(instructionPanel);
        this.add(boutonPanel);

    }
    private void setupBoutons(){
        boutonRegles=new JButton("Règles du jeu");
        boutonRegles.addActionListener(e->{
            ManuelDuJeu mdj=new ManuelDuJeu();
            mdj.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });
        boutonQuitter=new JButton("Quitter");
    }
    protected void setEtapeCourante(String instruction){
        etapeCourante.setText(instruction);
    }
    protected void setJoueurCourant(String nom){
        joueurCourant.setText(nom);
    }

}
