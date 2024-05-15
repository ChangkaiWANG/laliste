package com.laliste.viewElements;

import javafx.scene.control.TextField;

public class RandLinkTextField extends TextField{
    
    public RandLinkTextField(String initialText){
        super(initialText);
        this.setEditable(false);
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setPrefWidth(200);
    }

    public RandLinkTextField(String initialText, int maxWidth){
        super(initialText);
        this.setEditable(false);
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setMaxWidth(maxWidth);

    }
}
