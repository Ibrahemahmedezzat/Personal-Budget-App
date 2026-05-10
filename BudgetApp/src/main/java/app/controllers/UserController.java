package app.controllers;

import app.services.UserService;
import app.models.User;

/**
 * Controller class responsible for handling user-related requests
 * such as signup, login, and budget updates.
 * It acts as a bridge between the presentation layer and the UserService.
 */
public class UserController {

    /**
     * Service instance used to handle user operations.
     */
    private UserService userService = new UserService();

    /**
     * Registers a new user in the system.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param salary   the monthly income of the new user
     */
    public void signup(String username, String password, double salary) {
        userService.signup(username, password, salary);
    }

    /**
     * Authenticates a user using username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the authenticated User object if login is successful, otherwise null
     */
    public User login(String username, String password) {
        return userService.login(username, password);
    }

    /**
     * Updates the budget for a specific user and category based on spending.
     *
     * @param userId   the ID of the user
     * @param category the budget category
     * @param amount   the spending amount to update
     */
    public void updateBudget(int userId, String category, double amount) {
        userService.update(userId, category, amount);
    }
}