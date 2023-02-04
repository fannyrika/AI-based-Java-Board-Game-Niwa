package main.java.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import main.java.model.*;

public class JeuVue extends JFrame{
    public Jeu model;
    public Controleur controleur;

    //public JTextField textFieldPion;
    //public JButton buttonPion;

    public JeuVue(Jeu m) throws IOException{
        super();
        this.model=m;
        controleur=new Controleur(model, this);
        //TODO: construire l'interface graphique
    }
     
    //À MODIFIER
    public void addSomeListeners(){
        
        button6.addActionListener(e -> {
            int row = Integer.parseInt(textField1.getText());
            textField1.setText("");
            int col = Integer.parseInt(textField2.getText());
            textField2.setText("");

            try {
                controleur.placer(row,col);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        buttonPion.addActionListener(e -> {
            int pos = Integer.parseInt(textFieldPion.getText());
            textFieldPion.setText("");

            try {
                controleur.placerPion(pos);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        button1.addActionListener(e -> {
            try {
                controleur.passer();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        button2.addActionListener(e -> {
            try {
                controleur.rotate(0);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        button3.addActionListener(e -> {
            try {
                controleur.rotate(1);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        button4.addActionListener(e -> {
            try {
                controleur.rotate(2);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
        button5.addActionListener(e -> {
            try {
                controleur.rotate(3);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("from view:");//debug
            model.tuileCourante.afficherPiece(model.tuileCourante);
        });
    }

    public void creerPlateau() {
        while(true){
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){
                //controleur.rotationTmp=0;
                System.out.println("current player changed!");//debug
                model.setJoueurCourant(model.getJoueurs().get(i));
                System.out.println(model.getJoueurCourant());//debug
                //TODO: afficher l'information du joueur courant
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                
                //waiting
                while(model.shouldContinue()==false){
                    System.out.print("");
                }

                System.out.println("ready to update the info of player");//debug
                
                model.setShouldContinue(false);
            }
            if(model.getSac().estVide()){
                break;
            }
        }
    }

    public void jouer() {
        while(true){
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){
                //controleur.rotationTmp=0;
                System.out.println("current player changed!");//debug
                model.setJoueurCourant(model.getJoueurs().get(i));
                System.out.println(model.getJoueurCourant());//debug
                //TODO: afficher l'information du joueur courant
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                
                //waiting
                while(model.shouldContinue()==false){
                    System.out.print("");
                }

                System.out.println("ready to update the info of player");//debug
                
                model.setShouldContinue(false);
            }
            if(model.getGagneurs().size()==model.getJoueurs().size()-1){
                //game over
                break;
            }
        }
    }
      
    public void lancer() throws IOException{
        addSomeListeners();

        //fillScorePanel();

        creerPlateau();
        jouer();
        //TODO: quitter le jeu (fermer la fenêtre...)
    }

}
