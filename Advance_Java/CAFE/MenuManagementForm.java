package CAFE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MenuManagementForm {
    private JFrame frame;
    private JTextField nameField, priceField;
    private JTextArea descriptionField;
    private Connection conn;

    public MenuManagementForm() {
        connectDatabase();
        frame = new JFrame("Manage Menu");
        frame.setLayout(new FlowLayout());
        
        JLabel nameLabel = new JLabel("Item Name:");
        nameField = new JTextField(20);
        
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(10);
        
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(3, 20);
        
        JButton addButton = new JButton("Add Menu Item");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());

                // Add the item to the database
                addMenuItem(name, description, price);
            }
        });

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(descriptionLabel);
        frame.add(new JScrollPane(descriptionField));
        frame.add(addButton);
        
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/cafe";
            String user = "root";
            String pass = "root";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMenuItem(String name, String description, double price) {
        // Here you can add the logic to insert the new menu item into the database.
        String query = "INSERT INTO Menu (name, description, price) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // For now, we can just print it to the console.
        System.out.println("Adding menu item: " + name + " | " + description + " | Price: " + price);
    }

    public static void main(String[] args) {
        new MenuManagementForm();
    }
}
