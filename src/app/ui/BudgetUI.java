package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.BudgetController;

/**
 * User Interface class responsible for creating and managing
 * the budget creation screen.
 * It allows users to define budget categories and limits,
 * and navigate back to the main menu.
 */
public class BudgetUI {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Reference to the main menu UI for navigation.
     */
    private MainMenu mainMenu;

    /**
     * Controller responsible for handling budget operations.
     */
    private BudgetController controller = new BudgetController();

    /**
     * Constructs the BudgetUI with required dependencies.
     *
     * @param stage the JavaFX stage
     * @param user the current logged-in user
     * @param mainMenu reference to the main menu screen
     */
    public BudgetUI(Stage stage, User user, MainMenu mainMenu){
        this.stage = stage;
        this.user = user;
        this.mainMenu = mainMenu;
    }

    /**
     * Displays the budget creation screen.
     * Allows the user to enter a category and budget limit,
     * validate input, and save the budget.
     */
    public void show(){

        TextField category = new TextField();
        category.setPromptText("Category");

        TextField limit = new TextField();
        limit.setPromptText("Limit");

        Button save = new Button("Save");

        Button back = new Button("Back to Menu");

        Label msg = new Label();

        save.setOnAction(e -> {
            try {
                double l = Double.parseDouble(limit.getText());

                if(l > user.getSalary()){
                    msg.setText("Budget > Salary ❌");
                    return;
                }

                controller.create(user.getId(), category.getText(), l);
                msg.setText("Budget Created ✅");
            } catch (Exception ex) {
                msg.setText("Invalid Input!");
            }
        });

        back.setOnAction(e -> {
            mainMenu.show();
        });

        VBox root = new VBox(10, category, limit, save, back, msg);
        stage.setScene(new Scene(root, 350, 300));
    }
}