package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.BudgetController;

/**
 * UI screen responsible for creating and managing user budgets.
 * Allows the user to define a budget category and limit,
 * and navigate back to the main menu.
 */
public class BudgetUI {

    // Primary stage used to display the scene
    private Stage stage;

    // Currently logged-in user
    private User user;

    // Reference to main menu screen for navigation
    private MainMenu mainMenu;

    // Controller responsible for budget business logic
    private BudgetController controller = new BudgetController();

    /**
     * Constructor initializes BudgetUI with required dependencies.
     *
     * @param stage the JavaFX stage
     * @param user the logged-in user
     * @param mainMenu reference to main menu UI
     */
    public BudgetUI(Stage stage, User user, MainMenu mainMenu) {
        this.stage = stage;
        this.user = user;
        this.mainMenu = mainMenu;
    }

    /**
     * Builds and displays the Budget UI screen.
     * Handles user input, validation, and navigation.
     */
    public void show() {

        // Input field for budget category
        TextField category = new TextField();
        category.setPromptText("Category");

        // Input field for budget limit
        TextField limit = new TextField();
        limit.setPromptText("Limit");

        // Button to save budget
        Button save = new Button("Save");

        // Button to return to main menu
        Button back = new Button("Back to Menu");

        // Message label for feedback
        Label msg = new Label();

        // Handle save action
        save.setOnAction(e -> {
            try {
                double l = Double.parseDouble(limit.getText());

                // Validate budget does not exceed salary
                if (l > user.getSalary()) {
                    msg.setText("Budget > Salary ❌");
                    return;
                }

                // Create budget using controller
                controller.create(user.getId(), category.getText(), l);
                msg.setText("Budget Created ✅");

            } catch (NumberFormatException ex) {
                msg.setText("Invalid Input!");
            }
        });

        // Navigate back to main menu
        back.setOnAction(e -> mainMenu.show());

        // Layout container
        VBox root = new VBox(10, category, limit, save, back, msg);

        // Set scene and display stage
        stage.setScene(new Scene(root, 350, 300));
        stage.setTitle("Manage Budget");
        stage.show();
    }
}