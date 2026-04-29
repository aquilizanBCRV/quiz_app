package com.mycompany.quiz_application.App.mainQuiz;

import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListOfQuizes extends javax.swing.JFrame {

    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color BG_MAIN = new Color(245, 245, 245);
    static int id = 0;
    public static JFrame frame;
    private static JTable table;
    private static DefaultTableModel model;

    //eto yung class for create ng query for quizes
    private static Quiz_Query_Data quiz = new Quiz_Query_Data(new dbConnector());

    public static JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout(25, 25));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Question Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        buttonPanel.setOpaque(false);

        buttonPanel.add(createButton("Create Question"));
        buttonPanel.add(createButton("Edit Question"));
        buttonPanel.add(createButton("Delete Question"));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.NORTH);
        header.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"No.", "#", "Quiz Questions"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
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

        table.getColumnModel().getColumn(1).setMaxWidth(40);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();

                if (row != -1) {
                    id = (int) table.getModel().getValueAt(row, 0);
                    System.out.println("Selected ID: " + id);
                }
            }
        });
        return panel;
    }

    private static void refreshTable() {
        model.setRowCount(0);
        AddRowData(model);
    }

    private static void AddRowData(DefaultTableModel model) {
        quiz.setQuizGroupID(Globals.getInstance().getQuizGroupID());
        ResultSet quizList = quiz.displayQuiz("");
        int counter = 0;

        try {
            while (quizList.next()) {
                ++counter;
                model.addRow(new Object[]{
                    quizList.getInt("quizesID"), // hidden ID
                    counter,
                    quizList.getString("displayQuestion")
                });
            }
        } catch (SQLException ex) {
            System.getLogger(ListOfQuizes.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
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

            if (text.equals("Create Question")) {
                addQuiz add = new addQuiz();
                add.setVisible(true);
                ListOfQuizes.frame.setVisible(false);
            } else if (text.equals("Edit Question")) {
                Globals.getInstance().setQueryMode("edit");
                Globals.getInstance().setQuizID(id);
                addQuiz add = new addQuiz();
                add.setVisible(true);
                ListOfQuizes.frame.setVisible(false);

            } else if (text.equals("Delete Question")) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to delete this?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(frame, "You Successfully delete ID.%d".formatted(id));
                    quiz.setQuizID(id);
                    quiz.DeleteQuery();
                    refreshTable();
                }
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
