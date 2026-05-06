package app.models;

/**
 * Represents a budget assigned to a specific user and category.
 * It tracks the spending limit and the amount already spent.
 */
public class Budget {

    /** The ID of the user who owns this budget */
    public int userId;

    /** The category of the budget (e.g., Food, Transport) */
    public String category;

    /** The maximum allowed spending for this category */
    public double limit;

    /** The amount already spent from this budget */
    public double spent;

    /**
     * Constructs a new Budget object.
     *
     * @param userId   the ID of the user
     * @param category the budget category
     * @param limit    the maximum allowed spending
     */
    public Budget(int userId, String category, double limit) {
        this.userId = userId;
        this.category = category;
        this.limit = limit;
        this.spent = 0;
    }
}