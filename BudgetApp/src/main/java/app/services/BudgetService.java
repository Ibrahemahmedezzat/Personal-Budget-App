package app.services;

import app.data.FileDatabase;
import app.models.Budget;

import java.util.*;

/**
 * BudgetService – manages budgets in an in-memory ArrayList
 * AND persists them to file.
 *
 * SOLID notes
 * -----------
 * S – only business logic for budgets lives here.
 * O – new budget rules can be added without changing callers.
 */
public class BudgetService {

    private static final String FILE_NAME = "budgets.txt";

    /** In-memory cache – loaded lazily from file. */
    private final List<Budget> cache = new ArrayList<>();
    private boolean cacheLoaded = false;

    // ── Public API ─────────────────────────────────────────────────────────

    /** Creates a new budget, stores in ArrayList AND file. */
    public void create(int userId, String category, double limit) {
        ensureCacheLoaded(userId);
        Budget b = new Budget(userId, category, limit);
        cache.add(b);
        persistAll(userId);
        System.out.println("Budget Created ✅ – cache size: " + cache.size());
    }

    /** Returns all budgets for user from in-memory ArrayList. */
    public List<Budget> getBudgets(int userId) {
        ensureCacheLoaded(userId);
        List<Budget> result = new ArrayList<>();
        for (Budget b : cache)
            if (b.getUserId() == userId) result.add(b);
        return result;
    }

    /**
     * Updates spent amount. Returns alert level:
     * 0=ok, 1=near limit (>=80%), 2=over budget (>=100%)
     */
    public int update(int userId, String category, double amount) {
        ensureCacheLoaded(userId);
        int alertLevel = 0;
        for (Budget b : cache) {
            if (b.getUserId() == userId && b.getCategory().equals(category)) {
                b.setSpent(b.getSpent() + amount);
                double pct = (b.getSpent() / b.getLimit()) * 100;
                if      (pct >= 100) { alertLevel = 2; System.out.println("🚨 OVER BUDGET! – " + category); }
                else if (pct >= 80)  { alertLevel = 1; System.out.println("⚠️ Near limit! – " + category); }
                System.out.printf("Spent: %.2f / %.2f%n", b.getSpent(), b.getLimit());
            }
        }
        persistAll(userId);
        return alertLevel;
    }

    /** Checks if a budget already exists for this user+category. */
    public boolean exists(int userId, String category) {
        ensureCacheLoaded(userId);
        for (Budget b : cache)
            if (b.getUserId() == userId && b.getCategory().equals(category)) return true;
        return false;
    }

    // ── Cache helpers ────────────────────────────────────────────────────────

    private void ensureCacheLoaded(int userId) {
        if (cacheLoaded) return;
        cacheLoaded = true;
        for (String[] row : FileDatabase.read(FILE_NAME)) {
            if (row.length < 4) continue;
            try {
                int uid      = Integer.parseInt(row[0].trim());
                String cat   = row[1].trim();
                double limit = Double.parseDouble(row[2].trim());
                double spent = Double.parseDouble(row[3].trim());
                Budget b = new Budget(uid, cat, limit);
                b.setSpent(spent);
                cache.add(b);
            } catch (NumberFormatException ignored) {}
        }
    }

    private void persistAll(int userId) {
        List<String> lines = new ArrayList<>();
        // Keep other users' rows intact
        for (String[] row : FileDatabase.read(FILE_NAME)) {
            if (row.length < 1) continue;
            try {
                if (Integer.parseInt(row[0].trim()) != userId)
                    lines.add(String.join(",", row));
            } catch (NumberFormatException ignored) {}
        }
        // Write this user's updated cache
        for (Budget b : cache)
            if (b.getUserId() == userId)
                lines.add(b.getUserId() + "," + b.getCategory() + "," + b.getLimit() + "," + b.getSpent());
        FileDatabase.overwrite(FILE_NAME, lines);
    }
}
