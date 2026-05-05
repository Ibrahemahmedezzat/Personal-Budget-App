package app.services;

import app.data.FileDatabase;
import app.models.User;

public class TransactionService {

    BudgetService b = new BudgetService();

    public void add(int id, double amount, String cat) {

        if (amount <= 0)
            return;

        // 🔥 FIRST: update budget
        new BudgetService().update(id, cat, amount);

        // THEN save transaction
        FileDatabase.save("transactions.txt",
                id + "," + amount + "," + cat);

        System.out.println("Transaction Added");
    }
}