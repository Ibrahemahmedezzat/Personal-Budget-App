package app.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import app.controllers.UserController;
import app.models.User;

/**
 * User Interface class responsible for handling user authentication.
 * It provides login and signup functionality and navigates to the
 * main menu upon successful login.
 */
public class LoginUI {

    /**
     * The primary stage where the UI is displayed.
     */
    private Stage stage;

    /**
     * Controller responsible for handling user operations.
     */
    private UserController controller = new UserController();

    /**
     * Constructs the LoginUI with the given stage.
     *
     * @param stage the JavaFX stage used to display the UI
     */
    public LoginUI(Stage stage){
        this.stage = stage;
    }

    /**
     * Displays the login screen.
     * Allows users to log in or create a new account.
     * On successful login, navigates to the MainMenu screen.
     */
    public void show(){

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button login = new Button("Login");
        Button signup = new Button("Signup");

        Label msg = new Label();

        login.setOnAction(e -> {
            User u = controller.login(username.getText(), password.getText());

            if(u != null){
                msg.setText("Login Success");
                new MainMenu(stage, u).show();
            } else {
                msg.setText("Wrong credentials");
            }
        });

        signup.setOnAction(e -> {
            controller.signup(username.getText(), password.getText());
            msg.setText("Signup Done");
        });

        VBox root = new VBox(10, username, password, login, signup, msg);
        stage.setScene(new Scene(root, 350, 250));
        stage.show();
    }
}