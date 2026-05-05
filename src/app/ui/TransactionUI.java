package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.TransactionController;

public class TransactionUI {

    private Stage stage;
    private User user;
    private TransactionController controller = new TransactionController();

    public TransactionUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

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