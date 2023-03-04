package main.java.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.synth.SynthSpinnerUI;

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

import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import main.java.model.*;
public class InterfaceDeJeu extends JFrame implements KeyListener{
    protected Jeu model;
    //protected Controleur controleur;
    /**
     * qui sert 脿 sauvegarder l'index de location choisie
     */
    protected int indexChoisi=0;
    protected ArrayList<Coordonnee> locationsPossibles;

    protected GridTuile gridTuile;
    protected TableauDeBord tableauDeBord;
    public InterfaceDeJeu(Jeu m) throws IOException{
        setLayout(new BorderLayout());
        setVisible(true);
        setTitle("NIWA");
        model=m;
        gridTuile = new GridTuile(model);
        JScrollPane plateau=new JScrollPane(gridTuile);
        gridTuile.setBackground(Color.WHITE);
        plateau.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        plateau.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
    
        tableauDeBord=new TableauDeBord(model);
        tableauDeBord.boutonQuitter.addActionListener(e->{
            this.dispose();
        });
        JPanel vueComplete=new JPanel(new BorderLayout());
        vueComplete.add(plateau, BorderLayout.CENTER);
        vueComplete.add(tableauDeBord,BorderLayout.LINE_END);
        Dimension size =plateau.getPreferredSize();
        size.width /= 3; // divide the width by 3
        tableauDeBord.setPreferredSize(size);
        //controleur=new Controleur(model, this);
        //TODO: construire l'interface graphique (avec la tuileTemple du joueur0 comme la tuile initiale)
        addKeyListener(gridTuile);
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) { 
            requestFocus();	
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(vueComplete, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        //setVisible(true);
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
                
                afficherInstruction("<html> S/F pour chosir la location de cette tuile.<br> R pour placer un temple.<br> ESPACE pour confirmer</html>");
                locationsPossibles = afficherPossibleTuilePosition();
                indexChoisi=0;

                Coordonnee ancienneTuileCoordonnee = model.getTuileCourant().getLocationInGridTuile();
                ArrayList<Coordonnee> emplacementsPossibles = model.getPlateau().canPlaceLocations(ancienneTuileCoordonnee);
                System.out.println("il y a "+model.getSac().size()+" tuiles dans le sac.");
                tableauDeBord.pioche.setText("<html>il y a "+model.getSac().size()+" tuiles dans le sac.</html>");
                if(model.getSac().size()>0){
                    model.setTuileCourante(model.piocher());
                }
                else if(model.getSacTemples().size()>0){
                    System.out.println("Il faut placer des camps.");
                    tableauDeBord.pioche.setText("Placez les temples");
                    model.setTuileCourante(model.popTemple());
                }
                else break;
                int dx = -1;
                while (emplacementsPossibles.isEmpty()){
                    emplacementsPossibles = model.getPlateau().canPlaceLocations(ancienneTuileCoordonnee.getX() + dx, ancienneTuileCoordonnee.getY());
                    dx--;
                }
                model.getPlateau().placeTuileForce(model.getTuileCourant(), emplacementsPossibles.get(0));

                repaint();
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                System.out.println("ready to update the info of player");//debug
                
                model.setJeuEtat(JeuEtat.CHOOSING_TUILE_LOCATION);
            }
            if(model.getSac().size()==0 && model.getSacTemples().size()==0){
                tableauDeBord.pioche.setText("");
                break;
            }
        }
    }
                                   
    public void jouer() {

        // Surement temporaire : permet de placer les pions automatiquement après que les tuiles ont toutes été posées
        for (Joueur j : model.getJoueurs()) {
            model.getPlateau().placeStartPionAuto(j);
        }

        while(true){
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){
                //controleur.rotationTmp=0;
                System.out.println("current player changed!");//debug
                model.setJoueurCourant(model.getJoueurs().get(i));
                System.out.println(model.getJoueurCourant());//debug
                tableauDeBord.setJoueurCourant("<html>joueur courant:<br>"+model.getJoueurCourant()+"</html>");
                repaint();
                //TODO: afficher l'information du joueur courant
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                model.setJeuEtat(JeuEtat.CHOOSING_PION);
                //afficherPossiblePionPosition();
                indexChoisi=0;
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                System.out.println("ready to place the pion");//debug
                model.setJeuEtat(JeuEtat.PLACING_PION);
                //locationsPossibles = afficherPossibleDestination();
                //indexChoisi=0;
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    System.out.print("");
                }

                
                if(model.getPionCourant().size()>0){
                //si le pion saute, on n'a pas besoins de cette etape
                    System.out.println("ready to choose the destination for the pearl");//debug
                    model.setJeuEtat(JeuEtat.CHOOSING_PEARL_DESTINATION);
                    //afficherPossiblePionPosition();
                    //indexChoisi=0;
                    //waiting
                    while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                        System.out.print("");
                    }
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
        //TODO: quitter le jeu (fermer la fen锚tre etc...)
    }

    public void afficherInstruction(String str){
        System.out.println(str);//debug
        //TODO
        tableauDeBord.setEtapeCourante(str);
    }
    
    public ArrayList<Coordonnee> afficherPossibleTuilePosition() {
        ArrayList<Coordonnee> locationsPossibles = model.getPlateau().canPlaceLocations(model.getTuileCourant().getLocationInGridTuile());
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
     * TODO: dessiner un cercle rouge (indiquant la locations possible) dont le centre est sur la coordonn茅 donn茅
     * taille du cercle: un peu plus grand que la taille du cercle dans le centre des certaines tuiles
     */
    public void dessinerCercleRouge(Coordonnee location){

    }

    /**
     * TODO: dessiner un cercle blanc (indiquant la location choisie) dont le centre est sur la coordonn茅 donn茅
     * taille du cercle: un peu plus grand que la taille du cercle dans le centre des certaines tuiles
     */
    public void dessinerCercleBlanc(Coordonnee location){
        
    }

    private void glisserTuile(Tuile tuile, int dx, int dy){
        int x = model.getTuileCourant().getLocationInGridTuile().getX();
        int y = model.getTuileCourant().getLocationInGridTuile().getY();
        model.getPlateau().removeTuileBrutForce(model.getTuileCourant().getLocationInGridTuile());
        if(!model.getPlateau().placeTuileForce(tuile, x+dx, y+dy)){
            if(dx > 0){glisserTuile(tuile, dx+1, dy);}
            else if(dx < 0){glisserTuile(tuile, dx-1, dy);}
            else if(dy > 0){glisserTuile(tuile, dx, dy+1);}
            else if(dy < 0){glisserTuile(tuile, dx, dy-1);}
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'r'://placer l'autre tuile avec temple
                if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION && model.getSacTemples().size()>0){
                    Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                    model.getPlateau().removeTuileBrutForce(coordonnee);
                    repaint();
                    
                    model.setTuileCourante(model.popTemple());
                    model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                    repaint();
                }
                break;
            case 's':   // gauche
                //if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                //    indexChoisi=(indexChoisi - 1) % model.getJoueurCourant().getPions().size();
                //    dessinerCercleBlanc(model.getJoueurCourant().getPions().get(indexChoisi).getLocation());
                //}
                //else 
                if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                    Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                    model.getPlateau().removeTuileBrutForce(coordonnee);
                    repaint();
                    
                    Tuile tuileTmp = model.getTuileCourant();
                    //sens horaire inverse
                    //-1 mod 3 = 2 (fois)
                    tuileTmp.rotate();
                    tuileTmp.rotate();
                    model.setTuileCourante(tuileTmp);
                    model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                    repaint();
                }
                else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    glisserTuile(model.getTuileCourant(), -1, 0);
                    repaint();
                }
                //else if(model.getJeuEtat()==JeuEtat.PLACING_PION || 
                //model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                //    indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                //    dessinerCercleBlanc(locationsPossibles.get(indexChoisi));
                //}
                break;
            case 'f':   // droite
                if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                    indexChoisi=(indexChoisi + 1) % model.getJoueurCourant().getPions().size();
                    dessinerCercleBlanc(model.getJoueurCourant().getPions().get(indexChoisi).getLocation());
                }
                else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                    Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                    model.getPlateau().removeTuileBrutForce(coordonnee);
                    
                    Tuile tuileTmp = model.getTuileCourant();
                    tuileTmp.rotate();
                    model.setTuileCourante(tuileTmp);
                    model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                    repaint();
                }
                else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    glisserTuile(model.getTuileCourant(), 1, 0);
                    repaint();
                }
                else if(model.getJeuEtat()==JeuEtat.PLACING_PION || 
                model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                    indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                    dessinerCercleBlanc(locationsPossibles.get(indexChoisi));
                }
                break;
            case 'd':   // bas
                if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    glisserTuile(model.getTuileCourant(), 0, -1);
                    repaint();
                }
                break;
            case 'e':   // haut
                if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    glisserTuile(model.getTuileCourant(), 0, 1);
                    repaint();
                }
                break;
            case ' ':   // verifier le choix
                if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                    //TODO:programmez ici pour effacer tous les cercles
                    //TODO: renouveler la vue des pions
                    model.getPlateau().removeTuileBrutForce(model.getTuileCourant().getLocationInGridTuile());      // On enlève la tuile courante du plateau
                    if(model.getPlateau().placeTuileContraint(model.getTuileCourant(), model.getTuileCourant().getLocationInGridTuile())){  // Et on tente de la replacer, mais avec les contraintes
                        model.setJeuEtat(JeuEtat.ROTATING_TUILE);                                                                           // Si la tuile se place, on passe à la rotation
                        afficherInstruction("<html>Tappez 鈫鈫pour tourner la tuile.<br> Tappez SPACE pour v茅rifier la choix.</html>");
                    }
                    else{                                                                                                                   // Sinon, on reste sur le deplacement de tuile
                        model.getPlateau().placeTuileForce(model.getTuileCourant(),model.getTuileCourant().getLocationInGridTuile());
                    }
                }
                else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                    //TODO:programmez ici pour effacer tous les cercles
                    model.setJeuEtat(JeuEtat.CONTINUE);
                    afficherInstruction("<html>Tappez 鈫鈫pour chosir un pion<br>脿 d茅placer. <br>SPACE pour v茅rifier la choix ou passer votre tour.</html>");
                }
                //else if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                //    //TODO:programmez ici pour effacer tous les cercles
                //    //TODO: renouveler la vue des pions
                //    model.setPionCourant(model.getJoueurCourant().getPions().get(indexChoisi));
                //    model.setJeuEtat(JeuEtat.CONTINUE);
                //    afficherInstruction("Tappez 鈫鈫pour chosir la location. Tappez SPACE pour v茅rifier la choix ou passer votre tour.");
                //}
                //else if(model.getJeuEtat()==JeuEtat.PLACING_PION){
                //    //TODO:programmez ici pour effacer tous les cercles
                //    //TODO: renouveler la vue des pions
                //    model.getPlateau().placerPionForce(model.getPionCourant(), locationsPossibles.get(indexChoisi));
                //    model.setJeuEtat(JeuEtat.CONTINUE);
                //    afficherInstruction("Tappez 鈫鈫pour chosir un pion 脿 passer la perle. Tappez SPACE pour v茅rifier la choix ou passer votre tour.");
                //}
                //else if(model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                //    //TODO:programmez ici pour effacer tous les cercles
                //    //TODO: renouveler la vue des pions
                //    model.getPionCourant().passPerleTo(
                //        model.getJoueurCourant().getPions().get(indexChoisi)
                //    );
                //    model.setJeuEtat(JeuEtat.CONTINUE);
                //}
                break;
            case 'p':   // commencer a faire des operations sur la vue generale de la plateau
                if(model.getJeuEtat()==JeuEtat.CHANGING_VIEW){
                    //sortir la mode
                    model.setJeuEtat(model.getDernierEtat());
                    break;
                }
                //sinon entrer la mode
                model.setDernierEtat(model.getJeuEtat());
                model.setJeuEtat(JeuEtat.CHANGING_VIEW);
                break;
            case 'n':   // next step
                if( model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION ||
                model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                    //terminer la construction des tuiles et commencer a deplacer les pions
                    model.setJeuEtat(JeuEtat.CHOOSING_PION);
                }
                break;
            default:
                break;
        }
        
        //if( e.getKeyCode() == KeyEvent.VK_ENTER){}
        //else if ( e.getKeyCode()==KeyEvent.VK_RIGHT ){}
        //else if ( e.getKeyCode()==KeyEvent.VK_LEFT ){}
        //else if(e.getKeyCode()==KeyEvent.VK_SPACE){}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_HOME) {
            System.out.println("Left arrow key pressed, keyCode=" + keyCode);
        } else if (keyCode == KeyEvent.VK_END) {
            System.out.println("Right arrow key pressed, keyCode=" + keyCode);
        } else if (keyCode == KeyEvent.VK_PAGE_UP) {
            System.out.println("Up arrow key pressed, keyCode=" + keyCode);
        } else if (keyCode == KeyEvent.VK_PAGE_DOWN) {
            System.out.println("Down arrow key pressed, keyCode=" + keyCode);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
   
    public static void main(String[] args) throws IOException {
        Jeu model =  new Jeu(2);
        InterfaceDeJeu jeuVue = new InterfaceDeJeu(model);
        jeuVue.lancer();
    }


///scrollbar du plateau
class CustomScrollBarUI extends BasicScrollBarUI{
    CustomScrollBarUI(){
      
}
@Override
protected void configureScrollBarColors() {
    // Set the thumb and track colors
    super.thumbColor = Color.black;
    super.trackColor = Color.WHITE;

}
@Override
protected JButton createDecreaseButton(int orientation) {
    // Remove the decrease button
    return new JButton() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }
    };
}

@Override
protected JButton createIncreaseButton(int orientation) {
    // Remove the increase button
    return new JButton() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }
    };
}

   }

}

