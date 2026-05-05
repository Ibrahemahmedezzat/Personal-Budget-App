package app.models;

public class Budget {
    public int userId;
    public String category;
    public double limit;
    public double spent;

    public Budget(int userId, String category, double limit) {
        this.userId = userId;
        this.category = category;
        this.limit = limit;
        this.spent = 0;
    }
}