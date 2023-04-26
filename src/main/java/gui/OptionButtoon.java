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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.java.model.Jeu;

public class OptionButtoon extends JPanel implements ActionListener {
	// protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected GridBagConstraints pos = new GridBagConstraints();

	protected static final int nb_panels = 6;

	public class Arrow extends JButton {

		protected static ImageIcon arrow_left = new ImageIcon(StockageSettings.file_arrow_left.getAbsolutePath());
		protected static ImageIcon arrow_right = new ImageIcon(StockageSettings.file_arrow_right.getAbsolutePath());

		public Arrow(boolean isLeft){
			super((isLeft) ? arrow_left : arrow_right);
			this.setPreferredSize(new Dimension(50, 50));
			this.setBackground(new Color(245,236,206));
			this.setFocusPainted(false);
			this.setOpaque(false);
			this.setBorder(null);
		}
	}
  
	public class Option extends JLabel {

		public Option(String s){
			super(s);
			this.setFont(new Font("Congenial Black", Font.BOLD, 15));
			this.setForeground(Color.ORANGE);
			this.setBackground(Color.WHITE);
			this.setBorder(new LineBorder(Color.CYAN));
			this.setOpaque(false); 
		}
	}

	public class Value extends JLabel {
		
		public Value(String s, int pos){
			super(s,JLabel.CENTER);
			this.setFont(new Font("Congenial Black", Font.BOLD, 15));
			this.setForeground(Color.ORANGE);
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(50, 50));
			this.setBorder(new LineBorder(Color.CYAN)); 
		}
	}

	public OptionButtoon(NiwaWindow frame) {
   		
 	    setVisible(true);
 		setLayout(new BorderLayout());  
       	JLabel background = new JLabel(new ImageIcon(StockageSettings.file_parametreNiwa.getAbsolutePath()));
       	background.setLayout(new GridLayout(nb_panels, 1));

		JPanel[] panels = new JPanel[nb_panels];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
			panels[i].setOpaque(false);
		}

		Option titre=new Option("  P A R A M E T R E S  ");
		titre.setPreferredSize(new Dimension(170, 50));


 		Option text=new Option("  A C T I V E R / D E S A C T I V E R   M U S I Q U E  ");
        text.setPreferredSize(new Dimension(370, 50)); 

 		
 	   	ImageIcon sound;
		if(NiwaWindow.soundON){
			sound = new ImageIcon(StockageSettings.file_musicOn.getAbsolutePath());
		}
		else{
			sound = new ImageIcon(StockageSettings.file_musicOf.getAbsolutePath());
		}
 	   
 	   	JButton button = new JButton(sound);
 	    button.setPreferredSize(new Dimension(50, 50));
 	    button.setBackground(new Color(245,236,206));
 	    button.setFocusPainted( false );
 	    button.setBorder(new LineBorder(Color.CYAN));
 	   
 	   	button.addActionListener(new ActionListener() {
 	    	ImageIcon s;
 			@Override
 			public void actionPerformed(ActionEvent event) {
				if(NiwaWindow.soundON==true) {
					s= new ImageIcon(StockageSettings.file_musicOf.getAbsolutePath());
					NiwaWindow.clip.stop();
					// NiwaWindow.setSound(false);
					NiwaWindow.soundON=false;
 				}
				else {
					s= new ImageIcon(StockageSettings.file_musicOn.getAbsolutePath());
					NiwaWindow.clip.start();;
					// NiwaWindow.setSound(true);   
					NiwaWindow.soundON=true;			
				}
 				button.setIcon(s);
 			}
 			 
 		});  

		Option nb_pions=new Option("  N O M B R E   D E   P I O N S  ");
        nb_pions.setPreferredSize(new Dimension(230, 50));

		Value nb_pions_int = new Value(String.valueOf(Jeu.NB_PIONS),JLabel.CENTER);

		Option nb_perle=new Option("  N O M B R E   D E   P E R L E S   M A X  ");
		nb_perle.setPreferredSize(new Dimension(300, 50));

		Value nb_perle_int = new Value(String.valueOf(Jeu.NB_PEARL_MAX),JLabel.CENTER);

		Option nb_perle_start=new Option("  N O M B R E   D E   P E R L E S   D E   D E P A R T  ");
		nb_perle_start.setPreferredSize(new Dimension(380, 50));

		Value nb_perle_start_int = new Value(String.valueOf(Jeu.NB_PEARLS_AT_START),JLabel.CENTER);

		Arrow left1 = new Arrow(true);
		left1.addActionListener(e -> {
			if(Jeu.NB_PIONS > 3){
				Jeu.NB_PIONS--;
				nb_pions_int.setText(String.valueOf(Jeu.NB_PIONS));
				repaint();
			}
		});
		Arrow right1 = new Arrow(false);
		right1.addActionListener(e -> {
			if(Jeu.NB_PIONS < 6){
				Jeu.NB_PIONS++;
				nb_pions_int.setText(String.valueOf(Jeu.NB_PIONS));
				repaint();
			}
		});

		Arrow left2 = new Arrow(true);
		left2.addActionListener(e -> {
			if(Jeu.NB_PEARL_MAX > 3){
				Jeu.NB_PEARL_MAX--;
				nb_perle_int.setText(String.valueOf(Jeu.NB_PEARL_MAX));
				if(Jeu.NB_PEARLS_AT_START >= Jeu.NB_PEARL_MAX){
					Jeu.NB_PEARLS_AT_START = Jeu.NB_PEARL_MAX-1;
					nb_perle_start_int.setText(String.valueOf(Jeu.NB_PEARLS_AT_START));
				}
				repaint();
			}
		});
		Arrow right2 = new Arrow(false);
		right2.addActionListener(e -> {
			if(Jeu.NB_PEARL_MAX < 6){
				Jeu.NB_PEARL_MAX++;
				nb_perle_int.setText(String.valueOf(Jeu.NB_PEARL_MAX));
				repaint();
			}
		});

		Arrow left3 = new Arrow(true);
		left3.addActionListener(e -> {
			if(Jeu.NB_PEARLS_AT_START > 2){
				Jeu.NB_PEARLS_AT_START--;
				nb_perle_start_int.setText(String.valueOf(Jeu.NB_PEARLS_AT_START));
				repaint();
			}
		});
		Arrow right3 = new Arrow(false);
		right3.addActionListener(e -> {
			if(Jeu.NB_PEARLS_AT_START < 4){
				Jeu.NB_PEARLS_AT_START++;
				nb_perle_start_int.setText(String.valueOf(Jeu.NB_PEARLS_AT_START));
				if(Jeu.NB_PEARL_MAX <= Jeu.NB_PEARLS_AT_START){
					Jeu.NB_PEARL_MAX = Jeu.NB_PEARLS_AT_START+1;
					nb_perle_int.setText(String.valueOf(Jeu.NB_PEARL_MAX));
				}
				repaint();
			}
		});

 	  
		//Retourner a la page precedente 
		JButton retour=new JButton(" R E T O U R ");
		retour.setFont(new Font("Congenial Black", Font.BOLD, 15));
		retour.setPreferredSize(new Dimension(150, 50));
		retour.setFocusable(false);
		retour.setForeground(Color.ORANGE);
		retour.setBackground(new Color(245,236,206));
		retour.setBorder(new LineBorder(Color.CYAN));
		retour.addActionListener(e -> {
    	   	frame.beep();
			frame.setPanel(new JouerFrame(frame));
       	});

		panels[0].add(titre);

		panels[1].add(text);
		panels[1].add(button);

		panels[2].add(nb_pions);
		panels[2].add(left1);
		panels[2].add(nb_pions_int);
		panels[2].add(right1);

		panels[3].add(nb_perle);
		panels[3].add(left2);
		panels[3].add(nb_perle_int);
		panels[3].add(right2);

		panels[4].add(nb_perle_start);
		panels[4].add(left3);
		panels[4].add(nb_perle_start_int);
		panels[4].add(right3);

		panels[panels.length-1].add(retour);

		for (JPanel p : panels) {
			background.add(p);
		}
 	    
 	    add(background);
 		
 	}




 	@Override
 	public void actionPerformed(ActionEvent e) {
 		// TODO Auto-generated method stub
 		
 	}
 		
 	

}
