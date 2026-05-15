package app.ui;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import app.controllers.BudgetController;
import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Budget management screen.
 * Shows all budgets with progress bars + a form to create new ones.
 */
public class BudgetScreen {

    private final User user;
    private final BudgetController controller = new BudgetController();
    private VBox budgetList;

    public BudgetScreen(User user) { this.user = user; }

    public Node build() {
        HBox root = new HBox(20);
        root.setPadding(new Insets(32, 36, 32, 36));
        root.setStyle(AppStyles.SCENE_BG);

        VBox form = buildForm();
        VBox list  = buildList();
        HBox.setHgrow(list, Priority.ALWAYS);

        root.getChildren().addAll(form, list);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");
        return scroll;
    }

    // ── Create budget form ────────────────────────────────────────────────────
    private VBox buildForm() {
        VBox card = new VBox(16);
        card.setStyle(AppStyles.CARD_BOX + " -fx-padding: 24;");
        card.setPrefWidth(280);
        card.setMinWidth(240);

        Label title = new Label("📊  Create Budget");
        title.setStyle(AppStyles.LABEL_SECTION);

        ComboBox<String> catBox = new ComboBox<>();
        catBox.getItems().addAll(
            "Food", "Transport", "Bills", "Entertainment",
            "Health", "Shopping", "Other");
        catBox.setValue("Food");
        catBox.setStyle(AppStyles.INPUT_FIELD + " -fx-pref-width: 234px;");
        catBox.setPrefWidth(234);

        TextField limitField = field("Budget Limit (EGP)");

        Label msg = new Label();
        msg.setWrapText(true);
        msg.setStyle("-fx-font-size: 12px;");

        Button saveBtn = new Button("Create Budget");
        saveBtn.setStyle(AppStyles.BTN_PRIMARY);
        saveBtn.setPrefWidth(234);
        AppStyles.addHover(saveBtn, AppStyles.BTN_PRIMARY, AppStyles.BTN_PRIMARY_HOVER);

        saveBtn.setOnAction(e -> {
            String limitText = limitField.getText().trim();
            String cat = catBox.getValue();

            if (limitText.isEmpty()) {
                showMsg(msg, "⚠️ Enter a budget limit.", AppStyles.WARNING);
                return;
            }
            try {
                double limit = Double.parseDouble(limitText);
                if (limit <= 0) { showMsg(msg, "⚠️ Limit must be > 0.", AppStyles.WARNING); return; }

                // Check for duplicate
                if (hasBudget(cat)) {
                    showMsg(msg, "❌ Budget for '" + cat + "' already exists.", AppStyles.DANGER);
                    return;
                }

                controller.create(user.getId(), cat, limit);
                showMsg(msg, "✅ Budget created!", AppStyles.SUCCESS);
                limitField.clear();
                refreshList();
            } catch (NumberFormatException ex) {
                showMsg(msg, "❌ Invalid amount.", AppStyles.DANGER);
            }
        });

        card.getChildren().addAll(
            title,
            label("Category"), catBox,
            label("Monthly Limit"), limitField,
            saveBtn, msg
        );
        return card;
    }

    // ── Budget list ───────────────────────────────────────────────────────────
    private VBox buildList() {
        VBox panel = new VBox(14);

        Label title = new Label("📋  My Budgets");
        title.setStyle(AppStyles.LABEL_SECTION);

        budgetList = new VBox(12);
        refreshList();

        panel.getChildren().addAll(title, budgetList);
        return panel;
    }

    private void refreshList() {
        budgetList.getChildren().clear();
        List<String[]> all = FileDatabase.read("budgets.txt");
        int count = 0;

        for (String[] b : all) {
            if (b.length < 4 || parseInt(b[0]) != user.getId()) continue;

            String cat   = b[1];
            double limit = parseDouble(b[2]);
            double spent = parseDouble(b[3]);
            double pct   = limit == 0 ? 0 : (spent / limit) * 100;
            double remaining = Math.max(0, limit - spent);

            String barColor = pct >= 100 ? AppStyles.DANGER :
                              pct >= 80  ? AppStyles.WARNING : AppStyles.SUCCESS;

            String statusIcon = pct >= 100 ? "🚨" : pct >= 80 ? "⚠️" : "✅";

            // Card
            VBox card = new VBox(10);
            card.setStyle(AppStyles.CARD_BOX + " -fx-padding: 18 20 18 20;");

            // Title row
            Label catLbl = new Label(statusIcon + "  " + cat);
            catLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");

            Label pctLbl = new Label(String.format("%.0f%%", pct));
            pctLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + barColor + ";");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox topRow = new HBox(spacer, pctLbl);
            topRow.setAlignment(Pos.CENTER_RIGHT);

            // Actual title in a BorderPane style
            BorderPane titleRow = new BorderPane(null, null, pctLbl, null, catLbl);

            // Progress bar
            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.setPrefWidth(Double.MAX_VALUE);
            bar.setStyle("-fx-accent: " + barColor + "; -fx-pref-height: 10; -fx-background-radius: 5;");

            // Info row
            Label spentLbl = new Label("Spent: EGP " + String.format("%.0f", spent));
            spentLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
            Label remLbl = new Label("Remaining: EGP " + String.format("%.0f", remaining));
            remLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
            Label limLbl = new Label("Limit: EGP " + String.format("%.0f", limit));
            limLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");

            HBox infoRow = new HBox(20, spentLbl, remLbl, limLbl);
            infoRow.setAlignment(Pos.CENTER_LEFT);

            // Budget warning label
            if (pct >= 100) {
                Label warn = new Label("🚨 Budget exceeded by EGP " + String.format("%.0f", spent - limit));
                warn.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.DANGER + "; -fx-font-weight: bold;");
                card.getChildren().addAll(titleRow, bar, infoRow, warn);
            } else {
                card.getChildren().addAll(titleRow, bar, infoRow);
            }

            budgetList.getChildren().add(card);
            count++;
        }

        if (count == 0) {
            Label empty = new Label("No budgets yet. Create your first one! 📊");
            empty.setStyle(AppStyles.LABEL_SUBTITLE + " -fx-padding: 20;");
            budgetList.getChildren().add(empty);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private boolean hasBudget(String cat) {
        for (String[] b : FileDatabase.read("budgets.txt"))
            if (b.length >= 2 && parseInt(b[0]) == user.getId() && b[1].equals(cat))
                return true;
        return false;
    }

    private Label label(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
        return l;
    }

    private TextField field(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setStyle(AppStyles.INPUT_FIELD);
        f.setPrefWidth(234);
        f.focusedProperty().addListener((o, old, nv) ->
            f.setStyle(nv ? AppStyles.INPUT_FIELD_FOCUSED : AppStyles.INPUT_FIELD));
        return f;
    }

    private void showMsg(Label lbl, String text, String color) {
        lbl.setText(text);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + ";");
    }

    private int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; } }
    private double parseDouble(String s) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; } }
}
