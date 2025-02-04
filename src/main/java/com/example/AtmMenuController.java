package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class AtmMenuController {
    
    @FXML
    public void handleMenuSelection(@SuppressWarnings("exports") ActionEvent event) throws IOException{
        Button button = (Button)event.getSource();
        String text =  button.getId(); 
        Users loggUsers = GreetingController.logginUser;
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/"+text+".fxml"));
        if (loader.getLocation() == null) {
            throw new RuntimeException("FXML file not found");
        } 
        Parent root = loader.load();
        System.out.println(text);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        if(text.equals("AccountCheck")){
            AccountCheckController accountCheckController = loader.getController();
            accountCheckController.setText("User:"+ loggUsers.getName()+ "\naccount number:"+ loggUsers.getAccount_number() + "\nbalance:" + loggUsers.getBalance());
        }
           
       
    }

}



