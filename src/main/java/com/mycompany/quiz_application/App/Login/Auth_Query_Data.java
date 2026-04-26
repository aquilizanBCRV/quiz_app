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

    public Auth_Query_Data(dbConnector conn ) {
        this.myconn = conn;
    }

    public boolean checkLogin() {
        try {
            String loginQuery = """
                            SELECT username, password, userID from accountUser
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
            if(result.next()) {
                this.userID = result.getInt("userID");
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {   
            System.out.println(e);
                return false;
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
    
    public int getUserID() {
        return userID;
    }
}
