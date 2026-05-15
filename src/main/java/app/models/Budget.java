package app.models;

/**
 * Represents a budget assigned to a specific user and category.
 * It tracks the spending limit and the amount already spent.
 */
public class Budget {

    /** The ID of the user who owns this budget */
    private int userId;

    /** The category of the budget (e.g., Food, Transport) */
    private String category;

    /** The maximum allowed spending for this category */
    private double limit;

    /** The amount already spent from this budget */
    private double spent;

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

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public double getLimit() {
        return limit;
    }

    public double getSpent() {
        return spent;
    }

    // Setter (only if needed)
    public void setSpent(double spent) {
        this.spent = spent;
    }
}