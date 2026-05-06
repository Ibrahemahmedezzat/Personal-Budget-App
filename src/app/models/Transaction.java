package app.models;

/**
 * Represents a financial transaction for a user.
 * A transaction includes the amount of money and its category
 * (e.g., food, transport, bills, etc.).
 */
public class Transaction {

    /**
     * The ID of the user who made the transaction.
     */
    public int userId;

    /**
     * The amount of the transaction.
     * Positive or negative depending on the system design.
     */
    public double amount;

    /**
     * The category of the transaction (e.g., Food, Rent, Transport).
     */
    public String category;

    /**
     * Creates a new transaction for a specific user.
     *
     * @param userId the ID of the user
     * @param amount the transaction amount
     * @param category the category of the transaction
     */
    public Transaction(int userId, double amount, String category) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
    }
}