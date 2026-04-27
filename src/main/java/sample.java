
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class sample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First GUI");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Welcome to Java GUI Programming.");
        frame.add(label);

        JButton btnClick = new JButton("Click Me");
        btnClick.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Button Clicked!"));
        frame.add(btnClick);

        JTextField textField = new JTextField(15);
        JButton btnShow = new JButton("Show Text");
        btnShow.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You entered: " + textField.getText());
            textField.setText("");
        });
        frame.add(textField);
        frame.add(btnShow);

        frame.setVisible(true);
        
    }
}
