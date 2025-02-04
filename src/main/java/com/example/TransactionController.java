package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

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

public class TransactionController {
    
    @FXML
    private CheckBox checkBox;

    @FXML
    private Text errorMessage;

    @FXML
    private TextField textField;

    @FXML
    private TextField iban;
    
    @FXML
    private TextField focusedField;

    public DatabaseService ds = new DatabaseService();

    @FXML
    public void handleButton(@SuppressWarnings("exports") ActionEvent event) {
        Button button = (Button)event.getSource();
        String text = button.getText();
        focusedField.setText(focusedField.getText()+ text);
    }

    @FXML
    public void handleDelete(@SuppressWarnings("exports") ActionEvent event) {
        if(focusedField.getText().length()>0){
            String newText = focusedField.getText().substring(0,focusedField.getText().length() -1);
            focusedField.setText(newText);
        }
    }

    @FXML
    public void handleClear(@SuppressWarnings("exports") ActionEvent event) {
       
        Button button = (Button)event.getSource();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/AtmMenuController.fxml"));
            
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
       
        Button button = (Button) event.getSource();
        double amount  = Double.parseDouble(textField.getText());
        String iBan = iban.getText();
        Users userLog = GreetingController.logginUser;
        
        
        boolean exist = Users.users.stream().anyMatch(user-> user.getAccount_number().contains(iBan));
        
        if(checkBox.isSelected()  && !iban.getText().equals("") && !textField.getText().equals("") && !iban.getText().equals(userLog.getAccount_number())){
            if(exist){
                
                Optional<Users> matchingUser = Users.users.stream().filter(user -> user.getAccount_number().contains(iBan)).findFirst();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/Result.fxml"));

                if (loader.getLocation() == null) {
                    throw new RuntimeException("FXML file not found");
                }

                Parent root = loader.load();
                ResultController resultController = loader.getController();
            
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                if(amount<=500 && userLog.getBalance()>=amount) {
                    resultController.setResultMessage("Withdrawal successful! Amount: " + amount);
                    System.out.println("Recipient first balance: " + matchingUser.get().getBalance());
                    
                    matchingUser.get().deposit(amount);
                    System.out.println("Recipient last balance: " + matchingUser.get().getBalance());
                
                    userLog.withdrawal(amount);
                    System.out.println("The balnce of user is: " + userLog.getBalance());

                    ds.modifyBalances(userLog.getId(), userLog.getBalance());
                    ds.modifyBalances(matchingUser.get().getId(), matchingUser.get().getBalance());
                    ds.insertUser(userLog, amount, "transaction", LocalDate.now());
                    ds.insertUser(matchingUser.get(), amount, "credit", LocalDate.now());

                }else if(amount>500){
                    resultController.setResultMessage("Withdrawal is not Succesful!Daily limit is 500!");
                }else{
                     resultController.setResultMessage("Your balance is less than transactional amount!");
                }
            }else{
            errorMessage.setText("This user does not exist!");
            }
        }else{
            errorMessage.setText("A necessary  field is missing!");
        }
       
    }

    public void initialize() {

        textField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            event.consume(); 
        });

        iban.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            event.consume(); 
        });

        iban.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                focusedField = iban;
            }
        });
    
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                focusedField = textField;
            }
        });
    }
          
}
