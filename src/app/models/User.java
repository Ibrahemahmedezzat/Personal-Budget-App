package app.models;

/**
 * Represents a system user with basic financial and authentication information.
 * This class stores user identity, login credentials, and salary data.
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    private int id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The password of the user (should be stored securely in real applications).
     */
    private String password;

    /**
     * The monthly salary of the user.
     */
    private double salary;

    /**
     * Creates a new user with the given details.
     *
     * @param id the unique user ID
     * @param name the name of the user
     * @param password the user's password
     * @param salary the user's salary
     */
    public User(int id, String name, String password, double salary) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salary = salary;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getPassword() {
        return password;
    }

    // Optional setters (if needed)
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }
}