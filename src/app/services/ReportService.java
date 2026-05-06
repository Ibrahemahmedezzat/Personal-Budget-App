package app.services;

import app.data.FileDatabase;
import java.util.*;

/**
 * Service class responsible for generating financial reports for users.
 * It analyzes transactions and provides summaries of total spending
 * and spending breakdown by category.
 */
public class ReportService {

    /**
     * Displays a full financial report for a specific user.
     * The report includes total spending and breakdown by category.
     *
     * @param userId the ID of the user whose report will be displayed
     */
    public void show(int userId) {

        System.out.println("\n📊 ===== REPORT =====");

        double total = 0;

        Map<String, Double> category = new HashMap<>();

        for (String[] t : FileDatabase.read("transactions.txt")) {

            if (Integer.parseInt(t[0]) == userId) {

                double amount = Double.parseDouble(t[1]);
                String cat = t[2];

                total += amount;

                category.put(cat,
                        category.getOrDefault(cat, 0.0) + amount);
            }
        }

        System.out.println("💰 Total Spending: " + total);

        System.out.println("\n📂 By Category:");
        for (String c : category.keySet()) {
            System.out.println(c + " → " + category.get(c));
        }

        System.out.println("====================\n");
    }

    /**
     * Displays a summary report for a user.
     * This method currently calls {@link #show(int)} to present the full report.
     *
     * @param userId the ID of the user
     */
    public void summary(int userId) {
        show(userId);
    }
}