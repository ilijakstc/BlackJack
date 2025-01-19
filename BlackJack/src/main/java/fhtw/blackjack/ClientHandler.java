package fhtw.blackjack;

import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and a single client.
 */
public class ClientHandler implements Runnable {

    /**
     * The socket for communicating with the client.
     */
    private final Socket clientSocket;

    /**
     * The current game session in which the client is participating.
     */
    private final GameSession gameSession;

    /**
     * The human player associated with this client.
     */
    private final HumanPlayer player;

    /**
     * The game server managing the game logic and communication.
     */
    private final GameServer server;

    /**
     * The output stream for sending data to the client.
     */
    private ObjectOutputStream out;

    /**
     * The input stream for receiving data from the client.
     */
    private ObjectInputStream in;

    /**
     * Constructs a new ClientHandler with a clientSocket, gameSession, humanPlayer and gameServer.
     *
     * @param clientSocket the clients socket
     * @param gameSession the current gameSession
     * @param player the humanPlayer which represents the actual client
     * @param server the gameServer which handles the communication
     * @throws IOException IOException is thrown when there is a problem with the stream
     */
    public ClientHandler(Socket clientSocket, GameSession gameSession, HumanPlayer player, GameServer server) throws IOException {
        this.clientSocket = clientSocket;
        this.gameSession = gameSession;
        this.player = player;
        this.server = server;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    /**
     * Executes the run method and manages communication between the client and the server, processes player actions, and updates the game state accordingly.
     */
    public void run() {
        try {
            out.writeObject(player.getId());
            while (true) {
                String action = (String) in.readObject();
                if (action.equalsIgnoreCase("DISCONNECT")) {
                    handleDisconnection();
                    break;
                }
                processAction(action);
                server.broadcastGameState();
                if (gameSession.areAllPlayersDone()) {
                    server.checkAndPlayDealerTurn();
                } else {
                    HumanPlayer nextPlayer = gameSession.getCurrentTurnPlayer();
                    if (nextPlayer != null) {
                        System.out.println("Next turn for: " + nextPlayer.getId());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(player.getId() + " disconnected unexpectedly.");
            handleDisconnection();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    /**
     * Checks the humanPlayer actions and executes them.
     * @param action the action performed by the humanPlayer
     */
    private void processAction(String action) {
        if (action.startsWith("SET_BET")) {
            String[] parts = action.split(" ");
            if (parts.length == 2) {
                try {
                    double bet = Double.parseDouble(parts[1]);
                    player.setBet(bet);  // Setze den Wetteinsatz des Spielers

                    // Pot im Server aktualisieren
                    server.updatePot(bet);  // Pot wird um die Wette des Spielers erhöht

                    // Sende den aktuellen Pot an alle Clients (einschließlich GuiController)
                    server.broadcastPot();  // Pot an alle Clients senden

                    // Aktualisiere den Spielzustand
                    server.broadcastGameState();
                } catch (NumberFormatException e) {
                    sendUpdate("Invalid bet amount.");
                }
            } else {
                sendUpdate("Usage: SET_BET <amount>");
            }
            return;
        }
        if (!gameSession.getCurrentTurnPlayer().equals(player)) {
            sendUpdate("Not your turn!");
            return;
        }

        switch (action.toUpperCase()) {
            case "HIT":
                player.hit();
                if (player.isBusted()) {
                    System.out.println(player.getId() + " is busted!");
                    player.stand(); // Spieler wird automatisch auf "STAND" gesetzt
                }
                break;

            case "STAND":
                player.stand();
                break;

            default:
                sendUpdate("Unknown action: " + action);
                return;
        }

        // Nach jeder Aktion: Aktualisiere den Zustand
        server.broadcastGameState();

        if (gameSession.areAllPlayersDone()) {
            server.checkAndPlayDealerTurn();
        }
    }

    /**
     * Handles disconnection of client if exception in run()-method is thrown.
     */
    private void handleDisconnection() {
        gameSession.getHumanPlayers().remove(player);
        server.broadcastGameState();
    }

    /**
     * Sends game update to connected clients.
     * @param message update which is send to clients
     */
    public void sendUpdate(String message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            System.err.println("Error sending update to " + player.getId() + ": " + e.getMessage());
        }
    }

    /**
     * Retrieves the corresponding humanPlayer
     *
     * @return the corresponding humanPlayer
     */
    public HumanPlayer getPlayer() {
        return player;
    }
}
