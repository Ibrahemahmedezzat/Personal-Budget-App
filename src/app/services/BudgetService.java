package app.services;

import app.data.FileDatabase;
import java.util.*;

/**
 * Service class responsible for managing user budgets.
 * It allows creating budgets and updating them based on spending,
 * while tracking limits and warning when thresholds are reached.
 */
public class BudgetService {

    private static final String FILE_NAME = "budgets.txt";

    /**
     * Creates a new budget for a user and saves it to the database file.
     *
     * @param id the ID of the user
     * @param category the budget category (e.g., Food, Transport)
     * @param limit the maximum allowed spending for this category
     */
    public void create(int id, String category, double limit) {

        FileDatabase.save(FILE_NAME,
                id + "," + category + "," + limit + ",0");

        System.out.println("Budget Created ✅");
    }

    /**
     * Updates an existing budget by adding a new spending amount.
     * It calculates the current usage percentage and prints warnings
     * if the budget is near or over the limit.
     *
     * @param userId the ID of the user
     * @param category the budget category to update
     * @param amount the spending amount to add
     */
    public void update(int userId, String category, double amount) {

        List<String> lines = new ArrayList<>();
        boolean found = false;

        for (String[] budget : FileDatabase.read(FILE_NAME)) {

            if (Integer.parseInt(budget[0]) == userId &&
                    budget[1].equals(category)) {

                found = true;

                double limit = Double.parseDouble(budget[2]);
                double spent = Double.parseDouble(budget[3]);

                spent += amount;

                double percent = (spent / limit) * 100;

                if (percent >= 100) {
                    System.out.println("🚨 OVER BUDGET!");
                } else if (percent >= 80) {
                    System.out.println("⚠️ Near limit!");
                }

                System.out.println("Preview: " + spent + "/" + limit);

                lines.add(userId + "," + category + "," + limit + "," + spent);

            } else {
                lines.add(budget[0] + "," + budget[1] + "," +
                        budget[2] + "," + budget[3]);
            }
        }

        if (found) {
            FileDatabase.overwrite(FILE_NAME, lines);
        }
    }
}