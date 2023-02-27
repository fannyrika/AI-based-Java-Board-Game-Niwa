package main.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.java.model.Jeu;

public class Launcher{
    private TestPlateau view;
    //private JeuDominoControleur controleur;
    //public JeuDomino modele;

    public Launcher(Jeu model) throws IOException {
        view = new TestPlateau(model);
        view.lancer();
    }

    public static void main ( String [] args ) throws IOException {
        
    }
}