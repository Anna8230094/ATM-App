package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseConnection  {

        private static final String URL = "jdbc:postgresql://localhost:5432/atm_db";
        private static final String USER = "anna"; 
        private static final String PASSWORD = "18.11.04"; 
    
        @SuppressWarnings("exports")
        public  Connection connect() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the PostgreSQL server successfully.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }
    }
    
