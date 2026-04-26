package com.mycompany.quiz_application.App.Login;

import com.mycompany.quiz_application.App.mainQuiz.Quiz_Query_Data;
import com.mycompany.quiz_application.App.mainQuiz.Quizes.appQuiz;
//import com.mycompany.quiz_application.App.mainQuiz.Quiz.;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TeacherDashboard extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(appQuiz.class.getName());

    private JFrame frame;

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                TeacherDashboard window = new TeacherDashboard();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TeacherDashboard() {
        initialize();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initialize() {
        frame = new JFrame("Teacher Dashboard");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        JLabel header = new JLabel("Welcome back, Ma'am/Sir");
        header.setFont(new Font("Roboto", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        header.setForeground(new Color(17, 17, 17));
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        centerPanel.add(createCard("Create Quiz", "Build new quiz"));
        centerPanel.add(createCard("Manage Accounts", "Add/Edit students"));
        centerPanel.add(createCard("Reports", "View performance"));
        centerPanel.add(createCard("Settings", "System settings"));
        centerPanel.add(createCard("Analytics", "View statistics"));

        JButton logout = new JButton("Logout");
        logout.setFocusPainted(false);
        logout.setBackground(Color.BLACK);
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Roboto", Font.BOLD, 14));
        logout.setBorder(new EmptyBorder(10, 20, 10, 20));

        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logging out...");
            frame.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(logout);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    }

    static public void handleCardClick(String title) {
        switch (title) {
            case "Create Quiz":
                System.out.println("Open Create Quiz screen");
                break;
            case "Manage Accounts":
                System.out.println("Open Manage Accounts screen");
                break;
            case "Reports":
                System.out.println("Open Reports screen");
                break;
            case "Settings":
                System.out.println("Open Settings screen");
                break;
            case "Analytics":
                System.out.println("Open Analytics screen");
                break;
        }
    }

    private JPanel createCard(String title, String subtitle) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(229, 229, 229)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        titleLabel.setForeground(new Color(17, 17, 17));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        subLabel.setForeground(new Color(119, 119, 119));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subLabel);
        card.add(Box.createVerticalGlue());
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCardClick(title);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
        });
        return card;
    }

}
