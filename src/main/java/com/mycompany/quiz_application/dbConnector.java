/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application;


import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnector {
	
	private String url = "jdbc:mysql://localhost:3306/quiz_application";
	private String username = "root";
	private String password = "Gahatameiji12";
	
	public Connection con;
	
	
	public void connect() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			
			
		}catch(Exception e) {
			System.err.println("Failed to Connect");
			e.printStackTrace();
		}
	}

}
