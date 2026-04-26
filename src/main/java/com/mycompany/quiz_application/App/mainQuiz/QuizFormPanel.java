package com.mycompany.quiz_application.App.mainQuiz;

import javax.swing.*;
import java.awt.*;

public class QuizFormPanel {

    private static final Color CARD = Color.WHITE;
    private static final Color INPUT = new Color(240, 240, 240);
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color SUBTEXT = new Color(120, 120, 120);
    private static final Color BG_MAIN = new Color(245, 245, 245);

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(createHeader("Add / Edit Quiz"));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("Quiz Name"));
        panel.add(createInput());

        panel.add(Box.createVerticalStrut(25));

        panel.add(createLabel("Set Time (minutes)"));
        panel.add(createInput());

        panel.add(Box.createVerticalStrut(35));

        panel.add(createLabel("Set Deadline"));
        panel.add(createInput());

        panel.add(Box.createVerticalStrut(40));
        panel.add(createButton("Save"));

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
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quiz Form Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);
            frame.add(createPanel());
            frame.setVisible(true);
        });
    }
}