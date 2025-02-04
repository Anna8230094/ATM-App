package com.example;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

 

/**
 * JavaFX App
 */
public class AtmApp extends Application {

    
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        
        DatabaseConnection databaseConnection = new DatabaseConnection();

        AtmClass.atmClass = new AtmClass(100000000);
        
        PreparedStatement p ;
        ResultSet rs ;

        try {
 
            String sql = "SELECT * FROM users";
            p = databaseConnection.connect().prepareStatement(sql);
            rs = p.executeQuery();

            while (rs.next()) {

                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String account_number = rs.getString("account_number");
                int pin = rs.getInt("pin");
                double balance = rs.getDouble("balance");

                System.out.println(user_id + "\t\t" + username + "\t\t" +  account_number + "\t\t" + pin + "\t\t" + balance);
                Users.users.add(new Users(username, account_number, pin, balance, user_id));
            }

        }catch (SQLException e) {
            System.out.println(e);
        }
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/FXML/Greeting.fxml"));

        if (loader.getLocation() == null) {
            throw new RuntimeException("FXML file not found");
        }
                
        Parent root = loader.load();
        stage.setTitle("ATM Application");
        stage.setScene(new Scene(root));
        stage.show();
        
        System.out.println(Users.users);

    }
   
    public static void main(String[] args) throws ClassNotFoundException {  
        launch();
    }

}
