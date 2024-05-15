package com.laliste;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class MenuManager extends MenuBar{
    
    public MenuManager(Text msg_footer){

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
            msg_footer.setText(msg);
            msg_footer.setStyle("-fx-text-fill: rgb(104, 214, 99);");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> msg_footer.setText("")));
            timeline.play();
        });

        itemImportFromTxt.setOnAction(e->{
            String msg = DatabaseManager.importFromTxt();
            msg_footer.setText(msg);
            msg_footer.setStyle("-fx-text-fill: rgb(104, 214, 99);");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> msg_footer.setText("")));
            timeline.play();
        });
        
        itemClearTable.setOnAction(e->{
            clearTableAlert();
        });

        menuFichier.getItems().addAll(itemGenererTxt, itemImportFromTxt, separator, itemQuitter);
        menuEdition.getItems().addAll(itemClearTable);

        this.getMenus().addAll(menuFichier, menuEdition);
    }

    private void clearTableAlert(){
        Label confirmationText = new Label("Veuillez entrer \"Je confirme\" :");
        TextField confirmationTextField = new TextField();
        Button okButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        Text confirmationErrText = new Text("");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(confirmationText, 0, 0);
        grid.add(confirmationTextField, 1, 0);
        // grid.add(cancelButton, 0, 1);
        grid.add(okButton, 1, 1);
        grid.add(confirmationErrText, 0, 2, 2, 1);

        // Définir les contraintes de la grille pour le champ de texte afin qu'il s'étende horizontalement
        GridPane.setHgrow(confirmationTextField, Priority.ALWAYS);

        // Création de la boîte de dialogue personnalisée
        Alert confirmationDialog = new Alert(Alert.AlertType.NONE);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.getDialogPane().setContent(grid);
        confirmationDialog.getButtonTypes().setAll(cancelButtonType);

        // Définir le comportement du bouton OK
        okButton.setOnAction(e -> {
            String text = confirmationTextField.getText();
            System.out.println("Texte entré: " + text);
            if (text.equals("Je confirme")) {
                DatabaseManager.clearTableLiens();
                System.out.println("Confirmation réussie. Table vidée.");
                confirmationDialog.close();

            } else {
                confirmationErrText.setText("Le texte entré n'est pas valide.");
            }
        });
        

        // Définir le comportement du bouton Annuler
        cancelButton.setOnAction(e -> {
            confirmationDialog.getDialogPane().getScene().getWindow().hide();
        });

        // Afficher la boîte de dialogue
        confirmationDialog.showAndWait();
    
    }
}
