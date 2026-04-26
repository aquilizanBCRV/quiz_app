/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author yuzuki
 */
public class QuizGroup_Query_Data {

    private dbConnector myconn;
    
    private int teacherID;
    private String quizName;
    private boolean hasTime;
    private LocalTime timestamp;
    private LocalDateTime deadline;

    public QuizGroup_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public void createQuizGroup() {
        String insertQuery = """
                             INSERT INTO quizGroup
                             (teacherID,quizName,hasTime,timestamp,deadline)
                             VALUES(?,?,?,?,?)
                             """;
        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(insertQuery);

            prep.setInt(1, teacherID);
            prep.setString(2, quizName);
            prep.setBoolean(3, hasTime);

            if (hasTime) {
                prep.setTime(4, java.sql.Time.valueOf(timestamp));
            } else {
                prep.setNull(4, java.sql.Types.TIME);
            }

            prep.setTimestamp(5, java.sql.Timestamp.valueOf(deadline));

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

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
