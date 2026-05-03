package com.mycompany.quiz_application.App.mainQuiz;

import static com.mycompany.quiz_application.App.mainQuiz.ListOfQuizes.setWindow;
import com.mycompany.quiz_application.App.mainQuiz.Quizes.QuizLog_Query_Data;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AddQuizGroup {

    private static final Color CARD = Color.WHITE;
    private static final Color INPUT = new Color(240, 240, 240);
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color SUBTEXT = new Color(120, 120, 120);
    private static final Color BG_MAIN = new Color(245, 245, 245);

    private static QuizGroup_Query_Data quizLog = new QuizGroup_Query_Data(new dbConnector());

    private static JFrame frame;

    private static String quizName;
    private static String timerText;
    private static boolean hasTime;
    private static LocalDateTime deadlineValue;

    private static JTextField quizNameField;
    private static JCheckBox enableTimer;
    private static JTextField timerField;
    private static JDateChooser dateChooser;
    private static JPanel panel;
    private static JLabel setTextTimer;

    public static JPanel createPanel() {

        panel = new JPanel();
        panel.setBackground(CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(createHeader("Add / Edit Quiz"));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("Quiz Name"));
        quizNameField = createInput();
        panel.add(quizNameField);

        panel.add(Box.createVerticalStrut(25));

        enableTimer = new JCheckBox("Enable Timer");
        enableTimer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        enableTimer.setBackground(CARD);
        panel.add(enableTimer);

        panel.add(Box.createVerticalStrut(10));

        setTextTimer = createLabel("Set Time(minutes)");
        setTextTimer.setVisible(false);
        panel.add(setTextTimer);
        timerField = createInput();
        timerField.setVisible(false);
        panel.add(timerField);

        panel.add(Box.createVerticalStrut(25));

        panel.add(createLabel("Set Deadline"));

        // ✅ CALENDAR WIDGET
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datePanel.setBackground(CARD);

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(500, 40));
        datePanel.add(dateChooser);
        panel.add(datePanel);

        panel.add(Box.createVerticalStrut(40));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(CARD);

        JButton saveBtn = createButton("Save");
        JButton cancelBtn = createButton("Cancel");

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        panel.add(btnPanel);

        enableTimer.addActionListener(e -> {
            timerField.setVisible(enableTimer.isSelected());
            setTextTimer.setVisible(enableTimer.isSelected());
            panel.revalidate();
            panel.repaint();
        });

        saveBtn.addActionListener(e -> {

            quizName = quizNameField.getText();
            timerText = timerField.getText();
            hasTime = enableTimer.isSelected();

            Date selectedDate = dateChooser.getDate();

            if (selectedDate != null) {
                deadlineValue = selectedDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            }

            if (quizName.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Quiz Name is required!");
                return;
            }

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(panel, "Please select a deadline!");
                return;
            }

            JOptionPane.showMessageDialog(panel, "Saved successfully!");
        });

        return panel;
    }

    private static JLabel createHeader(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lbl.setForeground(TEXT);
        return lbl;
    }

    private static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(SUBTEXT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        return lbl;
    }

    private static JTextField createInput() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBackground(INPUT);
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        return field;
    }

    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(30, 30, 30));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBorder(BorderFactory.createEmptyBorder(18, 40, 18, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            System.out.println(text + " button clicked");

            if (text.equals("Save")) {

                // Get latest values
                quizName = quizNameField.getText().trim();
                timerText = timerField.getText().trim();
                hasTime = enableTimer.isSelected();

                // Deadline value
                Date selectedDate = dateChooser.getDate();

                if (selectedDate != null) {
                    deadlineValue = selectedDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                } else {
                    deadlineValue = null;
                }

                if (quizName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Quiz Name is required!");
                    return;
                }

                if (deadlineValue == null) {
                    JOptionPane.showMessageDialog(panel, "Deadline is required!");
                    return;
                }

                if (hasTime) {

                    if (timerText.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Timer is required!");
                        return;
                    }

                    try {

                        int setTimerLabel = Integer.parseInt(timerText);

                        if (setTimerLabel <= 0) {
                            JOptionPane.showMessageDialog(panel, "Timer must be greater than 0!");
                            return;
                        }

                        quizLog.setTimestamp(setTimerLabel);

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(panel, "Timer must be a valid number!");
                        return;
                    }

                } else {

                    quizLog.setTimestamp(0);
                }

                quizLog.setQuizName(quizName);
                quizLog.setHasTime(hasTime);
                quizLog.setDeadline(deadlineValue);
                quizLog.setTeacherID(Globals.getInstance().getTeacherID());

                if (Globals.getInstance().getQueryMode().equals("edit")) {

                    quizLog.setQuizGroupID(Globals.getInstance().getQuizGroupID());
                    quizLog.updateQuizGroup();

                } else {

                    quizLog.createQuizGroup();
                }

                JOptionPane.showMessageDialog(panel, "Saved successfully!");
            }

            ListOfGroupQuizes listGroup = new ListOfGroupQuizes();
            listGroup.setWindow(true);
            frame.setVisible(false);
//            } else if (text.equals("Delete Quiz")) {
//            }
        }
        );

        return btn;
    }

    public AddQuizGroup() {

    }

    public static void main(String[] args) {
        setWindow(true);
    }

    public static void displayData() {
        if (Globals.getInstance().getQueryMode().equals("edit")) {

            timerField.setVisible(enableTimer.isSelected());
            setTextTimer.setVisible(enableTimer.isSelected());
            panel.revalidate();
            panel.repaint();
            try {
                quizLog.setQuizGroupID(Globals.getInstance().getQuizGroupID());
                System.out.println("AddQuiz, " + Globals.getInstance().getQuizGroupID());
                ResultSet set = quizLog.getQuizGroup();

                while (set.next()) {
//                    System.out.println(;
                    quizNameField.setText(set.getString("quizName"));
                    enableTimer.setSelected(set.getBoolean("hasTime"));
                    if (set.getBoolean("hasTime")) {
                        timerField.setVisible(set.getBoolean("hasTime"));
                        timerField.setText(String.valueOf(set.getInt("timestamp")));
//                      
                    }
                    dateChooser.setDate(set.getDate("deadline"));
                }

            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public static void setWindow(boolean show) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Quiz Form Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);
            frame.add(createPanel());
            frame.setVisible(show);
            displayData();
        });
    }

    public void setVisible(boolean show) {
        setWindow(show);
    }
}
