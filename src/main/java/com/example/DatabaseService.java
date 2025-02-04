package com.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseService {

    DatabaseConnection dc = new DatabaseConnection();
    public   void modifyBalances(int user_id,double balance)throws SQLException {
        Connection con = dc.connect();

        String query = "UPDATE users SET balance = ? WHERE user_id = ? ";
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, user_id);
            pstmt.executeUpdate();

        }

    }

    public  void insertUser(Users user, double amount, String transaction_type, LocalDate localDate) {
        String SQL_INSERT = "INSERT INTO transactions(account_id, amount, transaction_type, transaction_date) VALUES ( ?, ?, ?, ?)";

        try (Connection conn = new DatabaseConnection().connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {
           
                try {
                    pstmt.setInt(1, user.getId());
                    pstmt.setDouble(2, amount);
                    pstmt.setString(3, transaction_type );
                    pstmt.setDate(4, java.sql.Date.valueOf(localDate));
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error inserting user: " + user.getName() + " - " + e.getMessage());
                }

            System.out.println("User added successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
