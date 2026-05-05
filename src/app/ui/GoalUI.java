package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.GoalController;

public class GoalUI {

    private Stage stage;
    private User user;
    private GoalController controller = new GoalController();

    public GoalUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

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