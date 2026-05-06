package app;

import javafx.application.Application;
import javafx.stage.Stage;
import app.ui.LoginUI;

/**
 * Main application class that starts the JavaFX program.
 */
public class Main extends Application {

    /**
     * Entry point of JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the login screen.
     */
    @Override
    public void start(Stage stage) {

        LoginUI loginUI = new LoginUI(stage);
        loginUI.show();
    }
}