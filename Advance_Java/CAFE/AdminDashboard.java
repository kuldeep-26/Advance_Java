package CAFE;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {
    private Connection conn;

    public AdminDashboard() {
        // Establish database connection
        connectDatabase();

        // Set up the GUI
        setTitle("Cafe Management System");
        setSize(600, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the welcome note and buttons
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        gbc.gridx = 0;
        gbc.insets = new Insets(4, 4, 4, 4); // Margin around components

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the Cafe Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        // Buttons
        JButton addButton = new JButton("Add Item");
        gbc.gridy = 1;
        panel.add(addButton, gbc);
        addButton.addActionListener(e -> addItem());

        JButton updateButton = new JButton("Update Item");
        gbc.gridy = 2;
        panel.add(updateButton, gbc);
        updateButton.addActionListener(e -> updateItem());

        JButton deleteButton = new JButton("Delete Item");
        gbc.gridy = 3;
        panel.add(deleteButton, gbc);
        deleteButton.addActionListener(e -> deleteItem());

        JButton viewButton = new JButton("View Items");
        gbc.gridy = 4;
        panel.add(viewButton, gbc);
        viewButton.addActionListener(e -> viewItem());

        /*JButton issueReturnButton = new JButton("Issue Book");
        gbc.gridy = 5;
        panel.add(issueReturnButton, gbc);
        issueReturnButton.addActionListener(e -> new IssueBook(conn));

        JButton ReturnButton = new JButton("Return Book");
        gbc.gridy = 6;
        panel.add(ReturnButton, gbc);
        ReturnButton.addActionListener(e -> new ReturnBook(conn));

        // Add button for viewing issued books
        JButton viewIssuedBooksButton = new JButton("View Issued Books");
        gbc.gridy = 7;
        panel.add(viewIssuedBooksButton, gbc);
        viewIssuedBooksButton.addActionListener(e -> viewIssuedBooks());

        // Add button for viewing returned books
        JButton viewReturnedBooksButton = new JButton("View Returned Books");
        gbc.gridy = 8;
        panel.add(viewReturnedBooksButton, gbc);
        viewReturnedBooksButton.addActionListener(e -> viewReturnedBooks()); */

        // Create a "Go Back" button
        JButton closeButton = new JButton("Close App");
        gbc.gridy = 9;
        panel.add(closeButton, gbc);

        // Add action listener to the "Go Back" button
        closeButton.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);

        // Show the window
        setVisible(true);
    }

    private void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/cafe";
            String user = "root";
            String pass = "root"; // Update with your MySQL password
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Item: ");
        String description = JOptionPane.showInputDialog(this, "Description: ");
        int price = Integer.parseInt(JOptionPane.showInputDialog(this, "Price: "));

        try {
            String query = "INSERT INTO Menu (name, description, price) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, description);
            pst.setInt(3, price);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewItem() {
        try {
            String query = "SELECT * FROM Menu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Define column names
            String[] columnNames = {"menu_id","name", "description", "price"};

            // Load data from the ResultSet into a 2D array
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
                        rs.getInt("menu_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price")
                });
            }

            // Convert the list to a 2D array
            Object[][] dataArray = data.toArray(new Object[0][]);

            // Create the JTable with the data
            JTable table = new JTable(dataArray, columnNames);

            // Set preferred size for JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 600));

            // Show the JTable in a message dialog
            JOptionPane.showMessageDialog(this, scrollPane, "View Items", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing Items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void updateItem() {
        int menuId;
        try {
            menuId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Menu ID to Update:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Menu ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] options = {"name", "description", "price"};
        String field = (String) JOptionPane.showInputDialog(this, "Which field would you like to update?", "Update Menu", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (field == null) {
            return; // User canceled
        }

        String newValue = JOptionPane.showInputDialog(this, "Enter new " + field + ":");
        if (newValue == null || newValue.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid " + field + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query;
            PreparedStatement pst;
            if ("price".equals(field)) {
                int newPrice;
                try {
                    newPrice = Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                query = "UPDATE Menu SET price = ? WHERE menu_id = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, newPrice);
            } else {
                query = "UPDATE Menu SET " + field + " = ? WHERE menu_id = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, newValue);
            }
            pst.setInt(2, menuId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Menu Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Menu ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating Menu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void deleteItem() {
        int menuId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Menu ID to Delete:"));

        try {
            String query = "DELETE FROM Menu WHERE menu_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, menuId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Item Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Item ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting Item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

/*    private void viewIssuedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getTimestamp("IssueDate")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(700, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "View Issued Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing issued books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void viewReturnedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NOT NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate", "ReturnDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getTimestamp("IssueDate"),
                        rs.getTimestamp("ReturnDate")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(700, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "View Returned Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing returned books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    } */

    public static void main(String[] args) {
        try {
            // Set the Nimbus look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//      // Set to default swing UI
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("Code Run Successful \n");
        new LoginForm();
        //new AdminDashboard();
    }
}

