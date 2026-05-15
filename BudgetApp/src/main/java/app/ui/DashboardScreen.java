package app.ui;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Dashboard screen – shows balance overview + recent transactions + budget summary.
 */
public class DashboardScreen {

    private final User user;

    public DashboardScreen(User user) { this.user = user; }

    public Node build() {
        ScrollPane scroll = new ScrollPane(content());
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");
        return scroll;
    }

    private VBox content() {
        VBox box = new VBox(24);
        box.setPadding(new Insets(32, 36, 32, 36));
        box.setStyle(AppStyles.SCENE_BG);

        // ── Header ───────────────────────────────────────────────────────────
        Label greeting = new Label("Hello, " + user.getName() + " 👋");
        greeting.setStyle(AppStyles.LABEL_TITLE);
        Label sub = new Label("Here's your financial overview");
        sub.setStyle(AppStyles.LABEL_SUBTITLE);

        // ── Stat cards ────────────────────────────────────────────────────────
        double[] data    = calcData();
        double totalIn   = data[0];
        double totalOut  = data[1];
        double balance   = totalIn - totalOut;
        int txCount      = (int) data[2];

        HBox statRow = new HBox(16,
            statCard("💳 Balance",     String.format("EGP %.0f", balance), AppStyles.STAT_PURPLE),
            statCard("⬆ Income",       String.format("EGP %.0f", totalIn),  AppStyles.STAT_GREEN),
            statCard("⬇ Expenses",     String.format("EGP %.0f", totalOut), AppStyles.STAT_RED),
            statCard("🔁 Transactions", String.valueOf(txCount),             AppStyles.STAT_ORANGE)
        );
        statRow.setAlignment(Pos.CENTER_LEFT);

        // ── Budget alert bar ─────────────────────────────────────────────────
        Node alertBox = buildBudgetAlerts();

        // ── Recent transactions ───────────────────────────────────────────────
        Label recTitle = new Label("Recent Transactions");
        recTitle.setStyle(AppStyles.LABEL_SECTION);

        VBox recentBox = buildRecentTransactions();

        box.getChildren().addAll(greeting, sub, statRow, alertBox, recTitle, recentBox);
        return box;
    }

    // ── Stat card ─────────────────────────────────────────────────────────────
    private VBox statCard(String label, String value, String colorStyle) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: rgba(255,255,255,0.85); -fx-font-size: 13px;");
        Label val = new Label(value);
        val.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        VBox card = new VBox(6, lbl, val);
        card.setStyle(colorStyle + " -fx-padding: 20 24 20 24; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        card.setPrefWidth(170);
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }

    // ── Budget alert bars ─────────────────────────────────────────────────────
    private Node buildBudgetAlerts() {
        List<String[]> budgets = FileDatabase.read("budgets.txt");
        List<String[]> mine = new ArrayList<>();
        for (String[] b : budgets)
            if (b.length >= 4 && parseInt(b[0]) == user.getId()) mine.add(b);

        if (mine.isEmpty()) return new Label(); // empty

        VBox box = new VBox(10);
        box.setStyle(AppStyles.CARD_BOX + " -fx-padding: 20;");

        Label title = new Label("📊 Budget Status");
        title.setStyle(AppStyles.LABEL_SECTION);
        box.getChildren().add(title);

        for (String[] b : mine) {
            String cat  = b[1];
            double lim  = parseDouble(b[2]);
            double spent = parseDouble(b[3]);
            double pct  = lim == 0 ? 0 : (spent / lim) * 100;

            String color = pct >= 100 ? AppStyles.DANGER :
                           pct >= 80  ? AppStyles.WARNING : AppStyles.SUCCESS;

            Label catLbl  = new Label(cat);
            catLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");
            Label pctLbl  = new Label(String.format("%.0f%%  (EGP %.0f / %.0f)", pct, spent, lim));
            pctLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");

            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.setPrefWidth(Double.MAX_VALUE);
            bar.setStyle("-fx-accent: " + color + "; -fx-background-radius: 6; -fx-pref-height: 8;");

            HBox row = new HBox(10, catLbl, pctLbl);
            row.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(catLbl, Priority.ALWAYS);

            box.getChildren().addAll(row, bar);
        }
        return box;
    }

    // ── Recent transactions ───────────────────────────────────────────────────
    private VBox buildRecentTransactions() {
        List<String[]> all = FileDatabase.read("transactions.txt");
        VBox box = new VBox(8);

        int count = 0;
        for (int i = all.size() - 1; i >= 0 && count < 5; i--) {
            String[] t = all.get(i);
            if (t.length < 3 || parseInt(t[0]) != user.getId()) continue;

            double amount = parseDouble(t[1]);
            String cat    = t[2];

            HBox row = new HBox();
            row.setStyle(AppStyles.CARD_BOX + " -fx-padding: 14 18 14 18;");
            row.setAlignment(Pos.CENTER_LEFT);

            Label catLbl = new Label("💸  " + cat);
            catLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label amtLbl = new Label(String.format("- EGP %.0f", amount));
            amtLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.DANGER + ";");

            row.getChildren().addAll(catLbl, spacer, amtLbl);
            box.getChildren().add(row);
            count++;
        }

        if (count == 0) {
            Label empty = new Label("No transactions yet. Add your first one! 🚀");
            empty.setStyle(AppStyles.LABEL_SUBTITLE + " -fx-padding: 20;");
            box.getChildren().add(empty);
        }
        return box;
    }

    // ── Data helpers ──────────────────────────────────────────────────────────
    private double[] calcData() {
        double income = user.getSalary();
        double expenses = 0;
        int count = 0;
        for (String[] t : FileDatabase.read("transactions.txt")) {
            if (t.length >= 2 && parseInt(t[0]) == user.getId()) {
                expenses += parseDouble(t[1]);
                count++;
            }
        }
        return new double[]{income, expenses, count};
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; }
    }

    private double parseDouble(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; }
    }
}
