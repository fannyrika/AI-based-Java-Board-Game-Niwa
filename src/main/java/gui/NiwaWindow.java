package main.java.gui;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;

public class NiwaWindow extends JFrame implements Runnable {

    public Thread t;
    public static final String threadName = "Thread_NW";

    protected static boolean soundON = true;
    protected static Clip clip, clip2;

    public static StockageSettings stockage = new StockageSettings();

    /**
     * 
     * @param imageFile
     * @throws IOException
     */
    public NiwaWindow() {

        // Fonctions permettant l'affichage correcte de la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Menu Niwa");
        // setLocationRelativeTo(null); // Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        
    }

    @Override
    public void run() {
        this.setPanel(new JouerFrame(this));
        this.pack();
        this.setVisible(true);

        // PARTIE AUDIO permettant le lancenment de la musique
        if(clip == null){
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(StockageSettings.niwaAudio);
            AudioInputStream ais;
            try {
                ais = AudioSystem.getAudioInputStream(inputStream);
                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.err.println("Problème au niveau du lancement du son...");
                if (!StockageSettings.DEBUG_MODE) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

   
    // SON POUR LES BOUTONS
    public void beep() {
        // PARTIE AUDIO permettant le lancenment de la musique
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(StockageSettings.niwaBeep);
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(inputStream);
            clip2 = AudioSystem.getClip();
            clip2.open(ais);
            clip2.start();

        } catch (Exception ee) {
            System.err.println("ProblÃ¨me au niveau du lancement du son...");
            if (!StockageSettings.DEBUG_MODE) {
                ee.printStackTrace();
            }
        }
    }

    public void setPanel(JPanel panel){
        this.setContentPane(panel);
        this.invalidate();
        this.validate();
    }

    public static void main(String args[]) throws IOException {

        NiwaWindow frame = new NiwaWindow();
        frame.start();

    }

}
