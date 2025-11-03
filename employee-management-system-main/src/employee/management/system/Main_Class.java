package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Class extends JFrame {

    public Main_Class() {
        // Window setup
        setTitle("Employee Management System");
        setSize(1120, 630);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon bgImage = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image scaledImage = bgImage.getImage().getScaledInstance(1120, 630, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 1120, 630);
        add(background);

        // Header
        JLabel header = new JLabel("Employee Management System");
        header.setBounds(340, 155, 400, 40);
        header.setFont(new Font("Times New Roman", Font.BOLD, 25));
        header.setForeground(Color.WHITE);
        background.add(header);

        // Add Employee Button
        JButton addBtn = createButton("Add Employee", 335, 270, 150, 40);
        addBtn.addActionListener(e -> {
            setVisible(false);
            new AddEmployee();
        });

        // View Employees Button
        JButton viewBtn = createButton("View Employees", 535, 270, 150, 40);
        viewBtn.addActionListener(e -> {
            setVisible(false);
            new View_Employee();
        });

        // Remove Employee Button (FIXED)
        JButton removeBtn = createButton("Remove Employee", 440, 370, 150, 40);
        removeBtn.addActionListener(e -> {
            setVisible(false);
            new RemoveEmployee(); // This should open the RemoveEmployee window
        });

        // Add buttons to background
        background.add(addBtn);
        background.add(viewBtn);
        background.add(removeBtn);

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_Class());
    }
}