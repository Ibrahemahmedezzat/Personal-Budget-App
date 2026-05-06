package app.models;

/**
 * Represents an income entry for a user.
 * This class stores the amount of money received by a specific user.
 */
public class Income {

    /**
     * The ID of the user who received the income.
     */
    public int userId;

    /**
     * The amount of money received as income.
     */
    public double amount;

    /**
     * Creates a new income record for a specific user.
     *
     * @param userId the ID of the user who received the income
     * @param amount the amount of income received
     */
    public Income(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}