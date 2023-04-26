package main.java.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JouerFrame extends JPanel {

    protected JButton jouerButton, validerButton, quitterButton, optionButton;

    public static StockageSettings stockage = new StockageSettings();

    /**
     * 
     * @param imageFile
     * @throws IOException
     */
    public JouerFrame(NiwaWindow frame) {

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
            // Pour le son lorqu'on clique sur le bouton
            frame.beep();

             // Proposition d'ouverture de sauvegarde
             if(searchSave.existSer(new File("."))){
                int o = -1;
                while(o == -1){
                    o = JOptionPane.showOptionDialog(null, "Il existe une/des sauvegardes.\nVoulez-vous ouvrir une sauvegarde ?", 
                    "Ouvrir une sauvegarde ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, null, null);

                    if (o == JOptionPane.YES_OPTION){
                        ArrayList<File> files = searchSave.tabSer(new File("."), new ArrayList<File>());
                        File[] filesNames = files.toArray(new File[0]);
                        // Affichage d'une liste de tout les .ser existants dans le dossier courant
                        File save = (File) JOptionPane.showInputDialog(null, 
                        "Sélectionnez la sauvegarde souhaitée:", 
                        "Ouvrir une sauvegarde ?", 
                        JOptionPane.PLAIN_MESSAGE, null, 
                        filesNames, filesNames[0]);

                        if(save == null) o = -1;
                        // ouvrir la sauvegarde save
                        // REMARQUE : les fichiers qtable0 et qtable1 ne sont pas des parties sauvegardés
                        try{
                            // execution de la deserialization
                            FileInputStream file = new FileInputStream(save);
                            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(save));
                            InterfaceDeJeu game = (InterfaceDeJeu) obj.readObject();

                            obj.close();
                            file.close();

                            // Suppression du fichier afin d'eviter toutes confusions
                            save.delete();

                            // Lancement de la partie deserializer
                            game.start();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Impossible d'ouvrir la sauvegarde :( ", "Erreur !", JOptionPane.ERROR_MESSAGE);
                        }
                    
                    }else if (o == JOptionPane.NO_OPTION);
                    else;
                }
            }
            frame.setPanel(new JouerButton(frame));
        });
        jouerButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jouerButton.setBackground(new Color(204, 235, 245));
            }

            public void mouseExited(MouseEvent evt) {
                jouerButton.setBackground(Color.white);
            }

        });

        // Permettant � l'utilisateur de garder ou d'enlever la musique en arriere plan
        optionButton = new JButton("O P T I O N S ");
        optionButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        optionButton.setPreferredSize(new Dimension(120, 50));
        optionButton.setFocusable(false);
        optionButton.setForeground(Color.ORANGE);
        optionButton.setBackground(Color.WHITE);
        optionButton.setBorder(new LineBorder(Color.CYAN));
        optionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.beep();
                frame.setPanel(new OptionButtoon(frame));
            }
        });
        optionButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                optionButton.setBackground(new Color(204, 235, 245));
            }

            public void mouseExited(MouseEvent evt) {
                optionButton.setBackground(Color.white);
            }

        });

        quitterButton = new JButton("Q U I T T E R");
        quitterButton.setFont(new Font("Congenial Black", Font.BOLD, 15));
        quitterButton.setPreferredSize(new Dimension(120, 50));
        quitterButton.setFocusable(false);
        quitterButton.setForeground(Color.ORANGE);
        quitterButton.setBackground(Color.WHITE);
        quitterButton.setBorder(new LineBorder(Color.CYAN));
        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.beep();
                System.exit(0);
            }
        });
        quitterButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                quitterButton.setBackground(new Color(204, 235, 245));
            }

            public void mouseExited(MouseEvent evt) {
                quitterButton.setBackground(Color.white);
            }

        });

        background.add(jouerButton);
        background.add(optionButton);
        background.add(quitterButton);
        add(background);

    }
}
