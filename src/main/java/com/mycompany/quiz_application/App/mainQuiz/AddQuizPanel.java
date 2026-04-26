package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddQuizPanel extends javax.swing.JFrame {

    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color BG_MAIN = new Color(245, 245, 245);

    public static JFrame frame;

    //eto yung class for create ng query for quizes
    private static Quiz_Query_Data quiz = new Quiz_Query_Data(new dbConnector());

    public static JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout(25, 25));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Quiz Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        buttonPanel.setOpaque(false);

        buttonPanel.add(createButton("Create Quiz"));
        buttonPanel.add(createButton("Edit Quiz"));
        buttonPanel.add(createButton("Delete Quiz"));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"No.", "Quiz Name"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
//        table.setCo
        table.setRowHeight(55);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setBackground(Color.WHITE);
        table.setForeground(TEXT);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 18));
        th.setPreferredSize(new Dimension(th.getWidth(), 50));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        AddRowData(model);
        return panel;
    }

    private static void AddRowData(DefaultTableModel model) {
//model.addRow(new Object[]{1, "Quiz 1"});
//Pagawa nito code ididisplay lahat nagawang quizes sa "quizes" table
//lahat ng quizes  under ng quizGroupID, gamit ka lang WHERE clase sa query. 
//Gamit ka muna ng quizGroupID 1 para madisplay muna

//increment number at "displayQuestion ididisplay sa table. 
//may code nmn ng select query, yun mo try icopy and icode 
        ResultSet quizList = quiz.displayQuiz();

        try {
            while (quizList.next()) {

            }
        } catch (SQLException ex) {
            System.getLogger(AddQuizPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(30, 30, 30));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 60, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 30, 30));
            }
        });
        btn.addActionListener(e -> {
            System.out.println(text + " button clicked");

            if (text.equals("Create Quiz")) {
                addQuiz add = new addQuiz();
                add.setVisible(true);
                AddQuizPanel.frame.setVisible(false);
            } else if (text.equals("Edit Quiz")) {
            } else if (text.equals("Delete Quiz")) {
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        setWindow(true);
    }

    static public void setWindow(boolean show) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Add Quiz Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);
            frame.add(createPanel());
            frame.setVisible(true);

        });
    }

    public void setVisible(boolean show) {
        setWindow(show);
    }
}
