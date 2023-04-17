package main.java.gui;

import java.util.ArrayList;

import main.java.model.Action;
import main.java.model.Jeu;
import main.java.model.JeuEtat;
import main.java.model.JoueurIA;
import main.java.model.MapEtat;
import main.java.model.State;

public class EntrainementIA implements Runnable {

    public Thread t;
    public static final String threadName = "Thread_EIA";

    protected Jeu model;
    protected int nb_rounds;
    protected int indexChoisi=0;

    public EntrainementIA(int nb_rounds){
        this.nb_rounds = nb_rounds;
    };

    public void lancerEntrainement(){

        for (int round = 0; round < nb_rounds; round++){
            
            System.out.println("-------------round "+round+"----------------");
            
            model = new Jeu(0, 2, MapEtat.MAP1_2P, 0);
            model.setJeuEtat(JeuEtat.CONTINUE);

            while(model.getJeuEtat()!=JeuEtat.GAME_OVER && model.getJeuEtat() != JeuEtat.GAME_INTERRUPT){
                
                //for(int i=0; i<model.getJoueurs().size(); i++) System.out.println("affiche joueurs:"+model.getJoueurs().get(i));
                for(int i=0; i<model.getJoueurs().size(); i++){
                    //controleur.rotationTmp=0;
                    model.setJoueurCourant(model.getJoueurs().get(i));
                    System.out.println(model.getJoueurCourant());//debug
                    

                    if(model.getJoueurCourant() instanceof JoueurIA){
                        //slow down the speed of the AI player
                        try {
                            Thread.sleep(100); 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        
                        
                        // create a mapping of the current state
                        State currentState = new State(model);
                        // get the legal actions for the current state
                        ArrayList<Action> legalActions = ((JoueurIA) model.getJoueurCourant()).getLegalActions(model);
                        if(legalActions.isEmpty()){
                            System.out.println("No legal actions for the current state, the current player loses the game.");
                            model.setGagneur(model.getJoueurs().get(0));//must be 2 players
                            break;
                        }
                        // AI player chooses an action
                        Action action = ((JoueurIA) model.getJoueurCourant()).chooseAction(model, currentState, legalActions);
                        
                        // apply the action
                        State nextState = currentState.getNextState(action);
                        // If the current player is an AI player, learn from the experience
                        ((JoueurIA) model.getJoueurCourant()).learn(model, currentState, action, nextState);

                        //update the model
                        nextState.updateGame(model);
                    }
                }
                if(model.getGagneurs().size()==model.getJoueurs().size()-1){
                    gameOver();
                }
            }
        }
    }

    public void gameOver(){
        model.gameOver();
        System.out.println("Game over!");//debug
    }


    @Override
    public void run() {
        this.lancerEntrainement();
    }

    public void start(){
        if(t == null){
            t = new Thread(this,threadName);
            t.start();
        }
    }

    public static void main(String[] args) {  
        EntrainementIA training = new EntrainementIA(2);
        training.start();
    }
    
}
