package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Objects;

public class QuizGroup_Query_Data {

    private final dbConnector myconn;

    // Fields
    private int quizGroupID;
    private int teacherID;
    private String quizName;
    private boolean hasTime;
    private int timestamp;
    private int studentID;
    private LocalDateTime deadline;
    private String roles;

    public QuizGroup_Query_Data(dbConnector conn) {
        this.myconn = Objects.requireNonNull(conn, "Connector cannot be null");
    }

    // ====================== CRUD Operations ======================

    public void createQuizGroup() {
        String sql = """
            INSERT INTO quizGroup (teacherID, quizName, hasTime, timestamp, deadline)
            VALUES (?, ?, ?, ?, ?)
            """;

        try {
            myconn.connect();
            try (PreparedStatement prep = myconn.con.prepareStatement(sql)) {
                prep.setInt(1, teacherID);
                prep.setString(2, quizName);
                prep.setBoolean(3, hasTime);
                prep.setInt(4, timestamp);
                setDateOrNull(prep, 5, deadline);

                int rows = prep.executeUpdate();
                System.out.println(rows > 0 ? "Quiz group created successfully" : "Failed to create quiz group");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuizGroup() {
        String sql = """
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
            try (PreparedStatement prep = myconn.con.prepareStatement(sql)) {
                prep.setInt(1, teacherID);
                prep.setString(2, quizName);
                prep.setBoolean(3, hasTime);
                prep.setInt(4, timestamp);
                setDateOrNull(prep, 5, deadline);
                prep.setInt(6, quizGroupID);

                int rows = prep.executeUpdate();
                System.out.println(rows > 0 ? "Quiz group updated successfully" : "No record updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuizGroup() {
        myconn.executeUpdate("DELETE FROM quizGroup WHERE quizGroupID = ?", 
                            new Object[]{quizGroupID});
    }

    public ResultSet getQuizGroup() {
        return myconn.readQuery("""
            SELECT * FROM quizGroup WHERE quizGroupID = ?
            """, new Object[]{quizGroupID});
    }

    public void publish() {
        myconn.executeUpdate("""
            UPDATE quizGroup SET published = 1 WHERE quizGroupID = ?
            """, new Object[]{quizGroupID});
    }

    // ====================== Display / Query ======================

    public ResultSet displayQuiz() {
        String baseQuery = """
        SELECT 
            	q.quizGroupID,
                q.quizName,
                -- Teacher Name
                CONCAT(
                    tUser.firstname, ' ',
                    tUser.lastname
                ) AS fullname,
            
                -- Student Name
                CONCAT(
                    sUser.firstname, ' ',
                    sUser.lastname
                ) AS studentName,
            
                s.studentID,
                q.published,
                q.hasTime, q.timestamp,
            
                -- If no progress record, display Not Done
                COALESCE(p.status, 'Not Done') AS quizStatus
            
            FROM Student s
            
            -- Student Account
            INNER JOIN accountUser sUser
                ON s.userID = sUser.userID
            
            -- Teacher
            INNER JOIN Teacher t
                ON s.teacherID = t.teacherID
            
            -- Teacher Account
            INNER JOIN accountUser tUser
                ON t.userID = tUser.userID
            
            -- Show all quiz groups
            CROSS JOIN quizGroup q
            
            -- Progress may or may not exist
            LEFT JOIN progress p
                ON p.studentID = s.studentID
                AND p.quizGroupID = q.quizGroupID
            %s
            ORDER BY q.quizName, studentName;
            """;

        Object[] params;
        String extraQuery;

        if ("Student".equals(roles)) {
            extraQuery = """
                
                WHERE q.published = 1
                AND (p.status IS  NULL OR p.status = "saved")
                AND s.studentID = ?
                """;
            params = new Object[]{studentID};
            System.err.println(studentID);

        } else if ("Teacher".equals(roles)) {
            extraQuery = """
                WHERE t.teacherID = ?
                """;
            params = new Object[]{teacherID};

        } else {
            // Fallback / error case
            return null;
        }

        return myconn.readQuery(baseQuery.formatted(extraQuery), params);
    }

    // ====================== Helper Methods ======================

    private void setDateOrNull(PreparedStatement prep, int parameterIndex, LocalDateTime dateTime) throws Exception {
        if (dateTime != null) {
            prep.setDate(parameterIndex, java.sql.Date.valueOf(dateTime.toLocalDate()));
        } else {
            prep.setNull(parameterIndex, Types.DATE);
        }
    }

    // ====================== Setters ======================

    public void setTeacherID(int teacherID) { this.teacherID = teacherID; }
    public void setQuizName(String quizName) { this.quizName = quizName; }
    public void setHasTime(boolean hasTime) { this.hasTime = hasTime; }
    public void setTimestamp(int timestamp) { this.timestamp = timestamp; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public void setQuizGroupID(int quizGroupID) { this.quizGroupID = quizGroupID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    public void setRoles(String roles) { this.roles = roles; }

    // ====================== Getters ======================

    public String getRoles() { return roles; }
}