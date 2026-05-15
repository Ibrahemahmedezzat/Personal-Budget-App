package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.GoalController;

/**
 * User Interface class responsible for creating and managing
 * the goal creation screen.
 * It allows users to define financial or personal goals
 * and save them through the GoalController.
 */
public class GoalUI {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Controller responsible for handling goal operations.
     */
    private GoalController controller = new GoalController();

    /**
     * Constructs the GoalUI with required dependencies.
     *
     * @param stage the JavaFX stage
     * @param user the currently logged-in user
     */
    public GoalUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

    /**
     * Displays the goal creation screen.
     * Allows the user to enter a goal name and target amount,
     * validates input, and saves the goal if valid.
     */
    public void show(){

        TextField name = new TextField();
        name.setPromptText("Goal Name");

        TextField target = new TextField();
        target.setPromptText("Target");

        Button save = new Button("Save");

        Label msg = new Label();

        save.setOnAction(e -> {

            double t = Double.parseDouble(target.getText());

            if(t > user.getSalary()){
                msg.setText("Goal > Salary ❌");
                return;
            }

            controller.create(user.getId(), name.getText(), t);
            msg.setText("Goal Created");
        });

        VBox root = new VBox(10, name, target, save, msg);
        stage.setScene(new Scene(root, 350, 250));
    }
}