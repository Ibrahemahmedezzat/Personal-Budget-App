package app.controllers;

import app.services.TransactionService;

/**
 * Controller class responsible for handling transaction-related requests
 * and delegating them to the TransactionService.
 * It acts as an interface between the presentation layer and business logic.
 */
public class TransactionController {

    /**
     * Service instance used to manage transaction operations.
     */
    private TransactionService transactionService = new TransactionService();

    /**
     * Adds a new financial transaction for a user.
     *
     * @param userId the ID of the user
     * @param amount the transaction amount
     * @param category the category of the transaction (e.g., Food, Transport)
     */
    public void add(int userId, double amount, String category) {
        transactionService.add(userId, amount, category);
    }
}