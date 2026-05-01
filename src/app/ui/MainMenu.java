package app.ui;

import java.util.*;
import app.models.User;
import app.controllers.*;

public class MainMenu {

    User user;
    Scanner sc = new Scanner(System.in);

    TransactionController t = new TransactionController();
    BudgetController b = new BudgetController();
    GoalController g = new GoalController();

    public MainMenu(User u) {
        this.user = u;
    }

    public void show() {

        while (true) {

            System.out.println("\n1-Add Transaction");
            System.out.println("2-Create Budget");
            System.out.println("3-Goal");
            System.out.println("4-Exit");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {

                System.out.print("Amount: ");
                double a = Double.parseDouble(sc.nextLine());

                System.out.print("Category: ");
                String c = sc.nextLine();

                // 🔥 CONFIRMATION STEP
                System.out.println("⚠️ Are you sure you want to add this transaction?");
                System.out.println("Amount: " + a + " | Category: " + c);
                System.out.print("Enter (Y/N): ");

                String confirm = sc.nextLine().trim();

                if (confirm.equalsIgnoreCase("Y")) {
                    t.add(user.id, a, c);
                } else {
                    System.out.println("❌ Cancelled");
                }
            } else if (ch == 2) {

                System.out.print("Category: ");
                String cat = sc.nextLine();

                System.out.print("Limit: ");
                double limit = Double.parseDouble(sc.nextLine());

                if (limit > user.salary) {
                    System.out.println("❌ Budget can't exceed salary!");
                } else {
                    b.create(user.id, cat, limit);
                }

            } else if (ch == 3) {

                System.out.print("Goal: ");
                String name = sc.nextLine();

                System.out.print("Target: ");
                double target = Double.parseDouble(sc.nextLine());

                if (target > user.salary) {
                    System.out.println("❌ Goal can't exceed salary!");
                } else {
                    g.create(user.id, name, target);
                }

            } else
                return;
        }
    }
}