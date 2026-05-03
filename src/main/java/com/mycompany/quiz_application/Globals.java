package com.mycompany.quiz_application;

public class Globals {

    private static Globals instance = new Globals();

    private int studentID = 0;
    private int teacherID = 0;

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
    private int quizGroupID = 0;
    private int quizID = 0;

    private String roles;

    private String firstname;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    private String queryMode = "";

    public Globals() {
    }

    public static Globals getInstance() {
        return instance;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getQuizGroupID() {
        return quizGroupID;
    }

    public void setQuizGroupID(int quizGroupID) {
        this.quizGroupID = quizGroupID;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }
}
