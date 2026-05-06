package app.services;

import app.data.FileDatabase;

/**
 * Service class responsible for managing user financial goals.
 * It allows creating goals and contributing savings toward them,
 * while tracking progress and completion status.
 */
public class GoalService {

    private static final String FILE_NAME = "goals.txt";

    /**
     * Creates a new financial goal for a user and saves it to the database file.
     *
     * @param id the ID of the user
     * @param name the name of the goal (e.g., Buy a car, Travel)
     * @param target the target amount required to achieve the goal
     */
    public void create(int id, String name, double target) {

        FileDatabase.save(FILE_NAME,
                id + "," + name + "," + target + ",0");

        System.out.println("🎯 Goal Created");
    }

    /**
     * Adds a contribution amount toward an existing goal.
     * Calculates progress and prints whether the goal is achieved
     * or shows the current completion percentage.
     *
     * @param id the ID of the user
     * @param name the name of the goal
     * @param amount the contribution amount to add
     */
    public void contribute(int id, String name, double amount) {

        for (String[] g : FileDatabase.read(FILE_NAME)) {

            if (Integer.parseInt(g[0]) == id && g[1].equals(name)) {

                double target = Double.parseDouble(g[2]);
                double saved = Double.parseDouble(g[3]);

                saved += amount;

                double percent = (saved / target) * 100;

                if (percent >= 100) {
                    System.out.println("🎉 Goal Achieved!");
                } else {
                    System.out.println("📈 Progress: " + percent + "%");
                }

                // (optional improvement in real system: persist update)
            }
        }
    }
}