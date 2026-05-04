package com.mycompany.quiz_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnector {

    private final String url = "jdbc:mysql://localhost:3306/quiz_application";
    private final String username = "root";
    private final String password = "Gahatameiji12";

    public Connection con;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println("Failed to Connect");
            e.printStackTrace();
        }
    }

    public ResultSet readQuery(String query, Object[] params) {
        connect();
        try {
            if (con == null) {
                throw new IllegalStateException("Database not connected. Call connect() first.");
            }

            PreparedStatement pst = con.prepareStatement(query);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            return pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query, Object[] params) {
        connect();

        try {
            if (con == null) {
                throw new IllegalStateException("Database not connected.");
            }

            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1); // generated ID
            }

            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Connection getConnection() {
        return con;
    }
}
