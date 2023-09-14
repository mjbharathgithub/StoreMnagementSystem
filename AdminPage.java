package myPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class AdminPage {

    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addStockButton;
    private JButton deleteStockButton;
    private JLabel statusLabel;

    private static final String DATA_FILE = "product_data.txt"; // File to store product data

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminPage window = new AdminPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminPage() {
        initialize();
        loadProductData(); // Load product data from file on startup
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Product Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
        topPanel.add(searchButton);

        addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStock();
            }
        });
        topPanel.add(addStockButton);

        deleteStockButton = new JButton("Delete Stock");
        deleteStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStock();
            }
        });
        topPanel.add(deleteStockButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price per Unit (Rs)");
        tableModel.addColumn("Quantity");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Status: ");
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    private void searchProduct() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        boolean found = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String productName = ((String) tableModel.getValueAt(i, 0)).toLowerCase();
            if (productName.contains(searchTerm)) {
                table.setRowSelectionInterval(i, i);
                found = true;
                break;
            }
        }
        if (!found) {
            int choice = JOptionPane.showConfirmDialog(frame, "The product is not present. Do you want to add it?", "Product Not Found", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                addProduct();
            }
        }

        // Clear the search bar after searching
        searchField.setText("");
    }


    private void addStock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Enter the quantity to add:", "Add Stock", JOptionPane.PLAIN_MESSAGE);
                if (input != null) {
                    int quantityToAdd = Integer.parseInt(input);
                    int currentQuantity = (int) tableModel.getValueAt(selectedRow, 2);
                    tableModel.setValueAt(currentQuantity + quantityToAdd, selectedRow, 2);
                    statusLabel.setText("Status: Stock added successfully.");
                    saveProductData(); // Save updated product data
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to add stock to.", "No Product Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteStock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                statusLabel.setText("Status: Product deleted successfully.");
                saveProductData(); // Save updated product data
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete.", "No Product Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addProduct() {
        String productName = JOptionPane.showInputDialog(frame, "Enter the product name:", "Add Product", JOptionPane.PLAIN_MESSAGE);
        if (productName != null && !productName.isEmpty()) {
            String priceStr = JOptionPane.showInputDialog(frame, "Enter the price per unit (Rs):", "Add Product", JOptionPane.PLAIN_MESSAGE);
            if (priceStr != null && !priceStr.isEmpty()) {
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter the initial quantity:", "Add Product", JOptionPane.PLAIN_MESSAGE);
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        int quantity = Integer.parseInt(quantityStr);
                        Object[] newRow = {productName, price, quantity};
                        tableModel.addRow(newRow);
                        statusLabel.setText("Status: Product added successfully.");
                        saveProductData(); // Save updated product data
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame, "Invalid price or quantity. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void saveProductData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String productName = (String) tableModel.getValueAt(i, 0);
                double price = (double) tableModel.getValueAt(i, 1);
                int quantity = (int) tableModel.getValueAt(i, 2);
                writer.write(productName + "," + price + "," + quantity);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProductData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String productName = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    Object[] newRow = {productName, price, quantity};
                    tableModel.addRow(newRow);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
