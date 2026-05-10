package app.ui;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Reports screen – visual spending breakdown by category.
 */
public class ReportScreen {

    private final User user;

    public ReportScreen(User user) { this.user = user; }

    public Node build() {
        VBox root = new VBox(24);
        root.setPadding(new Insets(32, 36, 32, 36));
        root.setStyle(AppStyles.SCENE_BG);

        Label title = new Label("📈  Financial Reports");
        title.setStyle(AppStyles.LABEL_TITLE);
        Label sub = new Label("Overview of your spending by category");
        sub.setStyle(AppStyles.LABEL_SUBTITLE);

        // ── Summary cards ─────────────────────────────────────────────────────
        Map<String, Double> byCategory = new LinkedHashMap<>();
        double totalSpent = 0;

        for (String[] t : FileDatabase.read("transactions.txt")) {
            if (t.length < 3 || parseInt(t[0]) != user.getId()) continue;
            if (t[2].startsWith("INCOME-")) continue;
            double amt = parseDouble(t[1]);
            byCategory.merge(t[2], amt, Double::sum);
            totalSpent += amt;
        }

        double income = user.getSalary();
        double balance = income - totalSpent;

        HBox summaryRow = new HBox(16,
            summaryCard("💰 Total Income",  String.format("EGP %.0f", income),     AppStyles.STAT_GREEN),
            summaryCard("💸 Total Spent",   String.format("EGP %.0f", totalSpent), AppStyles.STAT_RED),
            summaryCard("💳 Net Balance",   String.format("EGP %.0f", balance),
                balance >= 0 ? AppStyles.STAT_PURPLE : AppStyles.STAT_RED)
        );

        // ── Category breakdown ────────────────────────────────────────────────
        Label byCatTitle = new Label("Spending by Category");
        byCatTitle.setStyle(AppStyles.LABEL_SECTION);

        VBox breakdown = new VBox(12);
        breakdown.setStyle(AppStyles.CARD_BOX + " -fx-padding: 24;");

        if (byCategory.isEmpty()) {
            Label empty = new Label("No expense transactions yet.");
            empty.setStyle(AppStyles.LABEL_SUBTITLE);
            breakdown.getChildren().add(empty);
        } else {
            String[] colors = {
                AppStyles.PRIMARY, AppStyles.SUCCESS, AppStyles.ACCENT,
                AppStyles.WARNING, AppStyles.DANGER, "#4FC3F7", "#AB47BC", "#26A69A"
            };
            int ci = 0;
            for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
                String cat = entry.getKey();
                double amt = entry.getValue();
                double pct = totalSpent == 0 ? 0 : (amt / totalSpent) * 100;
                String color = colors[ci % colors.length];
                ci++;

                breakdown.getChildren().add(categoryRow(cat, amt, pct, color));
            }
        }

        // ── Budget vs Actual ──────────────────────────────────────────────────
        Label bvATitle = new Label("Budget vs Actual");
        bvATitle.setStyle(AppStyles.LABEL_SECTION);

        VBox bvA = buildBudgetVsActual(byCategory);

        root.getChildren().addAll(title, sub, summaryRow, byCatTitle, breakdown, bvATitle, bvA);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");
        return scroll;
    }

    // ── Category row with horizontal bar ─────────────────────────────────────
    private HBox categoryRow(String cat, double amt, double pct, String color) {
        Label catLbl = new Label(cat);
        catLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");
        catLbl.setMinWidth(120);

        // Visual bar
        HBox barBg = new HBox();
        barBg.setStyle("-fx-background-color: " + AppStyles.BORDER + "; -fx-background-radius: 6; -fx-pref-height: 12;");
        HBox.setHgrow(barBg, Priority.ALWAYS);

        HBox barFill = new HBox();
        barFill.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 6; -fx-pref-height: 12;");
        barFill.setPrefWidth(Math.max(4, 3 * pct)); // scale to ~300px max
        barBg.getChildren().add(barFill);

        Label amtLbl = new Label(String.format("EGP %.0f (%.0f%%)", amt, pct));
        amtLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
        amtLbl.setMinWidth(150);

        HBox row = new HBox(12, catLbl, barBg, amtLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // ── Budget vs Actual ──────────────────────────────────────────────────────
    private VBox buildBudgetVsActual(Map<String, Double> byCategory) {
        VBox box = new VBox(12);
        box.setStyle(AppStyles.CARD_BOX + " -fx-padding: 24;");

        List<String[]> budgets = FileDatabase.read("budgets.txt");
        boolean any = false;

        for (String[] b : budgets) {
            if (b.length < 4 || parseInt(b[0]) != user.getId()) continue;
            String cat = b[1];
            double limit = parseDouble(b[2]);
            double actual = byCategory.getOrDefault(cat, 0.0);
            double pct = limit == 0 ? 0 : (actual / limit) * 100;
            String color = pct >= 100 ? AppStyles.DANGER : pct >= 80 ? AppStyles.WARNING : AppStyles.SUCCESS;
            any = true;

            Label catLbl = new Label(cat);
            catLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");
            catLbl.setMinWidth(100);

            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.setPrefWidth(Double.MAX_VALUE);
            bar.setStyle("-fx-accent: " + color + "; -fx-pref-height: 12;");
            HBox.setHgrow(bar, Priority.ALWAYS);

            Label info = new Label(String.format("%.0f / %.0f EGP  (%.0f%%)", actual, limit, pct));
            info.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + ";");
            info.setMinWidth(180);

            HBox row = new HBox(12, catLbl, bar, info);
            row.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().add(row);
        }

        if (!any) {
            Label empty = new Label("No budgets to compare yet.");
            empty.setStyle(AppStyles.LABEL_SUBTITLE);
            box.getChildren().add(empty);
        }

        return box;
    }

    // ── Stat summary card ─────────────────────────────────────────────────────
    private VBox summaryCard(String label, String value, String colorStyle) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: rgba(255,255,255,0.85); -fx-font-size: 13px;");
        Label val = new Label(value);
        val.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox card = new VBox(6, lbl, val);
        card.setStyle(colorStyle + " -fx-padding: 18 22 18 22; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        card.setPrefWidth(200);
        return card;
    }

    private int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; } }
    private double parseDouble(String s) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; } }
}
