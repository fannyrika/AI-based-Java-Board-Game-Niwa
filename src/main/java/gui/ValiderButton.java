package main.java.gui;

import javax.swing.*;

import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.TuileTemple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ValiderButton extends JFrame implements ActionListener {

    JPanel conteneur;
    JButton btn;
    JRadioButton manuelButton, autoButton;
    JTextField nmbJoueur, nomJ1, nomJ2, nomJ3, nomJ4;
    JLabel background;
    protected ArrayList<TuileTemple> sacTemples = new ArrayList<TuileTemple>();

    ValiderButton(File imageFile, int nmbJoueurs, String r) {

    	//Fonctions permettant l'affichage correcte de la fenetre 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Parametre NIWA");
        pack();
        setLocationRelativeTo(null); //Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        JLabel background = new JLabel(new ImageIcon(imageFile.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

        //Conteneur pour les buttons et les JLabel
        JPanel conteneur0 = new JPanel();
        conteneur0.setLayout(new GridLayout(4, 1));
        JPanel conteneur1 = new JPanel();
        JPanel conteneur2 = new JPanel();
        JPanel conteneur3 = new JPanel();
        JPanel conteneur4 = new JPanel();
      
        //Choix des noms des joueurs
        // 4 JTextField sont crees car c'est le nmb maximal de joueurs
        JLabel label0 = new JLabel("  N O M S   J O E U R S    : ", JLabel.CENTER);
        nomJ1 = new JTextField();
        nomJ2 = new JTextField();
        nomJ3 = new JTextField();
        nomJ4 = new JTextField();

        //Taille des cases 
        nomJ1.setPreferredSize(new Dimension(100, 50));
        nomJ2.setPreferredSize(new Dimension(100, 50));
        nomJ3.setPreferredSize(new Dimension(100, 50));
        nomJ4.setPreferredSize(new Dimension(100, 50));

        conteneur1.add(label0);
        //On ajoute au debut que 2 cases car c'est le nmb de joueurs minimal
        //On verifie ensuite si le nmb de joueur=4 à ce moment la on ajoute 2 autres cases
        conteneur1.add(nomJ1);
        conteneur1.add(nomJ2);
        if (nmbJoueurs == 4) {
            conteneur1.add(nomJ3);
            conteneur1.add(nomJ4);
        }

        //Choix nmb de Tuiles
        JLabel label1 = new JLabel("  N O M B R E   T U I L E S : ", JLabel.CENTER);
        JTextField nmbTuile = new JTextField();
        nmbTuile.setPreferredSize(new Dimension(30, 30));
       
      //On verifie avec l'argument du constructeur chaine de caractere r si le choix est manuel
        //A ce moment la on ajoute à la fenetre la possiblite de choisir le nmb de tuile
        if (r.equals("manuel")) {
            conteneur2.add(label1);
            conteneur2.add(nmbTuile);
        }

        JButton suivant = new JButton("V A L I D E R  ");
        suivant.addActionListener(e -> {
              //Message d'erreur s'affichant si le nmb de tuile choisi n'est pas valide
            if (Integer.valueOf(nmbTuile.getText()) < 10 || Integer.valueOf(nmbTuile.getText()) > 24) {
                JOptionPane.showMessageDialog(this, "Veuillez chosir un nombre de Tuiles compris entre 10 et 24 : ");
             
            } else {
            	//On ajoute les noms des joueurs rentrées 
                if (!nomJ1.getText().equals("")) {

                    Joueur j = new Joueur(nomJ1.getText());
                    joueurs.add(j);
                    sacTemples.add(j.getTemple()); // On ajoute chaque temple dans la liste des temples
                }

                if (!nomJ2.getText().equals("")) {
                    Joueur j = new Joueur(nomJ2.getText());
                    joueurs.add(j);
                    sacTemples.add(j.getTemple()); // On ajoute chaque temple dans la liste des temples
                }

                if (!nomJ3.getText().equals("")) {
                    Joueur j = new Joueur(nomJ2.getText());
                    joueurs.add(j);
                    sacTemples.add(j.getTemple()); // On ajoute chaque temple dans la liste des temples
                }

                if (!nomJ4.getText().equals("")) {
                    Joueur j = new Joueur(nomJ2.getText());
                    joueurs.add(j);
                    sacTemples.add(j.getTemple()); // On ajoute chaque temple dans la liste des temples
                }

                if (joueurs.size() != 0) {
                	 
                    
                        
                        this.dispose();
                        Jeu model = new Jeu(joueurs, sacTemples);
                        try {
                            InterfaceDeJeu interfaceDeJeu = new InterfaceDeJeu(model);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    
                	
                	
                	
                	
                	
                	
                  

                }
            }

        });
        conteneur3.add(suivant);

        //On ajoute tous les conteneur à la fenetre
        conteneur0.add(conteneur1);
        conteneur0.add(conteneur2);
        conteneur0.add(conteneur3);
        background.add(conteneur0);
        add(background);

       
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        File imageFile = new File("C:\\Users\\33668\\Desktop\\2022-ed2-g2--niwa\\src\\parametreNiwa.png");
        new JouerButton(imageFile);
    }
}
