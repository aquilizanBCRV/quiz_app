package com.mycompany.quiz_application.App.mainQuiz;

import static com.mycompany.quiz_application.App.mainQuiz.ListOfQuizes.setWindow;
import com.mycompany.quiz_application.App.mainQuiz.Quizes.QuizLog_Query_Data;
import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import java.awt.*;

public class AddQuizGroup {

    private static final Color CARD = Color.WHITE;
    private static final Color INPUT = new Color(240, 240, 240);
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color SUBTEXT = new Color(120, 120, 120);
    private static final Color BG_MAIN = new Color(245, 245, 245);

    private static QuizLog_Query_Data quizLog = new QuizLog_Query_Data(new dbConnector());

    private static JFrame frame;

    /*
        Pagawa ng Save button  isavsave yung quizname, set time, and set deadline
    may na gawa na ako insert query ng quizLog at nawaga na aking code. gawin mo nlang
    create ka ng click save, then kunin mo yung quizname, set time, and set deadline, wag mo muna pakealaman yung value setTeacherID
     */
//        QuizGroup quiz_group = new QuizGroup(conn);
//
//        quiz_group.setTeacherID(1);
//        quiz_group.setQuizName("Science 1");
//        quiz_group.setHasTime(false); //make it false kapag empty ang Set Time jTextField
//        quiz_group.setTimestamp(null); //ikaw na dito paano isasave yung set timestamp, kpag empty default as 2 minutes
//        quiz_group.setDeadline(LocalDateTime.now().plusDays(3));
//        quiz_group.creeateQuizGroup();
    public static JPanel createPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(createHeader("Add / Edit Quiz"));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("Quiz Name"));
        JTextField quizNameField = createInput();
        panel.add(quizNameField);

        panel.add(Box.createVerticalStrut(25));

        
        //for timer
        JCheckBox enableTimer = new JCheckBox("Enable Timer");
        enableTimer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        enableTimer.setBackground(CARD);
        panel.add(enableTimer);

        panel.add(Box.createVerticalStrut(10));

        //timer textfield
        JTextField timerField = createInput();
        timerField.setVisible(false);
        panel.add(timerField);

        panel.add(Box.createVerticalStrut(25));

        panel.add(createLabel("Set Deadline"));
        JTextField deadlineField = createInput();
        panel.add(deadlineField);

        panel.add(Box.createVerticalStrut(40));

        JButton saveBtn = createButton("Save");
        panel.add(saveBtn);

        //logic
        enableTimer.addActionListener(e -> {
            timerField.setVisible(enableTimer.isSelected());
            panel.revalidate();
            panel.repaint();
        });

        //save button
        saveBtn.addActionListener(e -> {
            String quizName = quizNameField.getText();
            String timerText = timerField.getText();
            String deadlineText = deadlineField.getText();

            boolean hasTime = enableTimer.isSelected();

            if (quizName.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Quiz Name is required!");
                return;
            }

            System.out.println("Quiz Name: " + quizName);
            System.out.println("Has Timer: " + hasTime);
            System.out.println("Timer Value: " + timerText);
            System.out.println("Deadline: " + deadlineText);

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
        btn.setAlignmentX(Component.LEFT_ALIGNMENT); // ALIGN FIX
        btn.setBackground(new Color(30, 30, 30));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBorder(BorderFactory.createEmptyBorder(18, 40, 18, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 60, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 30, 30));
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        setWindow(true);
    }

    public static void setWindow(boolean show) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Quiz Form Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);
            frame.add(createPanel());
            frame.setVisible(show);
        });
    }

    public void setVisible(boolean show) {
        setWindow(show);
    }
}
