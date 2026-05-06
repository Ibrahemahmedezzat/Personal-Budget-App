package app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;

import app.data.FileDatabase;
import app.models.User;

/**
 * Service class responsible for user management operations such as
 * signup, login, and budget-related updates.
 * It also handles initial salary setup during first login.
 */
public class UserService {

    /**
     * Registers a new user and saves their data into the database file.
     *
     * @param u the username
     * @param p the password
     */
    public void signup(String u, String p) {
        int id = (int) (Math.random() * 10000);
        FileDatabase.save("users.txt", id + "," + u + "," + p + ",0");
        System.out.println("Signup Done ✅");
    }

    /**
     * Authenticates a user using username and password.
     * If the user logs in for the first time (salary = 0),
     * a dialog is shown to input their monthly salary.
     *
     * @param u the username
     * @param p the password
     * @return the authenticated User object if login is successful, otherwise null
     */
    public User login(String u, String p) {
        for (String[] d : FileDatabase.read("users.txt")) {
            if (d[1].equals(u) && d[2].equals(p)) {

                if (Double.parseDouble(d[3]) == 0) {

                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Salary Configuration");
                    dialog.setHeaderText("Welcome, " + u + "!");
                    dialog.setContentText("Please enter your monthly salary:");

                    Optional<String> result = dialog.showAndWait();
                    double s = 0;

                    if (result.isPresent()) {
                        try {
                            s = Double.parseDouble(result.get());
                        } catch (NumberFormatException e) {
                            s = 0;
                        }
                    }

                    FileDatabase.save("users.txt", d[0] + "," + d[1] + "," + d[2] + "," + s);
                    return new User(Integer.parseInt(d[0]), d[1], d[2], s);
                }

                return new User(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Double.parseDouble(d[3]));
            }
        }
        return null;
    }

    /**
     * Updates the user's budget when a transaction is made.
     * Calculates spending percentage and prints warnings when
     * the budget is near or exceeded.
     *
     * @param userId the ID of the user
     * @param cat the budget category
     * @param amount the spending amount to add
     */
    public void update(int userId, String cat, double amount) {
        List<String> lines = new ArrayList<>();
        for (String[] b : FileDatabase.read("budgets.txt")) {
            if (Integer.parseInt(b[0]) == userId && b[1].equals(cat)) {
                double limit = Double.parseDouble(b[2]);
                double spent = Double.parseDouble(b[3]) + amount;
                double percent = (spent / limit) * 100;

                if (percent >= 100) {
                    System.out.println("🚨 OVER BUDGET!");
                } else if (percent >= 80) {
                    System.out.println("⚠️ WARNING: Near Limit");
                }
                lines.add(userId + "," + cat + "," + limit + "," + spent);
            } else {
                lines.add(String.join(",", b));
            }
        }
        FileDatabase.overwrite("budgets.txt", lines);
    }
}