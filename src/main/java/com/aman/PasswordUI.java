package com.aman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordUI extends JFrame {

    private JPanel mainPanel;

    private JTextField loginField;
    private JTextField passwordField;
    private JLabel authenticationResultLabel;
    private JButton loginButton;

    private PasswordManager manager;

    PasswordUI(PasswordManager m) {
        
        this.manager = m;

        configureComponents();
        addListeners();

        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(600, 200));
        setVisible(true);

        //Make the login button the default button 'clicked' when enter pressed
        rootPane.setDefaultButton(loginButton);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void addListeners() {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get data from login and password
                String login = loginField.getText();
                String password = passwordField.getText();
                
                if (login.length() == 0 || password.length() == 0) {
                    authenticationResultLabel.setText("Please enter username and password");
                }
                
                else {

                    //Send username and password to DB for authentication
                    AuthResult result = manager.authenticateUser(login, password);
                    
                    if (result.error != null) {
                        authenticationResultLabel.setText("Error, could not connect to database.");
                    }
                    else if (result.username == null) {
                        //Authentication failure - DB query successful but returned no results - either username or password or both are wrong
                        authenticationResultLabel.setText("Username or password incorrect");
                    } else {
                        //User is authenticated - display welcome message.
                        authenticationResultLabel.setText("Welcome, " + result.username);
                    }
                }
            }
        });

    }

    /*Create and add components to GUI */
    private void configureComponents() {

        JLabel loginLabel = new JLabel("Login");
        loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JTextField();

        authenticationResultLabel = new JLabel();

        loginButton = new JButton("Log In");

        mainPanel = new JPanel();

        GridLayout layoutManager = new GridLayout(4, 2, 10, 10);   //a grid of 4 rows and 2 columns, with vertical and horizontal gaps between components of 10 pixels
        mainPanel.setLayout(layoutManager);

        mainPanel.add(loginLabel);
        mainPanel.add(loginField);

        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        mainPanel.add(loginButton);
        mainPanel.add(authenticationResultLabel);

    }
    
}
