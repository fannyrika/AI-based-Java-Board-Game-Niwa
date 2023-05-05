package main.java.gui;

import java.awt.*;

import javax.swing.*;
import main.java.model.Jeu;

public class TableauDeBord extends JPanel{
    protected Jeu model;
    protected JLabel pioche;
    protected JLabel joueurCourant;
    protected JLabel etapeCourante;
    protected PionGraphique pionSelectione;
    protected JButton boutonMenu;
    protected JButton boutonRegles;
    protected JButton boutonRotationHoraire;
    protected JButton boutonRotationAntiHoraire;
    protected JButton boutonZoom;
    protected JButton boutonDezoom;
    TableauDeBord(Jeu model){
        setLayout(new GridLayout(2,1));
        this.setBackground(new Color(61, 58, 58));
        this.model=model;
        this.etapeCourante=new JLabel("");
        etapeCourante.setHorizontalAlignment(JLabel.CENTER);
        etapeCourante.setForeground(Color.WHITE);
        this.joueurCourant=new JLabel();
        joueurCourant.setForeground(Color.WHITE);
        joueurCourant.setHorizontalAlignment(JLabel.CENTER);
        pioche=new JLabel();
        pioche.setHorizontalAlignment(JLabel.CENTER);
        pioche.setForeground(Color.WHITE);
        JPanel boutonPanel=new JPanel();
        boutonPanel.setBackground(Color.WHITE);
        this.setupBoutons();
        boutonPanel.setLayout(new GridLayout(3,1));
        JPanel gestionPartiePanel=new JPanel();
        gestionPartiePanel.setBackground(new Color(61, 58, 58));
        gestionPartiePanel.setLayout(new FlowLayout());
        gestionPartiePanel.add(boutonMenu);
        gestionPartiePanel.add(boutonRegles);

        JPanel gestionTuilePanel=new JPanel();
        gestionTuilePanel.setBackground(new Color(61, 58, 58));
        gestionTuilePanel.setLayout(new FlowLayout());
        gestionTuilePanel.add(boutonRotationHoraire);
        gestionTuilePanel.add(boutonRotationAntiHoraire);
        
        JPanel gestionPlateauPanel=new JPanel();
        gestionPlateauPanel.setBackground(new Color(61, 58, 58));
        gestionPlateauPanel.setLayout(new FlowLayout());
        gestionPlateauPanel.add(boutonDezoom);
        gestionPlateauPanel.add(boutonZoom);
        
        boutonPanel.add(gestionPlateauPanel);
        boutonPanel.add(gestionTuilePanel);
        boutonPanel.add(gestionPartiePanel);
        

        JPanel instructionPanel=new JPanel();
        instructionPanel.setLayout(new GridLayout(3,1));
        instructionPanel.setBackground(new Color(61, 58, 58));
        etapeCourante.setLayout(new BorderLayout());
        etapeCourante.setFont(new Font("SansSerif", Font.BOLD, 13));
        joueurCourant.setFont(new Font("SansSerif", Font.BOLD, 13));

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
        boutonRegles.setFocusable(false);

        boutonMenu=new JButton("Menu");
        boutonMenu.setFont(new Font("Arial", Font.BOLD, 16)); 
        boutonMenu.setForeground(Color.WHITE); 
        boutonMenu.setBackground(Color.BLACK);
        boutonMenu.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonMenu.setPreferredSize(new Dimension(120, 30));
        boutonMenu.setFocusable(false);

        boutonRotationHoraire=new JButton("⟳");
        boutonRotationHoraire.setFont(new Font("Calibri", Font.BOLD, 20)); 
        boutonRotationHoraire.setForeground(Color.WHITE);
        boutonRotationHoraire.setBackground(Color.BLACK); 
        boutonRotationHoraire.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonRotationHoraire.setPreferredSize(new Dimension(60, 30));
        boutonRotationHoraire.setFocusable(false);

        boutonRotationAntiHoraire=new JButton("⟲");
        boutonRotationAntiHoraire.setFont(new Font("Calibri", Font.BOLD, 20)); 
        boutonRotationAntiHoraire.setForeground(Color.WHITE);
        boutonRotationAntiHoraire.setBackground(Color.BLACK); 
        boutonRotationAntiHoraire.setBorder(BorderFactory.createLoweredSoftBevelBorder()); 
        boutonRotationAntiHoraire.setPreferredSize(new Dimension(60, 30));
        boutonRotationAntiHoraire.setFocusable(false);

        boutonZoom=new JButton("+");
        boutonZoom.setFont(new Font("Calibri", Font.BOLD, 20)); 
        boutonZoom.setForeground(Color.WHITE);
        boutonZoom.setBackground(Color.BLACK); 
        boutonZoom.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonZoom.setPreferredSize(new Dimension(60, 30));
        boutonZoom.setFocusable(false);

        boutonDezoom=new JButton("-");
        boutonDezoom.setFont(new Font("Calibri", Font.BOLD, 20)); 
        boutonDezoom.setForeground(Color.WHITE);
        boutonDezoom.setBackground(Color.BLACK); 
        boutonDezoom.setBorder(BorderFactory.createRaisedBevelBorder()); 
        boutonDezoom.setPreferredSize(new Dimension(60, 30));
        boutonDezoom.setFocusable(false);

    }
    protected void setEtapeCourante(String instruction){
        etapeCourante.setText(instruction);
    }
    
protected void setJoueurCourant(String nom){
        joueurCourant.setText(nom);
    }

}
