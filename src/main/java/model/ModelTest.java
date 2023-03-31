package main.java.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import main.java.model.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private JoueurIA joueurIA;
    private State state;
    private Action action;
    private Pion pion;
    private Jeu jeu;
    
    @BeforeEach
    public void init() {
        joueurIA = new JoueurIA("IA", 0.1, 0.2, 0.9);
        jeu = new Jeu(0,2,MapEtat.MAP1_2P);
        state = new State(jeu);
        pion = new Pion(joueurIA);
        pion.setLocation(new Coordonnee(0, 0));
        action = new Action(pion, new Coordonnee(1, 0), null);
    }
    
    @Test
    public void testJoueurIAChooseAction() {
        Action chosenAction = joueurIA.chooseAction(jeu, state);
        assertNotNull(chosenAction, "Action should not be null");
    }

    @Test
    public void testJoueurIALearn() {
        double initialQValue = joueurIA.getQValue(state, action);
        joueurIA.learn(jeu, state, action, state.getNextState(action), 1.0);
        double newQValue = joueurIA.getQValue(state, action);
        assertNotEquals(initialQValue, newQValue, "Q-value should be updated after learning");
    }

    @Test
    public void testStateDeepCopy() {
        State copiedState = new State(state);
        assertEquals(state, copiedState, "States should be equal");
        assertNotSame(state, copiedState, "States should not be the same object");
    }

    @Test
    public void testStateGetNextState() {
        State nextState = state.getNextState(action);
        assertNotEquals(state, nextState, "Next state should be different");
    }

    @Test
    public void testActionEquals() {
        Action anotherAction = new Action(pion, new Coordonnee(1, 0), null);
        assertEquals(action, anotherAction, "Actions should be equal");
    }

    @Test
    public void testPionEquals() {
        Pion anotherPion = new Pion(joueurIA);
        anotherPion.setLocation(new Coordonnee(0, 0));
        assertNotEquals(pion, anotherPion, "Pions should not be equal");
        
        Pion deepCopiedPion = new Pion(pion);
        assertEquals(pion, deepCopiedPion, "Pions should be equal");
        assertNotSame(pion, deepCopiedPion, "Pions should not be the same object");
    }

    @Test
    public void testPionPassPerleTo() {
        Pion targetPion = new Pion(joueurIA);
        pion.add(Couleurs.ROUGE);
        assertTrue(pion.passPerleTo(targetPion), "Perle should be passed");
        assertEquals(0, pion.size(), "Pion should not have any perle left");
        assertEquals(1, targetPion.size(), "Target pion should have one perle");
    }

    public static void main(String[] args) {
        ModelTest test = new ModelTest();
        test.init();
        test.testJoueurIAChooseAction();
        test.testJoueurIALearn();
        test.testStateDeepCopy();
        test.testStateGetNextState();
        test.testActionEquals();
        test.testPionEquals();
        test.testPionPassPerleTo();
    }
}

