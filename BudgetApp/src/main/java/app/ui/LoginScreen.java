package app.ui;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import app.controllers.UserController;
import app.models.User;

public class LoginScreen {

    private final Stage stage;
    private final UserController controller = new UserController();
    private boolean isLogin = true;

    public LoginScreen(Stage stage) { this.stage = stage; }

    public void show() {
        VBox left = buildBrandPanel();
        VBox right = buildFormPanel();

        HBox root = new HBox(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 660);
        stage.setScene(scene);
        stage.setTitle("Budget Manager – Login");
        stage.show();
    }

    private VBox buildBrandPanel() {
        VBox box = new VBox(20);
        box.setPrefWidth(370);
        box.setMinWidth(300);
        box.setStyle("-fx-background-color: " + AppStyles.PRIMARY + ";");
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50, 40, 50, 40));

        StackPane decoration = new StackPane();
        Circle c1 = new Circle(80); c1.setFill(Color.web("#FFFFFF", 0.08));
        Circle c2 = new Circle(55); c2.setFill(Color.web("#FFFFFF", 0.10));
        Circle c3 = new Circle(32); c3.setFill(Color.web("#FFFFFF", 0.15));
        decoration.getChildren().addAll(c1, c2, c3);

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 52px;");

        Label title = new Label("Budget Manager");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label sub = new Label("Take control of your finances.\nTrack income, manage budgets,\nand reach your goals.");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: rgba(255,255,255,0.80); -fx-text-alignment: center;");
        sub.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        HBox f1 = featurePill("📊", "Track Spending");
        HBox f2 = featurePill("🎯", "Set Goals");
        HBox f3 = featurePill("🔔", "Smart Alerts");

        box.getChildren().addAll(decoration, icon, title, sub, f1, f2, f3);
        return box;
    }

    private HBox featurePill(String emoji, String text) {
        Label e = new Label(emoji); e.setStyle("-fx-font-size: 16px;");
        Label t = new Label(text);
        t.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        HBox pill = new HBox(10, e, t);
        pill.setAlignment(Pos.CENTER_LEFT);
        pill.setPadding(new Insets(8, 18, 8, 18));
        pill.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 20;");
        pill.setMaxWidth(220);
        return pill;
    }

    private VBox buildFormPanel() {
        VBox box = new VBox(16);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(50, 60, 50, 60));
        box.setStyle(AppStyles.SCENE_BG);

        Label heading = new Label("Welcome Back 👋");
        heading.setStyle(AppStyles.LABEL_TITLE);

        Label sub = new Label("Sign in to your account");
        sub.setStyle(AppStyles.LABEL_SUBTITLE);

        Button btnLogin = new Button("Login");
        Button btnRegister = new Button("Register");
        styleToggle(btnLogin, true);
        styleToggle(btnRegister, false);

        HBox toggle = new HBox(0, btnLogin, btnRegister);
        toggle.setStyle("-fx-background-color: " + AppStyles.BORDER + "; -fx-background-radius: 12;");
        toggle.setPadding(new Insets(4));
        toggle.setAlignment(Pos.CENTER);

        // ── Fields ──────────────────────────────────────────────────────
        TextField userField = styledField("👤  Username");
        PasswordField passField = styledPass("🔒  Password");
        TextField salaryField = styledField("💵  Monthly Income (EGP)");

        VBox userBlock   = fieldBlock("Username", userField);
        VBox passBlock   = fieldBlock("Password", passField);
        VBox salaryBlock = fieldBlock("Monthly Income (EGP)", salaryField);

        // salary مخفي في البداية
        salaryBlock.setVisible(false);
        salaryBlock.setManaged(false);

        Label msgLabel = new Label();
        msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.DANGER + ";");
        msgLabel.setWrapText(true);
        msgLabel.setMaxWidth(280);

        Button actionBtn = new Button("Login");
        actionBtn.setStyle(AppStyles.BTN_PRIMARY);
        actionBtn.setPrefWidth(280);
        AppStyles.addHover(actionBtn, AppStyles.BTN_PRIMARY, AppStyles.BTN_PRIMARY_HOVER);

        btnLogin.setOnAction(e -> {
            isLogin = true;
            heading.setText("Welcome Back 👋");
            sub.setText("Sign in to your account");
            actionBtn.setText("Login");
            salaryBlock.setVisible(false);
            salaryBlock.setManaged(false);
            salaryField.clear();
            styleToggle(btnLogin, true);
            styleToggle(btnRegister, false);
            msgLabel.setText("");
        });

        btnRegister.setOnAction(e -> {
            isLogin = false;
            heading.setText("Create Account ✨");
            sub.setText("Start your financial journey");
            actionBtn.setText("Register");
            salaryBlock.setVisible(true);
            salaryBlock.setManaged(true);
            styleToggle(btnLogin, false);
            styleToggle(btnRegister, true);
            msgLabel.setText("");
        });

        actionBtn.setOnAction(e -> {
            String uname = userField.getText().trim();
            String pass  = passField.getText().trim();

            if (uname.isEmpty() || pass.isEmpty()) {
                msgLabel.setText("⚠️ Please fill in all fields.");
                msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.WARNING + ";");
                return;
            }

            if (isLogin) {
                User u = controller.login(uname, pass);
                if (u != null) {
                    new MainScreen(stage, u).show();
                } else {
                    msgLabel.setText("❌ Wrong username or password.");
                    msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.DANGER + ";");
                }
            } else {
                String salaryText = salaryField.getText().trim();
                if (salaryText.isEmpty()) {
                    msgLabel.setText("⚠️ Please enter your monthly income.");
                    msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.WARNING + ";");
                    return;
                }
                try {
                    double salary = Double.parseDouble(salaryText);
                    controller.signup(uname, pass, salary);
                    msgLabel.setText("✅ Account created! Please login.");
                    msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.SUCCESS + ";");
                    btnLogin.fire();
                } catch (NumberFormatException ex) {
                    msgLabel.setText("❌ Invalid income amount.");
                    msgLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.DANGER + ";");
                }
            }
        });

        passField.setOnAction(e -> actionBtn.fire());
        salaryField.setOnAction(e -> actionBtn.fire());

        // spacer عشان يتمركز المحتوى
        Region topSpacer = new Region();
        Region botSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(botSpacer, Priority.ALWAYS);

        box.getChildren().addAll(
            topSpacer,
            heading, sub, toggle,
            userBlock,
            passBlock,
            salaryBlock,
            msgLabel,
            actionBtn,
            botSpacer
        );
        return box;
    }

    private VBox fieldBlock(String label, Control field) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
        VBox b = new VBox(5, lbl, field);
        b.setMaxWidth(280);
        b.setMinWidth(280);
        return b;
    }

    private TextField styledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setStyle(AppStyles.INPUT_FIELD);
        f.setPrefWidth(280);
        f.focusedProperty().addListener((o, old, nv) ->
            f.setStyle(nv ? AppStyles.INPUT_FIELD_FOCUSED : AppStyles.INPUT_FIELD));
        return f;
    }

    private PasswordField styledPass(String prompt) {
        PasswordField f = new PasswordField();
        f.setPromptText(prompt);
        f.setStyle(AppStyles.INPUT_FIELD);
        f.setPrefWidth(280);
        f.focusedProperty().addListener((o, old, nv) ->
            f.setStyle(nv ? AppStyles.INPUT_FIELD_FOCUSED : AppStyles.INPUT_FIELD));
        return f;
    }

    private void styleToggle(Button btn, boolean active) {
        if (active) {
            btn.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: " + AppStyles.PRIMARY + ";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 8 24 8 24;" +
                "-fx-cursor: hand;");
        } else {
            btn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + AppStyles.TEXT_MUTED + ";" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 8 24 8 24;" +
                "-fx-cursor: hand;");
        }
    }
}