package main.java.gui;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.*;

public class Main {
	protected static JFrame view;
	protected static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Main() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		view = new JFrame();
		view.getContentPane().add(new ScenePrincipale(true));
		view.setTitle("Menu Principal");
		//view.setSize(new Dimension(1000,800));
		view.setExtendedState(JFrame.MAXIMIZED_BOTH);
		view.setVisible(true);
		view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}

	public static JFrame getView() {
		return view;
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Main();

	}

}
