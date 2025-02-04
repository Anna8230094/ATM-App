package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DepositController {

    public DatabaseService ds = new DatabaseService() ;
    
    @FXML
    private TextField textfield ;

    @FXML 
    private CheckBox checkBox;

    @FXML
    private Text errorMessage;
   
    @FXML
    public void handleButton(@SuppressWarnings("exports") ActionEvent event){
        Button button = (Button)event.getSource();
        String text = button.getText();

        textfield.setText(textfield.getText() + text);   
    }
    
    @FXML
    public void handleDelete(@SuppressWarnings("exports") ActionEvent event) {

        if(textfield.getText().length()>0){
            String newText = textfield.getText().substring(0,textfield.getText().length() -1);
            textfield.setText(newText);
       
        }
    }

    @FXML
    public void handleClear(@SuppressWarnings("exports") ActionEvent event) {
       Button button = (Button)event.getSource();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/AtmMenu.fxml"));
            
            if (loader.getLocation() == null) {
                throw new RuntimeException("FXML file not found");
            }

            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Ioexception");
        }
    }

    
    
    @FXML
    public void handleEnter(@SuppressWarnings("exports") ActionEvent event) throws IOException, SQLException {
       
        Users logginUser = GreetingController.logginUser;
        
        if(!textfield.getText().equals("")&& checkBox.isSelected() ){
            
            Button button = (Button) event.getSource();
            double amount = Double.parseDouble(textfield.getText());

            AtmClass.atmClass.deposit(amount);
            logginUser.deposit(amount);

            ds.modifyBalances(logginUser.getId(), logginUser.getBalance());
            ds.insertUser(logginUser,amount, "deposit", LocalDate.now());

            //Check if the balance of bank and user has changed 
            System.out.println("Bank balance: "+ AtmClass.atmClass.getBalance());
            System.out.println("User balance: " + logginUser.getBalance());
          
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/Result.fxml"));

            if (loader.getLocation() == null) {
                throw new RuntimeException("FXML file not found");
            }
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();  

            ResultController resultController = loader.getController();
            resultController.setResultMessage("Deposit amount is:"+textfield.getText()+"\nThanks for your support!");

        }else{
            errorMessage.setText("A necessary field is empty!");
        }
    }

    public void initialize() {
        textfield.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            event.consume(); 
        });   
    }
        

}
