package com.mycompany.quiz_application.App.addAccount;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageAccount_Query_Data {

    private final dbConnector myconn;

    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String middlename;
    private String role;
    private int teacherID;

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public ManageAccount_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public void createUser() {

        String query;

        int keys = myconn.executeUpdate("""
                    INSERT INTO accountUser
                    (username, password, firstname, middleName, lastname, roles)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """,
                new Object[]{username, password, firstname, middlename, lastname, role});

        if ("Student".equals(role)) {
            myconn.executeUpdate("""
                                 
                    INSERT INTO Student(userID,teacherID) VALUES(?,?)
                                 """, new Object[]{keys, teacherID});
        } else if ("Teacher".equals(role)) {
            myconn.executeUpdate("""
       
                    INSERT INTO Teacher(userID) VALUES(?)
                                 """, new Object[]{keys});
        }

    }

    public void deleteUser() {

        try {

            myconn.executeUpdate("""
                DELETE FROM Student  
                WHERE userID = ?
                """, new Object[]{userID});

            myconn.executeUpdate("""
                DELETE FROM Teacher  
                WHERE userID = ?
                """, new Object[]{userID});

            int keys = myconn.executeUpdate("""
                DELETE FROM accountUser  
                WHERE userID = ?
                """, new Object[]{userID});

            if (keys > 0) {
                System.out.println("User deleted successfully: " + userID);
            } else {
                System.out.println("No user found with ID: " + userID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser() {

        String query;

        int keys = myconn.executeUpdate("""
                    UPDATE accountUser SET
                    username=?, password=?, firstname=?, middleName=?, lastname=?, roles=?    
                    WHERE userID = ?
                    """,
                new Object[]{username, password, firstname, middlename, lastname, role, userID});

        if ("Student".equals(role)) {
            myconn.executeUpdate("""
                    Update Student SET teacherID = ? WHERE userID = ?
                                 """, new Object[]{teacherID, userID});
        }

    }

    public ResultSet getSpecifyRecords() {
        String query;
        Object[] obj = null;
        if ("Student".equals(role)) {
            query = """
                SELECT * FROM accountUser au
                JOIN Student s ON s.userID = au.userID
                WHERE au.userID = ?
            """;
            obj = new Object[]{userID};

        } else if ("Teacher".equals(role)) {
            query = """
                SELECT * FROM accountUser au
                JOIN Teacher s ON s.userID = au.userID
                WHERE au.userID = ?
            """;
            obj = new Object[]{userID};

        } else {
            query = """
                SELECT * FROM accountUser au
                WHERE au.userID = ?
            """;
            obj = new Object[]{userID};

        }
        return myconn.readQuery(query, obj);
    }

    public ResultSet displayRecords() {

        String query;

        Object[] obj = null;
        if ("Student".equals(role)) {
            query = """
                SELECT 
                    s.userID,
                    CONCAT(COALESCE(tu.firstname,''), ' ', COALESCE(tu.middlename,''), ' ', COALESCE(tu.lastname,'')) AS student_name,
                    CONCAT(COALESCE(au.firstname,''), ' ', COALESCE(au.middlename,''), ' ', COALESCE(au.lastname,'')) AS teacher_name,
                    au.username
                FROM Student s
                JOIN Teacher t ON s.teacherID = t.teacherID
                JOIN accountUser au ON t.userID = au.userID
                JOIN accountUser tu ON s.userID = tu.userID
            """;

        } else if ("Teacher".equals(role)) {
            query = """
                SELECT au.*, t.teacherID as teacherID,
                       CONCAT(COALESCE(au.firstname,''), ' ', COALESCE(au.middlename,''), ' ', COALESCE(au.lastname,'')) AS name
                FROM accountUser au
                JOIN Teacher t ON t.userID = au.userID
            """;

        } else if ("Admin".equals(role)) {
            query = """
                SELECT au.*, 
                       CONCAT(COALESCE(au.firstname,''), ' ', COALESCE(au.middlename,''), ' ', COALESCE(au.lastname,'')) AS name
                FROM accountUser au
                WHERE roles = 'Admin'
                AND userID != ?
            """;
            obj = new Object[]{userID};

        } else {
            query = """
                SELECT au.*, 
                       CONCAT(COALESCE(au.firstname,''), ' ', COALESCE(au.middlename,''), ' ', COALESCE(au.lastname,'')) AS name
                FROM accountUser au
               WHERE userID != ?
            """;

            obj = new Object[]{userID};
        }

        return myconn.readQuery(query, obj);
    }

    // Getters and Setters
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
