package main.java.gui;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class searchSave {
    //verifie que nom est bien une sauvegarde .ser
    public static boolean isSave(String nom){
        return (nom.charAt(nom.length() - 1) == 'r' 
        && nom.charAt(nom.length() - 2) == 'e' 
        && nom.charAt(nom.length() - 3) == 's');
    }

    // verifie si un fichier decendant de ac s'appelle nom
    public static boolean serExists(File ac, String nom){
        if(ac.getName().equals(nom + "ser")) return true;
        
        boolean x = false;
        File[] dir = ac.listFiles();
        for(File f : dir){
            if(f.isDirectory()){
                x = x || serExists(f, nom);
            }
            if(x == true) return true;
        }
        return x;
    }


    // Vérifie qu'il existe une sauvegarde
    public static boolean existSer(File racine){
       boolean x = false;
        File[] dir = racine.listFiles();
        for(File f : dir){
            if(f.isDirectory()){
                x = x || existSer(f);
            } else if(isSave(f.getName())){
                return true;
            }
            if(x == true) return true;
        }
        return x;
    }

    // Vérifie que le nom de sauvegarde qui sera donné est correct
    public static String nameSave(){
        String input = "";
        
        while(input == ""){
            input = JOptionPane.showInputDialog("Veuillez entrer le nom de votre sauvegarde (max. de 12 caractères alpha-numériques): ", null);                         
            
            if(input == ""){
                JOptionPane.showMessageDialog(null, "Nom de fichier entré non valide :( ", "Erreur !", JOptionPane.ERROR_MESSAGE);
            } else if(input.length() > 12 ){
                JOptionPane.showMessageDialog(null, "La taille du nom entré ne doit pas dépasser 12 caractères !", "Erreur !", JOptionPane.ERROR_MESSAGE);
                input = "";
            }

            boolean x = true;
            for(int i = 0; i < input.length(); i++){
                if(!Character.isLetterOrDigit(input.charAt(i))) x = false;
            }

            if(x == false){
                JOptionPane.showMessageDialog(null, "Le nom ne doit contenir que des caractères alpha-numériques !", "Erreur !", JOptionPane.ERROR_MESSAGE);
                input = "";
            }

            if(serExists(new File("."), input)) input = "";
        }

        return input;
    }

    //retourne une arraylist contenant tout les fichiers sauvegarde existants
    public static ArrayList<File> tabSer(File racine, ArrayList<File> list){
        File[] dir = racine.listFiles();
        for(File f : dir){
            if(f.isDirectory()){
                list = tabSer(f, list);
            }else if(isSave(f.getName())){
                list.add(f);
            }
        }

        return list;
    }
}
