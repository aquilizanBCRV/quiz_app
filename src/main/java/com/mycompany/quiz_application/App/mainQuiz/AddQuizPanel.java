package com.mycompany.quiz_application.App.mainQuiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AddQuizPanel {

    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(40, 40, 40);
    private static final Color BG_MAIN = new Color(245, 245, 245);

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

        return panel;
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

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Quiz Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(BG_MAIN);
            frame.add(createPanel());
            frame.setVisible(true);
        });
    }
}