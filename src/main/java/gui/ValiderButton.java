package main.java.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import main.java.model.Jeu;
import main.java.model.MapEtat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

public class ValiderButton extends JPanel implements ActionListener {

    public Thread t;
    public static final String threadName = "Thread_VB";

    JPanel conteneur;
    JButton btn;
    JRadioButton manuelButton, autoButton;
    JTextField nmbJoueur;
    JLabel background;

    public ValiderButton(NiwaWindow frame) {

        // setLocationRelativeTo(null); //Pour centrer la fenetre
        setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon(StockageSettings.file_parametreNiwa.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

        // Conteneur pour les buttons et les JLabel
        JPanel conteneur0 = new JPanel();
        conteneur0.setLayout(new GridLayout(4, 1));
        JPanel conteneur1 = new JPanel();
        JPanel conteneur2 = new JPanel();
        JPanel conteneur3 = new JPanel();
        JPanel conteneur4 = new JPanel();

        conteneur1.setBackground(new Color(245, 236, 206));
        conteneur2.setBackground(new Color(245, 236, 206));
        conteneur3.setBackground(new Color(245, 236, 206));
        conteneur4.setBackground(new Color(245, 236, 206));

        conteneur0.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        // Choix nmb de Tuiles
        JLabel label1 = new JLabel("       N O M B R E   T U I L E S :      ", JLabel.CENTER);
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        JTextField nmbTuile = new JTextField();
        nmbTuile.setPreferredSize(new Dimension(30, 30));

        // Choix de la map
        JLabel label2 = new JLabel("       C H O I X   M A P :       ", JLabel.CENTER);
        label2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        ButtonGroup choix_map = new ButtonGroup();
        JRadioButton map1 = new JRadioButton("map 1");
        JRadioButton map2 = new JRadioButton("map 2");
        map1.setBackground(new Color(245, 236, 206));
        map1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        map2.setBackground(new Color(245, 236, 206));
        map2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

        choix_map.add(map1);
        choix_map.add(map2);

        if (StockageSettings.NB_JOUEURS_TOTAL == 2 && StockageSettings.NB_IA >= 1) {
            map2.setEnabled(false);
            map1.setSelected(true);
        }

        JButton suivant = new JButton("V A L I D E R  ");
        suivant.setFont(new Font("Congenial Black", Font.BOLD, 12));
        suivant.setPreferredSize(new Dimension(100, 40));
        suivant.setFocusable(false);
        suivant.setForeground(Color.ORANGE);
        suivant.setBackground(new Color(245, 236, 206));
        suivant.setBorder(new LineBorder(Color.CYAN));

        // A ce moment la on ajoute � la fenetre la possiblite de choisir le nmb de
        // tuile
        if (StockageSettings.MAP_ETAT == MapEtat.MANUEL) {
            conteneur2.add(label1);
            conteneur2.add(nmbTuile);

            suivant.addActionListener(e -> {
                // Message d'erreur s'affichant si le nmb de tuile choisi n'est pas valide
                if (Integer.valueOf(nmbTuile.getText()) < 10 || Integer.valueOf(nmbTuile.getText()) > 24) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez chosir un nombre de Tuiles compris entre 10 et 24 : ");
                } else {
                    StockageSettings.NB_TUILES = Integer.valueOf(nmbTuile.getText());
                    frame.dispose();
                    lancerPartie();
                }
            });
        } else {
            conteneur2.add(label2);
            conteneur2.add(map1);
            conteneur2.add(map2);

            suivant.addActionListener(e -> {
                frame.beep();
                // Message d'erreur s'affichant si le nmb de tuile choisi n'est pas valide
                if (!map1.isSelected() && !map2.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Veuillez chosir une map : ");
                } else {
                    if (StockageSettings.NB_JOUEURS_TOTAL == 2) {
                        StockageSettings.MAP_ETAT = map1.isSelected() ? MapEtat.MAP1_2P : MapEtat.MAP2_2P;
                    } else {
                        StockageSettings.MAP_ETAT = map2.isSelected() ? MapEtat.MAP1_4P : MapEtat.MAP2_4P;
                    }
                    frame.dispose();
                    lancerPartie();
                }
            });
        }

        conteneur3.add(suivant);

        JButton retour = new JButton("R E T O U R ");
        retour.setFont(new Font("Congenial Black", Font.BOLD, 12));
        retour.setPreferredSize(new Dimension(120, 40));
        retour.setFocusable(false);
        retour.setForeground(Color.ORANGE);
        retour.setBackground(new Color(245, 236, 206));
        retour.setBorder(new LineBorder(Color.CYAN));
        retour.addActionListener(e -> {
            frame.beep();
            frame.setPanel(new JouerButton(frame));

            setVisible(false);
        });
        conteneur4.add(retour);

        // On ajoute tous les conteneur � la fenetre
        conteneur0.add(conteneur1);
        conteneur0.add(conteneur2);
        conteneur0.add(conteneur3);
        conteneur0.add(conteneur4);
        background.add(conteneur0);
        add(background);

    }

    public void actionPerformed(ActionEvent e) {

    }

    private static void lancerPartie() {
        Jeu model = new Jeu(StockageSettings.NB_HUMAIN, StockageSettings.NB_IA, StockageSettings.MAP_ETAT,
                StockageSettings.NB_TUILES);
        InterfaceDeJeu interfaceDeJeu = new InterfaceDeJeu(model);
        interfaceDeJeu.start();
    }
}
