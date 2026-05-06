package app.models;

/**
 * Represents a system user with basic financial and authentication information.
 * This class stores user identity, login credentials, and salary data.
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    public int id;

    /**
     * The name of the user.
     */
    public String name;

    /**
     * The password of the user (should be stored securely in real applications).
     */
    public String pass;

    /**
     * The monthly salary of the user.
     */
    public double salary;

    /**
     * Creates a new user with the given details.
     *
     * @param id the unique user ID
     * @param name the name of the user
     * @param pass the user's password
     * @param salary the user's salary
     */
    public User(int id, String name, String pass, double salary) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.salary = salary;
    }

    /**
     * Returns the salary of the user.
     *
     * @return the user's salary
     */
    public double getSalary() {
        return this.salary;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user ID
     */
    public int getId() {
        return this.id;
    }
}