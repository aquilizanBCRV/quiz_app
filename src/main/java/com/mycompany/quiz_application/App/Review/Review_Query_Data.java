/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.Review;

import com.mycompany.quiz_application.dbConnector;
import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 *
 * @author yuzuki
 */
public class Review_Query_Data {

    private dbConnector myconn;
    private int quizGroupID;
    private int studentID;
    private int teacherID;
    private String quizName;
    private boolean hasTime;
    private int timestamp;
    private LocalDateTime deadline;
    private String roles;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Review_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public ResultSet displayStudentList() {
        return myconn.readQuery("""
         SELECT 
              s.studentID,
              ac.*,
              CONCAT_WS(' ', firstname, middleName, lastname) AS fullname                         
              ,qg.quizGroupID, qg.quizName,
              p.status
          FROM quizGroup qg
          JOIN Student s ON s.teacherID = qg.teacherID
          JOIN accountUser ac ON ac.userID = s.userID
          LEFT JOIN progress p 
              ON p.quizGroupID = qg.quizGroupID 
             AND p.studentID = s.studentID
          AND (p.status = 'done' OR p.status IS NULL OR p.status <> 'done')
        WHERE qg.quizGroupID = ? AND qg.teacherID= ?
        """, new Object[]{quizGroupID, teacherID});
    }

    public ResultSet displayReview() {
        System.out.println(studentID+""+quizGroupID);
        return myconn.readQuery("""
        SELECT 
          	*,-- adjust column name as needed
              COALESCE(a.studentAnswer, 'Not Answered') AS studentAnswer,
              CASE WHEN a.logID IS NULL THEN 'Unanswered' ELSE 'Answered' END AS status
          FROM quiz_application.quizes q
          LEFT JOIN quiz_application.answerLog a 
                 ON q.quizesID = a.quizID 
                AND a.studentID = ?
          WHERE q.quizGroupID = ?
          ORDER BY status, q.quizesID;
        """, new Object[]{studentID,quizGroupID});
    }

    public ResultSet displayQuiz() {
        return myconn.readQuery("""
          SELECT *,
          CONCAT_WS(' ', firstname, middleName, lastname) AS fullname
          FROM progress p
          JOIN quizGroup q ON q.quizGroupID = p.quizGroupID
          JOIN Teacher t ON q.teacherID = t.teacherID
          JOIN accountUser u ON t.userID = u.userID  
          WHERE studentID = ?
          AND status = 'done'
        """, new Object[]{studentID});
    }

    public ResultSet getUnfinished() {
        Object[] params;
        String extraQuery;

        if ("Student".equals(roles)) {
            extraQuery = """
                AND s.studentID = ?
                """;
            params = new Object[]{studentID};

        } else if ("Teacher".equals(roles)) {
            extraQuery = """   
                And qg.teacherID = ?
                """;
            params = new Object[]{teacherID};

        } else {
            // Fallback / error case
            return null;
        }
        return myconn.readQuery("""
                                
     SELECT 
                      COUNT(*) AS unfinished_count
                  FROM quizGroup qg
                  JOIN Student s ON s.teacherID = qg.teacherID
                  JOIN accountUser ac ON ac.userID = s.userID
                  LEFT JOIN progress p 
                      ON p.quizGroupID = qg.quizGroupID 
                     AND p.studentID = s.studentID
                  WHERE (p.status IS NULL OR p.status != 'done')
                  AND published = 1
                  %s
        """.formatted(extraQuery), new Object[]{studentID});

    }

    public dbConnector getMyconn() {
        return myconn;
    }

    public void setMyconn(dbConnector myconn) {
        this.myconn = myconn;
    }

    public int getQuizGroupID() {
        return quizGroupID;
    }

    public void setQuizGroupID(int quizGroupID) {
        this.quizGroupID = quizGroupID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public boolean isHasTime() {
        return hasTime;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    void quizGroupID(int quizGroupID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
