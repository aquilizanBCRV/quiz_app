/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quiz_application.App.Quiz;

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
public class Quiz extends QuizGroup {

    private dbConnector myconn;
    private int quizGroupID;
    private String quizType;
    private String displayQuestion;
    private String[] questionList;
    private String quizAnswer;

    public Quiz(dbConnector conn) {
        super(conn);
        this.myconn = conn;
    }

    public ResultSet displayQuiz() {

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
            } else {
                System.out.println("Insert failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setQuizGroupID(int quizGroupID) {
        this.quizGroupID = quizGroupID;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public void setDisplayQuestion(String displayQuestion) {
        this.displayQuestion = displayQuestion;
    }

    public void setQuestionList(String[] questionList) {
        this.questionList = questionList;
    }

    public void setQuizAnswer(String quizAnswer) {
        this.quizAnswer = quizAnswer;
    }

}
