/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.mainQuiz.Quizes;

import com.mycompany.quiz_application.App.mainQuiz.Quiz_Query_Data;
import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;

/**
 *
 * @author yuzuki
 */
public class QuizLog_Query_Data extends Quiz_Query_Data {

    private int quizID;
    private int studentID;
    private String studentAnswer;
    private LocalTime timestamp;
    private dbConnector myconn;
    private int quizGroupID;
    private int counter;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public QuizLog_Query_Data(dbConnector conn) {
        super(conn);
        this.myconn = conn;
    }

    public void saveProgress() {
        String sql = """
    INSERT INTO progress (
        studentID,
        quizGroupID,
        quizCounter,
        currentTimestamp,
        status
    )
    VALUES (
        ?,
        ?,
        ?,
        ?,
        ?
    )
    ON DUPLICATE KEY UPDATE
        quizCounter = VALUES(quizCounter),
        currentTimestamp = VALUES(currentTimestamp),
        status = VALUES(status);
    """;

        try (PreparedStatement ps = myconn.con.prepareStatement(sql)) {

            ps.setInt(1, studentID);
            ps.setInt(2, quizGroupID);
            ps.setInt(3, counter);

            // Use Timestamp instead of Time
            ps.setTime(4, java.sql.Time.valueOf(timestamp));
            ps.setString(5, status);

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ResultSet setProgress() {
        String selectQuery = """
       SELECT * FROM progress
        WHERE studentID = ?
        AND quizGroupID = ?
                         ;
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            prep.setInt(1, studentID);
            prep.setInt(2, quizGroupID);
            System.out.println(quizGroupID);
            System.out.println(studentID);
            ResultSet rs = prep.executeQuery();
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void createQuiz() {
        String insertQuery = """
                             INSERT INTO answerLog
                             (quizID,studentID,studentAnswer,timestamp)
                             VALUES(?,?,?,?)
                             """;
        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(insertQuery);

            prep.setInt(1, quizID);
            prep.setInt(2, studentID);
            prep.setString(3, studentAnswer);
            prep.setTime(4, java.sql.Time.valueOf(timestamp));

            int rowsInserted = prep.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert successful");
            } else {
                System.out.println("Insert failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet displayResult() {
        String selectQuery = """
       SELECT * FROM quiz_application.answerLog 
       inner join quizes 
       ON quizes.quizesID = answerLog.quizID
        WHERE quizes.quizGroupID = ?
        AND answerLog.studentID = ?
                             ;
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            prep.setInt(1, quizGroupID);
            prep.setInt(2, studentID);
            ResultSet rs = prep.executeQuery();
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setquizGroupID(int quizGroupID) {
        this.quizGroupID = quizGroupID;
    }
}
