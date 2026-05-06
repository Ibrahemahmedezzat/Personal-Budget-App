package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;

/**
 * User Interface class responsible for displaying financial reports.
 * It shows a summary of user spending, budget usage, and categories.
 */
public class ReportUI {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Constructs the ReportUI with required dependencies.
     *
     * @param stage the JavaFX stage used for displaying the UI
     * @param user the currently logged-in user
     */
    public ReportUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

    /**
     * Displays the report screen.
     * Shows a summary of spending, budget usage, and categories.
     */
    public void show(){

        Label title = new Label("📊 Reports");
        Label data = new Label("Total Spent / Budget Usage / Categories");

        VBox root = new VBox(10, title, data);

        stage.setScene(new Scene(root, 350, 250));
    }
}