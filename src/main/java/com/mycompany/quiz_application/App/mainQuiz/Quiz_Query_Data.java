/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.dbConnector;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yuzuki
 */
public class Quiz_Query_Data extends QuizGroup_Query_Data {

    private dbConnector myconn;
    private int quizID;
    private int quizGroupID;
    private String quizType;
    private String displayQuestion;
    private ArrayList questionList;
    private String quizAnswer;

    public Quiz_Query_Data(dbConnector conn) {
        super(conn);
        this.myconn = conn;
    }

    public void DeleteQuery() {
        String deleteQuery = """
                     DELETE FROM quizes WHERE quizesID = ?;
                     """;
        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(deleteQuery);

            prep.setInt(1, quizID);

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

    public ResultSet displaySpecifyQuiz() {
        String selectQuery = """
        SELECT * FROM quiz_application.quizes 
        WHERE quizesID = ?
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            prep.setInt(1, quizID);

            ResultSet rs = prep.executeQuery();
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public ResultSet displayQuiz(String q) {
        String selectQuery = """
        SELECT * FROM quiz_application.quizes 
        INNER JOIN quizGroup 
        ON quizes.quizGroupID = quizGroup.quizGroupID
        WHERE quizGroup.quizGroupID = ?;
        """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(selectQuery);
            prep.setInt(1, quizGroupID);

            ResultSet rs = prep.executeQuery();
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public void updateQuiz() {
        String updateQuery = """
                           UPDATE quizes
                             SET 
                                 quizGroupID = ?,
                                 quizType = ?,
                                 displayQuestion = ?,
                                 questionList = ?,
                                 quizAnswer = ?
                             WHERE quizesID = ?     
                             """;

        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(updateQuery);

            prep.setInt(1, quizGroupID);
            prep.setString(2, quizType);
            prep.setString(3, displayQuestion);

            prep.setString(4, String.join(",", questionList));
            prep.setString(5, quizAnswer);

            prep.setInt(6, quizID);

            int rowsInserted = prep.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert successful");
                quizType = "";
                displayQuestion = "";
                questionList = new ArrayList<>();
                quizAnswer = "";

            } else {
                System.out.println("Insert failed");
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void createQuiz() {
        String insertQuery = """
                             INSERT INTO quizes
                             (quizGroupID,quizType,displayQuestion,questionList,quizAnswer)
                             VALUES(?,?,?,?,?)
                             """;
        try {
            myconn.connect();
            PreparedStatement prep = myconn.con.prepareStatement(insertQuery);

            prep.setInt(1, quizGroupID);
            prep.setString(2, quizType);
            prep.setString(3, displayQuestion);

            prep.setString(4, String.join(",", questionList));
            prep.setString(5, quizAnswer);

            int rowsInserted = prep.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert successful");
                quizType = "";
                displayQuestion = "";
                questionList = new ArrayList<>();
                quizAnswer = "";

            } else {
                System.out.println("Insert failed");
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ResultSet getDisplayCreatedQuiz() {
        return null;
    }

    public void setQuizGroupID(int quizGroupID) {
        this.quizGroupID = quizGroupID;
    }

    public void setQuizID(int quizGroupID) {
        this.quizID = quizGroupID;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public void setDisplayQuestion(String displayQuestion) {
        this.displayQuestion = displayQuestion;
    }

    public void setQuestionList(ArrayList questionList) {
        this.questionList = questionList;
    }

    public void setQuizAnswer(String quizAnswer) {
        this.quizAnswer = quizAnswer;
    }

}
