package com.company.employee_management.ui;

import com.company.employee_management.model.Employee;
import com.company.employee_management.service.EmployeeService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EmployeeManagementUI extends JFrame {
	
    private final EmployeeService employeeService;
    
    private JTable employeeTable;
    private JTextField searchField;

    public EmployeeManagementUI(EmployeeService employeeService) {
        this.employeeService = employeeService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill"));

        // Create components
        createMenuBar();
        createSearchPanel();
        createTablePanel();
        createButtonPanel();

        pack();
        setLocationRelativeTo(null);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu employeeMenu = new JMenu("Employee");
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        employeeMenu.add(addItem);
        employeeMenu.add(editItem);
        employeeMenu.add(deleteItem);

        menuBar.add(fileMenu);
        menuBar.add(employeeMenu);
        setJMenuBar(menuBar);
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel(new MigLayout("fillx"));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField, "growx");
        searchPanel.add(searchButton);
        
        add(searchPanel, "wrap");
    }

    private void createTablePanel() {
        employeeTable = new JTable(); // Initialize with your table model
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, "grow, push, wrap");
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("fillx"));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton, "push, align right");

        add(buttonPanel, "dock south");
    }
}