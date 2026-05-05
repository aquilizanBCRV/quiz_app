package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.App.Login.Dashboard;
import static com.mycompany.quiz_application.App.mainQuiz.ListOfQuizes.id;
import com.mycompany.quiz_application.App.mainQuiz.Quizes.appQuiz;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ListOfGroupQuizes extends JFrame {

    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color BG_MAIN = new Color(245, 245, 245);

    private static DefaultTableModel model;

    public static JFrame frame;

    public static JButton startQuiz;
    public static JButton openQuiz;
    public static JButton addQuiz;
    public static JButton editQuiz;
    public static JButton deleteQuiz;
    public static JButton backButton;
    public static JButton publishPanel;
    public static JTable table;

    private static QuizGroup_Query_Data quiz = new QuizGroup_Query_Data(new dbConnector());

    public static JPanel createPanel() {

        JPanel panel = new JPanel(new BorderLayout(25, 25));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // ================= HEADER =================
        JLabel titleLabel = new JLabel("List of Activity");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        buttonPanel.setOpaque(false);

        startQuiz = createButton("Start Quiz");
        openQuiz = createButton("Open Quiz");
        addQuiz = createButton("Add Quiz");
        editQuiz = createButton("Edit Quiz");
        deleteQuiz = createButton("Delete Quiz");
        backButton = createButton("Back");

        hideButton(buttonPanel);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(buttonPanel, BorderLayout.SOUTH);

        // ================= TABLE =================
        String[] columns = {"No.", "#", "Quiz Questions", "Teachers", "Publish"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(55);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setBackground(Color.WHITE);
        table.setForeground(TEXT);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        AddRowData(model);

        table.getColumnModel().getColumn(1).setMaxWidth(40);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    id = (int) table.getModel().getValueAt(row, 0);
                }
            }
        });

        // ================= BOTTOM PANEL (PUBLISH ONLY FOR TEACHER) =================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setOpaque(false);

        publishPanel = createButton("Publish");

        if ("Teacher".equals(Globals.getInstance().getRoles().trim())) {
            bottomPanel.add(publishPanel);
        }

        // ================= ADD COMPONENTS =================
        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ================= TABLE DATA =================
    private static void AddRowData(DefaultTableModel model) {

        if (Globals.getInstance().getRoles().equals("Student")) {
            System.out.println(Globals.getInstance().getStudentID());
            quiz.setStudentID(Globals.getInstance().getStudentID());
            quiz.setRoles("Student");

            table.getColumnModel().getColumn(4).setMinWidth(0);
            table.getColumnModel().getColumn(4).setMaxWidth(0);
            table.getColumnModel().getColumn(4).setWidth(0);
        } else if (Globals.getInstance().getRoles().equals("Teacher")) {
            quiz.setTeacherID(Globals.getInstance().getTeacherID());
            quiz.setRoles("Teacher");
        }

        ResultSet quizList = quiz.displayQuiz();
        int counter = 0;
        Set<Integer> displayedQuizIDs = new HashSet<>();

        try {
            while (quizList.next()) {

                int quizGroupID = quizList.getInt("quizGroupID");

                // Skip duplicate quizGroupID
                if (displayedQuizIDs.contains(quizGroupID)) {
                    continue;
                }

                displayedQuizIDs.add(quizGroupID);

                counter++;
                model.addRow(new Object[]{
                    quizList.getInt("quizGroupID"),
                    counter,
                    quizList.getString("quizName"),
                    quizList.getString("fullname"),
                    (quizList.getInt("published") == 1) ? "published" : "not published"
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void refreshTable() {
        model.setRowCount(0);
        AddRowData(model);
    }

    // ================= ROLE BUTTONS =================
    public static void hideButton(JPanel buttonPanel) {

        String role = Globals.getInstance().getRoles().trim();

        if ("Student".equals(role)) {
            buttonPanel.add(startQuiz);
        } else if ("Teacher".equals(role)) {
            buttonPanel.add(openQuiz);
            buttonPanel.add(addQuiz);
            buttonPanel.add(editQuiz);
            buttonPanel.add(deleteQuiz);
        }

        buttonPanel.add(backButton);
    }

    // ================= BUTTON FACTORY =================
    private static JButton createButton(String text) {

        JButton btn = new JButton(text);
        btn.setBackground(new Color(30, 30, 30));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {

            if (id == 0 && !text.equals("Add Quiz") && !text.equals("Back")) {
                JOptionPane.showMessageDialog(frame, "No selected row");
                return;
            }

            switch (text) {

                case "Open Quiz" -> {
                    Globals.getInstance().setQuizGroupID(id);
                    new ListOfQuizes().setVisible(true);
                    frame.setVisible(false);
                }

                case "Start Quiz" -> {
                    Globals.getInstance().setQuizGroupID(id);
                    new appQuiz().setVisible(true);
                    frame.setVisible(false);
                }

                case "Add Quiz" -> {
                    new AddQuizGroup().setVisible(true);
                    frame.setVisible(false);
                }

                case "Edit Quiz" -> {
                    Globals.getInstance().setQueryMode("edit");
                    Globals.getInstance().setQuizGroupID(id);
                    new AddQuizGroup().setVisible(true);
                    frame.setVisible(false);
                }

                case "Publish" -> {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure to want to publish this?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        quiz.setQuizGroupID(id);
                        quiz.publish();
                        JOptionPane.showMessageDialog(frame, "Publish quiz successfully");
                    }
                    refreshTable();
                }

                case "Delete Quiz" -> {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Delete this quiz?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        quiz.setQuizGroupID(id);
                        quiz.deleteQuizGroup();
                        JOptionPane.showMessageDialog(frame, "Deleted successfully");

                        refreshTable();
                    }
                }

                case "Back" -> {
                    new Dashboard().setWindow(true);
                    frame.setVisible(false);
                }
            }

            id = 0;
        });

        return btn;
    }

    // ================= WINDOW =================
    public static void setWindow(boolean show) {

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Quiz Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);

            frame.add(createPanel());

            frame.setVisible(show);
        });
    }

    public static void main(String[] args) {
        setWindow(true);
    }
}
