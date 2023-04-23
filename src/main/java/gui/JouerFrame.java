package main.java.gui;

import java.io.IOException;
import java.io.InputStream;

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
