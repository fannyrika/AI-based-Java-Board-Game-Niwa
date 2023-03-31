package main.java.model;

import java.util.ArrayList;

/**
 * Represents the state of the game: store all the info of the pions
 */
public class State {
    //representes the state of the game: store all the info of the pions
    protected ArrayList<Pion> pions= new ArrayList<Pion>();

    public State(Jeu jeu) {
        pions.clear();
        for (Joueur joueur : jeu.getJoueurs()) {
            for (Pion pion : joueur.getPions()) {
                pions.add(new Pion(pion));
            }
        }
    }

    //deep copy constructor
    public State(State state) {
        this.pions = new ArrayList<Pion>(state.getPions());
    }

    /**
     * return the next state of the game after an action
     * @param action
     * @return nextState
     */
    public State getNextState(Action action) {
        System.out.println("getNextState: " + action);
        State nextState = new State(this);
        Pion targetPion = null;
        if (action.getTargetPion() != null) {
            for (Pion pion : nextState.getPions()) {
                if (pion.equals(action.getTargetPion())) {
                    targetPion = pion;
                }
            }
        }
        for (Pion pion : nextState.getPions()) {
            if (pion.equals(action.getSelectedPion())) {
                pion.setLocation(action.getMoveDirection());
                if (targetPion != null) {
                    pion.passPerleTo(targetPion);
                }
            }
        }
    
        System.out.println("nextState: " + nextState);
        return nextState;
    }

    /**
     * update the state of the game
     * @return
     */
    public void updateGame(Jeu jeu) {
        Plateau plateau=jeu.getPlateau();
        //intialize the pions based on the "state"
        for (int i = 0; i < jeu.getJoueurCourant().getPions().size(); i++) {
            //print the 2 index
            //System.out.println("index in jeu: "+i);
            System.out.println("index in state: "+(i+3*jeu.getJoueurCourant().getID()));
            Pion pionReal=jeu.getJoueurCourant().getPions().get(i);
            Pion pionVirtual=pions.get(i+3*jeu.getJoueurCourant().getID());
            System.out.println("pionReal: "+pionReal);
            System.out.println("pionVirtual: "+pionVirtual);
            //throw exception if pionReal and pionVirtual are not the same
            if(pionReal.getID()!=pionVirtual.getID()){
                //print the 2 id
                System.out.println("pionReal id: "+pionReal.getID());
                System.out.println("pionVirtual id: "+pionVirtual.getID());
                throw new IllegalArgumentException("pionReal and pionVirtual are not the same");
            }

            //update the location
            plateau.movePionTo(pionReal, pionVirtual.getLocation());
            //update the pearls
            pionReal.clear();
            pionReal.addAll(pionVirtual);
        }
    }

    //getter
    public ArrayList<Pion> getPions() {
        return pions;
    }

    //setter
    public void setPions(ArrayList<Pion> pions) {
        this.pions = pions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        State other = (State) obj;
        for(int i=0; i<pions.size(); i++){
            if(!pions.get(i).equals(other.getPions().get(i))){
                return false;
            }
        }
        return true;
    }

}
