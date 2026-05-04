package com.mycompany.quiz_application.App.Login;

import com.mycompany.quiz_application.App.mainQuiz.ListOfQuizes;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizApp_Login {

    private static Auth_Query_Data login = new Auth_Query_Data(new dbConnector());

    private static JFrame frame;

    public static void main(String[] args) {
        setWindow(true);
    }

    public static void setWindow(boolean show) {

        SwingUtilities.invokeLater(() -> new QuizApp_Login().createUI(show));
    }

    private void createUI(boolean show) {

        frame = new JFrame("Quiz Application");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Font baseFont = new Font("Roboto", Font.PLAIN, 14);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 245, 245));

        // NAVBAR
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel logo = new JLabel("KAVE learning Hub");
        logo.setFont(new Font("Roboto", Font.BOLD, 20));

        JButton loginBtn = createButton("Login", baseFont);

        nav.add(logo, BorderLayout.WEST);
        nav.add(loginBtn, BorderLayout.EAST);

        // CENTER
        JPanel center = new JPanel();
        center.setBackground(new Color(245, 245, 245));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Test Your Knowledge");
        title.setFont(new Font("Roboto", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = createButton("Start Quiz", baseFont);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalGlue());
        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(startBtn);
        center.add(Box.createVerticalGlue());

        // ================= LOGIN DIALOG =================
        JDialog dialog = new JDialog(frame, "Login", true);
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 300));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Soft shadow
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 5, 5, new Color(0, 0, 0, 20)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        loginTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // USERNAME
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(baseFont);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField userField = new JTextField();
        styleField(userField);

        // PASSWORD
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(baseFont);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField passField = new JPasswordField();
        styleField(passField);

        // 👁️ Show/Hide
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setBackground(Color.WHITE);
        showPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        userField.setText("yuzuki");
        passField.setText("teacher12");
        showPass.addActionListener(e -> {
            if (showPass.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('•');
            }
        });

        // BUTTON (aligned with fields)
        JButton submit = new JButton("Login");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setFont(new Font("Roboto", Font.BOLD, 14));
        submit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        submit.setAlignmentX(Component.LEFT_ALIGNMENT);

        // STATUS
        JLabel status = new JLabel(" ");
        status.setFont(new Font("Roboto", Font.PLAIN, 12));
        status.setForeground(Color.GRAY);
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ADD COMPONENTS
        card.add(loginTitle);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(userLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(userField);
        card.add(Box.createRigidArea(new Dimension(0, 12)));

        card.add(passLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(passField);
        card.add(showPass);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(submit);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(status);

        dialog.add(card);

        loginBtn.addActionListener(e -> dialog.setVisible(true));
        startBtn.addActionListener(e -> dialog.setVisible(true));

        submit.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            login.setUsername(user);
            login.setPassword(pass);
            ResultSet checkLogin = login.checkLogin();
            try {
                if (user.isEmpty() || pass.isEmpty()) {
                    status.setForeground(Color.RED);
                    status.setText("Please enter username and password");
                } else if (checkLogin.next()) {
//                    Globals.getInstance().setUser(checkLogin.getInt("userID"));
                    status.setForeground(new Color(0, 128, 0));
                    status.setText("Login successful!");
                    JOptionPane.showMessageDialog(frame, "Welcome to Quiz!");
                    dialog.dispose();
                    getUserInfo(checkLogin);

                } else {
                    status.setForeground(Color.RED);
                    status.setText("Invalid username or password");
                }
            } catch (Exception e1) {
                System.out.println(e1);
            }
        });

        main.add(nav, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        frame.add(main);
        frame.setVisible(show);
    }

    private static void getUserInfo(ResultSet info) throws SQLException {

        String role = info.getString("roles");
        int userId = info.getInt("userID");

        Globals globals = Globals.getInstance();

        if ("Teacher".equals(role) || "Student".equals(role)) {

            login.setRoles(role);
            login.setUserID(userId);

            ResultSet userInfo = login.getInfo();
            if (userInfo == null || !userInfo.next()) {
                return;
            }

            globals.setFirstname(userInfo.getString("firstname"));
            globals.setUserID(userInfo.getInt("userID"));
            globals.setRoles(role);

            switch (role) {
                case "Student":
                    globals.setStudentID(userInfo.getInt("studentID"));
                    break;

                case "Teacher":
                    globals.setTeacherID(userInfo.getInt("teacherID"));
                    break;
            }

        } else if ("Admin".equals(role)) {

            globals.setRoles(role); // important fix: role was not set before
            globals.setFirstname(info.getString("firstname"));
            globals.setUserID(info.getInt("userID"));
        }

        frame.setVisible(false);
        Dashboard dash = new Dashboard();
        dash.setWindow(true);
    }

    private JButton createButton(String text, Font font) {
        JButton b = new JButton(text);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(font);
        b.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return b;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(new LineBorder(new Color(200, 200, 200)));
        field.setFont(new Font("Roboto", Font.PLAIN, 14));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(new LineBorder(Color.BLACK, 2));
            }

            public void focusLost(FocusEvent e) {
                field.setBorder(new LineBorder(new Color(200, 200, 200)));
            }
        });
    }
}
