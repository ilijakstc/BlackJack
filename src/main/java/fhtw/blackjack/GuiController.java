package fhtw.blackjack;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

/**
 * Connects the GUI to the Blackjack server and processes player actions.
 */
public class GuiController {
    @FXML
    private TextArea dealerTextArea;
    @FXML
    private TextArea dealerCardSumTextArea;
    @FXML
    private TextArea playerTextArea;
    @FXML
    private TextArea playerCardSumTextArea;
    @FXML
    private TextArea logTextArea;
    @FXML
    private Button hitButton;
    @FXML
    private Button standButton;
    @FXML
    private Button startGameButton;
    @FXML
    private TextField playerIdField;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String playerId;

    @FXML
    public void initialize() {
        logMessage("Welcome to Blackjack! Click 'Start Game' to connect.");
    }

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

    @FXML
    public void hitButtonPressed() {
        sendActionToServer("HIT");
    }

    @FXML
    public void standButtonPressed() {
        sendActionToServer("STAND");
    }

    private void sendActionToServer(String action) {
        try {
            out.writeObject(action);
        } catch (IOException e) {
            logMessage("Error: Unable to send action to server.");
        }
    }

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

    private void updatePlayerIdField() {
            playerIdField.setText(playerId);
    }

    private void logMessage(String message) {
        Platform.runLater(() -> logTextArea.appendText(message + "\n"));
    }
}
