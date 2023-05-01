package main.java.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicScrollBarUI;
import main.java.model.*;
import main.java.model.Action;
public class InterfaceDeJeu extends JFrame implements KeyListener, Runnable, Serializable {

    public transient Thread t;
    public static final String threadName = "Thread_IDJ";

    protected static final int TABLEAU_DE_BORD_RATIO = 4;

    protected Jeu model;
    //protected Controleur controleur;
    /**
     * qui sert 脿 sauvegarder l'index de location choisie
     */
    protected int indexChoisi=0;
    protected ArrayList<Coordonnee> locationsPossibles;

    protected static int nbJoueurs = 2;
    protected static int nb_joueurs_ia = 1;
    protected GridTuile gridTuile;
    protected TableauDeBord tableauDeBord;
    protected static JScrollPane scrollPane;
    
    public InterfaceDeJeu(Jeu m){
        setLayout(new BorderLayout());
        setTitle("Niwa");
        
        model=m;
        gridTuile = new GridTuile(model);
        gridTuile.setBackground(Color.WHITE);
        scrollPane=new JScrollPane(gridTuile);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setBackground(new Color(61, 58, 58));
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        tableauDeBord=new TableauDeBord(model);
        this.setActionButton();
        JPanel vueComplete=new JPanel(new BorderLayout());
        vueComplete.setBackground(new Color(61, 58, 58));
        vueComplete.add(scrollPane, BorderLayout.CENTER);
        vueComplete.add(tableauDeBord,BorderLayout.LINE_END);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        size.width /= TABLEAU_DE_BORD_RATIO; // divide the width by 4
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
        //setLocationRelativeTo(null);
        //setVisible(true);
    }

    public void setActionButton(){
        tableauDeBord.boutonZoom.addActionListener(e->{
            TuileGraphique.zoom(GridTuile.DISTANCE_ZOOM);
            //gridTuile.setPreferredSize(new Dimension(10*GridTuile.DISTANCE_ZOOM+gridTuile.getSize().width,10*GridTuile.DISTANCE_ZOOM+gridTuile.getSize().height));
            scrollPane.repaint();
            
        });
        tableauDeBord.boutonDezoom.addActionListener(e->{
            TuileGraphique.zoom(-GridTuile.DISTANCE_ZOOM);
            //gridTuile.setPreferredSize(new Dimension(-10*GridTuile.DISTANCE_ZOOM+gridTuile.getSize().width,-10*GridTuile.DISTANCE_ZOOM+gridTuile.getSize().height));
            scrollPane.repaint();
        });
        tableauDeBord.boutonRotationHoraire.addActionListener(e->{
            if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                model.getPlateau().removeTuileBrutForce(coordonnee);
                        
                Tuile tuileTmp = model.getTuileCourant();
                tuileTmp.rotate();
                model.setTuileCourante(tuileTmp);
                model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                repaint();
            }
        });
        tableauDeBord.boutonMenu.addActionListener(e ->{
            String[] choix = {"Reprendre", "Enregistrer et aller au menu principal", "Aller au menu principal", "Quitter"};
        int i = JOptionPane.showOptionDialog(null,
            "Que souhaitez-vous faire ^^ ?",
            "Menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choix, choix[0]);

            if (i == 0); // reprendre ne change rien
            else if(i == 1){
                boolean tmp = false;
                // On empêche le joueur de sauvegarder sa partie s'il n'a pas choisit où sa perle partait (problème de logique sinon)
                if(model.getJeuEtat() == JeuEtat.CHOOSING_PEARL_DESTINATION){
                    JOptionPane.showMessageDialog(null, "Veuillez d'abord sélectionnez le pion reçevant votre perle !", "Erreur !", JOptionPane.ERROR_MESSAGE);
                    tmp = true;
                }

                gridTuile.circlesToDraw.clear();

                if(tmp == false){
                    // proposer de choisir le nom de la sauvegarde 
                    String name = Save.nameSave();

                    Save.creerSave(this, name);

                    //fermeture de la partie donc ouverture du menu principal
                    model.setJeuEtat(JeuEtat.GAME_INTERRUPT);
                    (new NiwaWindow()).run();
                }
            }else if(i == 2 || i == 3){
                int q = -1;
                while(!(q == 0 || q == 1)){
                    // Avertissement avant de quitter et de perdre sa progression
                    q = JOptionPane.showOptionDialog(null, "Si vous quittez maintenant, votre partie sera perdue.\n Voulez-vous continuez ?", 
                    "Êtes-vous sûre ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, null, null);

                    if (q == JOptionPane.YES_OPTION) {
                        // Confirmation du choix
                        dispose();
                        model.setJeuEtat(JeuEtat.GAME_INTERRUPT);
                        if(i == 3) System.exit(0);
                        else if(i == 2) (new NiwaWindow()).run();
                    } else if (q == JOptionPane.NO_OPTION);
                    else;
                }
            };
        });
        tableauDeBord.boutonRotationAntiHoraire.addActionListener(e->{
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
        });
        tableauDeBord.boutonRegles.addActionListener(a->{
            ManuelDuJeu mdj=new ManuelDuJeu();
        });
    }

    /**
     * creer plateau
     */
     
    public void creerPlateau() {
        
        while(model.getJeuEtat() != JeuEtat.GAME_INTERRUPT){
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){                
                model.setJoueurCourant(model.getJoueurs().get(i));
                tableauDeBord.setJoueurCourant("<html><h2>Création du plateau</h2></html>");
                if(StockageSettings.DEBUG_MODE)
                    System.out.println("joueur courant:"+model.getJoueurCourant());//debug
                //TODO: afficher l'information du joueur courant
               
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                
                afficherInstruction("S/F pour chosir la location de cette tuile.<br>   R pour placer un temple.<br>   ESPACE pour confirmer");
                locationsPossibles = afficherPossibleTuilePosition();
                indexChoisi=0;

                Coordonnee ancienneTuileCoordonnee = model.getTuileCourant().getLocationInGridTuile();
                ArrayList<Coordonnee> emplacementsPossibles = model.getPlateau().canPlaceLocations(ancienneTuileCoordonnee);
                System.out.println("il y a "+model.getSac().size()+" tuiles dans le sac.");
                tableauDeBord.pioche.setText("il y a "+model.getSac().size()+" tuiles dans le sac.");
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

                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
                
                //waiting
                while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                    if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){
                        return;
                    }
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
        tableauDeBord.boutonRotationAntiHoraire.setVisible(false);
        tableauDeBord.boutonRotationHoraire.setVisible(false);
        
    }
                                   
    /**
     * dealing the loop of the game
     */
    public void jouer() {
        if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){return;}
        //2 ai players trains each other
        if(model.isAiTraining()){
            model.setJeuEtat(JeuEtat.CONTINUE);
        }
        else{
            model.setJeuEtat(JeuEtat.PLACING_START_PION);
            // waiting
            while (!model.allPionsPlaced()){
                if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){return;}
                //on désactive le zoom et le dézoom car sinon les cerlces ne sont pas centrés
                tableauDeBord.boutonZoom.setEnabled(false);
                tableauDeBord.boutonDezoom.setEnabled(false);
                tableauDeBord.setJoueurCourant("<html><h2>Placement des pions</h2></html>");
                afficherInstruction("Placez vos pions sur le plateau en cliquant sur les cercles noirs.");
                System.out.println("");
            }
            //on reactive zoom et dezoom
            tableauDeBord.boutonZoom.setEnabled(true);
            tableauDeBord.boutonDezoom.setEnabled(true);
        }

        //in order to avoid one part being too long
        int nbTour = 0;
        //update the current player correctly

        while(model.getJeuEtat()!=JeuEtat.GAME_OVER && model.getJeuEtat() != JeuEtat.GAME_INTERRUPT){
            nbTour++;
            ArrayList<Joueur> joueurListeComplete = new ArrayList<>(model.getJoueurs());
            //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
            for(int i=0; i<model.getJoueurs().size(); i++){
                //skip the player if he is in the perdant list
                if(!model.isAiTraining())
                    if(model.getPerdants().contains(model.getJoueurs().get(i))){
                        continue;
                    }
                model.resetAlmostBlockedCount();

                //controleur.rotationTmp=0;
                System.out.println("current player changed!");//debug
                model.setJoueurCourant(joueurListeComplete.get(i));
                System.out.println(model.getJoueurCourant());//debug
                tableauDeBord.setJoueurCourant("<html> "+model.getJoueurCourant()+"</html>");
                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
                
                //TODO: afficher l'information du joueur courant
                //validate();
                //repaint();
                
                //if(model.joueurCourant instanceof JoueurIA){
                //    controleur.controlIA();
                //}
                if(model.getJoueurCourant() instanceof JoueurIA){
                    //print the qtable
                    //((JoueurIA) model.getJoueurCourant()).printQTable(100);
                    //slow down the speed of the AI player
                    afficherInstruction("");
                    tableauDeBord.setJoueurCourant("<html> IA </html>");
                    try {
                        Thread.sleep(100); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("Current player is JoueurIA");
                    SwingUtilities.invokeLater(() -> {
                        repaint();
                    });
                    
                    // create a mapping of the current state
                    State currentState = new State(model);
                    // get the legal actions for the current state
                    ArrayList<Action> legalActions = ((JoueurIA) model.getJoueurCourant()).getLegalActions(model);
                    if(legalActions.isEmpty()){
                        System.out.println("No legal actions for the current state, the current player loses the game.");
                        model.setPerdant(model.getJoueurCourant());
                        //else{
                        //    System.out.println("eliminer loser");
                        //    model.eliminerJoueur(model.getJoueurCourant());
                        //}
                        break;
                    }
                    // draw circle around the possible destinations of the legal actions
                    drawPossibleActionDestinations(legalActions);
                    // AI player chooses an action
                    Action action = ((JoueurIA) model.getJoueurCourant()).chooseAction(model, currentState, legalActions);
                    if(StockageSettings.DEBUG_MODE)
                        System.out.println("Chosen action: " + action);
                    
                    // apply the action
                    State nextState = currentState.getNextState(action);
                    // If the current player is an AI player, learn from the experience
                    if(model.getJoueurs().size()==2)
                        ((JoueurIA) model.getJoueurCourant()).learn(model, currentState, action, nextState);

                    //update the model
                    nextState.updateGame(model);

                    gridTuile.circlesToDraw.clear();
                    //update the view
                    SwingUtilities.invokeLater(() -> {
                        repaint();
                    });
                    
                }
                
                else{
                    model.setJeuEtat(JeuEtat.CHOOSING_PION);
                    //afficherPossiblePionPosition();
                    indexChoisi=0;
                    //waiting
                    afficherInstruction("Cliquez sur le pion que vous voulez déplacer.");
                    while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                        if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){return;}
                        System.out.print("");
                    }
    
                    System.out.println("ready to place the pion");//debug
                    model.setJeuEtat(JeuEtat.PLACING_PION);
                    afficherInstruction("Placez le pion sur le plateau en cliquant sur un cercle");
                    //locationsPossibles = afficherPossibleDestination();
                    //indexChoisi=0;
                    //waiting
                    
                    while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                        if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){return;}
                        System.out.print("");
                    }
                       
                    if(model.getPionCourant().size()>0){//car si le pion peut sauter, on n'a pas besoins de cette etape
                        System.out.println("ready to choose the destination for the pearl");//debug
                        afficherInstruction("Choissinez la destination de la perle en cliquant dessus.");
                        model.setJeuEtat(JeuEtat.CHOOSING_PEARL_DESTINATION);
                        //afficherPossiblePionPosition();
                        //indexChoisi=0;
                        //waiting
                        while(model.getJeuEtat()!=JeuEtat.CONTINUE){
                            if(model.getJeuEtat() == JeuEtat.GAME_INTERRUPT){return;}
                            System.out.print("");
                        }
                    }
                }
                //check if we need to eliminate the current player
                //if(!model.isAiTraining())
                //    if(model.getGagneurs().contains(model.getJoueurCourant()) || model.getPerdants().contains(model.getJoueurCourant())){
                //        System.out.println("eliminer the current player");
                //        model.eliminerJoueur(model.getJoueurCourant());
                //}
            }
            System.out.println("gagneurs:"+model.getGagneurs());
            System.out.println("perdants:"+model.getPerdants());
            System.out.println("joueurs:"+model.getJoueurs());
            if(model.getGagneurs().size()==model.getJoueurs().size()-1 || model.getPerdants().size()==model.getJoueurs().size()-1 || nbTour>200){
                //print the q-table
                //((JoueurIA) (model.getJoueurCourant())).getQTable().print();
                    //System.out.println("----------nbTour:"+nbTour);

                System.out.println("----------nbTour:"+nbTour);
                gameOver();
                break;
                //
                
                ////eliminate all the players on the board
                //for(Joueur joueur : model.getJoueurs()){
                //    model.eliminerJoueur(joueur);
                //}
            }
        }
    }

    public String afficheGagnants(int x){
        // Chaine de caractere contenant le classement de la partie
        String res = "Classement de la partie : \n";

        for(int i = 0; i < x; i++){
            if(i == 0){
                res = res + "   " + ((i + 1) + "er ->  " + model.getGagneurs().get(i).getID() + "(Grand gagnant/e de la partie) \n");
            }else if(i == 1){
                res = res + "   " + ((i + 1) + "nd ->  " + model.getGagneurs().get(i).getID() + "\n");
            }else{
                res = res + "   " + ((i + 1) + "rd -> " + model.getGagneurs().get(i) + "\n");
            }
        }

        return res;
    }

    public void gameOver(){
        if(!model.isAiTraining()){
            //print debug info
            System.out.println("debugggg=============");
            //game over donc affichage d'un message avec le classement des joueurs
            String s = "Classement de la partie : ";
            int i = JOptionPane.showOptionDialog(null, 
                s + afficheGagnants(model.getGagneurs().size()),
                "Partie terminée", 
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, 
                new String[]{"Nouvelle partie", "Aller au menu principal", "Quitter"}, null
            );
                
                if (i == JOptionPane.YES_OPTION){
                    // Relance la fiche de choix de map
                }else if(i == JOptionPane.NO_OPTION){
                    // retour au menu principal
                    dispose();
                    (new NiwaWindow()).run();
                }else if(i == JOptionPane.CANCEL_OPTION) {
                    // quitter
                    System.exit(0);
            };}

        model.gameOver();
        System.out.println("Game over!");//debug
        gridTuile.clearAllCircles();
        dispose();
    }
    
    /**
     * draw circle around the possible destinations of the legal actions
     * @param legalActions
     */
    private void drawPossibleActionDestinations(ArrayList<Action> legalActions) {
        gridTuile.circlesToDraw.clear();
        for (Action action : legalActions) {
            gridTuile.addCircle(action.getMoveDirection());
        }
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
        
    }


    public void lancer(){
        if(model.getMapEtat().equals(MapEtat.MANUEL)){
            System.out.println("mapSettings: "+model.getMapEtat());
            creerPlateau();
        }
        jouer();
        dispose();
        //TODO: quitter le jeu (fermer la fen锚tre etc...)
    }

    public void afficherInstruction(String str){
        System.out.println(str);//debug
        //TODO
        str="<html> <div>"+str+"</div></html>";
        tableauDeBord.setEtapeCourante(str);
    }
    
    public ArrayList<Coordonnee> afficherPossibleTuilePosition() {
        ArrayList<Coordonnee> locationsPossibles = model.getPlateau().canPlaceLocations(model.getTuileCourant().getLocationInGridTuile());
        return locationsPossibles;
    }

    public ArrayList<Coordonnee> afficherPossibleDestination() {
        ArrayList<Coordonnee> locationsPossibles = model.getPlateau().canMoveLocations(model.getPionCourant());
        return locationsPossibles;
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
        else{
            if(dx > 0 && ((x+dx-2) * 2.5*TuileGraphique.radius) + GridTuile.dx > gridTuile.getPreferredSize().getWidth()){
                gridTuile.glisserVersByCran(-dx, dy);
            }
            else if(dx < 0 && ((x+dx+2) * 2.5*TuileGraphique.radius) + gridTuile.getPreferredSize().getWidth() + GridTuile.dx < 0){
                gridTuile.glisserVersByCran(-dx, dy);
            }
            else if(dy < 0 && ((y+dy+2) * 3*TuileGraphique.radius) + gridTuile.getPreferredSize().getHeight() - GridTuile.dy < 0){
                gridTuile.glisserVersByCran(dx, -dy);
            }
            else if(dy > 0 && ((y+dy-2) * 3*TuileGraphique.radius) - GridTuile.dy > gridTuile.getPreferredSize().getHeight()){
                gridTuile.glisserVersByCran(dx, -dy);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(model.getJoueurCourant() instanceof JoueurHumain){
            switch (e.getKeyChar()) {
                case 'r'://placer l'autre tuile avec temple
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        if(!(model.getTuileCourant() instanceof TuileTemple) && model.getSacTemples().size()>0){
                            model.getSac().add(model.getTuileCourant());
                            Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                            model.getPlateau().removeTuileBrutForce(coordonnee);                            
                            
                            model.setTuileCourante(model.popTemple());
                            model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                        }
                        else{
                            Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                            model.getSacTemples().add((TuileTemple) model.getTuileCourant());
                            model.getPlateau().removeTuileBrutForce(coordonnee);

                            model.setTuileCourante(model.piocher());
                            model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                        }
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
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
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                        
                        Tuile tuileTmp = model.getTuileCourant();
                        //sens horaire inverse
                        //-1 mod 3 = 2 (fois)
                        tuileTmp.rotate();
                        tuileTmp.rotate();
                        model.setTuileCourante(tuileTmp);
                        model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                    }
                    else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        glisserTuile(model.getTuileCourant(), -1, 0);
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
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
                    }
                    else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                        Coordonnee coordonnee = model.getTuileCourant().getLocationInGridTuile();
                        model.getPlateau().removeTuileBrutForce(coordonnee);
                        
                        Tuile tuileTmp = model.getTuileCourant();
                        tuileTmp.rotate();
                        model.setTuileCourante(tuileTmp);
                        model.getPlateau().placeTuileBrutForce( model.getTuileCourant(), coordonnee );
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                    }
                    else if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        glisserTuile(model.getTuileCourant(), 1, 0);
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                    }
                    else if(model.getJeuEtat()==JeuEtat.PLACING_PION || 
                    model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                        indexChoisi = (indexChoisi + 1) % locationsPossibles.size();
                    }
                    break;
                case 'd':   // bas
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        glisserTuile(model.getTuileCourant(), 0, -1);
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                    }
                    break;
                case 'e':   // haut
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        glisserTuile(model.getTuileCourant(), 0, 1);
                        SwingUtilities.invokeLater(() -> {
                            repaint();
                        });
                        
                    }
                    break;
                case ' ':   // verifier le choix
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_TUILE_LOCATION){
                        //TODO:programmez ici pour effacer tous les cercles
                        //TODO: renouveler la vue des pions
                        model.getPlateau().removeTuileBrutForce(model.getTuileCourant().getLocationInGridTuile());      // On enlève la tuile courante du plateau
                        if(model.getPlateau().placeTuileContraint(model.getTuileCourant(), model.getTuileCourant().getLocationInGridTuile())){  // Et on tente de la replacer, mais avec les contraintes
                            model.setJeuEtat(JeuEtat.ROTATING_TUILE);                                                                           // Si la tuile se place, on passe à la rotation
                            afficherInstruction("utilisez les boutons pour la rotation de la tuile<br>Tappez SPACE pour confirmer");
                            tableauDeBord.boutonRotationAntiHoraire.setEnabled(true);
                            tableauDeBord.boutonRotationHoraire.setEnabled(true);
                        }
                        else{         
                            gridTuile.vibrate();                                                                                                          // Sinon, on reste sur le deplacement de tuile
                            model.getPlateau().placeTuileForce(model.getTuileCourant(),model.getTuileCourant().getLocationInGridTuile());
                            tableauDeBord.boutonRotationAntiHoraire.setEnabled(false);
                            tableauDeBord.boutonRotationHoraire.setEnabled(false);
                        }
                    }
                    else if(model.getJeuEtat()==JeuEtat.ROTATING_TUILE){
                        //TODO:programmez ici pour effacer tous les cercles
                        model.setJeuEtat(JeuEtat.CONTINUE);
                        //afficherInstruction("Tappez 鈫鈫pour chosir un pion à déplacer. Tappez SPACE pour confirmer.");
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
                        gridTuile.viewModeChanged(false);
                        break;
                    }
                    //sinon entrer la mode
                    model.setDernierEtat(model.getJeuEtat());
                    model.setJeuEtat(JeuEtat.CHANGING_VIEW);
                    gridTuile.viewModeChanged(true);
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

    @Override
    public void run() {
        this.pack();
        this.setVisible(true);
        this.setExtendedState(MAXIMIZED_BOTH);

        this.lancer();
    }

    public void start(){
        if(t == null){
            t = new Thread(this, threadName);
            t.start();
        }
    }


///scrollbar du plateau
class CustomScrollBarUI extends BasicScrollBarUI{
    CustomScrollBarUI(){
      
}
@Override
protected void configureScrollBarColors() {
 
    super.thumbColor = Color.BLACK;
    super.trackColor = new Color(61, 58, 58);

            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
            
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

    public static void main(String[] args) throws IOException {

        //System.out.println("from main: mapsetting: "+model.getMapEtat());

        //test 1:  rounds of 2 AI players
        for(int i=0; i<1000000; i++){
            System.out.println("-------------round "+i+"----------------");
            //Jeu model =  new Jeu(1, 1, MapEtat.MAP1_2P,10);
            Jeu model =  new Jeu(0, 2, MapEtat.MAP1_2P, 10);
            InterfaceDeJeu jeuVue = new InterfaceDeJeu(model);
            if(model.getMapEtat().equals(MapEtat.MANUEL)){
                System.out.println("mapSettings: "+model.getMapEtat());
                jeuVue.creerPlateau();
            }
            jeuVue.run();
        }
        

        /*
        //test 2: 1 joueur humain vs 1 joueur IA
        Jeu model =  new Jeu(1, 1, MapEtat.MAP1_2P);
        InterfaceDeJeu jeuVue = new InterfaceDeJeu(model);
        jeuVue.lancer();
        */

        /*
        //test 3: 2 joueurs humains
        //Jeu model =  new Jeu(2, 0, MapEtat.MAP1_2P, 10);
        //InterfaceDeJeu jeuVue = new InterfaceDeJeu(model);
        //jeuVue.start();

        ////test 4: 2 joueurs humains  ( map manuel)
        //Jeu model =  new Jeu(2, 0, MapEtat.MANUEL, 15);
        //InterfaceDeJeu jeuVue = new InterfaceDeJeu(model);
        //jeuVue.start();
        */
        
    }

}