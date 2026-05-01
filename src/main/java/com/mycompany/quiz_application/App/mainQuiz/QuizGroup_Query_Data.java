package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class QuizGroup_Query_Data {

    private dbConnector myconn;
    private int quizGroupID;
    private int teacherID;
    private String quizName;
    private boolean hasTime;
    private int timestamp;
    private LocalDateTime deadline;

    public QuizGroup_Query_Data(dbConnector conn) {
        this.myconn = conn;
    }

    public void createQuizGroup() {
        String insertQuery = """
                             INSERT INTO quizGroup
                             (teacherID, quizName, hasTime, timestamp, deadline)
                             VALUES(?,?,?,?,?)
                             """;

        try {
            myconn.connect();

            try (PreparedStatement prep = myconn.con.prepareStatement(insertQuery)) {

                prep.setInt(1, teacherID);
                prep.setString(2, quizName);
                prep.setBoolean(3, hasTime);
                prep.setInt(4, timestamp);

                if (deadline != null) {
                    prep.setDate(5, java.sql.Date.valueOf(deadline.toLocalDate()));
                } else {
                    prep.setNull(5, java.sql.Types.DATE);
                }

                int rowsInserted = prep.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Insert successful");
                } else {
                    System.out.println("Insert failed");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateQuizGroup() {
        String updateQuery = """
                         UPDATE quizGroup
                         SET teacherID = ?, 
                             quizName = ?, 
                             hasTime = ?, 
                             timestamp = ?, 
                             deadline = ?
                         WHERE quizGroupID = ?
                         """;

        try {
            myconn.connect();

            try (PreparedStatement prep = myconn.con.prepareStatement(updateQuery)) {

                prep.setInt(1, teacherID);
                prep.setString(2, quizName);
                prep.setBoolean(3, hasTime);

             
                prep.setInt(4, timestamp);

                if (deadline != null) {
                    prep.setDate(5, java.sql.Date.valueOf(deadline.toLocalDate()));
                } else {
                    prep.setNull(5, java.sql.Types.DATE);
                }

                // WHERE condition parameter
                prep.setInt(6, quizGroupID);

                int rowsUpdated = prep.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Update successful");
                } else {
                    System.out.println("No record updated");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteQuizGroup() {
        String deleteQuery = """
                     DELETE FROM quizGroup WHERE quizGroupID = ?;
                     """;
        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(deleteQuery);

            prep.setInt(1, quizGroupID);

            int rowsInserted = prep.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Delete successful");

            } else {
                System.out.println("Delete failed");
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ResultSet getQuizGroup() {
        String selectQuery = """
            SELECT *
            FROM quizGroup 
            WHERE quizGroupID = ?
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            System.out.println("QUiz Group Query, " + quizGroupID);
            prep.setInt(1, quizGroupID);
            return prep.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet displayQuiz() {
        String selectQuery = """
            SELECT *,
            CONCAT_WS(' ', firstname, middleName, lastname) AS fullname
            FROM quizGroup q
            JOIN quiz_application.Teacher t ON q.teacherID = t.teacherID
            JOIN accountUser u ON t.userID = u.userID
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            return prep.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return null;
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

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setQuizGroupID(int quizGroupID) {
        System.out.println("Set Quiz ID group, " + quizGroupID);
        this.quizGroupID = quizGroupID;
    }
}
