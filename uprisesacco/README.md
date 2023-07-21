# Uprise Sacco Project

This project is a simple client-server application for managing a Sacco using Java and MySQL.

## Prerequisites

Before running the project, you'll need the following:

1. Java Development Kit (JDK) version 8 or higher installed on your system.
2. Apache Maven installed on your system.
3. MySQL Database Server installed and running.
4. MySQL Connector/J (JDBC Driver) version 8 or higher.

## Setup

1. Clone the project repository to your local machine:


2. Create a MySQL database for the project (assuming you have MySQL installed):


3. Import the database schema from the `uprise_sacco_db.sql` file:


4. Update the database connection details in the `UpriseServer.java` and `TestDBConnection.java` files (if needed).

## Build and Run

1. Build the project using Maven:

mvn clean package


2. Start the server:

mvn exec:java -Dexec.mainClass="com.example.UpriseServer"

![Screenshot from 2023-07-20 11-49-33](https://github.com/ConradKash/Uprise_Sacco/assets/78595738/698cf308-01fa-4bb5-8e0d-75ae660e296b)



3. In a new terminal, start the client:


ava -cp target/classes com.example.UpriseClient

![image](https://github.com/ConradKash/Uprise_Sacco/assets/78595738/a16adc78-6d67-4e3f-81b5-69e601a40bf1)



## Usage

Once the server and client are running, you can interact with the system using commands. The available commands are:

- `login username password`: Log in as a registered member.
- `deposit amount`: Deposit the specified amount into your account (after logging in).
- `checkstatement`: View your account statement (after logging in).
- `requestloan amount`: Request a loan of the specified amount (after logging in).

Make sure to follow the command format as specified.

Note: The default username and password for testing are provided in the `uprise_sacco_db.sql` file.

Feel free to explore and modify the project as needed!

# WEB SYSTEM FEATURES

1. **CSV File Upload:**
   - Implement a feature in the web system to allow the system administrator to upload CSV files containing member deposits data. Parse the CSV file, validate the data, and store it in the database.

2. **Dashboard for Pending Requests:**
   - Create a dashboard for the system administrator to view pending requests such as login requests, deposits, etc. These pending requests can be displayed as a list with appropriate details, and the administrator can attend to them by updating the database or taking necessary actions.

3. **Upload New Member Details:**
   - If a member's details or their deposit is not previously addressed in the system, provide a functionality for the administrator to upload these details manually. Validate the data before storing it in the database.

4. **Highlight Unaddressed References:**
   - Implement a mechanism to track references that have not been addressed within a period of 5 hours. These unaddressed references should be highlighted with a red star on the dashboard for easy identification.

5. **Graphs and PDF Reports:**
   - Use chart libraries like Chart.js or Highcharts to create graphs representing the assessment of the Sacco. Generate PDF reports using libraries like Dompdf or TCPDF to present a more detailed and elaborate assessment. The reports can include information such as member growth, total deposits, loan status, etc.

6. **Scheduled Email Reports:**
   - Use a scheduled task (e.g., a cron job) to generate the performance report of the Sacco at the top of every hour. Identify active members (members with deposits in the last 6 months), generate the report dynamically based on the data, and send it as an email to all active members.

7. **Security and Authentication:**
   - Implement proper security measures to protect the system and data. Use authentication and authorization mechanisms to control access to sensitive functionalities and data.

8. **Error Handling and Logging:**
   - Implement robust error handling and logging to capture and handle any unexpected errors or issues that may arise during the system's operation.

9. **User-Friendly Interfaces:**
   - Ensure that all user interfaces are user-friendly and intuitive, making it easy for both administrators and members to interact with the system.

10. **Testing and Quality Assurance:**
   - Thoroughly test the web system to ensure all functionalities work as expected. Perform quality assurance to identify and fix any bugs or issues before deploying the system.

11. **Deployment and Maintenance:**
   - Deploy the web system to a production environment. Regularly maintain and update the system as needed, keeping it secure and up-to-date with the latest features and enhancements.



