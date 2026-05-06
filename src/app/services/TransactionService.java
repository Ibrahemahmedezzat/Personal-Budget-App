package app.services;

import app.data.FileDatabase;

/**
 * Service class responsible for handling user transactions.
 * It records new transactions and updates related budgets accordingly.
 */
public class TransactionService {

    private static final String FILE_NAME = "transactions.txt";

    /**
     * Instance of BudgetService used to update budget information
     * whenever a new transaction is added.
     */
    private BudgetService budgetService = new BudgetService();

    /**
     * Adds a new financial transaction for a user.
     * If the amount is valid, it updates the related budget first,
     * then stores the transaction in the database file.
     *
     * @param id the ID of the user
     * @param amount the transaction amount (must be greater than 0)
     * @param category the category of the transaction (e.g., Food, Transport)
     */
    public void add(int id, double amount, String category) {

        if (amount <= 0) {
            return;
        }

        // FIRST: update budget
        budgetService.update(id, category, amount);

        // THEN save transaction
        FileDatabase.save(FILE_NAME,
                id + "," + amount + "," + category);

        System.out.println("Transaction Added");
    }
}