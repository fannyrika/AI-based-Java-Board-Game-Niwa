package main.java.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import main.java.model.MapEtat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

public class JouerButton extends JPanel implements ActionListener {

    JPanel conteneur;
    JButton btn;
    JRadioButton manuelButton, autoButton, j2, j4;
    JCheckBox p1, p2, p3, p4;
    JTextField nmbJoueur;
    JLabel background;

    int botsChecked;

    public JouerButton(NiwaWindow frame) {

        // setLocationRelativeTo(null); // Pour centrer la fenetre
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(StockageSettings.file_parametreNiwa.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

        // Conteneur pour les buttons et les JLabel
        JPanel conteneur0 = new JPanel();
        conteneur0.setLayout(new GridLayout(5, 1));
        JPanel conteneur1 = new JPanel();
        JPanel conteneur2 = new JPanel();
        JPanel conteneur3 = new JPanel();
        JPanel conteneur4 = new JPanel();
        JPanel conteneur5 = new JPanel();

        conteneur1.setBackground(new Color(245, 236, 206));
        conteneur2.setBackground(new Color(245, 236, 206));
        conteneur3.setBackground(new Color(245, 236, 206));
        conteneur4.setBackground(new Color(245, 236, 206));
        conteneur5.setBackground(new Color(245, 236, 206));

        conteneur0.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        // Permet de selectionner un seul choix(JRadioButton) � la foix
        ButtonGroup buttonGroup = new ButtonGroup();
        ButtonGroup buttonGroup2 = new ButtonGroup();

        // Choisir le nombre de joueurs 2 ou 4
        JLabel label0 = new JLabel("  N O M B R E   J O U E U R S    : ", JLabel.CENTER);
        label0.setFont(new Font("Congenial Black", Font.BOLD, 15));
        j2 = new JRadioButton("2");
        j4 = new JRadioButton("4");
        j2.setBackground(new Color(245, 236, 206));
        j4.setBackground(new Color(245, 236, 206));

        JLabel labelBot = new JLabel(" N O M B R E   D E   B O T S    : ", JLabel.CENTER);
        labelBot.setFont(new Font("Congenial Black", Font.BOLD, 15));
        p1 = new JCheckBox("J1");
        p2 = new JCheckBox("J2");
        p3 = new JCheckBox("J3");
        p4 = new JCheckBox("J4");

        JCheckBox[] botsChoices = { p1, p2, p3, p4 };

        conteneur3.add(labelBot);
        for (JCheckBox btn : botsChoices) {
            conteneur3.add(btn);
        }

        labelBot.setVisible(false);
        for (JCheckBox btn : botsChoices) {
            btn.setVisible(false);
        }

        j2.addActionListener(e -> {
            labelBot.setVisible(true);
            p1.setVisible(true);
            p2.setVisible(true);
            p3.setVisible(false);
            p4.setVisible(false);

            for (JCheckBox btn : botsChoices) {
                btn.setSelected(false);
            }
            botsChecked = 0;

            manuelButton.setEnabled(true);
            repaint();
        });

        j4.addActionListener(e -> {
            labelBot.setVisible(true);
            for (JCheckBox btn : botsChoices) {
                btn.setVisible(true);
                btn.setSelected(false);
            }
            botsChecked = 0;

            manuelButton.setEnabled(true);
            repaint();
        });

        p1.setEnabled(false);

        buttonGroup.add(j2);
        buttonGroup.add(j4);
        conteneur1.add(label0);
        conteneur1.add(j2);
        conteneur1.add(j4);

        // Choisir le mode de cr�ation du plateau AUTO ou MANUEL
        JLabel label1 = new JLabel("  C R E A T I O N   D U   P L A T E A U  : ", JLabel.CENTER);
        label1.setFont(new Font("Congenial Black", Font.BOLD, 15));
        manuelButton = new JRadioButton(" M A N U E L ");
        autoButton = new JRadioButton(" A U T O M A T I Q U E ");

        manuelButton.setBackground(new Color(245, 236, 206));
        manuelButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        autoButton.setBackground(new Color(245, 236, 206));
        autoButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

        buttonGroup2.add(manuelButton);
        buttonGroup2.add(autoButton);
        conteneur2.add(label1);
        conteneur2.add(manuelButton);
        conteneur2.add(autoButton);

        for (JCheckBox JCB : botsChoices) {
            JCB.addActionListener(e -> {
                if (JCB.isSelected()) {
                    botsChecked += 1;
                    manuelButton.setSelected(false);
                    manuelButton.setEnabled(false);
                    autoButton.setSelected(true);
                } else if (!JCB.isSelected()) {
                    botsChecked -= 1;
                    if (botsChecked == 0) {
                        manuelButton.setEnabled(true);
                    }
                }
                repaint();
            });
        }

        // Bouton permettant le passage � la fenetre d'apres apres verification de la
        // validite des choix du joueur
        JButton suivant = new JButton("S U I V A N T ");
        suivant.setFont(new Font("Congenial Black", Font.BOLD, 15));
        suivant.setPreferredSize(new Dimension(120, 50));
        suivant.setFocusable(false);
        suivant.setForeground(Color.ORANGE);
        suivant.setBackground(new Color(245, 236, 206));
        suivant.setBorder(new LineBorder(Color.CYAN));
        suivant.addActionListener(e -> {
            frame.beep();
            // Message d'erreur s'affichant si aucun choix pour le nombre de joueur ou le
            // mode du plateau n'est selctionn�
            if (!(j2.isSelected()) && !(j4.isSelected())
                    || !(manuelButton.isSelected()) && !(autoButton.isSelected())) {
                JOptionPane.showMessageDialog(this, "Veuillez Completer Vos Choix : ");

                // Sinon si le joueur a bien fait ses choix on affiche la page suivante
                // A laquelle on envoie dans son constructeur le nombre de joueur ainsi que le
                // mode du plateau defini avec la chaine de caractere r
            }

            else {
                StockageSettings.NB_JOUEURS_TOTAL = j2.isSelected() ? 2 : 4;
                StockageSettings.NB_IA = 0;
                if (p1.isSelected()) {
                    StockageSettings.NB_IA += 1;
                }
                if (p2.isSelected()) {
                    StockageSettings.NB_IA += 1;
                }
                if (p3.isSelected()) {
                    StockageSettings.NB_IA += 1;
                }
                if (p4.isSelected()) {
                    StockageSettings.NB_IA += 1;
                }
                StockageSettings.NB_HUMAIN = StockageSettings.NB_JOUEURS_TOTAL - StockageSettings.NB_IA;
                StockageSettings.MAP_ETAT = manuelButton.isSelected() ? MapEtat.MANUEL : null;
                frame.setPanel(new ValiderButton(frame));
            }
        });
        conteneur4.add(suivant);

        JButton retour = new JButton("R E T O U R ");
        retour.setFont(new Font("Congenial Black", Font.BOLD, 15));
        retour.setPreferredSize(new Dimension(150, 50));
        retour.setFocusable(false);
        retour.setForeground(Color.ORANGE);
        retour.setBackground(new Color(245, 236, 206));
        retour.setBorder(new LineBorder(Color.CYAN));
        retour.addActionListener(e -> {
            frame.beep();
            frame.setPanel(new JouerFrame(frame));
        });
        conteneur5.add(retour);

        // On ajoute tous les conteneur � la fenetre
        conteneur0.add(conteneur1);
        conteneur0.add(conteneur2);
        conteneur0.add(conteneur3);
        conteneur0.add(conteneur4);
        conteneur0.add(conteneur5);
        background.add(conteneur0);
        add(background);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
