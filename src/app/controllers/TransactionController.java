package app.controllers;

import app.services.TransactionService;

public class TransactionController {

    TransactionService s = new TransactionService();

    public void add(int userId, double amount, String category) {
        s.add(userId, amount, category);
    }
}