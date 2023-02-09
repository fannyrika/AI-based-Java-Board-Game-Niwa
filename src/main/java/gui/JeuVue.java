package main.java.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import main.java.model.*;

public class JeuVue extends JFrame implements KeyListener, JeuVueInterface{
    protected Jeu model;
    //protected Controleur controleur;
    /**
     * qui sert à sauvegarder l'index de location choisie
     */
    protected int indexChoisi=0;
    protected ArrayList<Coordonnee> locationsPossibles;

    protected JButton placerTuileSansTemple;
    protected JButton placerTuileTemple;
    protected JButton rotaterTuile;

    public JeuVue(Jeu m) throws IOException{
        super();
        this.model=m;
        //controleur=new Controleur(model, this);
        //TODO: construire l'interface graphique (avec la tuileTemple du joueur0 comme la tuile initiale)
    }

    /**
     * TODO: dessiner les tuiles avec temples;
     */
     
    public void creerPlateau() {
        while(true){
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){
                //controleur.rotationTmp=0;
                model.setJoueurCourant(model.getJoueurs().get(i));
                System.out.println("joueur courant:"+model.getJoueurCourant());//debug
                //TODO: afficher l'information du joueur courant
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                afficherInstruction("Tappez ← → pour la location pour cette tuile. Tappez SPACE pour vérifier la choix.");
                locationsPossibles = afficherPossibleTuilePosition();
                indexChoisi=0;
                
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                System.out.println("ready to update the info of player");//debug
                
                model.setJeuEtat(JeuEtat.CHOOSING_TUILE_LOCATION);
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
                model.setJeuEtat(JeuEtat.CHOOSING_PION);
                afficherPossiblePionPosition();
                indexChoisi=0;
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                System.out.println("ready to place the pion");//debug
                model.setJeuEtat(JeuEtat.PLACING_PION);
                locationsPossibles = afficherPossibleDestination();
                indexChoisi=0;
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                System.out.println("ready to choose the destination for the pearl");//debug
                model.setJeuEtat(JeuEtat.CHOOSING_PEARL_DESTINATION);
                afficherPossiblePionPosition();
                indexChoisi=0;
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }
            }
            if(model.getGagneurs().size()==model.getJoueurs().size()-1){
                //game over
                break;
            }
        }
    }
      
    public void lancer() throws IOException{
        creerPlateau();
        jouer();
        //TODO: quitter le jeu (fermer la fenêtre etc...)
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_RIGHT ){
            if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                indexChoisi=(indexChoisi + 1) % model.getJoueurCourant().getPions().size();
                dessinerCercleBlanc(model.getJoueurCourant().getPions().get(indexChoisi).getLocation());
            }
            else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                model.getTuileCourant().rotate();
                //TODO: renouveler la vue de tuile tournée
            }
            else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                    dessinerTuile(locationsPossibles.get(indexChoisi));
                }
            else if(model.getJeuEtat()==JeuEtat.PLACING_PION || 
            model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                dessinerCercleBlanc(locationsPossibles.get(indexChoisi));
            }
        }
        else if ( e.getKeyCode()==KeyEvent.VK_LEFT ){
            if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                indexChoisi=(indexChoisi - 1) % model.getJoueurCourant().getPions().size();
                dessinerCercleBlanc(model.getJoueurCourant().getPions().get(indexChoisi).getLocation());
            }
            else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                //sens de l'inverse
                //-1 mod 3 = 2 (fois)
                model.getTuileCourant().rotate();
                model.getTuileCourant().rotate();
                //TODO: renouveler la vue de tuile tournée
            }
            else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                    dessinerTuile(locationsPossibles.get(indexChoisi));
                }
            else if(model.getJeuEtat()==JeuEtat.PLACING_PION || 
            model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                dessinerCercleBlanc(locationsPossibles.get(indexChoisi));
            }
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE){
            if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                //TODO:programmez ici pour effacer tous les cercles
                //TODO: renouveler la vue des pions
                model.getPlateau().placeTuileContraint(model.getTuileCourant(), locationsPossibles.get(indexChoisi));
                model.setJeuEtat(JeuEtat.ROTATING_TUILE);
                afficherInstruction("Tappez ← → pour tourner la tuile. Tappez SPACE pour vérifier la choix.");
            }
            else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                //TODO:programmez ici pour effacer tous les cercles
                //TODO: renouveler la vue des pions
                model.setJeuEtat(JeuEtat.CHOOSING_PION);
                afficherInstruction("Tappez ← → pour chosir un pion à déplacer. Tappez SPACE pour vérifier la choix ou passer votre tour.");
            }
            else if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                //TODO:programmez ici pour effacer tous les cercles
                //TODO: renouveler la vue des pions
                model.setPionCourant(model.getJoueurCourant().getPions().get(indexChoisi));
                model.setJeuEtat(JeuEtat.PLACING_PION);
                afficherInstruction("Tappez ← → pour chosir la location. Tappez SPACE pour vérifier la choix ou passer votre tour.");
            }
            else if(model.getJeuEtat()==JeuEtat.PLACING_PION){
                //TODO:programmez ici pour effacer tous les cercles
                //TODO: renouveler la vue des pions
                model.getPlateau().placerPionForce(model.getPionCourant(), locationsPossibles.get(indexChoisi));
                model.setJeuEtat(JeuEtat.CHOOSING_PEARL_DESTINATION);
                afficherInstruction("Tappez ← → pour chosir un pion à passer la perle. Tappez SPACE pour vérifier la choix ou passer votre tour.");
            }
            else if(model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                //TODO:programmez ici pour effacer tous les cercles
                //TODO: renouveler la vue des pions
                model.getPionCourant().passPerleTo(
                    model.getJoueurCourant().getPions().get(indexChoisi)
                );
                model.setJeuEtat(JeuEtat.CONTINUE);
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void afficherInstruction(String str){
        //TODO
    }
    
    public ArrayList<Coordonnee> afficherPossibleTuilePosition() {
        ArrayList<Coordonnee> locationsPossibles = model.getPlateau().canPlaceLocations(model.getTuileCourant());
        for(int i=0; i<locationsPossibles.size(); i++) {
            dessinerCercleRouge(locationsPossibles.get(i));
        }
        return locationsPossibles;
    }

    public void afficherPossiblePionPosition() {
        ArrayList<Pion> pionsPossibles=model.getJoueurCourant().getPions();
        for(int i=0; i<pionsPossibles.size();i++){
            dessinerCercleRouge(pionsPossibles.get(i).getLocation());
        }
    }

    public ArrayList<Coordonnee> afficherPossibleDestination() {
        ArrayList<Coordonnee> locationsPossibles = model.getPlateau().canMoveLocations(model.getPionCourant());
        for(int i=0; i<locationsPossibles.size(); i++) {
            dessinerCercleRouge(locationsPossibles.get(i));
        }
        return locationsPossibles;
    }

    /**
     * TODO: dessiner un cercle rouge (indiquant la locations possible) dont le centre est sur la coordonné donné
     * taille du cercle: un peu plus grand que la taille du cercle dans le centre des certaines tuiles
     */
    public void dessinerCercleRouge(Coordonnee location){

    }

    /**
     * TODO: dessiner un cercle blanc (indiquant la location choisie) dont le centre est sur la coordonné donné
     * taille du cercle: un peu plus grand que la taille du cercle dans le centre des certaines tuiles
     */
    public void dessinerCercleBlanc(Coordonnee location){
        
    }

        /**
     * TODO: dessiner une tuile (indiquant la location choisie)
     * taille du cercle: un peu plus grand que la taille du cercle dans le centre des certaines tuiles
     */
    public void dessinerTuile(Coordonnee location){
        
    }

}
