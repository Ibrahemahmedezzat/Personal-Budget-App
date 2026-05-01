package app.controllers;

import app.services.BudgetService;

public class BudgetController {

    BudgetService s = new BudgetService();

    public void create(int userId, String c, double l) {
        s.create(userId, c, l);
    }
}