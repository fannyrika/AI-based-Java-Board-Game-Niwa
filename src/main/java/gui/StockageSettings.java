package main.java.gui;

import java.io.File;

import main.java.model.MapEtat;

public class StockageSettings {

    /**
     * MODE DEBUG :
     * - Tous les pions peuvent se déplacer n'importe où sur le plateau
     * -
     * -
     * - *à rajouter si nécessaire...*
     */
    public static final boolean DEBUG_MODE = false;
    /**
     * Pour le debogage, sert à montrer les coordonnées des hexagones en direct
     */
    public static final boolean SHOW_COORDS = false;

    public static int NB_JOUEURS_TOTAL;
    public static int NB_HUMAIN;
    public static int NB_IA;
    public static int NB_TUILES;
    public static MapEtat MAP_ETAT;

    public static String niwaAudio = "main/java/gui/resources/niwaAudio.wav";
    public static String bg_parametreNiwa = "main/java/gui/resources/parametreNiwa.png";
    public static String bg_photo6 = "main/java/gui/resources/photo6.png";
    public static String niwaBeep = "main/java/gui/resources/beep.wav";
    public static String musicOn = "main/java/gui/resources/violetON.png";
    public static String musicOf = "main/java/gui/resources/violet.png";
    public static String arrow_right = "main/java/gui/resources/arrow_right.png";
    public static String arrow_left = "main/java/gui/resources/arrow_left.png";

    /*
     * Pour que ces File puisse être initialiser, ne pas oublier de créer un objet
     * StockageSettings un moment où un autre.
     * Pour le moment, l'objet StockageSettings est initialiser dans la classe
     * JouerFrame
     */
    public static File file_parametreNiwa;
    public static File file_photo6;
    public static File file_musicOn;
    public static File file_musicOf;
    public static File file_arrow_right;
    public static File file_arrow_left;

    public StockageSettings() {
        try {
            file_photo6 = new File(getClass().getClassLoader().getResource(bg_photo6).toURI());
        } catch (Exception e) {
            System.err.println("Problème d'ouverture de l'image photo6...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        try {
            file_parametreNiwa = new File(
                    getClass().getClassLoader().getResource(bg_parametreNiwa).toURI());
        } catch (Exception e) {
            System.err.println("Problème d'ouverture de l'image parametreNiwa...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        try {
            file_musicOn = new File(getClass().getClassLoader().getResource(musicOn).toURI());
        } catch (Exception e) {
            System.err.println("ProblÃ¨me d'ouverture de l'image musicOn...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        try {
            file_musicOf = new File(getClass().getClassLoader().getResource(musicOf).toURI());
        } catch (Exception e) {
            System.err.println("ProblÃ¨me d'ouverture de l'image musicOf...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        try {
            file_arrow_right = new File(getClass().getClassLoader().getResource(arrow_right).toURI());
        } catch (Exception e) {
            System.err.println("ProblÃ¨me d'ouverture de l'image arrow_right...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        try {
            file_arrow_left = new File(getClass().getClassLoader().getResource(arrow_left).toURI());
        } catch (Exception e) {
            System.err.println("ProblÃ¨me d'ouverture de l'image arrow_left...");
            if (!DEBUG_MODE) {
                e.printStackTrace();
            }
        }

    }
}
