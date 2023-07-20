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

java -cp target/classes com.example.UpriseServer


3. In a new terminal, start the client:


java -cp target/classes com.example.UpriseClient


## Usage

Once the server and client are running, you can interact with the system using commands. The available commands are:

- `login username password`: Log in as a registered member.
- `deposit amount`: Deposit the specified amount into your account (after logging in).
- `checkstatement`: View your account statement (after logging in).
- `requestloan amount`: Request a loan of the specified amount (after logging in).

Make sure to follow the command format as specified.

Note: The default username and password for testing are provided in the `uprise_sacco_db.sql` file.

Feel free to explore and modify the project as needed!

