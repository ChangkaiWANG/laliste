package com.laliste;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.stage.Stage;

import com.laliste.viewElements.*;

public class MenuManager extends MenuBar{
    
    public MenuManager(Text info_msg, Stage stagePrincipal){

        SeparatorMenuItem separator = new SeparatorMenuItem();

        Menu menuFichier = new Menu("Fichier");
        MenuItem itemGenererTxt = new MenuItem("Generer un fichier txt");
        MenuItem itemImportFromTxt = new MenuItem("Importer un fichier txt");
        MenuItem itemQuitter = new MenuItem("Quitter");

        Menu menuEdition = new Menu("Edition");
        MenuItem itemClearTable = new MenuItem("Vider la table");

        itemQuitter.setOnAction(e->{
            System.exit(0);
        });

        itemGenererTxt.setOnAction(e->{
            String msg = DatabaseManager.generateTxtFile();
            info_msg.setText(msg);
            info_msg.setStyle("-fx-fill: rgb(104, 214, 99);");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> info_msg.setText("")));
            timeline.play();
        });

        itemImportFromTxt.setOnAction(e->{
            String msg = DatabaseManager.importFromTxt();
            info_msg.setText(msg);
            info_msg.setStyle("-fx-fill: rgb(104, 214, 99);");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> info_msg.setText("")));
            timeline.play();
        });
        
        itemClearTable.setOnAction(e->{
            DialogClearConfirmation dialog = new DialogClearConfirmation(stagePrincipal);
            dialog.showAndWait();
            String result = dialog.getResult();

            if(result != null && result.equals("ok")){
                DatabaseManager.clearTableLiens();
                info_msg.setText("Table vidée.");
                info_msg.setStyle("-fx-fill: rgb(104, 214, 99);");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> info_msg.setText("")));
                timeline.play();
            }else{
                info_msg.setText("Action annulée.");
                info_msg.setStyle("-fx-fill: rgb(255, 0, 0);");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> info_msg.setText("")));
                timeline.play();
            }
        });

        menuFichier.getItems().addAll(itemGenererTxt, itemImportFromTxt, separator, itemQuitter);
        menuEdition.getItems().addAll(itemClearTable);

        this.getMenus().addAll(menuFichier, menuEdition);
    }

}
