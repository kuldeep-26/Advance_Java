package CAFE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        frame = new JFrame("Cafe Management System - Login");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        
        JButton loginButton = new JButton("Login");
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Here you would validate the username and password
                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    // Launch appropriate interface based on user role (admin, cashier, etc.)
                    openMainDashboard();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        // Add authentication logic here (e.g., checking database)
        // For simplicity, we're just going to check for fixed values.
        return "admin".equals(username) && "password".equals(password);
    }

    private void openMainDashboard() {
        // Open the main dashboard, which could be the admin interface or cashier, etc.
        AdminDashboard dashboard = new AdminDashboard();
        /*JFrame dashboard = new JFrame("Main Dashboard");
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setSize(400, 400);
        dashboard.setVisible(true);*/
    }
}
