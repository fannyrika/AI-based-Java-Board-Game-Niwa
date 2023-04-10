package main.java.model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.InvalidClassException;

import java.util.ArrayList;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;

public class JoueurIA extends Joueur{

    private double epsilon;
    private double alpha;
    private double gamma;
    private qTable QTable = new qTable();
    /**
     * distance towards the temple of the competitor
     */
    private double distance=0.0;
    
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
        loadQTable();
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
    public void learn(Jeu jeu, State currentState, Action action, State nextState) {
        double reward=0;
        double maxNextQValue=0;
        double currentQValue = getQValue(currentState, action);

        //set the game to the next state in order to get the max next Q-value
        nextState.updateGame(jeu);
        ArrayList<Action> legalActions = getLegalActions(jeu);
        if(legalActions.isEmpty()) {
            System.out.println("No legal actions");
            reward = -100;
        }
        else{
            maxNextQValue = getMaxQValue(jeu, nextState, legalActions);
            // get the reward from the next state
            reward = jeu.getReward();
            System.out.println("Reward: " + reward);
        }
        
        //reset the game to the current state
        currentState.updateGame(jeu);
    
        System.out.println("Current Q-value: " + currentQValue);
        System.out.println("Max next Q-value: " + maxNextQValue);
    
        double newQValue = currentQValue + alpha * (reward + gamma * maxNextQValue - currentQValue);
        setQValue(currentState, action, newQValue);
        System.out.println("New Q-value: " + newQValue);
    }

    //getter
    public double getQValue(State state, Action action) {
        if (!QTable.map.containsKey(state)) {
            QTable.map.put(state, new HashMap<>());
        }
        Map<Action, Double> actionQValues = QTable.map.get(state);
        if (!actionQValues.containsKey(action)) {
            actionQValues.put(action, 0.0);
        }
        return actionQValues.get(action);
    }

    public double getDistance() {
        return distance;
    }

    //setter
    public void setQValue(State state, Action action, double value) {
        if (!QTable.map.containsKey(state)) {
            QTable.map.put(state, new HashMap<>());
        }
        QTable.map.get(state).put(action, value);
    }

    public void setDistance(double minDistance) {
        this.distance = minDistance;
    }

    /**
     * get all the legal actions based on the current state of the game
     * precondition: the state is the current state of the game(Jeu)
     * @param jeu the current game
     * @param state
     * @param state the current state of the game
     * @return an ArrayList of legal actions
     */
    public ArrayList<Action> getLegalActions(Jeu jeu){
        ArrayList<Action> legalActions = new ArrayList<>();
        int nbBlockedPion = 0;

        // implement a method to find all valid actions based on the given Jeu instance
        // refer to the methods and properties of the Jeu class to implement this method

        for (Pion pionReal : jeu.getJoueurCourant().getPions()) {
            jeu.setPionCourant(pionReal);
            //case 1: pion is blocked
            if (jeu.getPlateau().canMoveLocations(jeu.getPionCourant())==null){
                System.out.println("Pion is blocked");
                nbBlockedPion++;
            }
            //case 2: pion can move
            else{
                int nbNullDirection = 0;
                for (Coordonnee direction : jeu.getPlateau().canMoveLocations(jeu.getPionCourant())) {
                    if(direction==null){
                        System.out.println("direction is null");
                        nbNullDirection++;
                        continue;
                    }
                    //case 2: pion with no pearl
                    if (pionReal.size()==0){
                        //System.out.println("Pion with no pearl");
                        legalActions.add(new Action(new Pion(pionReal), direction, null));
                    }
                    else{
                        //System.out.println("Pion with pearl");
                        for (Pion targetPion : jeu.getJoueurCourant().getPions()){
                            if (targetPion != null && !targetPion.equals(pionReal) && targetPion.size()<3) {
                                legalActions.add(new Action(new Pion(pionReal), direction, new Pion(targetPion)));
                            }
                        }
                    }
                }
                //case 3: pion can move but all directions are null
                if (nbNullDirection==jeu.getPlateau().canMoveLocations(jeu.getPionCourant()).size()){
                    System.out.println("Pion can move but all directions are null, bizarrrrrre");
                    nbBlockedPion++;
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
    private double getMaxQValue(Jeu jeu, State state, ArrayList<Action> legalActions) {
        
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

    //get qtable
    public qTable getQTable() {
        return QTable;
    }

    //save qtable
    public void saveQTable() {
        try {
            String filePath = "qtable"+id+".ser";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(QTable);
            out.close();
            fileOut.close();
            System.out.println("QTable saved to: " + filePath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //load qtable
    public void loadQTable() {
        String filePath = "qtable"+id+".ser";
        qTable qTable = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            qTable = (qTable) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("QTable loaded from: " + filePath);
            this.QTable=qTable;
        } catch (FileNotFoundException f) {
            System.out.println("QTable file not found, creating a new one.");
        } catch (EOFException e) {
            System.out.println("QTable file is empty, creating a new one.");
        } catch (InvalidClassException i) {
            System.out.println("QTable file contains an invalid class, creating a new one.");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("QTable class not found, creating a new one.");
            c.printStackTrace();
        }
    }

    public void printQTable() {
        this.QTable.print();
    }
}
