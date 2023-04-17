package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class OptionButton extends JFrame implements ActionListener {
    // protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected GridBagConstraints pos = new GridBagConstraints();

    public OptionButton(JouerFrame prec) {

        // Fonctions permettant l'affichage correcte de la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // this.setSize(screenSize);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("OPTION NIWA");
        pack();
        setLocationRelativeTo(null); // Pour centrer la fenetre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(StockageSettings.file_parametreNiwa.getAbsolutePath()));
        background.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 280));

        JLabel text = new JLabel(" A C T I V E R / D E S A C T I V E R   M U S I Q U E  ");
        text.setFont(new Font("Congenial Black", Font.BOLD, 15));
        text.setForeground(Color.ORANGE);
        text.setBackground(Color.WHITE);
        text.setPreferredSize(new Dimension(370, 50));
        text.setBorder(new LineBorder(Color.CYAN));

        ImageIcon sound = new ImageIcon(StockageSettings.file_musicOn.getAbsolutePath());

        JButton button = new JButton(sound);
        button.setPreferredSize(new Dimension(50, 50));
        button.setBackground(new Color(245, 236, 206));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.CYAN));

        button.addActionListener(new ActionListener() {
            ImageIcon s;

            @Override
            public void actionPerformed(ActionEvent event) {
                if (JouerFrame.soundON == true) {
                    s = new ImageIcon(StockageSettings.file_musicOf.getAbsolutePath());
                    JouerFrame.stopMusic();
                    // JouerFrame.setSound(false);
                    prec.soundON = false;
                } else {
                    s = new ImageIcon(StockageSettings.file_musicOn.getAbsolutePath());
                    JouerFrame.startMusic();
                    // JouerFrame.setSound(true);
                    prec.soundON = true;

                }
                button.setIcon(s);
            }

        });

        // Retourner a la page precedente
        JButton retour = new JButton("R E T O U R ");
        retour.setFont(new Font("Congenial Black", Font.BOLD, 15));
        retour.setPreferredSize(new Dimension(150, 50));
        retour.setFocusable(false);
        retour.setForeground(Color.ORANGE);
        retour.setBackground(new Color(245, 236, 206));
        retour.setBorder(new LineBorder(Color.CYAN));
        retour.addActionListener(e -> {
            prec.beep();
            prec.setVisible(true);

            setVisible(false);
        });

        background.add(text);
        background.add(button);
        background.add(retour);

        add(background);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}