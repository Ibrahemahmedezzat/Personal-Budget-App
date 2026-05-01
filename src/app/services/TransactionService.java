package app.services;

import app.data.FileDatabase;
import app.models.User;

public class TransactionService {

    BudgetService b = new BudgetService();

    public void add(int id, double a, String c) {

        if (a <= 0) {
            System.out.println("❌ Invalid");
            return;
        }

        // 🔥 call budget check BEFORE saving
        b.update(id, c, a);

        FileDatabase.save("transactions.txt",
                id + "," + a + "," + c);

        System.out.println("✅ Transaction Added");
    }
}