
package com.mycompany.quiz_application.App.addAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AddUpdateRecords extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField txtId, txtName, txtCourse, txtYear;
    JButton addBtn, updateBtn, clearBtn, closeBtn;

    public AddUpdateRecords() {

        setTitle("Add and Update Records");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("ADD AND UPDATE RECORDS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        mainPanel.add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Course", "Year"};
        String[][] data = {
            {"101", "John Cruz", "BSIT", "1"},
            {"102", "Maria Santos", "BSCS", "2"},
            {"103", "Paul Reyes", "BSBA", "3"}
        };

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        table.setRowHeight(28);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);

        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(6,2,10,10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtCourse = new JTextField();
        txtYear = new JTextField();

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Course:"));
        formPanel.add(txtCourse);

        formPanel.add(new JLabel("Year:"));
        formPanel.add(txtYear);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        clearBtn = new JButton("Clear");
        closeBtn = new JButton("Close");

        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);

        updateBtn.setBackground(Color.GRAY);
        updateBtn.setForeground(Color.WHITE);

        clearBtn.setBackground(Color.BLACK);
        clearBtn.setForeground(Color.WHITE);

        closeBtn.setBackground(Color.GRAY);
        closeBtn.setForeground(Color.WHITE);

        formPanel.add(addBtn);
        formPanel.add(updateBtn);
        formPanel.add(clearBtn);
        formPanel.add(closeBtn);

        mainPanel.add(formPanel, BorderLayout.SOUTH);

        add(mainPanel);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtId.setText(model.getValueAt(row,0).toString());
                txtName.setText(model.getValueAt(row,1).toString());
                txtCourse.setText(model.getValueAt(row,2).toString());
                txtYear.setText(model.getValueAt(row,3).toString());
            }
        });

        addBtn.addActionListener(e -> {
            model.addRow(new Object[]{
                txtId.getText(),
                txtName.getText(),
                txtCourse.getText(),
                txtYear.getText()
            });

            JOptionPane.showMessageDialog(null, "Record Added!");
            clearFields();
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if(row >= 0) {
                model.setValueAt(txtId.getText(), row, 0);
                model.setValueAt(txtName.getText(), row, 1);
                model.setValueAt(txtCourse.getText(), row, 2);
                model.setValueAt(txtYear.getText(), row, 3);

                JOptionPane.showMessageDialog(null, "Record Updated!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Select row first!");
            }
        });

        clearBtn.addActionListener(e -> clearFields());

        closeBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
        txtYear.setText("");
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        new AddUpdateRecords();
    }
}