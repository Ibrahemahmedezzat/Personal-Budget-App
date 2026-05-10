package app.ui;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import app.controllers.GoalController;
import app.data.FileDatabase;
import app.models.User;

import java.util.*;

/**
 * Goals screen – create savings goals and track progress.
 */
public class GoalScreen {

    private final User user;
    private final GoalController controller = new GoalController();
    private VBox goalList;

    public GoalScreen(User user) { this.user = user; }

    public Node build() {
        HBox root = new HBox(20);
        root.setPadding(new Insets(32, 36, 32, 36));
        root.setStyle(AppStyles.SCENE_BG);

        VBox form = buildForm();
        VBox list = buildList();
        HBox.setHgrow(list, Priority.ALWAYS);

        root.getChildren().addAll(form, list);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-focus-color: transparent;");
        return scroll;
    }

    // ── Create goal form ──────────────────────────────────────────────────────
    private VBox buildForm() {
        VBox card = new VBox(16);
        card.setStyle(AppStyles.CARD_BOX + " -fx-padding: 24;");
        card.setPrefWidth(280);
        card.setMinWidth(240);

        Label title = new Label("🎯  New Goal");
        title.setStyle(AppStyles.LABEL_SECTION);

        TextField nameField   = field("Goal name (e.g. Vacation)");
        TextField targetField = field("Target Amount (EGP)");
        TextField savedField  = field("Already Saved (EGP)");

        Label msg = new Label();
        msg.setWrapText(true);

        Button saveBtn = new Button("Create Goal");
        saveBtn.setStyle(AppStyles.BTN_PRIMARY);
        saveBtn.setPrefWidth(234);
        AppStyles.addHover(saveBtn, AppStyles.BTN_PRIMARY, AppStyles.BTN_PRIMARY_HOVER);

        saveBtn.setOnAction(e -> {
            String name   = nameField.getText().trim();
            String tgtTxt = targetField.getText().trim();
            String savTxt = savedField.getText().trim();

            if (name.isEmpty() || tgtTxt.isEmpty()) {
                showMsg(msg, "⚠️ Name and target are required.", AppStyles.WARNING);
                return;
            }
            try {
                double target = Double.parseDouble(tgtTxt);
                double saved  = savTxt.isEmpty() ? 0 : Double.parseDouble(savTxt);

                if (target <= 0) { showMsg(msg, "⚠️ Target must be > 0.", AppStyles.WARNING); return; }
                if (saved > target) { showMsg(msg, "⚠️ Saved cannot exceed target.", AppStyles.WARNING); return; }

                // Save: userId,name,target,saved
                FileDatabase.save("goals.txt",
                    user.getId() + "," + name + "," + target + "," + saved);

                showMsg(msg, "✅ Goal created!", AppStyles.SUCCESS);
                nameField.clear(); targetField.clear(); savedField.clear();
                refreshList();
            } catch (NumberFormatException ex) {
                showMsg(msg, "❌ Invalid number.", AppStyles.DANGER);
            }
        });

        card.getChildren().addAll(
            title,
            label("Goal Name"), nameField,
            label("Target Amount"), targetField,
            label("Already Saved"), savedField,
            saveBtn, msg
        );
        return card;
    }

    // ── Goals list ────────────────────────────────────────────────────────────
    private VBox buildList() {
        VBox panel = new VBox(14);
        Label title = new Label("🎯  My Goals");
        title.setStyle(AppStyles.LABEL_SECTION);

        goalList = new VBox(12);
        refreshList();

        panel.getChildren().addAll(title, goalList);
        return panel;
    }

    private void refreshList() {
        goalList.getChildren().clear();
        List<String[]> all = FileDatabase.read("goals.txt");
        int count = 0;

        for (String[] g : all) {
            if (g.length < 4 || parseInt(g[0]) != user.getId()) continue;

            String name   = g[1];
            double target = parseDouble(g[2]);
            double saved  = parseDouble(g[3]);
            double pct    = target == 0 ? 0 : (saved / target) * 100;
            boolean done  = pct >= 100;

            String barColor = done ? AppStyles.SUCCESS : AppStyles.PRIMARY;
            String statusIcon = done ? "🎉" : "🎯";

            VBox card = new VBox(10);
            card.setStyle(AppStyles.CARD_BOX + " -fx-padding: 18 20 18 20;");

            Label nameLbl = new Label(statusIcon + "  " + name);
            nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");

            Label pctLbl = new Label(String.format("%.0f%%", Math.min(pct, 100)));
            pctLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + barColor + ";");

            BorderPane topRow = new BorderPane(null, null, pctLbl, null, nameLbl);

            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.setPrefWidth(Double.MAX_VALUE);
            bar.setStyle("-fx-accent: " + barColor + "; -fx-pref-height: 10;");

            Label savedLbl  = new Label("Saved: EGP " + String.format("%.0f", saved));
            savedLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
            Label targetLbl = new Label("Target: EGP " + String.format("%.0f", target));
            targetLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
            Label remLbl    = new Label("Remaining: EGP " + String.format("%.0f", Math.max(0, target - saved)));
            remLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");

            HBox infoRow = new HBox(20, savedLbl, targetLbl, remLbl);

            // Add contribution field
            TextField contribField = new TextField();
            contribField.setPromptText("Add contribution (EGP)");
            contribField.setStyle(AppStyles.INPUT_FIELD + " -fx-pref-width: 160px;");
            contribField.setPrefWidth(160);

            Button addBtn = new Button("Add");
            addBtn.setStyle(AppStyles.BTN_SUCCESS);
            AppStyles.addHover(addBtn, AppStyles.BTN_SUCCESS, AppStyles.BTN_SUCCESS);

            addBtn.setOnAction(ev -> {
                try {
                    double contrib = Double.parseDouble(contribField.getText().trim());
                    // Update the saved amount in the file
                    updateGoalSaved(name, saved + contrib);
                    refreshList();
                } catch (NumberFormatException ex) { /* ignore */ }
            });

            HBox contribRow = new HBox(10, contribField, addBtn);
            contribRow.setAlignment(Pos.CENTER_LEFT);

            card.getChildren().addAll(topRow, bar, infoRow, contribRow);
            if (done) {
                Label doneLabel = new Label("🎉 Goal Achieved! Great work!");
                doneLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.SUCCESS + "; -fx-font-weight: bold;");
                card.getChildren().add(doneLabel);
            }
            goalList.getChildren().add(card);
            count++;
        }

        if (count == 0) {
            Label empty = new Label("No goals yet. Set your first savings goal! 🎯");
            empty.setStyle(AppStyles.LABEL_SUBTITLE + " -fx-padding: 20;");
            goalList.getChildren().add(empty);
        }
    }

    private void updateGoalSaved(String goalName, double newSaved) {
        List<String> lines = new ArrayList<>();
        for (String[] g : FileDatabase.read("goals.txt")) {
            if (g.length >= 4 && parseInt(g[0]) == user.getId() && g[1].equals(goalName)) {
                lines.add(user.getId() + "," + goalName + "," + g[2] + "," + newSaved);
            } else {
                lines.add(String.join(",", g));
            }
        }
        FileDatabase.overwrite("goals.txt", lines);
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

    private void showMsg(Label lbl, String text, String color) {
        lbl.setText(text);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + ";");
    }

    private int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; } }
    private double parseDouble(String s) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; } }
}
