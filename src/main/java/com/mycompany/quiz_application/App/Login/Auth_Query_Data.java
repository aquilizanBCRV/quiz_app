/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.Login;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author yuzuki
 */
public class Auth_Query_Data {

    private dbConnector myconn;
    private int userID;
    private String username;
    private String password;
    private String roles;

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Auth_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public ResultSet getInfo() {
        try {

            String tableName = "";

            if (roles.equals("Student")) {
                tableName = "Student";
            } else if (roles.equals("Teacher")) {
                tableName = "Teacher";
            } else {
                throw new IllegalArgumentException("Invalid role");
            }

           String loginQuery = """
        SELECT *
        FROM %s
        INNER JOIN accountUser
            ON accountUser.userID = %s.userID
        WHERE accountUser.userID = ?
        """.formatted(tableName, tableName);

            myconn.connect();

            PreparedStatement prep = myconn.con.prepareStatement(loginQuery);

            prep.setInt(1, userID);

            return prep.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet checkLogin() {
        try {
            String loginQuery = """
                            SELECT * from accountUser
                            WHERE username = ?
                            AND password = ?
                            LIMIT 1
                            """;
            myconn.connect();
            String select = "SELECT * from accounts where account_number = ?";
            PreparedStatement prep = myconn.con.prepareStatement(loginQuery);
            prep.setString(1, username);
            prep.setString(2, password);

            ResultSet result = prep.executeQuery();

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //USERNAME
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //PASSWORD
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}
