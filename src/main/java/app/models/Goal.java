package app.models;

/**
 * Represents a financial or personal goal for a user.
 * This class stores information about the goal such as its name,
 * target amount, and the amount saved so far.
 */
public class Goal {

    /**
     * The ID of the user who owns this goal.
     */
    private int userId;

    /**
     * The name of the goal (e.g., Buy a car, Save for travel).
     */
    private String name;

    /**
     * The target amount that the user aims to achieve.
     */
    private double target;

    /**
     * The amount that has been saved so far toward the goal.
     */
    private double saved;

    /**
     * Creates a new goal for a specific user with a name and target amount.
     * The saved amount is initialized to zero by default.
     *
     * @param userId the ID of the user
     * @param name the name of the goal
     * @param target the target amount to achieve
     */
    public Goal(int userId, String name, double target) {
        this.userId = userId;
        this.name = name;
        this.target = target;
        this.saved = 0;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getTarget() {
        return target;
    }

    public double getSaved() {
        return saved;
    }

    // Setter (if needed)
    public void setSaved(double saved) {
        this.saved = saved;
    }
}