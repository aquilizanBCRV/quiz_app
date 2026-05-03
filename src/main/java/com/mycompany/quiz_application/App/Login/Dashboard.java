package com.mycompany.quiz_application.App.Login;

import com.mycompany.quiz_application.App.Review.Review_Query_Data;
import com.mycompany.quiz_application.App.Review.finishedQuiz;
import com.mycompany.quiz_application.App.mainQuiz.ListOfGroupQuizes;
import com.mycompany.quiz_application.App.mainQuiz.Quizes.appQuiz;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;
import com.mysql.cj.xdevapi.Result;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Dashboard extends JFrame {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(appQuiz.class.getName());

    private static JFrame frame;
    private static JPanel centerPanel;
    private static JLabel header;

    private static JPanel unfinished;
    private static JPanel createQuizCard;
    private static JPanel manageAccountsCard;
    private static JPanel reportsCard;
    private static JPanel settingsCard;
    private static JPanel viewRecords;
    private static JPanel viewQuiz;

    private static Review_Query_Data rev = new Review_Query_Data(new dbConnector());

    public static void main(String[] args) {
        setWindow(true);
    }

    public static void setWindow(boolean setBool) {
        EventQueue.invokeLater(() -> {
            try {
                Dashboard window = new Dashboard();
                window.frame.setVisible(setBool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Dashboard() {
        initialize();
        displayInfo();
        setVisible(true);
    }

    public static void displayInfo() {

        header.setText(
                "Welcome back, "
                + Globals.getInstance().getFirstname()
        );

        // STUDENT
        if (Globals.getInstance().getRoles().equals("Student")) {

            rev.setStudentID(Globals.getInstance().getStudentID());
            rev.setRoles("Student");
            ResultSet list = rev.getUnfinished();
            try {

                int unfinishedData = (list.next()) ? list.getInt("unfinished_count") : 0;

                unfinished = createCard(
                        "" + unfinishedData,
                        "Your Unfinished Quiz",
                        30,
                        "/icons/unfinished.png"
                );

                viewQuiz = createCard(
                        "View Quiz",
                        "See the new quiz",
                        16,
                        "/icons/view.png"
                );

                reportsCard = createCard(
                        "Records",
                        "See your recorded quiz",
                        16,
                        "/icons/records.png"
                );

                settingsCard = createCard(
                        "Settings",
                        "System settings",
                        16,
                        "/icons/settings.png"
                );

                centerPanel.add(unfinished);
                centerPanel.add(viewQuiz);
                centerPanel.add(reportsCard);
                centerPanel.add(settingsCard);
            } catch (SQLException ex) {
                System.getLogger(Dashboard.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }

        // TEACHER
        if (Globals.getInstance().getRoles().equals("Teacher")) {
            rev.setStudentID(Globals.getInstance().getTeacherID());
            rev.setRoles("Teacher");
            ResultSet list = rev.getUnfinished();
            try {

                int unfinishedData = (list.next()) ? list.getInt("unfinished_count") : 0;

                unfinished = createCard(
                        ""+unfinishedData,
                        "Students not taken quizzes",
                        30,
                        "/icons/student.png"
                );

                createQuizCard = createCard(
                        "Create Quiz",
                        "Build new quiz",
                        16,
                        "/icons/create.png"
                );

                viewRecords = createCard(
                        "View Records",
                        "View taken quizzes",
                        16,
                        "/icons/records.png"
                );

                manageAccountsCard = createCard(
                        "Manage Accounts",
                        "Add/Edit students",
                        16,
                        "/icons/manage.png"
                );

                centerPanel.add(unfinished);
                centerPanel.add(createQuizCard);
                centerPanel.add(viewRecords);
                centerPanel.add(manageAccountsCard);
            } catch (SQLException ex) {
                System.getLogger(Dashboard.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }
    }

    private void initialize() {

        frame = new JFrame("Dashboard");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        frame.setContentPane(mainPanel);

        // HEADER
        header = new JLabel("Welcome back");
        header.setFont(new Font("Roboto", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        header.setForeground(new Color(17, 17, 17));

        mainPanel.add(header, BorderLayout.NORTH);

        // CENTER PANEL
        centerPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(245, 245, 245));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // LOGOUT BUTTON
        JButton logout = new JButton("Logout");

        logout.setFocusPainted(false);
        logout.setBackground(Color.BLACK);
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Roboto", Font.BOLD, 14));
        logout.setBorder(new EmptyBorder(10, 20, 10, 20));

        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logging out...");
            frame.dispose();
            QuizApp_Login login = new QuizApp_Login();
            login.setWindow(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(logout);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    static public void handleCardClick(String title) {

        switch (title) {

            case "Create Quiz":
            case "View Quiz":

                frame.setVisible(false);

                ListOfGroupQuizes quizes = new ListOfGroupQuizes();
                quizes.setWindow(true);

                break;

            case "Manage Accounts":
                System.out.println("Manage Accounts");
                break;

            case "Records":

                finishedQuiz reports = new finishedQuiz();
                reports.setVisible(true);

                frame.setVisible(false);

                break;

            case "Settings":
                System.out.println("Open Settings");
                break;
        }
    }

    private static JPanel createCard(
            String title,
            String subtitle,
            int sizeHeader,
            String imagePath
    ) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(229, 229, 229)
                )
        );

        // IMAGE
        JLabel imageLabel = new JLabel();

        try {

            ImageIcon icon = new ImageIcon(
                    Dashboard.class.getResource(imagePath)
            );

            Image img = icon.getImage().getScaledInstance(
                    64,
                    64,
                    Image.SCALE_SMOOTH
            );

            imageLabel.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TITLE
        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font("Roboto", Font.BOLD, sizeHeader)
        );

        titleLabel.setForeground(new Color(17, 17, 17));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SUBTITLE
        JLabel subLabel = new JLabel(subtitle);

        subLabel.setFont(
                new Font("Roboto", Font.PLAIN, 12)
        );

        subLabel.setForeground(new Color(119, 119, 119));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ADD COMPONENTS
        card.add(Box.createVerticalGlue());

        card.add(imageLabel);

        card.add(Box.createRigidArea(
                new Dimension(0, 10)
        ));

        card.add(titleLabel);

        card.add(Box.createRigidArea(
                new Dimension(0, 5)
        ));

        card.add(subLabel);

        card.add(Box.createVerticalGlue());

        // HOVER EFFECT
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
