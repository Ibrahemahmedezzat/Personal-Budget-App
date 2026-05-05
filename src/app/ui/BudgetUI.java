package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.models.User;
import app.controllers.BudgetController;

public class BudgetUI {

    private Stage stage;
    private User user;
    private MainMenu mainMenu; 
    private BudgetController controller = new BudgetController();

    public BudgetUI(Stage stage, User user, MainMenu mainMenu){
        this.stage = stage;
        this.user = user;
        this.mainMenu = mainMenu;
    }

    public void show(){

        TextField category = new TextField();
        category.setPromptText("Category");

        TextField limit = new TextField();
        limit.setPromptText("Limit");

        Button save = new Button("Save");
        
        // 3. إنشاء زرار الـ Back
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