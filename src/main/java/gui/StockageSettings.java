package main.java.gui;

import java.io.File;

import main.java.model.MapEtat;

public class StockageSettings {

    public static final boolean DEBUG_MODE = false;
    
    public static int NB_JOUEURS_TOTAL;
    public static int NB_HUMAIN;
    public static int NB_IA;
    public static int NB_TUILES;
    public static MapEtat MAP_ETAT;

    public static String niwaAudio = "main/java/gui/resources/niwaAudio.wav";
    public static String bg_parametreNiwa = "main/java/gui/resources/parametreNiwa.png";
    public static String bg_photo6 = "main/java/gui/resources/photo6.png";

    /*
     * Pour que ces File puisse être initialiser, ne pas oublier de créer un objet StockageSettings un moment où un autre.
     * Pour le moment, l'objet StockageSettings est initialiser dans la classe JouerFrame
     */
    public static File file_parametreNiwa;
    public static File file_photo6;

    public StockageSettings(){
        try {
            file_photo6 = new File(getClass().getClassLoader().getResource(StockageSettings.bg_photo6).toURI());
        } catch (Exception e) {
            System.err.println("Problème d'ouverture de l'image photo6...");
            if(!StockageSettings.DEBUG_MODE){
                e.printStackTrace();
            }
        }

        try {
            file_parametreNiwa = new File(getClass().getClassLoader().getResource(StockageSettings.bg_parametreNiwa).toURI());
        } catch (Exception e) {
            System.err.println("Problème d'ouverture de l'image parametreNiwa...");
            if(!StockageSettings.DEBUG_MODE){
                e.printStackTrace();
            }
        }
    }
}
