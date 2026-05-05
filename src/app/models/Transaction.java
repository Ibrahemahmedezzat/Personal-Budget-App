package app.models;

public class Transaction {
    public int userId;
    public double amount;
    public String category;

    public Transaction(int userId, double amount, String category) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
    }
}