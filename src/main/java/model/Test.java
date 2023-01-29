package main.java.model;

public class Test {
    public static void main(String[] args) {
        Plateau p = new Plateau();
        Tuile t = new Tuile();
        
        p.placeTuileForce(t, new Coordonnee(0, 0));
        p.placeTuileForce(t, new Coordonnee(1, 0));

        for (Coordonnee c : p.gridTuile.keySet()) {
            System.out.println(c);
            System.out.println(p.gridTuile.get(c));
            System.out.println("----------------------------");
        }

        System.out.println("===============");

        for (Coordonnee c : p.gridHexagone.keySet()) {
            System.out.println(c);
            System.out.println(p.gridHexagone.get(c));
            System.out.println("----------------------------");
        }

        Coordonnee c1 = new Coordonnee(0, 0);
        Coordonnee c2 = new Coordonnee(1, 6);
        Coordonnee c3 = new Coordonnee(0, 0);
    }
}
