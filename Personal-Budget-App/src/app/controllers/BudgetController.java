package app.controllers;

import app.services.BudgetService;

/**
 * Controller class responsible for handling budget-related requests
 * and delegating them to the BudgetService.
 * It acts as a bridge between the UI (or higher layers) and business logic.
 */
public class BudgetController {

    /**
     * Service instance used to handle budget operations.
     */
    private BudgetService budgetService = new BudgetService();

    /**
     * Creates a new budget for a user in a specific category.
     *
     * @param userId the ID of the user
     * @param category the budget category (e.g., Food, Transport)
     * @param limit the maximum allowed spending for the category
     */
    public void create(int userId, String category, double limit) {
        budgetService.create(userId, category, limit);
    }

    /**
     * Checks and updates the budget after a new spending transaction.
     *
     * @param userId the ID of the user
     * @param category the budget category
     * @param amount the spending amount to check against the budget
     */
    public void checkBudget(int userId, String category, double amount) {
        budgetService.update(userId, category, amount);
    }
}