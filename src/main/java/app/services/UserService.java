package app.services;

import java.util.*;
import app.data.FileDatabase;
import app.models.User;

/**
 * Service class responsible for user management operations such as
 * signup, login, and budget-related updates.
 */
public class UserService {

    private static final String USERS_FILE = "users.txt";
    private static final String BUDGETS_FILE = "budgets.txt";

    /**
     * Registers a new user and saves their data into the database file.
     */
    public void signup(String username, String password, double salary) {
        int id = (int) (Math.random() * 10000);
        FileDatabase.save(USERS_FILE,
                id + "," + username + "," + password + "," + salary);
        System.out.println("Signup Done ✅");
    }

    /**
     * Authenticates a user using username and password.
     */
    public User login(String username, String password) {

        for (String[] d : FileDatabase.read(USERS_FILE)) {

            if (d[1].equals(username) && d[2].equals(password)) {

                double salary = Double.parseDouble(d[3]);

                if (salary == 0) {
                    salary = 0; // UI logic should be moved outside service
                }

                return new User(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        salary);
            }
        }
        return null;
    }

    /**
     * Updates the user's budget when a transaction is made.
     */
    public void update(int userId, String category, double amount) {

        List<String> lines = new ArrayList<>();

        for (String[] b : FileDatabase.read(BUDGETS_FILE)) {

            if (Integer.parseInt(b[0]) == userId &&
                    b[1].equals(category)) {

                double limit = Double.parseDouble(b[2]);
                double spent = Double.parseDouble(b[3]) + amount;

                double percent = (spent / limit) * 100;

                if (percent >= 100) {
                    System.out.println("🚨 OVER BUDGET!");
                } else if (percent >= 80) {
                    System.out.println("⚠️ WARNING: Near Limit");
                }

                lines.add(userId + "," + category + "," + limit + "," + spent);

            } else {
                lines.add(String.join(",", b));
            }
        }

        FileDatabase.overwrite(BUDGETS_FILE, lines);
    }
}