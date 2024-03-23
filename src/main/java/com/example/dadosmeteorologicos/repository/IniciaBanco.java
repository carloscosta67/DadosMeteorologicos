package com.example.dadosmeteorologicos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IniciaBanco {


    public void iniciarBanco(){
    String url = "jdbc:postgresql://localhost/ApiFatec";
        String user = "postgres";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
