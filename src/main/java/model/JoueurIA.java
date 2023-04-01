package main.java.model;

import java.util.ArrayList;
import java.util.Random;

import main.java.model.interfaces.HexagoneAutour;

import java.util.HashMap;
import java.util.Map;

public class JoueurIA extends Joueur {

    private double epsilon;
    private double alpha;
    private double gamma;
    private Map<State, Map<Action, Double>> qTable = new HashMap<>();
    
    /**
     * constructor
     * @param name
     * @param epsilon
     * @param alpha
     * @param gamma
     */
    public JoueurIA(String name, double epsilon, double alpha, double gamma) {
        super(name);
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
    }

    /**
     * choose an action based on the current state of the game
     * @param jeu the current game
     * @param currentState the current state of the game
     * @return an action
     */
    public Action chooseAction(Jeu jeu, State currentState, ArrayList<Action> legalActions) {
        System.out.println("Legal actions: " + legalActions);
    
        if (Math.random() < epsilon) {
            // Choose a random action with probability epsilon
            Action randomAction = legalActions.get(new Random().nextInt(legalActions.size()));
            System.out.println("Chose random action: " + randomAction);
            return randomAction;
        } else {
            // Choose the action with the highest Q-value with probability (1 - epsilon)
            Action bestAction = getBestAction(currentState, legalActions);
            System.out.println("Chose best action: " + bestAction);
            return bestAction;
        }
    }

    /**
     * learn from the experience
     * @param jeu
     * @param currentState
     * @param action
     * @param nextState
     * @param reward
     */
    public void learn(Jeu jeu, State currentState, Action action, State nextState, double reward) {
        double currentQValue = getQValue(currentState, action);
        double maxNextQValue = getMaxQValue(jeu, nextState);
    
        System.out.println("Current Q-value: " + currentQValue);
        System.out.println("Max next Q-value: " + maxNextQValue);
    
        double newQValue = currentQValue + alpha * (reward + gamma * maxNextQValue - currentQValue);
        setQValue(currentState, action, newQValue);
    }

    //getter
    public double getQValue(State state, Action action) {
        if (!qTable.containsKey(state)) {
            qTable.put(state, new HashMap<>());
        }
        Map<Action, Double> actionQValues = qTable.get(state);
        if (!actionQValues.containsKey(action)) {
            actionQValues.put(action, 0.0);
        }
        return actionQValues.get(action);
    }

    //setter
    public void setQValue(State state, Action action, double value) {
        if (!qTable.containsKey(state)) {
            qTable.put(state, new HashMap<>());
        }
        qTable.get(state).put(action, value);
    }

    /**
     * get all the legal actions based on the current state of the game
     * precondition: the state is the current state of the game(Jeu)
     * @param jeu the current game
     * @param state
     * @param state the current state of the game
     * @return an ArrayList of legal actions
     */
    public ArrayList<Action> getLegalActions(Jeu jeu, State state){
        ArrayList<Action> legalActions = new ArrayList<>();
        int nbBlockedPion = 0;

        // implement a method to find all valid actions based on the given Jeu instance
        // refer to the methods and properties of the Jeu class to implement this method

        for (Pion pionReal : jeu.getJoueurCourant().getPions()) {
            //case 1: pion is blocked
            if (jeu.getPlateau().canMoveLocations(jeu.getPionCourant())==null){
                System.out.println("Pion is blocked");
                nbBlockedPion++;
            }
            else{
                for (Coordonnee direction : jeu.getPlateau().canMoveLocations(jeu.getPionCourant())) {
                    //case 2: pion with no pearl
                    if (pionReal.size()==0){
                        System.out.println("Pion with no pearl");
                        legalActions.add(new Action(new Pion(pionReal), direction, null));
                    }
                    else{
                        System.out.println("Pion with pearl");
                        for (Pion targetPion : jeu.getJoueurCourant().getPions()){
                            if (targetPion != null && !targetPion.equals(pionReal) && targetPion.size()<3) {
                                legalActions.add(new Action(new Pion(pionReal), direction, new Pion(targetPion)));
                            }
                        }
                    }
                }
            }
        }
        //all pions are blocked
        if (nbBlockedPion==jeu.getJoueurCourant().getPions().size()){
            jeu.eliminerJoueur(jeu.getJoueurCourant());
        }
        return legalActions;
    }
    
    /**
     * get the best action based on the current state of the game
     * precondition: the state is the current state of the game(Jeu)
     * @param jeu the current game
     * @param state the current state of the game
     * @return the best action
     */
    private Action getBestAction(State jeu, ArrayList<Action> legalActions) {
        Action bestAction = null;
        double maxQValue = Double.NEGATIVE_INFINITY;

        for (Action action : legalActions) {
            double qValue = getQValue(jeu, action);
            if (qValue > maxQValue) {
                maxQValue = qValue;
                bestAction = action;
            }
        }

        return bestAction;
    }

    /**
     * get the maximum Q-value based on the current state of the game
     * precondition: the state is the current state of the game(Jeu)
     * @param jeu the current game
     * @param state the current state of the game
     * @return the maximum Q-value
     */
    private double getMaxQValue(Jeu jeu, State state) {
        ArrayList<Action> legalActions = getLegalActions(jeu, state);
        double maxQValue = Double.NEGATIVE_INFINITY;
    
        for (Action action : legalActions) {
            double qValue = getQValue(state, action);
            if (qValue > maxQValue) {
                System.out.println("qValue: " + qValue + " maxQValue: " + maxQValue);
                maxQValue = qValue;
            }
        }
    
        return maxQValue;
    }
}
