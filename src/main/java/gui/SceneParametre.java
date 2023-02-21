package main.java.gui;

import java.awt.*;
import java.io.IOException;



import main.java.model.*;
import java.util.LinkedList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;



public class SceneParametre extends Scene{
	
 	 protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    protected JPanel menu;
	    protected GridBagConstraints pos = new GridBagConstraints();
	    protected JTextField nomJoueur, ia;
	    protected JButton ajouter, valider;
	    protected static LinkedList<Joueur> joueurs;
	    protected JeuVue jeuVue;
	    protected Jeu model ;

	    public SceneParametre(boolean visible) {
	        super(visible);

	        Main.getView().setTitle("Parametre");
	        this.setSize(screenSize);
	        this.setLayout(null);
	        this.setVisible(visible);
	        joueurs = new LinkedList<Joueur>();

	        setupMenu();
	        setupNameField();
	        setupIAField();
	        setupAjouterButton();
	        setupValideButton();
	    }

	    public void setupMenu() {
	        menu = new JPanel();
	        menu.setLayout(new GridBagLayout());
	        menu.setVisible(true);
	        menu.setBounds(new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight()));
	        add(menu);
	        
	    }

	    public void setupNameField() {
	        nomJoueur = new JTextField("Remplacer par votre nom..");
	        nomJoueur.setPreferredSize(new Dimension(270,60));
	        nomJoueur.setForeground(Color.ORANGE);// couleur du texte
	        nomJoueur.setBackground(Color.WHITE);
	        nomJoueur.setFont(new Font("Lucida Console", Font.BOLD,15));
	        pos.gridx = 0;
	        pos.gridy = 0;
	        pos.weighty=0.1;
	        menu.add(nomJoueur, pos);
	    }

	    public void setupIAField() {
	        ia = new JTextField("Etes vous une IA VRAI/FAUX");
	        ia.setPreferredSize(new Dimension(270, 60));
	        ia.setForeground(Color.ORANGE);// couleur du texte
	        ia.setBackground(Color.WHITE);
	        ia.setFont(new Font("Lucida Console", Font.BOLD,15));
	        pos.gridx = 0;
	        pos.gridy = 1;
	        pos.weighty=0.1;
	        menu.add(ia, pos);
	    }

	    public void setupAjouterButton() {
	        ajouter = new JButton("Ajouter");
	        ajouter.setPreferredSize(new Dimension(200,60));
	        ajouter.setForeground(Color.ORANGE);// couleur du texte
	        ajouter.setBackground(Color.WHITE);
	        ajouter.setFont(new Font("Lucida Console", Font.BOLD,15));
	        pos.gridx = 0;
	        pos.gridy = 2;
	        pos.weighty=0.1;

	        ajouter.addActionListener(e -> {
	            if ((ia.getText().equals("VRAI")) || (ia.getText().equals("FAUX"))) {
	                System.out.println("Ajouté");
	                if (ia.getText().equals("VRAI")) {
	                    joueurs.add(new Joueur());
	                } else {
	                    joueurs.add(new Joueur());
	                }
	            } else {
	                System.out.println(ia.getText());
	                ia.setText("VRAI/FAUX");
	            }
	        });

	        menu.add(ajouter, pos);
	    }

	    public void setupValideButton(){
	        valider = new JButton("Valider");
	        valider.setPreferredSize(new Dimension(200,60));
	        valider.setForeground(Color.ORANGE);// couleur du texte
	        valider.setBackground(Color.WHITE);
	        valider.setFont(new Font("Lucida Console", Font.BOLD,15));
	        pos.gridx = 0;
	        pos.gridy = 3;
	        pos.weighty=0.1;

	        valider.addActionListener(e -> {
	        	   try {
					jeuVue=new JeuVue(model,true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	                Main.getView().add(jeuVue);
	                changeScene(this,jeuVue);         
	        });

	        menu.add(valider, pos);
	    }

	    public static LinkedList<Joueur> getJoueurs() {
	        return joueurs;
	    }

	   

	

}
