package fhtw.blackjack;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

/**
 * Connects the GUI to the game server and processes player actions.
 * This class manages user interaction and updates the game state on the GUI.
 */
public class GuiController {
    /**
     * Displays the dealer's cards on the GUI.
     */
    @FXML
    private TextArea dealerTextArea;
    /**
     * Displays the sum of the dealer's cards.
     */
    @FXML
    private TextArea dealerCardSumTextArea;
    /**
     * Displays the player's cards on the GUI.
     */
    @FXML
    private TextArea playerTextArea;
    /**
     * Displays the sum of the player's cards.
     */
    @FXML
    private TextArea playerCardSumTextArea;
    /**
     * Displays game logs and updates on the GUI.
     */
    @FXML
    private TextArea logTextArea;
    /**
     * The "Hit" button, allowing the player to request an additional card.
     */
    @FXML
    private Button hitButton;
    /**
     * The "Stand" button, allowing the player to end their turn.
     */
    @FXML
    private Button standButton;
    /**
     * The button to start the game and connect to the server.
     */
    @FXML
    private Button startGameButton;
    /**
     * Displays the player's ID assigned by the server.
     */
    @FXML
    private TextField playerIdField;

    /**
     * The socket used for communication with the server.
     */
    private Socket socket;
    /**
     * The output stream for sending data to the server.
     */
    private ObjectOutputStream out;
    /**
     * The input stream for receiving data from the server.
     */
    private ObjectInputStream in;

    /**
     * The player's ID assigned by the server.
     */
    private String playerId;

    /**
     * Initializes the GUI controller and sets the welcome message.
     */
    @FXML
    public void initialize() {
        logMessage("Welcome to Blackjack! Click 'Start Game' to connect.");
    }

    /**
     * Handles the "Start Game" button press.
     * Establishes a connection to the server and begins listening for updates.
     */
    @FXML
    public void startGamePressed() {
        try {
            connectToServer("localhost", 12345);
            listenForUpdates();
            Platform.runLater(() -> {
                hitButton.setDisable(true);
                standButton.setDisable(true);
                startGameButton.setDisable(true);
            });
            logMessage("Connected to server. Waiting for the game to start...");
        } catch (Exception e) {
            logMessage("Error: Unable to connect to server.");
        }
    }

    /**
     * Establishes a connection to the game server.
     *
     * @param host the server's hostname or IP address
     * @param port the port to connect to
     * @throws IOException if an I/O error occurs during the connection
     */
    private void connectToServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        try {
            playerId = (String) in.readObject();
            updatePlayerIdField();
            logMessage("You are " + playerId);
        } catch (ClassNotFoundException e) {
            logMessage("Error: Unable to receive player ID.");
        }
    }

    /**
     * Handles the "Hit" button press.
     * Sends a "HIT" action to the server, requesting an additional card.
     */
    @FXML
    public void hitButtonPressed() {
        sendActionToServer("HIT");
    }

    /**
     * Handles the "Stand" button press.
     * Sends a "STAND" action to the server, ending the player's turn.
     */
    @FXML
    public void standButtonPressed() {
        sendActionToServer("STAND");
    }

    /**
     * Sends a player action to the server.
     *
     * @param action the action to send (e.g., "HIT" or "STAND")
     */
    private void sendActionToServer(String action) {
        try {
            out.writeObject(action);
        } catch (IOException e) {
            logMessage("Error: Unable to send action to server.");
        }
    }

    /**
     * Starts a background thread to listen for updates from the server.
     */
    void listenForUpdates() {
        new Thread(() -> {
            try {
                while (true) {
                    String update = (String) in.readObject();
                    if (update.contains("Game Over")) {
                        logMessage(update);
                        Platform.runLater(() -> {
                            hitButton.setDisable(true);
                            standButton.setDisable(true);
                        });
                        break;
                    }
                    updateGameState(update);
                }
            } catch (IOException | ClassNotFoundException e) {
                logMessage("Connection to server lost.");
                Platform.runLater(() -> {
                    hitButton.setDisable(true);
                    standButton.setDisable(true);
                });
            }
        }).start();
    }

    /**
     * Updates the game state displayed on the GUI based on a server update.
     *
     * @param gameState the game state update received from the server
     */
    private void updateGameState(String gameState) {
        String[] parts = gameState.split("\n");

        for (String part : parts) {
            if (part.startsWith("Dealer:")) {
                String[] dealerDetails = part.split(" \\(");
                Platform.runLater(() -> dealerTextArea.setText(dealerDetails[0].replace("Dealer: ", "")));
                if (dealerDetails.length > 1) {
                    Platform.runLater(() -> dealerCardSumTextArea.setText(dealerDetails[1].replace(" points)", "")));
                }
            } else if (part.startsWith("Your Cards:")) {
                String[] playerDetails = part.split(" \\(");
                Platform.runLater(() -> playerTextArea.setText(playerDetails[0].replace("Your Cards: ", "")));
                if (playerDetails.length > 1) {
                    Platform.runLater(() -> playerCardSumTextArea.setText(playerDetails[1].replace(" points)", "")));
                }
            } else {
                logMessage(part);
            }
        }

        if (gameState.contains("TURN: " + playerId)) {
            Platform.runLater(() -> {
                hitButton.setDisable(false);
                standButton.setDisable(false);
            });
            logMessage("It's your turn!");
        } else {
            Platform.runLater(() -> {
                hitButton.setDisable(true);
                standButton.setDisable(true);
            });
            //logMessage("Waiting for other players...");
        }
    }

    /**
     * Updates the player ID field on the GUI with the assigned ID.
     */
    private void updatePlayerIdField() {
            playerIdField.setText(playerId);
    }

    /**
     * Logs a message to the log area on the GUI.
     *
     * @param message the message to log
     */
    private void logMessage(String message) {
        Platform.runLater(() -> logTextArea.appendText(message + "\n"));
    }
}
