package com.laliste;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

public class FileManager {
    
    /**
     * MARK: - Generate txt file
     * cree un fichier txt si n'exsite pas avec le contenu passe en parametre, append=true
     * @param content
     */
    public static void generateTxtFile(String content){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Settings.FILENAME_GENERATED, true))){
            writer.write(content);
            writer.newLine();
        }
        catch(IOException e){
            writeInlogFile("FileManager - generateTxtFile - Une erreur a eu lieu lors de la generation d'une ligne", "info");
        }
    }

    public static File importFromTxt(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner un fichier a importer");

        // Configuration du filtre pour n'afficher que les fichiers texte
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            return selectedFile;
        }

        return null;
    }

    public static void writeInlogFile(String content, String level){
        File logFile = new File(Settings.LOG_PATH);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Initialisez le gestionnaire de fichier et le formateur pour le fichier de log
        FileHandler fileHandler;
        try{
            fileHandler = new FileHandler(Settings.LOG_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }

        // Créez un objet Logger pour enregistrer les messages de log
        Logger logger = Logger.getLogger("com.laliste");
        logger.addHandler(fileHandler);

        if(level.equals("info")){
            logger.info(content);
        }
        else if(level.equals("warning")){
            logger.warning(content);
        }
        else if(level.equals("severe")){
            logger.severe(content);
        }

        fileHandler.close();
    }   


}
