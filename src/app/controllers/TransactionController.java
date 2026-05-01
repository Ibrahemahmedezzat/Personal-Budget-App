package app.controllers;

import app.services.TransactionService;

public class TransactionController {

    TransactionService s = new TransactionService();

    public void add(int userId, double amount, String cat){
        s.add(userId, amount, cat);
    }
}