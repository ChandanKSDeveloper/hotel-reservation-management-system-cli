# Hotel Reservation Management System 🏨

A Java-based hotel reservation management system designed to handle hotel operations like room bookings, customer management, and more. The system connects to a MySQL database via JDBC for seamless data handling and storage.

## Table of Contents 📚
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [How to Run the Project](#how-to-run-the-project)
- [Database Setup](#database-setup)
- [How it Works](#how-it-works)
- [Project Structure](#project-structure)
- [Future Enhancements](#future-enhancements)
- [Troubleshooting](#troubleshooting)

## Features 🌟
- **Room Booking**: Add, update, delete, and view bookings.
- **Customer Management**: Manage customer information, including adding, updating, and deleting customers.
- **Room Availability Check**: View available rooms for booking based on current reservations.
- **Exit Confirmation**: Option to safely exit the program with a confirmation prompt.
- **Robust Error Handling**: Validates user inputs and displays appropriate error messages for invalid actions.

## Technologies Used 🛠️
- **Programming Language**: Java (Core Java concepts such as loops, conditionals, and OOP principles)
- **Database**: MySQL
- **JDBC**: Java Database Connectivity for interacting with the database
- **IDE**: Any Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans)
- **JDK Version**: Java 17 or higher

## Prerequisites 🔧
Before running the project, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
- **MySQL Server**: Ensure it's running
- **MySQL Connector for Java (JDBC Driver)**
- **Java IDE**: IntelliJ IDEA, Eclipse, or any other preferred IDE

## How to Run the Project 🚀

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/hotel-reservation-management.git
    cd hotel-reservation-management
    ```

2. **Open the project in your Java IDE** (e.g., IntelliJ IDEA, Eclipse).

3. **Set up the MySQL database**:
    - Ensure MySQL is installed and running.
    - Import the database schema using the provided SQL script (explained below).

4. **Configure Database Connection**:
    - Locate the database connection configuration in your Java code.
    - Update your MySQL credentials:
      ```java
      final String URL = "jdbc:mysql://localhost:3306/hotel_db";
      final String USER = "your_mysql_username";
      final String PASSWORD = "your_mysql_password";
      ```

5. **Run the project**:
    - Locate and run the `Main.java` file to start the application.

## Database Setup 🗄️

1. **Create the Database in MySQL**:
    Run the following command in your MySQL client:
    ```sql
    CREATE DATABASE hotel_db;
    ```

2. **Import the SQL Schema**:
    Use the provided SQL script (`hotel_db.sql`) to create tables and seed initial data:
    ```bash
    mysql -u your_username -p hotel_db < hotel_db.sql
    ```

3. **Verify Tables**:
    Ensure the required tables (`customers`, `rooms`, `reservations`) are created by running:
    ```sql
    SHOW TABLES;
    ```

## How it Works ⚙️

The system operates through a menu-driven console interface. Here's a quick overview of the main features:

- **Room Booking**: Allows you to add, view, update, and delete room bookings.
- **Customer Management**: Manages customer information (add, update, delete).
- **Data Persistence**: All data (bookings, customers) is stored in a MySQL database.

## Project Structure 📂
```plaintext
hotel-reservation-management/
├── src/
│   ├── Main.java                # Entry point for the application
│   ├── DBConnection.java.java   # Handles database connectivity and operations
│   ├── RunStatement.java        # contain sql statement to send query
│   ├── InputHandler.java        # Utility class for handling user input
│   └── TextUI.java.java         # Manages reservation-related functionality
├── README.md                   # Project documentation
└── .gitignore                  # Git ignore file
```
## Troubleshooting ⚠️

- **Invalid MySQL Credentials**: Ensure the username and password in the code match your MySQL setup.
- **JDBC Driver Not Found**: If the MySQL Connector JAR is missing, add it to your project's build path. You can download it from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).
- **Database Does Not Exist**: Ensure you've have (hotel_db) database.
