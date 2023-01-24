package main.java.model;

public class Test {
    public static void main(String[] args) {
        Plateau p = new Plateau();
        Tuile t1 = new Tuile();

        System.out.println(p.placeTuileForce(t1, 0, 0));
        System.out.println(p.placeTuileForce(t1, 0, 0));

        System.out.println(p.placeTuileForce(t1, 1, 0));
        System.out.println(p.placeTuileContraint(t1, 2, 0));

        System.out.println(p.placeTuileForce(t1, 1, 0));
        System.out.println(p.placeTuileForce(t1, 2, 0));
    }
}
