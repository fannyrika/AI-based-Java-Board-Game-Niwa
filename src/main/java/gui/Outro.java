package main.java.gui;
import java.awt.*;
import javax.swing.*;

import main.java.model.Joueur;
public class Outro extends Intro{
    Outro(){
        super();
        this.j.setVisible(false);
        titre.setText("Game Over");
    }
     public static void main(String[] args) {
        new Outro();
    }
}
