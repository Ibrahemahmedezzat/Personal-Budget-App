package app.services;

import app.data.FileDatabase;
import java.util.*;

/**
 * Service class responsible for generating financial reports for users.
 * It analyzes transactions and provides summaries of total spending
 * and spending breakdown by category.
 */
public class ReportService {

    private static final String FILE_NAME = "transactions.txt";

    /**
     * Displays a full financial report for a specific user.
     * The report includes total spending and breakdown by category.
     *
     * @param userId the ID of the user whose report will be displayed
     */
    public void show(int userId) {

        System.out.println("\n📊 ===== REPORT =====");

        double total = 0;

        Map<String, Double> categoryTotals = new HashMap<>();

        for (String[] t : FileDatabase.read(FILE_NAME)) {

            if (Integer.parseInt(t[0]) == userId) {

                double amount = Double.parseDouble(t[1]);
                String category = t[2];

                total += amount;

                categoryTotals.put(category,
                        categoryTotals.getOrDefault(category, 0.0) + amount);
            }
        }

        System.out.println("💰 Total Spending: " + total);

        System.out.println("\n📂 By Category:");

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
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