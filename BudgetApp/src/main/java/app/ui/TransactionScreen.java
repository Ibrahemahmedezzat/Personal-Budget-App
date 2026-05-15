package app.ui;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import app.controllers.TransactionController;
import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Transaction management screen.
 * Lets users add a new transaction (expense/income) and view the full history.
 */
public class TransactionScreen {

    private final User user;
    private final TransactionController controller = new TransactionController();

    // for refreshing the list in-place
    private VBox listBox;
    private String filterCategory = "All";

    public TransactionScreen(User user) { this.user = user; }

    public Node build() {
        HBox root = new HBox(20);
        root.setPadding(new Insets(32, 36, 32, 36));
        root.setStyle(AppStyles.SCENE_BG);

        VBox formCard  = buildForm();
        VBox historyPanel = buildHistory();

        HBox.setHgrow(historyPanel, Priority.ALWAYS);
        root.getChildren().addAll(formCard, historyPanel);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");
        return scroll;
    }

    // ── Add transaction form ──────────────────────────────────────────────────
    private VBox buildForm() {
        VBox card = new VBox(16);
        card.setStyle(AppStyles.CARD_BOX + " -fx-padding: 24;");
        card.setPrefWidth(280);
        card.setMinWidth(240);

        Label title = new Label("➕  Add Transaction");
        title.setStyle(AppStyles.LABEL_SECTION);

        // Type toggle
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbExpense = new RadioButton("Expense");
        RadioButton rbIncome  = new RadioButton("Income");
        rbExpense.setToggleGroup(tg);
        rbIncome.setToggleGroup(tg);
        rbExpense.setSelected(true);
        rbExpense.setStyle("-fx-font-size: 13px;");
        rbIncome.setStyle("-fx-font-size: 13px;");
        HBox typeRow = new HBox(16, rbExpense, rbIncome);
        typeRow.setAlignment(Pos.CENTER_LEFT);

        TextField amtField = field("Amount (EGP)");
        ComboBox<String> catBox = new ComboBox<>();
        catBox.getItems().addAll(
            "Food", "Transport", "Bills", "Entertainment",
            "Health", "Shopping", "Salary", "Other");
        catBox.setValue("Food");
        catBox.setStyle(AppStyles.INPUT_FIELD + " -fx-pref-width: 234px;");
        catBox.setPrefWidth(234);

        TextField descField = field("Description (optional)");

        Label msg = new Label();
        msg.setWrapText(true);
        msg.setStyle("-fx-font-size: 12px;");

        Button saveBtn = new Button("Save Transaction");
        saveBtn.setStyle(AppStyles.BTN_PRIMARY);
        saveBtn.setPrefWidth(234);
        AppStyles.addHover(saveBtn, AppStyles.BTN_PRIMARY, AppStyles.BTN_PRIMARY_HOVER);

        saveBtn.setOnAction(e -> {
            String amtText = amtField.getText().trim();
            String cat     = catBox.getValue();

            if (amtText.isEmpty()) {
                showMsg(msg, "⚠️ Please enter an amount.", AppStyles.WARNING);
                return;
            }
            try {
                double amount = Double.parseDouble(amtText);
                if (amount <= 0) { showMsg(msg, "⚠️ Amount must be > 0.", AppStyles.WARNING); return; }

                if (rbExpense.isSelected()) {
                    controller.add(user.getId(), amount, cat);
                } else {
                    // Income: store as negative budget impact (just record it)
                    FileDatabase.save("transactions.txt",
                        user.getId() + "," + amount + ",INCOME-" + cat);
                }
                showMsg(msg, "✅ Transaction saved!", AppStyles.SUCCESS);
                amtField.clear();
                descField.clear();
                refreshList();
            } catch (NumberFormatException ex) {
                showMsg(msg, "❌ Invalid amount.", AppStyles.DANGER);
            }
        });

        card.getChildren().addAll(
            title,
            label("Type"), typeRow,
            label("Amount"), amtField,
            label("Category"), catBox,
            label("Description"), descField,
            saveBtn, msg
        );
        return card;
    }

    // ── Transaction history ───────────────────────────────────────────────────
    private VBox buildHistory() {
        VBox panel = new VBox(14);

        Label title = new Label("📋  Transaction History");
        title.setStyle(AppStyles.LABEL_SECTION);

        // Filter bar
        HBox filterRow = new HBox(10);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        String[] cats = {"All","Food","Transport","Bills","Entertainment","Health","Shopping","Salary","Other"};
        for (String c : cats) {
            Button fb = filterBtn(c);
            fb.setOnAction(e -> {
                filterCategory = c;
                for (javafx.scene.Node n : filterRow.getChildren())
                    if (n instanceof Button b)
                        b.setStyle(b.getText().equals(c) ? filterBtnActive() : filterBtnStyle());
                refreshList();
            });
            filterRow.getChildren().add(fb);
        }

        listBox = new VBox(8);
        refreshList();

        ScrollPane listScroll = new ScrollPane(listBox);
        listScroll.setFitToWidth(true);
        listScroll.setPrefHeight(400);
        listScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");

        panel.getChildren().addAll(title, filterRow, listScroll);
        return panel;
    }

    private void refreshList() {
        listBox.getChildren().clear();
        List<String[]> all = FileDatabase.read("transactions.txt");
        int count = 0;

        for (int i = all.size() - 1; i >= 0; i--) {
            String[] t = all.get(i);
            if (t.length < 3 || parseInt(t[0]) != user.getId()) continue;
            String cat = t[2];
            if (!filterCategory.equals("All") && !cat.contains(filterCategory)) continue;

            double amount = parseDouble(t[1]);
            boolean isIncome = cat.startsWith("INCOME-");
            String displayCat = isIncome ? cat.replace("INCOME-", "") : cat;
            String icon = isIncome ? "⬆" : "⬇";
            String color = isIncome ? AppStyles.SUCCESS : AppStyles.DANGER;

            HBox row = new HBox();
            row.setStyle(AppStyles.CARD_BOX + " -fx-padding: 12 16 12 16;");
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(12);

            Label iconLbl = new Label(icon);
            iconLbl.setStyle("-fx-font-size: 16px; -fx-text-fill: " + color + ";");

            Label catLbl = new Label(displayCat);
            catLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            String sign = isIncome ? "+" : "-";
            Label amtLbl = new Label(sign + " EGP " + String.format("%.2f", amount));
            amtLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

            row.getChildren().addAll(iconLbl, catLbl, spacer, amtLbl);
            listBox.getChildren().add(row);
            count++;
        }

        if (count == 0) {
            Label empty = new Label("No transactions found.");
            empty.setStyle(AppStyles.LABEL_SUBTITLE + " -fx-padding: 20;");
            listBox.getChildren().add(empty);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
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

    private Button filterBtn(String text) {
        Button b = new Button(text);
        boolean active = text.equals(filterCategory);
        b.setStyle(active ? filterBtnActive() : filterBtnStyle());
        return b;
    }

    private String filterBtnStyle() {
        return "-fx-background-color: white; -fx-border-color: " + AppStyles.BORDER +
               "; -fx-border-radius: 20; -fx-background-radius: 20;" +
               "-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED +
               "; -fx-padding: 5 14 5 14; -fx-cursor: hand;";
    }

    private String filterBtnActive() {
        return "-fx-background-color: " + AppStyles.PRIMARY + "; -fx-border-radius: 20;" +
               "-fx-background-radius: 20; -fx-font-size: 12px;" +
               "-fx-text-fill: white; -fx-padding: 5 14 5 14; -fx-cursor: hand;";
    }

    private void showMsg(Label lbl, String text, String color) {
        lbl.setText(text);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + ";");
    }

    private int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; } }
    private double parseDouble(String s) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; } }
}
