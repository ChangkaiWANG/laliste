package com.laliste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/laliste.db";

    //MARK: - Connect to database
    private static Connection connect() throws SQLException{
        boolean databaseExists = new File(Settings.DB_PATH).exists();
        if(!databaseExists){
            System.out.println("Database n'existe pas, creation...");
            createDatabase();
        }
        return DriverManager.getConnection(DB_URL);
    }

    //MARK: - Create database
    private static void createDatabase(){
        //utilisation de statement car pas de parametre dynamique
        try(Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS liens(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "lien TEXT UNIQUE NOT NULL)");//unique pour ne pas avoir de doublons
            System.out.println("Database and table created successfully.");
        }catch(SQLException e){
            FileManager.writeInlogFile("DatabaseManager.java - createDatabase() - Erreur lors de la création de la BD", "severe");
            System.out.println("Error creating database: "+e.getMessage());
        }
    }


    //MARK: - Insert information
    public static String insertInformation(String lien){

        if(lien.isEmpty()){
            return "Le lien saisie est vide !";
        }

        String sql = "INSERT OR IGNORE INTO liens (lien) VALUES (?)";
        try(Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, lien);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return "Le lien a été ajouté avec succès !";
            } else {
                return "Le lien existe déjà dans la base de données !";
            }

        }catch(SQLException e){
            System.out.println("Error insertLien: "+e.getMessage());
            return "Erreur lors de l'ajout du lien !";
        }
        
    }

    public static String getRandLink(){
        String sql = "select lien from liens order by random() limit 1";
        try(Connection conn = connect();
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet res = statement.executeQuery()){
            if(res.next()){
                return res.getString("lien");
            }
        }catch(SQLException e){
            System.out.println("Error getRandLink: "+e.getMessage());
        }
        return null;
    }

    public static void clearTableLiens(){
        String sql = "DELETE FROM liens";
        try(Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            System.out.println("Table liens cleared successfully.");
        }catch(SQLException e){
            System.out.println("Error clearTableLiens: "+e.getMessage());
        }
    }

    //MARK: - Generate txt file
    public static String generateTxtFile(){
        String sql = "SELECT lien FROM liens";
        int writed_line_number = 0;

        try(Connection conn = connect();
        PreparedStatement statement = conn.prepareStatement(sql)){
            ResultSet res = statement.executeQuery();

            while(res.next()){
                String lien = res.getString("lien");
                FileManager.generateTxtFile(lien);
                writed_line_number++;
            }
        }
        catch(SQLException e){
            System.out.println("Error generateTxtFile: "+e.getMessage());
            FileManager.writeInlogFile("DatabaseManager.java - generateTxtFile() - Erreur lors de la génération du fichier txt, nombre de ligne ecrite" + writed_line_number, "severe");
        }

        return "Fichier généré avec succès, nombre de ligne ecrite: "+writed_line_number;
    }

    //MARK: - Import from txt
    public static String importFromTxt(){
        File selectedFile = FileManager.importFromTxt();

        if(selectedFile !=null){
            try(Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement("INSERT OR IGNORE INTO liens (lien) VALUES (?)");
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile))){
                String line;
                while((line = reader.readLine()) != null){
                    statement.setString(1, line);
                    statement.executeUpdate();
                }
            }
            catch(SQLException e){
                System.out.println("Error importFromTxt: "+e.getMessage());
            }
            catch(IOException e){
                System.out.println("Error importFromTxt: "+e.getMessage());
            }
        }

        return "Fichier importé avec succès !";
    }
}
