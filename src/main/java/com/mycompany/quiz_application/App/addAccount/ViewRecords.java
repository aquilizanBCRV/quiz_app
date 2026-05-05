package com.mycompany.quiz_application.App.addAccount;

import com.mycompany.quiz_application.App.Login.Dashboard;
import com.mycompany.quiz_application.Globals;
import com.mycompany.quiz_application.dbConnector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewRecords extends JFrame {

    JTable table;
    DefaultTableModel model;

    JComboBox<String> filterRoleComboBox;

    JButton addBtn, updateBtn, deleteBtn, backBtn;

    private static ManageAccount_Query_Data acc
            = new ManageAccount_Query_Data(new dbConnector());

    public ViewRecords() throws SQLException {

        setTitle("Manage Accounts");
        setSize(1100, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("ACCOUNT MANAGEMENT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        filterRoleComboBox = new JComboBox<>(new String[]{
            "All", "Student", "Teacher", "Admin"
        });

        topPanel.add(new JLabel("Filter Role: "));
        topPanel.add(filterRoleComboBox);

        mainPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadData("All");

        filterRoleComboBox.addActionListener(e -> {
            try {
                loadData(filterRoleComboBox.getSelectedItem().toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        addBtn.addActionListener(e -> {
            ModifyAccount account = null;
            try {
                account = new ModifyAccount();
            } catch (SQLException ex) {
                System.getLogger(ViewRecords.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            setVisible(false);
            account.setVisible(true);
        }
        );

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a record first",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            ModifyAccount account = null;
            try {
                account = new ModifyAccount();

                setVisible(false);
                account.setVisible(true);
                String roles = filterRoleComboBox.getSelectedItem().toString().equals("All")
                        ? model.getValueAt(row, 3).toString()
                        : filterRoleComboBox.getSelectedItem().toString();

                int id = Integer.parseInt(model.getValueAt(row, 1).toString());
                //i need get the ID row column
                account.upDateQuery(id, roles);
            } catch (SQLException ex) {
                System.getLogger(ViewRecords.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        deleteBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a record first",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Delete selected record?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                // STEP 1: READ DATA FIRST (before deleteRow)
                int id = Integer.parseInt(model.getValueAt(row, 1).toString());

                String roles = filterRoleComboBox.getSelectedItem().toString()
                        .equals("All")
                        ? model.getValueAt(row, 3).toString()
                        : filterRoleComboBox.getSelectedItem().toString();

                // STEP 2: DELETE FROM DATABASE
                acc.setRole(roles);
                acc.setUserID(id);
                acc.deleteUser();

                // STEP 3: REMOVE FROM TABLE
                model.removeRow(row);

                JOptionPane.showMessageDialog(null, "Record deleted successfully");
            }
        });

        backBtn.addActionListener(e -> {
        dispose();
            Dashboard dash = new Dashboard();
            dash.setWindow(true);
                });

        setVisible(true);
    }

    private void loadData(String role) throws SQLException {

        acc.setRole(role.equals("All") ? "" : role);
        if(Globals.getInstance().getRoles().equals("Admin")) {
            acc.setUserID(Globals.getInstance().getUserID());
        }
        ResultSet result = acc.displayRecords();

        ArrayList<String[]> rows = new ArrayList<>();
        String[] columns;

        int counter = 1;

        if (role.equals("Student")) {

            columns = new String[]{
                "#", "ID", "Student Name", "Teacher Name", "Username"
            };

            while (result.next()) {
                rows.add(new String[]{
                    String.valueOf(counter++),
                    result.getString("userID"),
                    result.getString("student_name"),
                    result.getString("teacher_name"),
                    result.getString("username")
                });
            }

        } else if (role.equals("Teacher")) {

            columns = new String[]{
                "#", "ID", "Teacher Name", "Username"
            };

            while (result.next()) {
                rows.add(new String[]{
                    String.valueOf(counter++),
                    result.getString("userID"),
                    result.getString("name"),
                    result.getString("username")
                });
            }

        } else if (role.equals("Admin")) {

            columns = new String[]{
                "#", "ID", "Admin Name", "Username", "Access Level"
            };

            while (result.next()) {
                rows.add(new String[]{
                    String.valueOf(counter++),
                    result.getString("userID"),
                    result.getString("name"),
                    result.getString("username"),});
            }

        } else {

            columns = new String[]{
                "#", "ID", "Name", "Role", "Username"
            };

            while (result.next()) {
                rows.add(new String[]{
                    String.valueOf(counter++),
                    result.getString("userID"),
                    result.getString("name"),
                    result.getString("roles"),
                    result.getString("username")
                });
            }
        }

        String[][] data = rows.toArray(new String[0][]);

        model.setDataVector(data, columns);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ViewRecords();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
