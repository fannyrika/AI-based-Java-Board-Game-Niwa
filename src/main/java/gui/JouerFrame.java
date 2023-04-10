package main.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.java.model.*;

import javax.swing.*;

public class JouerFrame extends JFrame{
    boolean isWaitingToClick=true;
    //private JTextField nomJeu, nom1, nom2, nom3, nom4, type1, type2, type3, type4;
    protected JLabel nomJeu=new JLabel("NIWA");
    protected JTextField nom1, nom2, nom3, nom4;
    protected ArrayList<TuileTemple> sacTemples = new ArrayList<TuileTemple>();
    public JouerFrame() throws IOException{
        //this.setSize(1800, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ArrayList<Joueur> joueurs=new ArrayList<Joueur>();
        JPanel panel0 = new JPanel();

        //nomJeu = new JTextField("CARCASSONNE");
        nom1 = new JTextField("JOUEUR1");
        nom2 = new JTextField("JOUEUR2");
        nom3 = new JTextField("JOUEUR3");
        nom4 = new JTextField("JOUEUR4");
        //type1 = new JTextField("HUMAIN");
        //type2 = new JTextField("HUMAIN");
        //type3 = new JTextField("HUMAIN");
        //type4 = new JTextField("HUMAIN");

        panel0.add(nomJeu);
        this.add(panel0);

        JPanel panel1=new JPanel();
        panel1.add(nom1);

        JPanel panel2=new JPanel();
        panel2.add(nom2);

        JPanel panel3=new JPanel();
        panel3.add(nom3);

        JPanel panel4=new JPanel();
        panel4.add(nom4);

        JButton valider=new JButton("VALIDER");

        JPanel mainContainer=new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(panel0);
        mainContainer.add(panel1);
        mainContainer.add(panel2);
        mainContainer.add(panel3);
        mainContainer.add(panel4);
        mainContainer.add(valider);

        this.add(mainContainer);
        this.setVisible ( true );

        
        valider.addActionListener(e->{
            this.validate();
            SwingUtilities.invokeLater(() -> {
                this.repaint();
            });
            
            if(!nom1.getText().equals("")){
                Joueur j = new Joueur(nom1.getText());
                joueurs.add(j);
                sacTemples.add(j.getTemple());   // On ajoute chaque temple dans la liste des temples
            }
            if(!nom2.getText().equals("")){
                Joueur j = new Joueur(nom2.getText());
                joueurs.add(j);
                sacTemples.add(j.getTemple());   // On ajoute chaque temple dans la liste des temples
            }
            if(!nom3.getText().equals("")){
                Joueur j = new Joueur(nom3.getText());
                joueurs.add(j);
                sacTemples.add(j.getTemple());   // On ajoute chaque temple dans la liste des temples
            }
            if(!nom4.getText().equals("")){
                Joueur j = new Joueur(nom4.getText());
                joueurs.add(j);
                sacTemples.add(j.getTemple());   // On ajoute chaque temple dans la liste des temples
            }
            isWaitingToClick=false;
            
        });
        while(isWaitingToClick){
            System.out.print("");
        }
        if(joueurs.size() != 0){
            this.dispose();
            Jeu model=new Jeu(joueurs, sacTemples);
            new Launcher(model);
        }
    }
    public static void main(String[] args) throws IOException {
        new JouerFrame();
    }

}
