package app.controllers;

import app.services.GoalService;

public class GoalController {

    GoalService s = new GoalService();

    public void create(int userId, String name, double target) {
        s.create(userId, name, target);
    }

    public void addProgress(int userId, String name, double amount) {
        s.contribute(userId, name, amount);
    }
}