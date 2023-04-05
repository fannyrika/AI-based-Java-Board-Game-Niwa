package main.java.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

public class JouerButton extends JFrame implements ActionListener {

    JPanel conteneur;
    JButton btn;
    JRadioButton manuelButton, autoButton, j2, j4;
    JTextField nmbJoueur;
    JLabel background;
    static File imageFile = new File("C:\\Users\\33668\\Desktop\\Projet\\2022-ed2-g2--niwa\\src\\parametreNiwa.png");

    JouerButton(File imageFile) {
    	
        //Fonctions permettant l'affichage correcte de la fenetre 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("MENU NIWA JOUERFRAME");
        pack();
        setLocationRelativeTo(null); // Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(imageFile.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

        
        //Conteneur pour les buttons et les JLabel
        JPanel conteneur0 = new JPanel();
        conteneur0.setLayout(new GridLayout(4, 1));
        JPanel conteneur1 = new JPanel();
        JPanel conteneur2 = new JPanel();
        JPanel conteneur3 = new JPanel();
        JPanel conteneur4 = new JPanel();   
        
        //Permet de selectionner un seul choix(JRadioButton) à la foix
        ButtonGroup buttonGroup = new ButtonGroup();
        ButtonGroup buttonGroup2 = new ButtonGroup();
       
        //Choisir le nombre de joueurs 2 ou 4
        JLabel label0 = new JLabel("  N O M B R E   J O U E U R S    : ", JLabel.CENTER);
        label0.setFont(new Font("Congenial Black", Font.BOLD, 15));
        j2 = new JRadioButton("2");
        j4 = new JRadioButton("4");
        buttonGroup.add(j2);
        buttonGroup.add(j4);
        conteneur1.add(label0);
        conteneur1.add(j2);
        conteneur1.add(j4);
        
        //Choisir le mode de création du plateau AUTO ou MANUEL
        JLabel label1 = new JLabel("  C R E A T I O N   D U   P L A T E A U  : ", JLabel.CENTER);
        label1.setFont(new Font("Congenial Black", Font.BOLD, 15));
        manuelButton = new JRadioButton(" M A N U E L ");
        autoButton = new JRadioButton(" A U T O M A T I Q U E ");
        buttonGroup2.add(manuelButton);
        buttonGroup2.add(autoButton);
        conteneur2.add(label1);
        conteneur2.add(manuelButton);
        conteneur2.add(autoButton);

        
        //Bouton permettant le passage à la fenetre d'apres apres verification de la validite des choix du joueur
        JButton suivant = new JButton("S U I V A N T ");
        suivant.setFont(new Font("Congenial Black", Font.BOLD, 15));
        suivant.setPreferredSize(new Dimension(120, 50));
        suivant.setFocusable(false);
        suivant.setForeground(Color.ORANGE);
        suivant.setBackground(Color.WHITE);
        suivant.setBorder(new LineBorder(Color.CYAN));
        suivant.addActionListener(e -> {
        	// Creation d'une chaine de caractere contenant le choix du joueur pour le mode du plateau
        	//La Chaine de caractere sera envoyer dans le constructeur de la page suivante (Class ValiderButton)
            String r = "";
            if (manuelButton.isSelected()) {
                r = "manuel";
            } else {
                r = "auto";
            }

            //Message d'erreur s'affichant si aucun choix pour le nombre de joueur ou le mode du plateau n'est selctionné
            if (!(j2.isSelected()) && !(j4.isSelected()) || !(manuelButton.isSelected()) && !(autoButton.isSelected())) {
                JOptionPane.showMessageDialog(this, "Veuillez Completer Vos Choix : ");
  
            //Sinon si le joueur a bien fait ses choix on  affiche la page suivante
            //A laquelle on envoie dans son constructeur le nombre de joueur ainsi que le mode du plateau defini avec la chaine de caractere r
            } else if (j2.isSelected()) {
                ValiderButton pageSuivante = new ValiderButton(imageFile, Integer.valueOf(j2.getText()), r);
            } else {
                ValiderButton pageSuivante = new ValiderButton(imageFile, Integer.valueOf(j4.getText()), r);
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
        new JouerButton(imageFile);
    }
}
