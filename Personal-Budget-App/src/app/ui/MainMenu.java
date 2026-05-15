package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;

public class MainMenu {
    private Stage stage;
    private User user;

    public MainMenu(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() {
        Button t = new Button("Transactions");
        Button b = new Button("Budgets");
        Button g = new Button("Goals");
        Button r = new Button("Reports");
        Button exit = new Button("Exit");

        // الإصلاح هنا: إضافة (this) كمتغير ثالث
        t.setOnAction(e -> new TransactionUI(stage, user).show());
        b.setOnAction(e -> new BudgetUI(stage, user, this).show());
        g.setOnAction(e -> new GoalUI(stage, user).show());
        r.setOnAction(e -> new ReportUI(stage, user).show());

        exit.setOnAction(e -> stage.close());

        VBox root = new VBox(10, t, b, g, r, exit);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Main Menu");
        stage.show();
    }
}