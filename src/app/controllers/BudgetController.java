package app.controllers;

import app.services.BudgetService;

public class BudgetController {

    BudgetService s = new BudgetService();

    public void create(int userId, String category, double limit) {
        s.create(userId, category, limit);
    }
    
    public void checkBudget(int userId, String category, double amount) {
        s.update(userId, category, amount);
    }
}