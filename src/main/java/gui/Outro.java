package main.java.gui;

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
