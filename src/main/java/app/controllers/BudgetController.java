package app.controllers;

import app.services.BudgetService;
import app.models.Budget;
import java.util.List;

/**
 * BudgetController – bridges UI and BudgetService.
 * Delegates createBudget() to service which stores in ArrayList + file.
 */
public class BudgetController {

    private final BudgetService budgetService = new BudgetService();

    /** Create a new budget (stored in ArrayList + persisted). */
    public void create(int userId, String category, double limit) {
        budgetService.create(userId, category, limit);
    }

    /**
     * Check & update budget after spending.
     * @return 0=ok, 1=near limit, 2=exceeded
     */
    public int checkBudget(int userId, String category, double amount) {
        return  budgetService.update(userId, category, amount);
    }

    /** Get all budgets for user from in-memory ArrayList. */
    public List<Budget> getBudgets(int userId) {
        return budgetService.getBudgets(userId);
    }

    /** True if a budget already exists for this category. */
    public boolean exists(int userId, String category) {
        return budgetService.exists(userId, category);
    }
}
