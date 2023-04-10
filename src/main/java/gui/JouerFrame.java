package main.java.gui;

import java.io.IOException;
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

public class JouerFrame extends JFrame {

    protected JButton jouerButton, validerButton, quitterButton, optionButton;


    /**
     * 
     * @param imageFile
     * @throws IOException
     */
    public JouerFrame(File imageFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	
    	//Fonctions permettant l'affichage correcte de la fenetre 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("MENU NIWA");
        pack();
        setLocationRelativeTo(null); // Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // PARTIE AUDIO permettant le lancenment de la musique
        File file = new File("C:\\Users\\33668\\Desktop\\Projet\\2022-ed2-g2--niwa\\niwaAudio.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();


        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(imageFile.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 350));

        // CREATION DES BOUTONS JOUER OPTIONS QUITTER
        jouerButton = new JButton("J O U E R ");
        jouerButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        jouerButton.setPreferredSize(new Dimension(120, 50));
        jouerButton.setFocusable(false);
        jouerButton.setForeground(Color.ORANGE);
        jouerButton.setBackground(Color.WHITE);
        jouerButton.setBorder(new LineBorder(Color.CYAN));
        jouerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                File imageFile = new File("C:\\Users\\33668\\Desktop\\Projet\\2022-ed2-g2--niwa\\src\\parametreNiwa.png");
                //Passage a la page suivante pour que le joueur effectue les choix du mode de jeux
                JouerButton jouerButton = new JouerButton(imageFile);
            }
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

    public static void main(String args[]) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                File imageFile = new File("C:\\Users\\33668\\Desktop\\Projet\\2022-ed2-g2--niwa\\src\\photo6.png");

                if (imageFile != null) {
                    JouerFrame frame;
                    try {
                        frame = new JouerFrame(imageFile);
                        frame.setVisible(true);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // frame.setVisible(true);
                }

            }
        });

    }

}
