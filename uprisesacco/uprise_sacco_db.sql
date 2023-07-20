-- Create the database
CREATE DATABASE uprise_sacco;

-- Switch to the newly created database
USE uprise_sacco;

-- Create the Members table
CREATE TABLE members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    member_number VARCHAR(20) NOT NULL,
    phone_number VARCHAR(15) NOT NULL
);

-- Create the Deposits table
CREATE TABLE deposits (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    date_deposited DATE NOT NULL,
    receipt_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Create the Loans table
CREATE TABLE loans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    loan_application_number VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_period_in_months INT NOT NULL,
    loan_status ENUM('requested', 'granted', 'rejected') DEFAULT 'requested',
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Create the Loan Installments table
CREATE TABLE loan_installments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loan_id INT NOT NULL,
    installment_amount DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    payment_status ENUM('paid', 'pending') DEFAULT 'pending',
    FOREIGN KEY (loan_id) REFERENCES loans(id)
);
