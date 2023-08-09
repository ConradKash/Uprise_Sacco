package com.example;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                if (parts.length == 3) {
                    String memberNumber = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    if (depositAmount(memberNumber, amount)) {
                        return "Deposit successful.";
                    } else {
                        return "Failed to deposit. Member not found.";
                    }
                } else {
                    return "Invalid deposit format. Usage: deposit memberNumber amount";
            
                }
        
            case "checkstatement":
                if (parts.length == 3) {
                    String dateFrom = parts[1];
                    String dateTo = parts[2];
                    String statement = generateStatement(dateFrom, dateTo);
                    return statement;
                } else {
                    return "Invalid checkstatement format. Usage: checkstatement dateFrom dateTo";
                }

            case "requestloan":
                // Handle requestLoan command 
                break;

            case "requestloanstatus":
                // Handle requestLoanstatus command 
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

    // Method to handle the deposit amount in the database
    private static boolean depositAmount(String memberNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Retrieve member_id based on member_number
            String memberIdQuery = "SELECT id FROM members WHERE member_number = ?";
            try (PreparedStatement memberIdStatement = connection.prepareStatement(memberIdQuery)) {
                memberIdStatement.setString(1, memberNumber);
                try (ResultSet memberIdResult = memberIdStatement.executeQuery()) {
                    if (memberIdResult.next()) {
                        int memberId = memberIdResult.getInt("id");
    
                        // Generate receipt number based on timestamp
                        String receiptNumber = generateReceiptNumber();
    
                        // Insert deposit information
                        String insertQuery = "INSERT INTO deposits (member_id, amount, date_deposited, receipt_number) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setInt(1, memberId);
                            preparedStatement.setDouble(2, amount);
                            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Current date
                            preparedStatement.setString(4, receiptNumber);
                            int rowsAffected = preparedStatement.executeUpdate();
    
                            return rowsAffected > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static String generateReceiptNumber() {
        long timestamp = System.currentTimeMillis();
        return "RECEIPT" + timestamp;
    }
    


    //handle checkstatment
    private static String generateStatement(String dateFrom, String dateTo) {
        StringBuilder statementBuilder = new StringBuilder("Statement:\n");
    
        String depositStatement = generateDepositStatement(dateFrom, dateTo);
        statementBuilder.append(depositStatement);
    
        String loanStatement = generateLoanStatement();
        statementBuilder.append(loanStatement);
    
        String performanceInfo = generatePerformanceInfo(0);
        statementBuilder.append(performanceInfo);
    
        return statementBuilder.toString();
    }
    
    private static String generateDepositStatement(String dateFrom, String dateTo) {
        StringBuilder depositStatement = new StringBuilder("Deposit Statement:\n");
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM deposits WHERE date_deposited BETWEEN ? AND ?")) {
            preparedStatement.setString(1, dateFrom);
            preparedStatement.setString(2, dateTo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    double amount = resultSet.getDouble("amount");
                    String depositedDate = resultSet.getString("date_deposited");
                    depositStatement.append("Deposited: ").append(amount).append(" on ").append(depositedDate).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return depositStatement.toString();
    }

    private static String generateLoanStatement() {
        StringBuilder loanStatement = new StringBuilder("Loan Statement:\n");
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM loans")) {
    
            while (resultSet.next()) {
                int memberId = resultSet.getInt("member_id");
                String loanApplicationNumber = resultSet.getString("loan_application_number");
                double loanAmount = resultSet.getDouble("amount");
                int paymentPeriod = resultSet.getInt("payment_period_in_months");
                String loanStatus = resultSet.getString("loan_status");
    
                loanStatement.append("Member ID: ").append(memberId)
                        .append(", Application Number: ").append(loanApplicationNumber)
                        .append(", Amount: ").append(loanAmount)
                        .append(", Payment Period: ").append(paymentPeriod)
                        .append(", Status: ").append(loanStatus)
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return loanStatement.toString();
    }
      
    //geneate performace info
    // Modify the generatePerformanceInfo method
    private static String generatePerformanceInfo(int memberId) {
    StringBuilder performanceInfo = new StringBuilder("\nPerformance Information:\n");

    double loanProgress = calculateLoanProgressFromDB(memberId); // Call new method
    double contributionProgress = calculateContributionProgressFromDB(memberId); // Call new method
    double saccoPerformance = calculateSaccoPerformanceFromDB(); // Call new method

    performanceInfo.append("Loan Progress: ").append(loanProgress).append("%\n");
    performanceInfo.append("Contribution Progress: ").append(contributionProgress).append("%\n");
    performanceInfo.append("Sacco Performance: ").append(saccoPerformance).append("%\n");

    return performanceInfo.toString();
}

// New methods to calculate progress from database retrieved values
    private static double calculateLoanProgressFromDB(int memberId) {
    // Query to retrieve cleared loan installments count and total payment periods
    String sql = "SELECT COUNT(*) AS cleared_installments, SUM(payment_period_in_months) AS total_payment_periods " +
                 "FROM loans JOIN loan_installments ON loans.id = loan_installments.loan_id " +
                 "WHERE loans.member_id = ? AND loan_installments.payment_status = 'paid'";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, memberId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int clearedInstallments = resultSet.getInt("cleared_installments");
                int totalPaymentPeriods = resultSet.getInt("total_payment_periods");
                return (double) clearedInstallments / totalPaymentPeriods * 100;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0; // Default if calculation fails
}

    private static double calculateContributionProgressFromDB(int memberId) {
    // Query to retrieve cleared contributions count and total contribution periods
    String sql = "SELECT COUNT(*) AS cleared_contributions, SUM(contribution_period_in_months) AS total_contribution_periods " +
                 "FROM contribution_table WHERE member_id = ? AND contribution_status = 'cleared'";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, memberId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int clearedContributions = resultSet.getInt("cleared_contributions");
                int totalContributionPeriods = resultSet.getInt("total_contribution_periods");
                return (double) clearedContributions / totalContributionPeriods * 100;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0; // Default if calculation fails
}

    private static double calculateSaccoPerformanceFromDB() {
    // Query to retrieve average total progress
    String sql = "SELECT AVG(total_progress) AS avg_total_progress " +
                 "FROM (SELECT (calculateLoanProgressFromDB(member_id) + calculateContributionProgressFromDB(member_id)) / 2 AS total_progress FROM members) AS total_progress_table";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            return resultSet.getDouble("avg_total_progress");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0; // Default if calculation fails
}

}

