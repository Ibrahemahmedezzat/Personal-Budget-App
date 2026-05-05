package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;

public class ReportUI {

    private Stage stage;
    private User user;

    public ReportUI(Stage stage, User user){
        this.stage = stage;
        this.user = user;
    }

    public void show(){

        Label title = new Label("📊 Reports");
        Label data = new Label("Total Spent / Budget Usage / Categories");

        VBox root = new VBox(10, title, data);

        stage.setScene(new Scene(root, 350, 250));
    }
}