/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.Quiz;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.time.LocalTime;

/**
 *
 * @author yuzuki
 */
public class QuizLog extends Quiz {

    private int quizID;
    private int studentID;
    private String studentAnswer;
    private LocalTime timestamp;
    private dbConnector myconn;

    public QuizLog(dbConnector conn) {
        super(conn);
        this.myconn = conn;
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

}
