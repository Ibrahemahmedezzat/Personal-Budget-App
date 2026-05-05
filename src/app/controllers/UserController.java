package app.controllers;

import app.services.UserService;
import app.models.User;

public class UserController {

    UserService s = new UserService();

    public void signup(String username, String password) {
        s.signup(username, password);
    }

    public User login(String username, String password) {
        return s.login(username, password);
    }

    public void updateBudget(int userId, String category, double amount) {
        s.update(userId, category, amount);
    }
}