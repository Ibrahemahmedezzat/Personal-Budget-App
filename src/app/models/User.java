package app.models;

public class User {
    public int id;
    public String name;
    public String pass;
    public double salary;

    public User(int id, String name, String pass, double salary) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.salary = salary;
    }

    public double getSalary() {
        return this.salary;
    }

    public int getId() {
        return this.id;
    }
}