/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.addAccount;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author yuzuki
 */
public class ManageAccount_Query_Data {

    private dbConnector myconn;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String middlename;
    private String role;

    ManageAccount_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public void createUser() {
        String insertQuery = """
                             INSERT INTO accountUser
                             (username,password,firstname,middleName,lastname,roles)
                             VALUES(?,?,?,?,?,?)
                             """;
        try {
            PreparedStatement prep = myconn.con.prepareStatement(insertQuery);
            prep.setString(1, username);
            prep.setString(2, password);
            prep.setString(3, username);
            prep.setString(4, firstname);
            prep.setString(5, middlename);
            prep.setString(6, role);
            prep.execute();

            int rowsInserted = prep.executeUpdate();

            if (rowsInserted > 0) {

            }
        } catch (Exception e) {
        }
    }

    public void updateUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
