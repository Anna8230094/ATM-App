package com.example;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GreetingController {

    @FXML
    private PasswordField userPin ;
    @FXML 
    private PasswordField userID;
    @FXML
    private Text errorMessage ;
    @FXML
    private PasswordField focusedField;

    public static Users logginUser;

  
    @FXML
    public void handleButton(@SuppressWarnings("exports") ActionEvent event){
        Button button =(Button)event.getSource();
        String text = button.getText();

        focusedField.setText(focusedField.getText()+ text); 
    }
    
    @FXML
    public void mouseClicked(@SuppressWarnings("exports") javafx.scene.input.MouseEvent event ){
        System.out.println("mouse clicke");
        SVGPath svg = (SVGPath)event.getSource();
        System.out.println(svg.getId());      
    }//I must fix svg functionallity
    
    @FXML
    public void handleEnter(@SuppressWarnings("exports") ActionEvent event) throws IOException {
       
        int id =Integer.parseInt(userID.getText());
        int pin =Integer.parseInt(userPin.getText());
        
        if(!userPin.getText().equals("") && !userID.getText().equals("")){
            try {
                Optional <Users> logginUsers = Users.users.stream().filter(user -> user.getPin()==pin && user.getId()==id).findFirst();
                logginUser = logginUsers.get();
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/AtmMenu.fxml"));
                
                if (loader.getLocation() == null) {
                    throw new RuntimeException("FXML file not found");
                }

                Parent root = loader.load();
                Stage stage = (Stage) userPin.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (NoSuchElementException e){
                System.err.println("This user does not exist!");
                errorMessage.setText("Try again!");
                userPin.setText("");
                userID.setText("");
            }

        }else{
            errorMessage.setText("a necessary firld is empty");
            userPin.setText("");
            userID.setText("");

        }
         
    }
    
    
    @FXML
    public void handleDelete(@SuppressWarnings("exports") ActionEvent event) {
       
        if(focusedField.getText().length()>0 ){
            String newText = focusedField.getText().substring(0,focusedField.getText().length() -1);
            focusedField.setText(newText);
        }
    
    }

    public void initialize() {

        userPin.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            event.consume(); 
        });
        
        userID.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            event.consume();
        });
        
        
        userPin.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                focusedField = userPin;
            }
        });
    
        userID.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                focusedField = userID;
            }
        });      
    } 
}
    


