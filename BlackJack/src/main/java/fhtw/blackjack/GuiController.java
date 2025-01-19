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
     * The TextField used to input the bet amount by the player.
     * This field allows the player to enter the amount they wish to bet for the current round.
     */
    @FXML
    private TextField betField;

    /**
     * The Button that allows the player to submit their bet.
     * When clicked, it sends the player's bet to the server to be processed.
     */
    @FXML
    private Button setBetButton;

    /**
     * The TextArea used to display the current pot in the game.
     * It shows the total amount of money in the pot, which is accumulated by the players' bets.
     */
    @FXML
    private TextArea potTextArea;  // Pot TextArea aus dem FXML

    /**
     * The current total amount of money in the pot for the ongoing round.
     * This value is updated as players place bets and the round progresses.
     */
    private double currentPot = 0.0;  // Pot-Variable

    /**
     * A boolean flag indicating whether the game has started.
     * This value is set to true when the game officially starts, and false before the game begins.
     */
    private boolean gameStarted = false;  // Variable für den Spielstatus


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
            loadBetFromFile(playerId);
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

                    // Überprüfen, ob das Spiel gestartet wurde
                    if (update.contains("GAME_STARTED: true")) {
                        gameStarted = true;  // Setzen von gameStarted auf true
                        Platform.runLater(() -> {
                            setBetButton.setDisable(true);  // Setzt den Bet-Button auf disabled, wenn das Spiel gestartet ist
                        });

                        sendActionToServer("SET_BET " + 5);
                        logMessage("Your bet has been set to: " + 5);


                        // Pot aktualisieren
                        currentPot += 5;
                        updatePotDisplay(5);  // Pot-Anzeige aktualisieren

                        saveBetToFile(playerId, 5); // Speichere den Einsatz in die Datei
                    }

                    // Wenn das Spiel gestartet ist und es der Spieler dran ist
                    if (gameStarted && update.contains("TURN: " + playerId)) {
                        Platform.runLater(() -> {
                            hitButton.setDisable(false);
                            standButton.setDisable(false);
                            logMessage("It's your turn!");
                        });
                    }

                    if (update.contains("Current Pot:")) {
                        String[] parts = update.split(":");
                        if (parts.length > 1) {
                            double newPot = Double.parseDouble(parts[1].trim().replace("€", ""));
                            updatePotDisplay(newPot);  // Pot-Anzeige im GUI aktualisieren
                        }
                    } else if (update.contains("Game Over")) {
                        logMessage(update);
                        Platform.runLater(() -> {
                            hitButton.setDisable(true);
                            standButton.setDisable(true);
                            setBetButton.setDisable(true);
                        });
                        break;
                    } else {
                        updateGameState(update);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                logMessage("Connection to server lost.");
                Platform.runLater(() -> {
                    hitButton.setDisable(true);
                    standButton.setDisable(true);
                    setBetButton.setDisable(true);
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

        if (gameState.contains("TURN: " + playerId) ) {
            Platform.runLater(() -> {
                hitButton.setDisable(false);
                standButton.setDisable(false);
                logMessage("It's your turn!");

            });


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

    /**
     * This method is called when the player presses the "Set Bet" button.
     * It reads the bet amount from the betField, validates it, and sends the bet to the server.
     * If the bet is valid, it also updates the pot and saves the bet to a file.
     * If the bet is invalid (non-positive), an error message is displayed.
     */
    @FXML
    public void setBetPressed() {
        try {
            double bet = Double.parseDouble(betField.getText());  // Get the bet amount from the betField
            if (bet > 0) {
                sendActionToServer("SET_BET " + bet);  // Send the bet to the server
                logMessage("Your bet has been set to: " + bet);

                // Update the pot with the player's bet
                currentPot += bet;
                updatePotDisplay(bet);  // Update the pot display on the UI

                saveBetToFile(playerId, bet); // Save the bet to the file
            } else {
                logMessage("Bet must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            logMessage("Invalid bet amount. Please enter a valid number.");
        }
    }

    /**
     * Saves the player's bet to a file.
     * The bet is saved with the player's ID, and if the player already has a recorded bet, it is updated.
     * If the player does not have a recorded bet, a new entry is added.
     *
     * @param playerId The ID of the player whose bet is being saved.
     * @param bet The amount of the player's bet to save.
     */
    private void saveBetToFile(String playerId, double bet) {
        File file = new File("bet.txt");
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean betUpdated = false;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(playerId)) {
                    // Update the bet for the existing player
                    updatedContent.append(playerId).append(":").append(bet).append("\n");
                    betUpdated = true;
                } else {
                    // Keep the unchanged lines
                    updatedContent.append(line).append("\n");
                }
            }

            // If the player does not have a bet recorded, add a new entry
            if (!betUpdated) {
                updatedContent.append(playerId).append(":").append(bet).append("\n");
            }
        } catch (IOException e) {
            logMessage("Error reading file for updating bet: " + e.getMessage());
            return;
        }

        // Write the updated content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            logMessage("Error saving updated bets to file: " + e.getMessage());
        }
    }

    /**
     * Loads the player's previous bet from a file.
     * If a previous bet exists for the player, it populates the betField with that value.
     *
     * @param playerId The ID of the player whose previous bet is being loaded.
     */
    private void loadBetFromFile(String playerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("bet.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(playerId)) {
                    double bet = Double.parseDouble(parts[1]);
                    Platform.runLater(() -> betField.setText(String.valueOf(bet)));
                    logMessage("Loaded your previous bet: " + bet);
                    break;
                }
            }
        } catch (IOException e) {
            logMessage("Error loading bets from file: " + e.getMessage());
        }
    }

    /**
     * Updates the displayed pot value on the user interface.
     * The pot is updated whenever a new bet is placed or the game progresses.
     *
     * @param newPot The current value of the pot to display.
     */
    public void updatePotDisplay(double newPot) {
        Platform.runLater(() -> {
            potTextArea.setText("Pot: " + newPot + " €");  // Pot-Anzeige im GUI aktualisieren
        });
    }

}
