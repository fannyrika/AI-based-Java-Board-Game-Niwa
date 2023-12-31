package main.java.gui;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;

import main.java.model.interfaces.ColorsSwitcher;
import main.java.model.interfaces.HexagoneAutour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import main.java.gui.TuileGraphique.Circle;
import main.java.model.*;

public class GridTuile extends JPanel implements KeyListener, MouseInputListener{

    public class CursorInfo extends Cursor {

        /**
         * Point pour connaitre l'emplacement du curseur sur l'écran
         */
        protected Point point;
        /**
         * Pion sur lequel le curseur pointe
         */
        protected Pion pion;
        /**
         * S'il y a une perle à donner au pion pointé, elle est ici
         */
        protected Couleurs perle;

        /**
         * Longueur des rectangles de couleurs
         */
        protected static final int lx = 30;
        /**
         * Hauteur des rectangles de couleurs
         */
        protected static final int ly = 20;
        /**
         * Taux de transparence lorsqu'on veut donner une perle au pion
         */
        protected static final int transparence = 50;
        /**
         * Taille de la croix lorsqu'on ne peut pas donner la perle au pion
         */
        protected static final int red_X = 15;

        public CursorInfo(int type, Point point, Pion pion) {
            super(type);
            this.point = point;
            this.pion = pion;
        }

        public CursorInfo(int type, Point point, Pion pion, Couleurs perle){
            this(type, point, pion);
            this.perle = perle;
        }


        public void draw(Graphics g, Point p){
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();
            Stroke oldStroke = g2d.getStroke();

            Pion pion_copy = new Pion(pion);

            if(perle != null){

                if(pion_copy.size() >= Jeu.NB_PEARL_MAX){

                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(p.x + 5, p.y, p.x + 5 + red_X, p.y - red_X);
                    g2d.drawLine(p.x + 5, p.y - red_X, p.x + 5 + red_X, p.y);

                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(p.x + 5, p.y, p.x + 5 + red_X, p.y - red_X);
                    g2d.drawLine(p.x + 5, p.y - red_X, p.x + 5 + red_X, p.y);


                    g2d.setColor(oldColor);
                    g2d.setStroke(oldStroke);
                    return;
                }

                Color c = ColorsSwitcher.toColor(perle);

                g2d.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),transparence));
                g2d.fillRect(p.x + 5, p.y - (pion_copy.size()+1)*ly, lx, ly);
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(0, 0, 0, transparence));
                g2d.drawRect(p.x + 5, p.y - (pion_copy.size()+1)*ly, lx, ly);
                g2d.setStroke(oldStroke);
            }

            while (!pion_copy.isEmpty()) {
                g2d.setColor(ColorsSwitcher.toColor(pion_copy.peek()));
                g2d.fillRect(p.x + 5, p.y - pion_copy.size()*ly, lx, ly);

                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(Color.BLACK);
                g2d.drawRect(p.x + 5, p.y - pion_copy.size()*ly, lx, ly);
                g2d.setStroke(oldStroke);
            
                pion_copy.pop();
            }

            g2d.setColor(oldColor);
        }
    }

    public class CursorViewMode extends Cursor {

        protected Point point;
        protected boolean zoomMode;

        public CursorViewMode(int type, Point point, boolean zoomMode) {
            super(type);
            this.point = point;
            this.zoomMode = zoomMode;
        }

        public void draw(Graphics g, Point p){
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();

            g2d.setColor(Color.GRAY);

            if(zoomMode){
                g2d.fillRect(point.x+5, point.y-20, 95,20);
                g2d.setColor(Color.BLACK);
                g2d.drawString("MOVE & ZOOM", point.x+10, point.y-5);
            }
            else{
                g2d.fillRect(point.x+5, point.y-20, 55,20);
                g2d.setColor(Color.BLACK);
                g2d.drawString("IN GAME", point.x+10, point.y-5);
            }

            g2d.setColor(oldColor);
        }
    }
    /**
     * Le model sur lequel le graphique se base
     */
    protected Jeu model;
    /**
     * Certainement temporaire, représente la taille de l'écran
     */
    protected static Dimension screen = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/InterfaceDeJeu.TABLEAU_DE_BORD_RATIO),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/InterfaceDeJeu.TABLEAU_DE_BORD_RATIO);

    /**
     * Décalage des tuiles sur le graphique en x
     */
    protected static int dx = 0;
    /**
     * Décalage des tuiles sur le graphique en y
     */
    protected static int dy = 0;

    /**
     * Représente la vitesse à laquelle le zoom/dezoom est fait (+ on l'augmente, plus le zoom/dezoom est rapide)
     */
    protected static final int DISTANCE_ZOOM = 10;

    protected ArrayList<TuileGraphique> tuilesGraphique = new ArrayList<TuileGraphique>();
    protected static HashMap<Coordonnee,Circle> allCircles = new HashMap<Coordonnee,Circle>();
    protected ArrayList<Circle> circlesToDraw = new ArrayList<Circle>(); 

    transient Timer vibe = new Timer();
    transient Timer viewMode = new Timer();

    /**
     * Constructeur
     * @param m
     */
    public GridTuile(Jeu m){
        this.model=m;
        this.setPreferredSize(screen);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(new ComponentAdapter() {                  // Ce bout de code sert à remettre le plateau au centre lorsqu'on resize la fenetre
            public void componentResized(ComponentEvent componentEvent) {
                GridTuile.dx = 0;
                GridTuile.dy = 0;
                screen = GridTuile.this.getSize();
                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
                
            }
        });
    }

    @Override
    @SuppressWarnings("all")
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D mainGraphics = (Graphics2D)g;

        tuilesGraphique.clear();
        allCircles.clear();
        super.paintComponent(g);
    
        Graphics2D g2d = (Graphics2D) g.create();
        GradientPaint gradient = new GradientPaint(0, 0,new Color(74, 174, 255), 
                                                getWidth(), 0,new Color(255, 143, 169));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        // On parcours toutes les tuiles sur le plateau, pour ainsi les dessiner
        HashMap<Coordonnee,Tuile> tuileMapCopy = (HashMap<Coordonnee, Tuile>) model.getPlateau().getGridTuile().clone();
        for(Tuile t : tuileMapCopy.values()){
            int x = t.getLocationInGridTuile().getX();
            int y = t.getLocationInGridTuile().getY();
            TuileGraphique tuileG;
            if(t instanceof TuileTemple){
                tuileG = new TuileTempleGraphique((TuileTemple) t, x,y,((TuileTemple)t).getProprietaire());
            }
            else{
                tuileG = new TuileGraphique(t, x,y);
            }

            for (Circle c : tuileG.cercles) {
                if(!this.allCircles.containsKey(c.getLocationInGridHexagone())){
                    this.allCircles.put(c.getLocationInGridHexagone(), c);
                }
            }
            tuileG.drawTile(mainGraphics);
            tuilesGraphique.add(tuileG);
        }

        // On parcours tous les pions sur le plateau, pour ainsi les dessiner
        HashMap<Coordonnee,Pion> pionMapCopy = (HashMap<Coordonnee, Pion>) model.getPlateau().getGridPion().clone();
        for (Pion p : pionMapCopy.values()){
            if(p == null){
                System.out.println("Pion null");
                continue;
            }
            if(p!=null){
                PionGraphique pionG = new PionGraphique(p);
                pionG.draw(mainGraphics);}
            else
                continue;
            
        }

        for (Circle c : circlesToDraw) {
            if(c != null){
                c.draw(mainGraphics);
            }
        }

        if(getCursor() instanceof CursorInfo){
            CursorInfo cursor = (CursorInfo) getCursor();
            cursor.draw(mainGraphics, cursor.point);
        }
        else if(getCursor() instanceof CursorViewMode){
            CursorViewMode cursor = (CursorViewMode) getCursor();
            cursor.draw(mainGraphics, cursor.point);
        }

        // Pour le debogage
        if(StockageSettings.SHOW_COORDS){
            for (Circle c : allCircles.values()) {
                mainGraphics.drawString("("+c.locationInGridHexagone.getX()+","+c.locationInGridHexagone.getY()+")", c.x, c.y);
            }
        }
    }

    /**
     * Méthode permettant de déplacer graphiquement le plateau d'un certain x et y
     * @param dx -> Déplacemenent en x
     * @param dy -> Déplacemenent en y
     */
    public void glisserVers(int dx, int dy){
        GridTuile.dx += dx;
        GridTuile.dy -= dy;
        for (Circle c : circlesToDraw) {
            c.x += dx;
            c.y -= dy;
        }
    }

    public void glisserVersByCran(int dx, int dy){
        glisserVers((int)(2.5*TuileGraphique.radius)*dx, (int)(3*TuileGraphique.radius)*dy);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(model.getJeuEtat()==JeuEtat.CHANGING_VIEW){
        switch (e.getKeyChar()) {
            case 's':   // gauche
                glisserVersByCran(1, 0);
                break;
            case 'f':   // droite
                glisserVersByCran(-1, 0);
                break;
            case 'd':   // bas
                glisserVersByCran(0, 1);
                break;
            case 'e':   // haut
                glisserVersByCran(0, -1);
                break;
            case '+':   // zoom
                TuileGraphique.zoom(DISTANCE_ZOOM);
                break;
            case '_':   // dezoom
                TuileGraphique.zoom(-DISTANCE_ZOOM);
            default:
                break;
        }}
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
        
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if(model.getJoueurCourant() instanceof JoueurHumain){
            Point clic = e.getPoint();
            for (TuileGraphique t : tuilesGraphique) {  
                for (Circle c : t.cercles) {
                    if(c.contains(clic)){

                        System.out.println(c.getLocationInGridHexagone());
                        Pion pionChoisi=model.getPlateau().getGridPion().get(c.getLocationInGridHexagone());

                        if(model.getJeuEtat()==JeuEtat.PLACING_START_PION){
                            if(circlesToDraw.stream().anyMatch(v -> {return v.locationInGridHexagone.equals(c.locationInGridHexagone);})){
                                for(Coordonnee coordonnee : HexagoneAutour.get(c.locationInGridHexagone)){
                                    Hexagone h = model.getPlateau().getGridHexagone().get(coordonnee);
                                    if(h instanceof HexagoneCentral){
                                        Tuile temple = model.getPlateau().getGridTuile().get(((HexagoneCentral) h).getLocationInGridTuile());
                                        if(temple instanceof TuileTemple){
                                            Joueur j = ((TuileTemple) temple).getProprietaire();
                                            for (Pion p : j.getPions()){
                                                if (!p.isPlaced()) {
                                                    model.getPlateau().placeStartPion(p, c.locationInGridHexagone);
                                                    showStartPionLocations();
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                            if(pionChoisi!=null && pionChoisi.getProprietaire()==model.getJoueurCourant()){
                                model.setPionCourant(pionChoisi);
                                circlesToDraw.clear();
                                ArrayList<Coordonnee> locationsPossible = model.getPlateau().canMoveLocations(model.getPionCourant());
                                // si il y a des locations possibles a placer
                                if(locationsPossible.size()>0){
                                    for (Coordonnee coordonnee : locationsPossible) {
                                        Circle circle = allCircles.get(coordonnee);
                                        if(!pionChoisi.isEmpty()){
                                            circle.setColor(ColorsSwitcher.toColor(pionChoisi.peek()));
                                        }
                                        else{
                                            circle.setColor(Color.BLACK);
                                        }
                                        circlesToDraw.add(circle);
                                    }
                                    
                                    model.setJeuEtat(JeuEtat.CONTINUE);
                                }
                                SwingUtilities.invokeLater(() -> {
                                    repaint();
                                });
                            }
                        }
                        else if(model.getJeuEtat()==JeuEtat.PLACING_PION){

                            // Cas ou le joueur veut changer son choix du pion
                            if(model.getPlateau().getGridPion().containsKey(c.getLocationInGridHexagone()) && model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()).getProprietaire() == model.getJoueurCourant()){
                                model.setPionCourant(pionChoisi);
                                circlesToDraw.clear();
                                ArrayList<Coordonnee> locationsPossible = model.getPlateau().canMoveLocations(model.getPionCourant());
                                // si il y a des locations possibles a placer
                                if(locationsPossible.size()>0){
                                    for (Coordonnee coordonnee : locationsPossible) {
                                        Circle circle = allCircles.get(coordonnee);
                                        if(!pionChoisi.isEmpty()){
                                            circle.setColor(ColorsSwitcher.toColor(pionChoisi.peek()));
                                        }
                                        else{
                                            circle.setColor(Color.BLACK);
                                        }
                                        circlesToDraw.add(circle);
                                    }
                                }
                                SwingUtilities.invokeLater(() -> {
                                    repaint();
                                });
                            }
                            else{
                                Coordonnee depart = model.getPionCourant().getLocation();
                                ArrayList<Coordonnee> locationsPossible = model.getPlateau().canMoveLocations(model.getPionCourant());
                                if(locationsPossible.contains(c.getLocationInGridHexagone())){
                                    model.getPlateau().getGridPion().remove(depart);
                                    model.getPlateau().placerPionForce(model.getPionCourant(), c.getLocationInGridHexagone());
                                    model.getPionCourant().setLocation(c.getLocationInGridHexagone());
                                    circlesToDraw.clear();
                                    model.verifyIfPlayerBlocked();
                                    SwingUtilities.invokeLater(() -> {
                                        repaint();
                                    });
                                    
    
                                    //Après que le pion a été déplacer, on check si le joueur courant a gagne
                                    System.out.println("checking if player won");
                                    if(model.aGagne(model.getJoueurCourant())){
                                        model.setGagneur(model.getJoueurCourant());
                                    }
                                    model.setJeuEtat(JeuEtat.CONTINUE);   
                                }
                            }
                        }
                        else if(model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                            if(pionChoisi!=null){
                                if(pionChoisi.size()<Jeu.NB_PEARL_MAX){
                                    if(model.getPionCourant().passPerleTo(pionChoisi)){
                                        //renouveler la vue des 2 pions
                                        model.getPlateau().getGridPion().remove(pionChoisi.getLocation());
                                        model.getPlateau().placerPionForce(pionChoisi,pionChoisi.getLocation());
            
                                        model.getPlateau().getGridPion().remove(model.getPionCourant().getLocation());
                                        model.getPlateau().placerPionForce(model.getPionCourant(), model.getPionCourant().getLocation());

                                        if(getCursor() instanceof CursorInfo){
                                            ((CursorInfo)getCursor()).perle = null;
                                        }

                                        SwingUtilities.invokeLater(() -> {
                                            repaint();
                                        });
                                        
            
                                        model.setJeuEtat(JeuEtat.CONTINUE);
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

        /**
         * Méthode qui montrera les endroits où on peut poser les pions de départ (c'est-à-dire les emplacements autour des temples)
         */
    public void showStartPionLocations(){
        circlesToDraw.clear();
        ArrayList<Coordonnee> autourTemples = new ArrayList<Coordonnee>();
        for (Joueur j : model.getJoueurs()) {
            if(!j.placedAllPions()){
                TuileTemple temple = j.getTemple();
                ArrayList<Coordonnee> hexagones = HexagoneAutour.getList(temple.getLocationInGridHexagone());
                for (Coordonnee c : hexagones) {
                    int index = 0;
                    Pion p = j.getPions().get(index);
                    while (p.isPlaced()) {
                        index++;
                        p = j.getPions().get(index);
                    }
                    allCircles.get(c).setColor(ColorsSwitcher.toColor(p.peek()));
                }
                autourTemples.addAll(hexagones);
            }
        }
        autourTemples.removeAll(model.getPlateau().getGridPion().keySet());
        for (Coordonnee coordonnee : autourTemples) {
            circlesToDraw.add(allCircles.get(coordonnee));
        }
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
        
    }    

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
        Point position = e.getPoint();
        for (TuileGraphique t : tuilesGraphique) {
            for (Circle c : t.cercles) {
                if(c.contains(position)){
                    if(model.getJeuEtat()==JeuEtat.PLACING_START_PION){
                        showStartPionLocations();
                        for (Circle circle : circlesToDraw) {
                            if(circle != null){
                                if(circle.locationInGridHexagone.equals(c.locationInGridHexagone)){
                                    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    return;
                                }
                            }
                        }
                    }
                    if(model.getJeuEtat()==JeuEtat.CHOOSING_PION){
                        if(model.getPlateau().getGridPion().containsKey(c.getLocationInGridHexagone())){
                            if(model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()).getProprietaire() == model.getJoueurCourant()){
                                this.setCursor(new CursorInfo(Cursor.HAND_CURSOR, position, model.getPlateau().getGridPion().get(c.getLocationInGridHexagone())));
                            }
                        }
                    }
                    else if(model.getJeuEtat()==JeuEtat.PLACING_PION){
                        if(model.getPlateau().getGridPion().containsKey(c.getLocationInGridHexagone()) && model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()).getProprietaire() == model.getJoueurCourant()){
                            this.setCursor(new CursorInfo(Cursor.HAND_CURSOR, position, model.getPlateau().getGridPion().get(c.getLocationInGridHexagone())));
                        }
                        else{
                            ArrayList<Coordonnee> possibilites = model.getPlateau().canMoveLocations(model.getPionCourant());
                            if(possibilites.stream().anyMatch(v -> {return c.getLocationInGridHexagone().equals(v);})){
                                this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                        }
                    }
                    else if(model.getJeuEtat()==JeuEtat.CHOOSING_PEARL_DESTINATION){
                        if(model.getPlateau().getGridPion().containsKey(c.getLocationInGridHexagone()) && model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()) != model.getPionCourant() && model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()).getProprietaire() == model.getPionCourant().getProprietaire()){
                            this.setCursor(new CursorInfo(Cursor.HAND_CURSOR, position, model.getPlateau().getGridPion().get(c.getLocationInGridHexagone()),model.getPionCourant().peek()));
                        }
                    }
                    return;
                }
            }
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Méthode qui dessine les cercles
     * @param moveDirection
     */
	public void addCircle(Coordonnee moveDirection) {
        circlesToDraw.add(allCircles.get(moveDirection));
	}

    /**
     * method: clear the arraylist :allCircles
     */
    public void clearAllCircles(){
        allCircles.clear();
    }
    
    public void vibrate(){
        vibe.schedule(new TimerTask() {

            int time = 31;
            int vibrationPower = 5;

            @Override
            public void run() {
                if(time % 2 == 0){

                    glisserVers(vibrationPower, 0);
                    repaint();
                }
                else if(time % 2 == 1){
                    glisserVers(-vibrationPower, 0);
                    repaint();
                }
                if(time == 0){
                    cancel();
                }
                time--;
            }
            
        },5,15);
    }

    public void viewModeChanged(boolean zoomMode){
        vibe.schedule(new TimerTask() {

            int time = 31;
            Cursor old = getCursor();

            @Override
            public void run() {

                setCursor(new CursorViewMode(old.getType(),getMousePosition(),zoomMode));
                repaint();

                if(time == 0){
                    cancel();
                    setCursor(old);
                    repaint();
                }
                time--;
            }
            
        },5,15);
    }
}
