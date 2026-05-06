package app;

import javafx.application.Application;
import javafx.stage.Stage;
import app.ui.LoginUI;

/**
 * Main entry point of the application.
 * This class launches the JavaFX application and initializes
 * the first screen (Login UI).
 */
public class App extends Application {

    /**
     * Starts the JavaFX application and sets up the initial stage.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        new LoginUI(stage).show();
    }
}