package fhtw.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the Blackjack application.
 * It launches the JavaFX application, sets up the primary stage, and loads the GUI from the FXML file.
 *
 * The main method calls launch to start the JavaFX application.
 *
 */
public class Main2 extends Application {

    /**
     * Starts the JavaFX application by loading the FXML layout file and displaying the main stage.
     * The window is initialized with a title and dimensions (1080x720).
     *
     * @param stage The primary stage for this application, on which the GUI will be displayed.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main2.class.getResource("BlackJack.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("BlackJack");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main method serves as the entry point for the application.
     * It calls the launch method to start the JavaFX application.
     */
    public static void main(String[] args) {
        launch();
    }
}