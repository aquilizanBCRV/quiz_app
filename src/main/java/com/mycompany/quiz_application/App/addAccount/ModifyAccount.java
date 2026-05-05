package com.mycompany.quiz_application.App.addAccount;

import com.mycompany.quiz_application.App.Login.Dashboard;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ModifyAccount extends JFrame {

    JTextField txtFirstName;
    JTextField txtLastName;
    JTextField txtMiddleName;
    JTextField txtUsername;
    JTextField txtPassword;

    JComboBox<String> roleComboBox;
    JComboBox<TeacherItem> teacherComboBox;

    JLabel teacherLabel;
    JLabel roleLabel;

    JButton generateBtn;
    JButton saveBtn;
    JButton backBtn;

    boolean edit;
    int id;

    private static ManageAccount_Query_Data acc =
            new ManageAccount_Query_Data(new dbConnector());

    public ModifyAccount() throws SQLException {

        setTitle("Modify Account");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // ================= TITLE =================
        JLabel title = new JLabel("MODIFY ACCOUNT");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setBounds(150, 20, 300, 40);

        panel.add(title);

        // ================= FIRST NAME =================
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 90, 120, 30);

        txtFirstName = new JTextField();
        txtFirstName.setBounds(180, 90, 250, 30);

        panel.add(firstNameLabel);
        panel.add(txtFirstName);

        // ================= LAST NAME =================
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 140, 120, 30);

        txtLastName = new JTextField();
        txtLastName.setBounds(180, 140, 250, 30);

        panel.add(lastNameLabel);
        panel.add(txtLastName);

        // ================= MIDDLE NAME =================
        JLabel middleNameLabel = new JLabel("Middle Name:");
        middleNameLabel.setBounds(50, 190, 120, 30);

        txtMiddleName = new JTextField();
        txtMiddleName.setBounds(180, 190, 250, 30);

        panel.add(middleNameLabel);
        panel.add(txtMiddleName);

        // ================= ROLE =================
        roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 240, 120, 30);

        roleComboBox = new JComboBox<>(
                new String[]{
                    "--- Select Role ---",
                    "Teacher",
                    "Student",
                    "Admin"
                }
        );

        roleComboBox.setBounds(180, 240, 250, 30);

        panel.add(roleLabel);
        panel.add(roleComboBox);

        // ================= TEACHER =================
        teacherLabel = new JLabel("Teacher:");
        teacherLabel.setBounds(50, 290, 120, 30);

        teacherComboBox = new JComboBox<>();
        teacherComboBox.setBounds(180, 290, 250, 30);

        loadTeachers();

        panel.add(teacherLabel);
        panel.add(teacherComboBox);

        // ================= USERNAME =================
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 340, 120, 30);

        txtUsername = new JTextField();
        txtUsername.setBounds(180, 340, 250, 30);

        panel.add(usernameLabel);
        panel.add(txtUsername);

        // ================= PASSWORD =================
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 390, 120, 30);

        txtPassword = new JTextField();
        txtPassword.setBounds(180, 390, 250, 30);

        panel.add(passwordLabel);
        panel.add(txtPassword);

        // ================= GENERATE BUTTON =================
        generateBtn = new JButton("Generate Code");
        generateBtn.setBounds(180, 440, 250, 35);

        generateBtn.setBackground(Color.BLACK);
        generateBtn.setForeground(Color.WHITE);

        panel.add(generateBtn);

        // ================= SAVE BUTTON =================
        saveBtn = new JButton("Save");
        saveBtn.setBounds(100, 500, 120, 40);

        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);

        panel.add(saveBtn);

        // ================= BACK BUTTON =================
        backBtn = new JButton("Back");
        backBtn.setBounds(280, 500, 120, 40);

        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(Color.BLACK);

        panel.add(backBtn);

        // ================= ROLE ACTION =================
        roleComboBox.addActionListener(e -> toggleTeacherComboBox());

        toggleTeacherComboBox();

        // ================= GENERATE =================
        generateBtn.addActionListener(e -> generateCredentials());

        // ================= SAVE =================
        saveBtn.addActionListener(e -> {
            try {
                saveAccount();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // ================= BACK =================
        backBtn.addActionListener(e -> {
            dispose();

            if (Globals.getInstance().isManageAccount()) {
                Dashboard dash = new Dashboard();
                dash.setWindow(true);
                return;
            }

            try {
                ViewRecords rec = new ViewRecords();
                rec.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        add(panel);

        // IMPORTANT:
        // REMOVE setVisible(true) HERE
    }

    // ================= LOAD TEACHERS =================
    private void loadTeachers() throws SQLException {

        teacherComboBox.removeAllItems();

        acc.setRole("Teacher");

        ResultSet result = acc.displayRecords();

        while (result.next()) {

            teacherComboBox.addItem(
                    new TeacherItem(
                            result.getInt("teacherID"),
                            result.getString("name")
                    )
            );
        }
    }

    // ================= UPDATE QUERY =================
    public void upDateQuery(int ID, String role) throws SQLException {

        acc.setRole(role);
        acc.setUserID(ID);

        ResultSet list = acc.getSpecifyRecords();

        if (list.next()) {

            txtFirstName.setText(list.getString("firstname"));
            txtMiddleName.setText(list.getString("middleName"));
            txtLastName.setText(list.getString("lastname"));
            txtPassword.setText(list.getString("password"));
            txtUsername.setText(list.getString("username"));

            roleComboBox.setSelectedItem(
                    list.getString("roles")
            );

            if (role.equals("Student")) {
                selectTeacherById(
                        list.getInt("teacherID")
                );
            }

            id = list.getInt("userID");
        }

        edit = true;

        if (Globals.getInstance().isManageAccount()) {

            teacherLabel.setVisible(false);
            roleLabel.setVisible(false);
            roleComboBox.setVisible(false);
            teacherComboBox.setVisible(false);
            generateBtn.setVisible(false);

            // REMOVE pack();
        }
    }

    // ================= SELECT TEACHER =================
    private void selectTeacherById(int teacherId) {

        for (int i = 0; i < teacherComboBox.getItemCount(); i++) {

            TeacherItem item =
                    teacherComboBox.getItemAt(i);

            if (item.getId() == teacherId) {

                teacherComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    // ================= SAVE ACCOUNT =================
    private void saveAccount() throws SQLException {

        String firstName =
                txtFirstName.getText().trim();

        String lastName =
                txtLastName.getText().trim();

        String middleName =
                txtMiddleName.getText().trim();

        String username =
                txtUsername.getText().trim();

        String password =
                txtPassword.getText().trim();

        String role =
                roleComboBox.getSelectedItem().toString();

        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "First Name is required!"
            );
            return;
        }

        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Last Name is required!"
            );
            return;
        }

        if (middleName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Middle Name is required!"
            );
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Username is required!"
            );
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Password is required!"
            );
            return;
        }

        acc.setFirstname(firstName);
        acc.setLastname(lastName);
        acc.setMiddlename(middleName);
        acc.setUsername(username);
        acc.setPassword(password);
        acc.setRole(role);

        if (role.equals("Student")) {

            TeacherItem selectedTeacher =
                    (TeacherItem)
                            teacherComboBox.getSelectedItem();

            acc.setTeacherID(
                    selectedTeacher.getId()
            );
        }

        if (edit) {

            acc.setUserID(id);
            acc.updateUser();

        } else {

            acc.createUser();
        }

        JOptionPane.showMessageDialog(
                null,
                "ACCOUNT SAVED!"
        );

        dispose();

        if (Globals.getInstance().isManageAccount()) {

            Dashboard dash = new Dashboard();
            dash.setWindow(true);
            return;
        }

        try {

            ViewRecords rec = new ViewRecords();
            rec.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ================= TOGGLE TEACHER =================
    private void toggleTeacherComboBox() {

        String role =
                roleComboBox.getSelectedItem().toString();

        boolean isStudent =
                role.equals("Student");

        teacherLabel.setVisible(isStudent);
        teacherComboBox.setVisible(isStudent);
    }

    // ================= GENERATE =================
    private void generateCredentials() {

        String firstName =
                txtFirstName.getText()
                        .trim()
                        .toLowerCase();

        String lastName =
                txtLastName.getText()
                        .trim()
                        .toLowerCase();

        if (firstName.isEmpty() || lastName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Enter First Name and Last Name first!"
            );

            return;
        }

        Random random = new Random();

        int number =
                1000 + random.nextInt(9000);

        String username =
                firstName.substring(0, 1)
                        + lastName
                        + number;

        String password =
                "PASS" + number;

        txtUsername.setText(username);
        txtPassword.setText(password);
    }

    // ================= TEACHER ITEM =================
    class TeacherItem {

        private int id;
        private String name;

        public TeacherItem(int id, String name) {

            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                ModifyAccount acc =
                        new ModifyAccount();

                acc.setVisible(true);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}