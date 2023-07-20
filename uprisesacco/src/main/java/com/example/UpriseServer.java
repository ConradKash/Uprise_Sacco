package com.example;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpriseServer {
    private static final int PORT = 12345;
    private static final String DB_URL = "jdbc:mysql://localhost/uprise_sacco";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "free 123";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //test registration

    private static boolean registerMember(String username, String password, String memberNumber, String phoneNumber) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO members (username, password, member_number, phone_number) VALUES (?, ?, ?, ?)")) {
    
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, memberNumber);
            preparedStatement.setString(4, phoneNumber);
            int rowsAffected = preparedStatement.executeUpdate();
    
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

   
private static void handleClient(Socket clientSocket) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

        String inputLine; // Declare inputLine variable here
        while ((inputLine = reader.readLine()) != null) {
            System.out.println("Received command from client: " + inputLine);
            String response; // Declare response variable here

            // Handle register command
            if (inputLine.startsWith("register")) {
                String[] parts = inputLine.split(" ");
                if (parts.length == 5) {
                    String username = parts[1];
                    String password = parts[2];
                    String memberNumber = parts[3];
                    String phoneNumber = parts[4];

                    if (registerMember(username, password, memberNumber, phoneNumber)) {
                        response = "Member registered successfully.";
                    } else {
                        response = "Failed to register member.";
                    }
                } else {
                    response = "Invalid register format. Usage: register username password memberNumber phoneNumber";
                }
            } else {
                // Process other commands as before using switch-case
                response = processRequest(inputLine);
            }

            // Send the response back to the client
            writer.println(response);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private static String processRequest(String request) {
             
        // Implement logic to parse and process client requests
        //  using String.split() to extract command and parameters
        String[] parts = request.split(" ");
        String command = parts[0].toLowerCase();

        
        switch (command) {
            case "login":
                if (parts.length == 3) {
                    String username = parts[1];
                    String password = parts[2];
                    if (validateUser(username, password)) {
                        // User is valid, provide secure menu items
                        return "Welcome, " + username + "! You can proceed to use the sacco menu.";
                    } else {
                        // Invalid login, ask for member number and phone number
                        return "Invalid login. Please provide member number and phone number.";
                    }
                } else {
                    return "Invalid login format. Usage: login username password";
                }
            case "deposit":
                // Handle deposit command
                // ...
                break;
            case "checkstatement":
                // Handle checkStatement command 
                // ...
                break;
            case "requestloan":
                // Handle requestLoan command 
                break;
            
            default:
                return "Unknown command: " + request;
        }
    
        return "Command processing complete."; 
    }

    //  validating user login using the database
    private static boolean validateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM members WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If the result set has at least one row, the user is valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

