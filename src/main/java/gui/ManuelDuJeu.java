package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class ManuelDuJeu extends JFrame{
    ManuelDuJeu(){
        JPanel contenu=new JPanel();
        //ImageIcon img=new ImageIcon("2022-ed2-g2--niwa/src/fondNiwa.jpg");
        //Image scaledImg =img.getImage().getScaledInstance(800,1500, Image.SCALE_SMOOTH);
        //img=new ImageIcon(scaledImg);
        //JLabel fond=new JLabel(img);
        //contenu.add(fond);
        
         
        JEditorPane editorPane = new JEditorPane("text/html", "");
       
        
        editorPane.setOpaque(false);
        editorPane.setEditable(false);
        try (BufferedReader reader = new BufferedReader(new FileReader("src/ManuelDuJeuFR.html"))) {
            editorPane.read(reader, null);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editorPane.setPreferredSize(new Dimension(800,2000));
        JScrollPane page=new JScrollPane(editorPane);
        page.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        page.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //fond.setLayout(new BorderLayout()); // set the layout of the JLabel to BorderLayout
        //fond.add(editorPane, BorderLayout.CENTER); // add the JEditorPane to the JLabel
        page.setViewportView(editorPane); // set the view of the scroll pane to the JLabel
        page.setViewportBorder(null); // remove the border around the viewport
        page.setOpaque(false); // make the scroll pane transparent
        page.getViewport().setOpaque(false); // make the viewport transparent
        this.add(page);
        this.pack();
        this.setResizable(false);
        this.setSize(800,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    

    public static void main(String[] args){
        ManuelDuJeu j=new ManuelDuJeu();
        
}
}
