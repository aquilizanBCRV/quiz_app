import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewRecordsDesign extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewRecordsDesign() {
        setTitle("View Records");
        setSize(750, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("VIEW RECORDS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        panel.add(title, BorderLayout.NORTH);

        // Table Data
        String[] columns = {"ID", "Name", "Course", "Year"};
        String[][] data = {
            {"101", "John Cruz", "BSIT", "1"},
            {"102", "Maria Santos", "BSCS", "2"},
            {"103", "Paul Reyes", "BSBA", "3"},
            {"104", "Anne Lopez", "BSED", "4"}
        };

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(28);
        table.setSelectionBackground(Color.GRAY);
        table.setSelectionForeground(Color.WHITE);

        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton refresh = new JButton("Refresh");
        JButton close = new JButton("Close");

        refresh.setBackground(Color.BLACK);
        refresh.setForeground(Color.WHITE);

        close.setBackground(Color.GRAY);
        close.setForeground(Color.WHITE);

        close.addActionListener(e -> System.exit(0));

        btnPanel.add(refresh);
        btnPanel.add(close);

        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewRecordsDesign();
    }
}