package main.java.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SceneOptions extends Scene{
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected JPanel menu;
	protected GridBagConstraints pos = new GridBagConstraints();
	protected JTextField sound;
	protected boolean sonON;
	protected JButton soundButton;
	protected Clip clip;
	
	
	
	public SceneOptions(boolean visible)throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(visible);
        
        File file = new File("crown3.wav");

        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(ais);
      //  sonON=false;
	    //clip.start();
        
        
        
        
        
        
        Main.getView().setTitle("Options");
        this.setSize(screenSize);
        this.setLayout(null);
        this.setVisible(visible);
        
        setupMenu();
        setupMusicField();
        setupMusicOnField();
       // setupSoundField();
       // setupSoundOnField();
	}
	
	public void setupMenu() {
        menu = new JPanel();
        menu.setLayout(new GridBagLayout());
        menu.setVisible(true);
        menu.setBounds(new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight()));
        add(menu);
        
    }
	
	public void setupMusicField() {
        sound = new JTextField(" M U S I C   O N / O F F");
        sound.setPreferredSize(new Dimension(250,60));
        sound.setForeground(Color.WHITE);// couleur du texte
        sound.setBackground(Color.BLACK);
        sound.setFont(new Font("Lucida Console", Font.BOLD,15));
        pos.gridx = 0;
        pos.gridy = 0;
        pos.weighty=0.1;
        menu.add(sound, pos);
    }
	
	public void setupMusicOnField() {
		
		ImageIcon icon=new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
    	soundButton=new JButton(icon);
    	soundButton.setPreferredSize(new Dimension(60,60));
    	soundButton.setBackground(Color.BLACK);
    	
    	soundButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                
                ImageIcon icon2;
               if(ScenePrincipale.sonON==true) {
            	   icon2 =new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
            	   clip.setFramePosition(0);
	               ScenePrincipale.clip.start();
            	   ScenePrincipale.sonON=false;
            	   
               }else {
            	   icon2 =new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violet.png");
            	   clip.setFramePosition(0);
	               ScenePrincipale.clip.stop();
            	   ScenePrincipale.sonON=true;
               }
                     
               
                soundButton.setIcon(icon2);
                
            }
        });
	 
	    pos.gridx = 2;
        pos.gridy = 0;
        pos.weighty=0.1;
        menu.add(soundButton, pos);
     
}
	
	/*public void setupSoundField() {
        sound = new JTextField(" S O U N D   O N / O F F");
        sound.setPreferredSize(new Dimension(250,60));
        sound.setForeground(Color.WHITE);// couleur du texte
        sound.setBackground(Color.BLACK);
        sound.setFont(new Font("Lucida Console", Font.BOLD,15));
        pos.gridx = 0;
        pos.gridy = 1;
        pos.weighty=0.1;
        menu.add(sound, pos);
    }
	
	
public void setupSoundOnField() {
		
		ImageIcon icon=new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
    	soundButton=new JButton(icon);
    	soundButton.setPreferredSize(new Dimension(60,60));
    	soundButton.setBackground(Color.BLACK);
    	
    	soundButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                
                ImageIcon icon2;
               if(sonON==true) {
            	   icon2 =new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violetON.png");
            	   clip.setFramePosition(0);
	               clip.start();
            	   sonON=false;
            	   
               }else {
            	   icon2 =new ImageIcon("C:\\Users\\33668\\2022-ed2-g2--niwa-develop\\src\\violet.png");
            	   clip.setFramePosition(0);
	               clip.stop();
            	   sonON=true;
               }
                     
               
                soundButton.setIcon(icon2);
                
            }
        });
	 
	    pos.gridx = 2;
        pos.gridy = 1;
        pos.weighty=0.1;
        menu.add(soundButton, pos);
     
}  */
		
	}


