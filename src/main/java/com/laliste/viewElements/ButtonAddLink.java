package com.laliste.viewElements;

import java.util.Arrays;

import com.laliste.DatabaseManager;
import com.laliste.Settings;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ButtonAddLink extends Button{
    
    public ButtonAddLink(Text info_text, TextField entry_field){
        super("Ajouter");
        this.setPrefWidth(100);

        this.setOnAction(e->{
            String text_query = entry_field.getText();

            System.out.println("text_query " + text_query);
            boolean is_correct_signature = Arrays.stream(Settings.URL_SIGNATURES).anyMatch(text_query::startsWith);
            // for (String s : Settings.URL_SIGNATURES) {
            //     if(text_query.startsWith(s)){
            //         System.out.println("s " +s);
            //         is_correct_signature = true;
            //         break;
            //     }
            // }

            if(is_correct_signature){
                System.out.println("signature correct");
                String msg_err = DatabaseManager.insertInformation(text_query);
                info_text.setText(msg_err);
            }else{
                info_text.setText("La signature du lien n'est pas correcte.");
            }
            entry_field.clear();
            entry_field.requestFocus();
        });
    }
}
