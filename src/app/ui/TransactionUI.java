package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.TransactionController;

/**
 * User Interface class responsible for handling transaction input.
 * It allows users to add financial transactions with amount and category,
 * and confirms the action before saving.
 */
public class TransactionUI {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * The currently logged-in user.
     */
    private User user;

    /**
     * Controller responsible for handling transaction operations.
     */
    private TransactionController controller = new TransactionController();

    /**
     * Constructs the TransactionUI with required dependencies.
     *
     * @param stage the JavaFX stage used for displaying the UI
     * @param user the currently logged-in user
     */
    public TransactionUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

    /**
     * Displays the transaction input screen.
     * Allows the user to enter an amount and category, confirms the action,
     * and submits the transaction through the controller.
     */
    public void show(){

        TextField amount = new TextField();
        amount.setPromptText("Amount");

        TextField category = new TextField();
        category.setPromptText("Category");

        Button add = new Button("Add Transaction");

        Label msg = new Label();

        add.setOnAction(e -> {

            double a = Double.parseDouble(amount.getText());
            String c = category.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Add " + a + " to " + c + "?");

            alert.showAndWait().ifPresent(res -> {
                controller.add(user.getId(), a, c);
                msg.setText("Transaction Added");
            });
        });

        VBox root = new VBox(10, amount, category, add, msg);
        stage.setScene(new Scene(root, 350, 250));
    }
}