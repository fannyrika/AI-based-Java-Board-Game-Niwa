package main.java.gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main.java.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ScenePrincipale extends Scene {

    // Attribut
    protected JButton soundButton;

    protected static boolean sonON;
    protected GridBagConstraints pos = new GridBagConstraints();
    protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected JPanel buttons;
    protected JButton jouerButton, parametreButton, quitterButton, sonButton;
    protected Scene sceneParametre;
    protected Scene sceneOptions;
    protected static Clip clip;

    // Constructor
    public ScenePrincipale(boolean visible)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	
    	
        super(visible);
        
        
       /* JLabel background;
        ImageIcon img=new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\NIWA.png");
        background=new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,1300,700);
        this.add(background); */
        //this.setBackground(Color.pink);
        File file = new File("crown3.wav");

        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);

       // this.setBackground(Color.red);
        this.setSize(screenSize);
        this.setLayout(null);
        this.setVisible(visible);

        setupMenu();
        setupJouerButton();
        setupParametreButton();
        setupExitButton();
        //setupSoundButton();

        sonON = false;
        //clip.start();
    }
    

	public void setupMenu() {
        buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        buttons.setVisible(true);
        buttons.setBounds(new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight()));
        add(buttons);
    }

    public void setupJouerButton() {
        jouerButton = new JButton("J O U E R");
       // jouerButton.setBounds(20,40,100,40);
        jouerButton.setPreferredSize(new Dimension(150, 60));
        jouerButton.setFocusable(false);// enelever "cadre" interne
        jouerButton.setForeground(Color.ORANGE);// couleur du texte
        jouerButton.setBackground(Color.WHITE);
        jouerButton.setBorder(new LineBorder(Color.CYAN));
        jouerButton.setFont(new Font("Congenial Black", Font.BOLD, 18));

        jouerButton.addActionListener(e -> {
            sceneParametre = new SceneParametre(true);
            Main.getView().add(sceneParametre);
            Scene.changeScene(this, sceneParametre);

        });

        pos.gridx = 0;
        pos.gridy = 0;
        pos.weighty = 0.1;  
        buttons.add(jouerButton,pos);

    }

    public void setupParametreButton() {
        parametreButton = new JButton("O P T I O N S");
       // parametreButton.setBounds(140,40,100,40);
        parametreButton.setPreferredSize(new Dimension(150, 60));
        parametreButton.setFocusable(false);
        parametreButton.setFont(new Font("Congenial Black", Font.BOLD, 18));
        parametreButton.setForeground(Color.ORANGE);// couleur du texte
        parametreButton.setBackground(Color.WHITE);
        parametreButton.setBorder(new LineBorder(Color.CYAN));
        // parametreButton.setBorder(BorderFactory.createEtchedBorder());

        parametreButton.addActionListener(e -> {
            try {
                sceneOptions = new SceneOptions(true);
            } catch (UnsupportedAudioFileException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Main.getView().add(sceneOptions);
            Scene.changeScene(this, sceneOptions);

        });

        pos.gridx = 0;
        pos.gridy = 1;
        pos.weighty = 0.1;  
        buttons.add(parametreButton,pos);

    }

    public void setupExitButton() {
        quitterButton = new JButton("Q U I T T E R");
        //quitterButton.setBounds(260,40,100,40);
        quitterButton.setPreferredSize(new Dimension(150, 60));
        quitterButton.setFocusable(false);
      //  quitterButton.setFont(new Font("Congenial Black", Font.BOLD, 18));
        quitterButton.setForeground(Color.ORANGE);// couleur du texte
        quitterButton.setBackground(Color.WHITE);
        quitterButton.setBorder(new LineBorder(Color.CYAN));
        quitterButton.setFont(new Font("Congenial Black", Font.BOLD, 18));
        // quitterButton.setBorder(BorderFactory.createEtchedBorder());

        quitterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        pos.gridx = 0;
        pos.gridy = 2;
        pos.weighty = 0.1;
        buttons.add(quitterButton,pos);

    }

  /*  public void setupSoundButton() {
        ImageIcon icon = new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
        soundButton = new JButton(icon);

        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                ImageIcon icon2;
                if (sonON == true) {
                    icon2 = new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
                    clip.setFramePosition(0);
                    clip.start();
                    sonON = false;

                } else {
                    icon2 = new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violet.png");
                    clip.setFramePosition(0);
                    clip.stop();
                    sonON = true;
                }

                soundButton.setIcon(icon2);

            }
        });

        pos.gridx = 0;
        pos.gridy = 3;
        pos.weighty = 0.1;
        buttons.add(soundButton, pos);

    }  */

    /*
     * public static void main(String[] args) {
     * new ScenePrincipale(true);
     * }
     */
}
