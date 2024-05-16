package com.laliste.viewElements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogClearConfirmation extends Stage{

    private String result;
    
    public DialogClearConfirmation(Stage stagePrincipal){
        super();
        this.initOwner(stagePrincipal);
        this.setTitle("Confirmation de suppression");

        Label info_text = new Label("Veuillez entrer \"Je confirme\" :");
        TextField confirmationText = new TextField();
        Text errText = new Text("");
        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        cancelButton.setOnAction(e->{
            this.result = "cancel";
            this.close();
        });

        confirmButton.setOnAction(e->{
            if(confirmationText.getText().equals("Je confirme")){
                this.result = "ok";
                this.close();
            }else{
                errText.setText("Le texte entré n'est pas valide.");
            }
        });

        confirmationText.setOnAction(e->{
            confirmButton.fire();
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20));

        grid.add(info_text, 0, 0);
        grid.add(confirmationText, 1, 0);
        grid.add(errText, 0, 1, 2, 1);
        grid.add(cancelButton, 0, 2);
        grid.add(confirmButton, 1, 2);

        this.setScene(new javafx.scene.Scene(grid));
        this.setResizable(false);
        this.setFullScreen(false);

    }

    public String getResult(){
        return this.result;
    }
}
