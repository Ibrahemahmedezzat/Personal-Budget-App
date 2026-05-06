package app.services;

import app.data.FileDatabase;
import app.models.User;

/**
 * Service class responsible for handling user transactions.
 * It records new transactions and updates related budgets accordingly.
 */
public class TransactionService {

    /**
     * Instance of BudgetService used to update budget information
     * whenever a new transaction is added.
     */
    BudgetService b = new BudgetService();

    /**
     * Adds a new financial transaction for a user.
     * If the amount is valid, it updates the related budget first,
     * then stores the transaction in the database file.
     *
     * @param id the ID of the user
     * @param amount the transaction amount (must be greater than 0)
     * @param cat the category of the transaction (e.g., Food, Transport)
     */
    public void add(int id, double amount, String cat) {

        if (amount <= 0)
            return;

        //  FIRST: update budget
        new BudgetService().update(id, cat, amount);

        // THEN save transaction
        FileDatabase.save("transactions.txt",
                id + "," + amount + "," + cat);

        System.out.println("Transaction Added");
    }
}