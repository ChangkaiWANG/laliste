package com.laliste;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

import java.io.FileWriter;
import java.io.IOException;
import javafx.util.Duration;

import javafx.scene.input.ClipboardContent;

import com.laliste.viewElements.*;


/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stagePrincipal) throws IOException {

        Label mainMessage = new Label("Bienvenue sur l'application !");
        Text footer_msg = new Text("");
        TextField textField_randLink = new RandLinkTextField("",300);
        TextField searchField = new SearchTextField("","Entrez le lien ...", 300);
        Button buttonSoumet = new ButtonAddLink(footer_msg, searchField);
        Button buttonRand = new Button("Generer un lien aleatoire");

        searchField.setOnAction(e->{//ajouter un lien en appuyant sur la touche entrée
            buttonSoumet.fire();
        });

        Timeline autosaveTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e->{//sauvegarde automatique
            buttonSoumet.fire();
        }));
        autosaveTimeline.setCycleCount(1);
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.isEmpty()){
                return;
            }
            autosaveTimeline.stop();
            autosaveTimeline.playFromStart();
        });

        buttonRand.setOnAction(e->{
            textField_randLink.clear();
            String lien = DatabaseManager.getRandLink();
            if (lien == null) {
                mainMessage.setText("Aucun lien dans la base de données !");
            }
            else{
                textField_randLink.setText(lien);
                textField_randLink.requestFocus();
                textField_randLink.selectAll();
                //copier le lien dans le presse-papier
                ClipboardContent content = new ClipboardContent();
                content.putString(lien);
                Clipboard.getSystemClipboard().setContent(content);
            }
        });

        /* ============================================= *
         *              MARK: Layout                     * 
         * ============================================= */

        BorderPane rootPane = new BorderPane();
        HBox footer = new HBox();
        VBox mainPane = new VBox(mainMessage); 

        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(10);
        mainPane.getChildren().addAll(searchField, buttonSoumet, buttonRand, textField_randLink);

        rootPane.setTop(new MenuManager(footer_msg));
        rootPane.setCenter(mainPane);
        rootPane.setBottom(footer);

        footer_msg.setTranslateX(10);
        footer.getChildren().add(footer_msg);

        Scene mainScene = new Scene(rootPane, 640, 480);

        stagePrincipal.centerOnScreen();//centrer la fenêtre sur l'écran
        stagePrincipal.setScene(mainScene);
        stagePrincipal.setTitle("La Liste");
        stagePrincipal.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}