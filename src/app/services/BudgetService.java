package app.services;

import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Service class responsible for managing user budgets.
 * It allows creating budgets and updating them based on spending,
 * while tracking limits and warning when thresholds are reached.
 */
public class BudgetService {

    /**
     * Creates a new budget for a user and saves it to the database file.
     *
     * @param id the ID of the user
     * @param cat the budget category (e.g., Food, Transport)
     * @param limit the maximum allowed spending for this category
     */
    public void create(int id, String cat, double limit) {

        FileDatabase.save("budgets.txt",
                id + "," + cat + "," + limit + ",0");

        System.out.println("Budget Created ✅");
    }

    /**
     * Updates an existing budget by adding a new spending amount.
     * It calculates the current usage percentage and prints warnings
     * if the budget is near or over the limit.
     *
     * @param userId the ID of the user
     * @param cat the budget category to update
     * @param amount the spending amount to add
     */
    public void update(int userId, String cat, double amount) {

        List<String> lines = new ArrayList<>();
        boolean found = false;

        for (String[] b : FileDatabase.read("budgets.txt")) {

            if (Integer.parseInt(b[0]) == userId && b[1].equals(cat)) {

                found = true;

                double limit = Double.parseDouble(b[2]);
                double spent = Double.parseDouble(b[3]);

                spent += amount;

                double percent = (spent / limit) * 100;

                if (percent >= 100) {
                    System.out.println("🚨 OVER BUDGET!");
                } else if (percent >= 80) {
                    System.out.println("⚠️ Near limit!");
                }

                System.out.println("Preview: " + spent + "/" + limit);

                lines.add(userId + "," + cat + "," + limit + "," + spent);

            } else {
                lines.add(b[0] + "," + b[1] + "," + b[2] + "," + b[3]);
            }
        }

        if (found) {
            FileDatabase.overwrite("budgets.txt", lines);
        }
    }
}