package app;

import javafx.application.Application;

/**
 * Entry point of the application.
 * This class is responsible for launching the JavaFX application
 * by calling the {@link App} class.
 */
public class Main {

    /**
     * Main method that starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Application.launch(App.class);
    }
}