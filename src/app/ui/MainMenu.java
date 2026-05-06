package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;

/**
 * User Interface class that represents the main menu screen of the application.
 * It provides navigation to different features such as transactions, budgets,
 * goals, and reports.
 */
public class MainMenu {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Constructs the MainMenu with required dependencies.
     *
     * @param stage the JavaFX stage used for displaying the UI
     * @param user the currently logged-in user
     */
    public MainMenu(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

    /**
     * Displays the main menu screen.
     * Provides navigation buttons to different application modules
     * such as transactions, budget, goals, and reports.
     */
    public void show(){

        Button t = new Button("Add Transaction");
        Button b = new Button("Budget");
        Button g = new Button("Goal");
        Button r = new Button("Report");
        Button exit = new Button("Exit");

        t.setOnAction(e -> new TransactionUI(stage, user).show());
        b.setOnAction(e -> new BudgetUI(stage, user).show());
        g.setOnAction(e -> new GoalUI(stage, user).show());
        r.setOnAction(e -> new ReportUI(stage, user).show());
        exit.setOnAction(e -> stage.close());

        VBox root = new VBox(10, t, b, g, r, exit);

        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}