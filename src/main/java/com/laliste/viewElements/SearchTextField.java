package com.laliste.viewElements;

import javafx.scene.control.TextField;

public class SearchTextField extends TextField{
    
    public SearchTextField(String initialText, String promptText, int maxWidth){
        super(initialText);
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setPromptText(promptText);
        // this.setPrefWidth(200);
        this.setMaxWidth(maxWidth);

        this.setOnAction(e->{

        });

    }
}
