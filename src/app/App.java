package app;

import javafx.application.Application;
import javafx.stage.Stage;
import app.ui.LoginUI;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        new LoginUI(stage).show();
    }
}