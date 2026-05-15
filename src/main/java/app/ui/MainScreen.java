package app.ui;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import app.models.User;

/**
 * MainScreen – shell with sidebar + top navigation bar (Back / Forward).
 *
 * Navigation logic is fully delegated to NavigationService (SOLID SRP).
 */
public class MainScreen {

    private final Stage stage;
    private final User  user;

    private final StackPane contentArea = new StackPane();
    private NavigationService navService;

    // Sidebar buttons – kept for active-state styling
    private Button btnDash, btnTrans, btnBudg, btnGoal, btnRep;
    private Button activeBtn;

    // Top-bar nav arrows
    private Button backBtn, fwdBtn;

    public MainScreen(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public void show() {
        // Wire NavigationService
        navService = new NavigationService(contentArea, user);
        navService.setListener(this::onNavigate);

        BorderPane root = new BorderPane();
        root.setLeft(buildSidebar());
        root.setTop(buildTopBar());
        root.setCenter(contentArea);
        contentArea.setStyle(AppStyles.SCENE_BG);

        // Start on Dashboard (no history entry)
        navService.navigate(NavigationService.Screen.DASHBOARD);

        Scene scene = new Scene(root, 1000, 680);
        stage.setScene(scene);
        stage.setTitle("💰 Budget Manager – " + user.getName());
        stage.show();
    }

    // ── Top bar (Back / Forward / breadcrumb) ─────────────────────────────────
    private HBox buildTopBar() {
        backBtn = navBtn("◀  Back");
        fwdBtn  = navBtn("Forward  ▶");

        backBtn.setOnAction(e -> { navService.goBack();    refreshArrows(); });
        fwdBtn.setOnAction(e  -> { navService.goForward(); refreshArrows(); });

        refreshArrows();

        Label appName = new Label("💰 Budget Manager");
        appName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_DARK + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLbl = new Label("👤 " + user.getName());
        userLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");

        HBox bar = new HBox(10, backBtn, fwdBtn, appName, spacer, userLbl);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(10, 20, 10, 20));
        bar.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: " + AppStyles.BORDER + ";" +
            "-fx-border-width: 0 0 1 0;");
        return bar;
    }

    private Button navBtn(String label) {
        Button b = new Button(label);
        b.setStyle(AppStyles.BTN_OUTLINE);
        b.setOnMouseEntered(e -> b.setStyle(AppStyles.BTN_PRIMARY));
        b.setOnMouseExited(e  -> b.setStyle(AppStyles.BTN_OUTLINE));
        return b;
    }

    private void refreshArrows() {
        backBtn.setDisable(!navService.canGoBack());
        fwdBtn.setDisable(!navService.canGoForward());
    }

    // ── Sidebar ────────────────────────────────────────────────────────────────
    private VBox buildSidebar() {
        VBox box = new VBox(6);
        box.setStyle(AppStyles.SIDEBAR);
        box.setPadding(new Insets(28, 14, 28, 14));

        Label logo = new Label("💰 BudgetApp");
        logo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 0 0 12 6;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.12);");

        btnDash  = sideBtn("🏠", "Dashboard");
        btnTrans = sideBtn("💸", "Transactions");
        btnBudg  = sideBtn("📊", "Budgets");
        btnGoal  = sideBtn("🎯", "Goals");
        btnRep   = sideBtn("📈", "Reports");

        btnDash.setOnAction(e  -> navService.navigate(NavigationService.Screen.DASHBOARD));
        btnTrans.setOnAction(e -> navService.navigate(NavigationService.Screen.TRANSACTIONS));
        btnBudg.setOnAction(e  -> navService.navigate(NavigationService.Screen.BUDGETS));
        btnGoal.setOnAction(e  -> navService.navigate(NavigationService.Screen.GOALS));
        btnRep.setOnAction(e   -> navService.navigate(NavigationService.Screen.REPORTS));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logout = sideBtn("🚪", "Logout");
        logout.setOnAction(e -> new LoginScreen(stage).show());

        box.getChildren().addAll(logo, sep,
            btnDash, btnTrans, btnBudg, btnGoal, btnRep,
            spacer, logout);
        return box;
    }

    private Button sideBtn(String emoji, String label) {
        Button btn = new Button(emoji + "  " + label);
        btn.setStyle(AppStyles.SIDEBAR_BTN);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnMouseEntered(e -> {
            if (btn != activeBtn)
                btn.setStyle(AppStyles.SIDEBAR_BTN + "-fx-background-color: rgba(255,255,255,0.08);");
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeBtn) btn.setStyle(AppStyles.SIDEBAR_BTN);
        });
        return btn;
    }

    /** Called by NavigationService whenever the screen changes. */
    private void onNavigate(NavigationService.Screen screen) {
        refreshArrows();
        Button target = switch (screen) {
            case DASHBOARD    -> btnDash;
            case TRANSACTIONS -> btnTrans;
            case BUDGETS      -> btnBudg;
            case GOALS        -> btnGoal;
            case REPORTS      -> btnRep;
        };
        if (activeBtn != null) activeBtn.setStyle(AppStyles.SIDEBAR_BTN);
        activeBtn = target;
        target.setStyle(AppStyles.SIDEBAR_BTN_ACTIVE);
    }
}
