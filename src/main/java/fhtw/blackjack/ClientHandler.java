package fhtw.blackjack;

import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and a single client.
 */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final GameSession gameSession;
    private final HumanPlayer player;
    private final GameServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket clientSocket, GameSession gameSession, HumanPlayer player, GameServer server) throws IOException {
        this.clientSocket = clientSocket;
        this.gameSession = gameSession;
        this.player = player;
        this.server = server;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
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

    private void processAction(String action) {
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

    private void handleDisconnection() {
        gameSession.getHumanPlayers().remove(player);
        server.broadcastGameState();
    }

    public void sendUpdate(String message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            System.err.println("Error sending update to " + player.getId() + ": " + e.getMessage());
        }
    }

    public HumanPlayer getPlayer() {
        return player;
    }
}
