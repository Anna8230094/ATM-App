package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultController {
    @FXML 
    private  Text resultMessage;


    public void setResultMessage(String text){
        resultMessage.setText(text);
    }

   
    public String getResultMessage(){
       return  resultMessage.getText();
    }

    @FXML
    public void handleEvent(@SuppressWarnings("exports") ActionEvent event){
        Button button =  (Button)event.getSource();
        String text = button.getId();

        System.out.println(text);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/"+ text +".fxml"));
            
            if (loader.getLocation() == null) {
                throw new RuntimeException("FXML file not found");
            }

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Ioexception");
        }
    }
        
}
