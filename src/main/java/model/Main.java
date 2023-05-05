package main.java.model;

public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu(2, 0, MapEtat.MAP1_2P,5);
        Pion pVirtual=jeu.getJoueurs().get(0).getPions().get(0);
        pVirtual.clear();

        System.out.println("strReal: " + jeu.getJoueurs().get(0).getPions().get(0));
        System.out.println("strVirtual: " + pVirtual);
    }
}
