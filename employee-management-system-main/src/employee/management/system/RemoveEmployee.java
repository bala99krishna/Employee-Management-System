package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    private Choice choiceEMPID;
    private JButton delete, back;
    private JLabel textName, textPhone, textEmail;

    public RemoveEmployee() {
        setTitle("Remove Employee");
        setSize(900, 500);
        setLocation(300, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Employee ID Selection
        JLabel label = new JLabel("Employee ID");
        label.setBounds(50, 50, 150, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(label);

        choiceEMPID = new Choice();
        choiceEMPID.setBounds(200, 50, 150, 30);
        add(choiceEMPID);

        loadEmployeeIDs(); // Load available IDs

        // Employee Details
        addDetailLabel("Name:", 50, 100);
        textName = createDetailField(200, 100);

        addDetailLabel("Phone:", 50, 150);
        textPhone = createDetailField(200, 150);

        addDetailLabel("Email:", 50, 200);
        textEmail = createDetailField(200, 200);

        // Buttons
        delete = createActionButton("Delete", 80, 300);
        back = createActionButton("Back", 220, 300);

        // Load initial employee data
        updateEmployeeDetails();

        // Add selection listener
        choiceEMPID.addItemListener(e -> updateEmployeeDetails());

        // Add images
        addImage("icons/delete.png", 700, 80, 200, 200);
        addImage("icons/rback.png", 0, 0, 1120, 630);

        setVisible(true);
    }

    private void addDetailLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(label);
    }

    private JLabel createDetailField(int x, int y) {
        JLabel field = new JLabel();
        field.setBounds(x, y, 250, 30);
        field.setFont(new Font("Tahoma", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(field);
        return field;
    }

    private JButton createActionButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 30);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(this);
        add(btn);
        return btn;
    }

    private void addImage(String path, int x, int y, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            JLabel image = new JLabel(new ImageIcon(img));
            image.setBounds(x, y, w, h);
            add(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Image not found: " + path);
        }
    }

    private void loadEmployeeIDs() {
        try (Connection c = new conn().connection;
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT empId FROM employee")) {

            while (rs.next()) {
                choiceEMPID.add(rs.getString("empId"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to load employee IDs");
            e.printStackTrace();
        }
    }

    private void updateEmployeeDetails() {
        String empId = choiceEMPID.getSelectedItem();
        if (empId == null) return;

        try (Connection c = new conn().connection;
             PreparedStatement ps = c.prepareStatement(
                     "SELECT name, phone, email FROM employee WHERE empId = ?")) {

            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                textName.setText(rs.getString("name"));
                textPhone.setText(rs.getString("phone"));
                textEmail.setText(rs.getString("email"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to load employee details");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete this employee?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteEmployee();
            }
        } else {
            dispose(); // Close this window
            new Main_Class().setVisible(true); // Return to main menu
        }
    }

    private void deleteEmployee() {
        String empId = choiceEMPID.getSelectedItem();

        try (Connection c = new conn().connection;
             PreparedStatement ps = c.prepareStatement(
                     "DELETE FROM employee WHERE empId = ?")) {

            ps.setString(1, empId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Employee deleted successfully!");
                dispose();
                new Main_Class().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete employee!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error during deletion");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}