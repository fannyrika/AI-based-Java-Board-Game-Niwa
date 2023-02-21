package main.java.gui;
import main.java.model.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;


public abstract class Scene extends JPanel{
	protected JFrame frame;
	 protected static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    public Scene(boolean visible) {  
	        setSize(screenSize);
	        setVisible(visible);
	        
	    }

	    public static void changeScene(Scene depart, Scene arrive) {
	        /*
	         * Fonction permettant une transition entre les différentes pages.
	         */
	        depart.setVisible(false);
	        arrive.setVisible(true);
	    }
	    
	    

}
