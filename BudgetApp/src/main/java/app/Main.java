package app;

import javafx.application.Application;
import javafx.stage.Stage;
import app.ui.LoginScreen;

/**
 * Main application class that starts the JavaFX program.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("💰 Budget Manager");
        stage.setMinWidth(900);
        stage.setMinHeight(620);
        new LoginScreen(stage).show();
    }
}
