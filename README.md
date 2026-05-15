📊 Budget App

A Java Console-Based Personal Finance Management System designed to help users efficiently manage their income, expenses, budgets, and savings goals while generating insightful financial reports and statistics.

🎯 Project Overview

The Budget App is built to simulate a real-world personal finance tracker.

It allows users to take full control of their financial life by:

Tracking daily expenses and income
Organizing budgets per category
Setting savings goals
Receiving financial alerts
Viewing detailed reports and statistics

The system is fully console-based and follows Object-Oriented Programming (OOP) principles with a modular architecture and file-based data storage.

✨ Key Features

🔐 Authentication System

User Signup
Secure Login
Basic session handling

💰 Budget Management

Create budgets per category (Food, Transport, etc.)
Set monthly spending limits
Monitor remaining budget

📉 Expense & Income Tracking

Add income sources
Record daily expenses
Categorize transactions
Track financial flow over time

🚨 Financial Alerts

Alerts when spending exceeds budget limits
Warnings for low savings balance
Smart notifications based on user activity

🎯 Savings Goals

Set financial goals (e.g., Buy a Laptop, Travel)
Track progress toward goals
Automatic savings calculations

📊 Reports & Statistics

Monthly income vs expenses report
Category-based spending analysis
Summary statistics for better decision-making

🧠 Technologies Used

☕ Java (Core Programming Language)

🧱 Object-Oriented Programming (OOP Principles)

📁 File Handling (Data Storage without Database)

🧮 Collections Framework (Lists, Maps, etc.)

🏗️ Project Architecture

The project follows a layered modular structure:

### 🏗️ Project Architecture

```text
BudgetApp/
└── src/
    ├── models/                # Data Entities
    │   ├── User.java
    │   ├── Budget.java
    │   ├── Expense.java
    │   ├── Income.java
    │   └── SavingsGoal.java
    ├── services/              # Business Logic
    │   ├── AuthService.java
    │   ├── BudgetService.java
    │   ├── TransactionService.java
    │   └── ReportService.java
    ├── ui/                    # Presentation Layer
    │   ├── MainMenu.java
    │   ├── UserMenu.java
    │   └── InputHandler.java
    ├── database/              # Data Access & Storage
    │   ├── FileManager.java
    │   └── DataStorage.java
    └── Main.java              # Application Entry Point

▶️ How to Run the Project

1️⃣ Prerequisites

Install Java JDK 8+
Install IntelliJ IDEA or VS Code

2️⃣ Setup

Clone or download the project
Open it in your IDE

3️⃣ Run

Compile all .java files
Run Main.java

4️⃣ Start Using

Create an account
Login
Start managing your budget 💸

📈 Project Goals

This project was developed to:

Strengthen understanding of OOP concepts
Practice real-world system design
Improve problem-solving with Java
Simulate a financial management system

👥 Team Members
Ibrahim Ahmed
Mohamed Abdullah 
Hesham Mohamed
Youssef Sayed

🚀 Future Improvements

🖥️ GUI version using JavaFX or Swing
🗄️ Database integration (MySQL / SQLite)
📊 Graphical reports and charts
☁️ Cloud synchronization
📱 Mobile version (Android app)
🔔 Advanced smart notifications system
📌 Notes

This project is fully console-based
Data is stored using file handling (no database)
Designed for educational and learning purposes
Focuses on clean architecture and OOP design
