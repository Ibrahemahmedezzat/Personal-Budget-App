<<<<<<< HEAD
# 💰 Budget Management App

## Project Structure
```
BudgetManagementApp/
├── src/main/java/app/
│   ├── Main.java
│   ├── ui/
│   │   ├── AppStyles.java        ← all colours & styles
│   │   ├── LoginScreen.java      ← login + register
│   │   ├── MainScreen.java       ← sidebar shell
│   │   ├── DashboardScreen.java  ← overview
│   │   ├── TransactionScreen.java
│   │   ├── BudgetScreen.java
│   │   ├── GoalScreen.java
│   │   └── ReportScreen.java
│   ├── controllers/
│   ├── services/
│   ├── models/
│   └── data/
├── data/                         ← txt files stored here
├── pom.xml
└── .vscode/
    ├── launch.json
    └── settings.json
=======
<div align="center">

# 💰 Personal Budget Management System

**A full-featured desktop application to take control of your personal finances.**

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![JavaFX](https://img.shields.io/badge/JavaFX-GUI-blue?style=for-the-badge)](https://openjfx.io/)
[![Architecture](https://img.shields.io/badge/Architecture-MVC-green?style=for-the-badge)]()
[![License](https://img.shields.io/badge/License-MIT-purple?style=for-the-badge)]()

</div>

---

## 📖 Overview

The **Personal Budget Management System** is a desktop financial management application built with **Java** and **JavaFX**. It provides an intuitive, responsive UI that helps users track income and expenses, manage monthly budgets, set savings goals, and generate insightful financial reports — all in one place.

> 📚 Developed as part of **CS251 – Introduction to Software Engineering**, Cairo University.

---

## ✨ Features

| Feature | Description |
|---|---|
| 💵 **Income & Expense Tracking** | Log transactions with categories and dates |
| 📅 **Budget Management** | Create and monitor monthly spending budgets |
| 🎯 **Savings Goals** | Set savings targets and track progress visually |
| 📊 **Financial Reports** | Generate analytical insights from your financial data |
| 🖥️ **Modern JavaFX UI** | Clean, responsive, and user-friendly interface |
| 💾 **Data Persistence** | All data saved locally via JSON-based storage |

---

## 🏗️ Architecture & Design

This project follows industry-standard software engineering principles:

- **MVC Architecture** — Clear separation of Model, View, and Controller layers
- **OOP Principles** — Encapsulation, inheritance, and polymorphism throughout
- **SOLID Principles** — Single responsibility, open/closed, and dependency inversion applied
- **Modular Design** — Scalable, maintainable, and cleanly structured codebase

---

## 🗂️ Project Structure

```
Personal-Budget-App/
│
├── src/
│   └── app/                   # Application source code (MVC structure)
│
├── Personal-Budget-App/        # JavaFX project module
│
├── data/                       # Application data directory
│
├── users.json                  # Persistent user data storage
├── incomes.json                # Income records
├── budgets.json                # Budget records
│
└── README.md
>>>>>>> 63c3ef789edfda1fb0710eff82b3149e6564b841
```

---

<<<<<<< HEAD
## ▶ How to Run

### Option 1 – Maven (Recommended, easiest)

```bash
cd BudgetManagementApp
mvn javafx:run
```

Maven automatically downloads JavaFX – no extra setup needed.

### Option 2 – VS Code with JavaFX SDK

1. Download JavaFX SDK from https://gluonhq.com/products/javafx/
2. Extract it somewhere, e.g. `C:\javafx-sdk-21`
3. Set environment variable:
   - Windows: `setx PATH_TO_FX "C:\javafx-sdk-21\lib"`
4. Open the project folder in VS Code
5. Press **F5** → "Run BudgetApp"

### Option 3 – VS Code Extension (Java Extension Pack)
1. Install **Extension Pack for Java** from VS Code marketplace
2. Open `pom.xml` – VS Code will auto-detect it as a Maven project
3. In the Maven sidebar → `BudgetManagementApp` → `Plugins` → `javafx` → `javafx:run`

---

## 🔴 The Error You Saw

```
Error: Unable to initialize main class app.Main
Caused by: java.lang.NoClassDefFoundError: Stage
```

**Why:** JavaFX is NOT part of standard Java since Java 11.  
**Fix:** Use Maven (`mvn javafx:run`) – it handles everything automatically.

---

## 📁 Data Files
All data is saved in the `data/` folder as `.txt` files:
- `users.txt` – user accounts
- `transactions.txt` – all transactions  
- `budgets.txt` – budget limits & spending
- `goals.txt` – savings goals
=======
## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- [Java JDK 17+](https://adoptium.net/)
- [JavaFX SDK](https://openjfx.io/)
- An IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/) (recommended)

### Installation

1. **Clone the repository**

```bash
git clone https://github.com/Ibrahemahmedezzat/Personal-Budget-App.git
cd Personal-Budget-App
```

2. **Open in your IDE**

   Import the project as a Java project. Make sure JavaFX is configured in your module path.

3. **Configure JavaFX**

   If using IntelliJ IDEA, add the following to your VM options:

```
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```

4. **Run the application**

   Locate and run the `Main` class inside `src/app/`.

---

## 📸 Screenshots

> _Screenshots can be added here to showcase the UI — e.g., Dashboard, Budget view, Reports._

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java** | Core application logic |
| **JavaFX** | Desktop GUI framework |
| **JSON** | Lightweight data persistence |
| **MVC Pattern** | Application architecture |

---

## 🤝 Contributing

Contributions are welcome! To get started:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 👥 Authors

- **Ibrahem Ahmed Ezzat** — [@Ibrahemahmedezzat](https://github.com/Ibrahemahmedezzat)

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  <sub>Built with ❤️ as part of CS251 – Introduction to Software Engineering · Cairo University</sub>
</div>
>>>>>>> 63c3ef789edfda1fb0710eff82b3149e6564b841
