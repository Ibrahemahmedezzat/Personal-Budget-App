package app.models;

public class Income {
    public int userId;
    public double amount;

    public Income(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}