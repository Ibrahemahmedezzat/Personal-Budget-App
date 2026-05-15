package app.controllers;

import app.services.GoalService;

/**
 * Controller class responsible for handling goal-related requests
 * and delegating them to the GoalService.
 * It acts as an intermediary between the presentation layer and business logic.
 */
public class GoalController {

    /**
     * Service instance used to handle goal operations.
     */
    private GoalService goalService = new GoalService();

    /**
     * Creates a new financial or personal goal for a user.
     *
     * @param userId the ID of the user
     * @param name the name of the goal (e.g., Buy a car, Travel)
     * @param target the target amount required to achieve the goal
     */
    public void create(int userId, String name, double target) {
        goalService.create(userId, name, target);
    }

    /**
     * Adds progress toward an existing goal by contributing an amount.
     *
     * @param userId the ID of the user
     * @param name the name of the goal
     * @param amount the amount to contribute toward the goal
     */
    public void addProgress(int userId, String name, double amount) {
        goalService.contribute(userId, name, amount);
    }
}