package main.java.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;

import main.java.model.JeuEtat;
import main.java.model.MapEtat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;

public class JouerButton extends JFrame implements ActionListener, Runnable {

    public Thread t;
    public static final String threadName = "Thread_JB";

    JPanel conteneur;
    JButton btn;
    JRadioButton manuelButton, autoButton, j2, j4;
    JCheckBox p1, p2, p3, p4;
    JTextField nmbJoueur;
    JLabel background;

    JouerButton() {
    	
        //Fonctions permettant l'affichage correcte de la fenetre 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("MENU NIWA JOUERFRAME");
        //setLocationRelativeTo(null); // Pour centrer la fenetre
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(StockageSettings.file_parametreNiwa.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

        
        //Conteneur pour les buttons et les JLabel
        JPanel conteneur0 = new JPanel();
        conteneur0.setLayout(new GridLayout(4, 1));
        JPanel conteneur1 = new JPanel();
        JPanel conteneur2 = new JPanel();
        JPanel conteneur3 = new JPanel();
        JPanel conteneur4 = new JPanel();   
        
        //Permet de selectionner un seul choix(JRadioButton) � la foix
        ButtonGroup buttonGroup = new ButtonGroup();
        ButtonGroup buttonGroup2 = new ButtonGroup();
       
        //Choisir le nombre de joueurs 2 ou 4
        JLabel label0 = new JLabel("  N O M B R E   J O U E U R S    : ", JLabel.CENTER);
        label0.setFont(new Font("Congenial Black", Font.BOLD, 15));
        j2 = new JRadioButton("2");
        j4 = new JRadioButton("4");

        JLabel labelBot = new JLabel(" N O M B R E   D E   B O T S    : ",JLabel.CENTER);
        labelBot.setFont(new Font("Congenial Black", Font.BOLD, 15));
        p1 = new JCheckBox("J1");
        p2 = new JCheckBox("J2");
        p3 = new JCheckBox("J3");
        p4 = new JCheckBox("J4");

        conteneur3.add(labelBot);
        conteneur3.add(p1);
        conteneur3.add(p2);
        conteneur3.add(p3);
        conteneur3.add(p4);

        labelBot.setVisible(false);
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);

        j2.addActionListener(e -> {
            labelBot.setVisible(true);
            p1.setVisible(true);
            p2.setVisible(true);
            p3.setVisible(false);
            p4.setVisible(false);

            p3.setSelected(false);
            p4.setSelected(false);
        });

        j4.addActionListener(e -> {
            labelBot.setVisible(true);
            p1.setVisible(true);
            p2.setVisible(true);
            p3.setVisible(true);
            p4.setVisible(true);
        });

        buttonGroup.add(j2);
        buttonGroup.add(j4);
        conteneur1.add(label0);
        conteneur1.add(j2);
        conteneur1.add(j4);
        
        //Choisir le mode de cr�ation du plateau AUTO ou MANUEL
        JLabel label1 = new JLabel("  C R E A T I O N   D U   P L A T E A U  : ", JLabel.CENTER);
        label1.setFont(new Font("Congenial Black", Font.BOLD, 15));
        manuelButton = new JRadioButton(" M A N U E L ");
        autoButton = new JRadioButton(" A U T O M A T I Q U E ");
        buttonGroup2.add(manuelButton);
        buttonGroup2.add(autoButton);
        conteneur2.add(label1);
        conteneur2.add(manuelButton);
        conteneur2.add(autoButton);

        
        //Bouton permettant le passage � la fenetre d'apres apres verification de la validite des choix du joueur
        JButton suivant = new JButton("S U I V A N T ");
        suivant.setFont(new Font("Congenial Black", Font.BOLD, 15));
        suivant.setPreferredSize(new Dimension(120, 50));
        suivant.setFocusable(false);
        suivant.setForeground(Color.ORANGE);
        suivant.setBackground(Color.WHITE);
        suivant.setBorder(new LineBorder(Color.CYAN));
        suivant.addActionListener(e -> {
            //Message d'erreur s'affichant si aucun choix pour le nombre de joueur ou le mode du plateau n'est selctionn�
            if (!(j2.isSelected()) && !(j4.isSelected()) || !(manuelButton.isSelected()) && !(autoButton.isSelected())) {
                JOptionPane.showMessageDialog(this, "Veuillez Completer Vos Choix : ");
  
            //Sinon si le joueur a bien fait ses choix on  affiche la page suivante
            //A laquelle on envoie dans son constructeur le nombre de joueur ainsi que le mode du plateau defini avec la chaine de caractere r
            }

            else{
                this.dispose();
                StockageSettings.NB_JOUEURS_TOTAL = j2.isSelected() ? 2 : 4;
                StockageSettings.NB_IA = 0;
                if(p1.isSelected()){StockageSettings.NB_IA += 1;}
                if(p2.isSelected()){StockageSettings.NB_IA += 1;}
                if(p3.isSelected()){StockageSettings.NB_IA += 1;}
                if(p4.isSelected()){StockageSettings.NB_IA += 1;}
                StockageSettings.NB_HUMAIN = StockageSettings.NB_JOUEURS_TOTAL-StockageSettings.NB_IA;
                StockageSettings.MAP_ETAT = manuelButton.isSelected() ? MapEtat.MANUEL : null;
                ValiderButton pageSuivante = new ValiderButton();
                pageSuivante.start();
            }
        });
        conteneur4.add(suivant);

      //On ajoute tous les conteneur � la fenetre
        conteneur0.add(conteneur1);
        conteneur0.add(conteneur2);
        conteneur0.add(conteneur3);
        conteneur0.add(conteneur4);
        background.add(conteneur0);
        add(background);

       
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void run() {
        this.pack();
        this.setVisible(true);
    }

    public void start(){
        if(t == null){
            t = new Thread(this,threadName);
            t.start();
        }
    }

    public static void main(String[] args) {
        new JouerButton().start();;
    }
}
