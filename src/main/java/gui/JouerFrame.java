package main.java.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.java.model.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JouerFrame extends JFrame implements Runnable {

    public Thread t;
    public static final String threadName = "Thread_JF";

    protected JButton jouerButton, validerButton, quitterButton, optionButton;

    public static StockageSettings stockage = new StockageSettings();


    /**
     * 
     * @param imageFile
     * @throws IOException
     */
    public JouerFrame() {

    	//Fonctions permettant l'affichage correcte de la fenetre 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("MENU NIWA");
        //setLocationRelativeTo(null); // Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(StockageSettings.file_photo6.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 350));

        // CREATION DES BOUTONS JOUER OPTIONS QUITTER
        jouerButton = new JButton("J O U E R ");
        jouerButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        jouerButton.setPreferredSize(new Dimension(120, 50));
        jouerButton.setFocusable(false);
        jouerButton.setForeground(Color.ORANGE);
        jouerButton.setBackground(Color.WHITE);
        jouerButton.setBorder(new LineBorder(Color.CYAN));
        jouerButton.addActionListener(e -> {
            this.dispose();
            //Passage a la page suivante pour que le joueur effectue les choix du mode de jeux
            JouerButton jouerButton = new JouerButton();
            jouerButton.start();
        });
         
        //Permettant � l'utilisateur de garder ou d'enlever la musique en arriere plan
        optionButton = new JButton("O P T I O N S ");
        optionButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        optionButton.setPreferredSize(new Dimension(120, 50));
        optionButton.setFocusable(false);
        optionButton.setForeground(Color.ORANGE);
        optionButton.setBackground(Color.WHITE);
        optionButton.setBorder(new LineBorder(Color.CYAN));
     
        quitterButton = new JButton("Q U I T T E R");
        quitterButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        quitterButton.setPreferredSize(new Dimension(120, 50));
        quitterButton.setFocusable(false);
        quitterButton.setForeground(Color.ORANGE);
        quitterButton.setBackground(Color.WHITE);
        quitterButton.setBorder(new LineBorder(Color.CYAN));
        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        background.add(jouerButton);
        background.add(optionButton);
        background.add(quitterButton);
        add(background);

    } 

    @Override
    public void run() {
        this.pack();
        this.setVisible(true);

        // PARTIE AUDIO permettant le lancenment de la musique
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(StockageSettings.niwaAudio);
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            System.err.println("Problème au niveau du lancement du son...");
            e.printStackTrace();
        }
    }

    public void start(){
        if(t == null){
            t = new Thread(this,threadName);
            t.start();
        }
    }

    public static void main(String args[]) throws IOException{

        JouerFrame frame = new JouerFrame();
        frame.start();

    }

}
