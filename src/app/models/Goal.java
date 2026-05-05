package app.models;

public class Goal {
    public int userId;
    public String name;
    public double target;
    public double saved;

    public Goal(int userId, String name, double target) {
        this.userId = userId;
        this.name = name;
        this.target = target;
        this.saved = 0;
    }
}