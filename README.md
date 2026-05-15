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
```

---

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
