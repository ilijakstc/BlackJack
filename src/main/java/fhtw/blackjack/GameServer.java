package fhtw.blackjack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Manages the Blackjack game session and player connections.
 */
public class GameServer {
    private ServerSocket serverSocket;
    private GameSession gameSession = new GameSession();
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    private final int MAX_PLAYERS = 7;
    private Timer timer;
    private boolean timerStarted = false;
    private boolean gameStarted = false;

    /**
     * Starts the server and listens for client connections.
     *
     * @param port the port to listen on
     */
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (!gameStarted && gameSession.getHumanPlayers().size() < MAX_PLAYERS) {
                    handleNewConnection(clientSocket);
                } else {
                    clientSocket.close();
                    System.out.println("Connection rejected: Game already started or maximum players reached.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    private void handleNewConnection(Socket clientSocket) {
        try {
            String playerId = "Player" + (gameSession.getHumanPlayers().size() + 1);
            HumanPlayer newPlayer = new HumanPlayer(playerId, new ArrayList<>(), gameSession);
            gameSession.addPlayer(newPlayer);

            ClientHandler handler = new ClientHandler(clientSocket, gameSession, newPlayer, this);
            clientHandlers.add(handler);
            new Thread(handler).start();

            System.out.println(playerId + " connected.");

            if (!timerStarted) {
                startGameTimer();
            }

            if (gameSession.getHumanPlayers().size() == MAX_PLAYERS) {
                startGameSession();
            }
        } catch (IOException e) {
            System.err.println("Error handling new connection: " + e.getMessage());
        }
    }

    private void startGameTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameStarted) {
                    System.out.println("Timer expired, starting the game session...");
                    startGameSession();
                }
            }
        }, 60000);
        timerStarted = true;
        System.out.println("Game timer started. The session will begin in 1 minute or when max players connect.");
    }

    private void startGameSession() {
        if (gameStarted) {
            return;
        }
        gameStarted = true;

        if (timer != null) {
            timer.cancel();
        }

        System.out.println("Distributing cards and initializing the game...");
        gameSession.startGame();
        broadcastGameState();
        System.out.println("Game session started with " + gameSession.getAllPlayers().size() + " players.");
    }

    public synchronized void broadcastGameState() {
        for (ClientHandler handler : clientHandlers) {
            String personalizedState = generatePersonalizedGameState(handler.getPlayer());
            handler.sendUpdate(personalizedState);
        }

        // Logge den Turn-Wechsel
        HumanPlayer currentTurnPlayer = gameSession.getCurrentTurnPlayer();
        if (currentTurnPlayer != null) {
            System.out.println("Turn for: " + currentTurnPlayer.getId());
        } else {
            System.out.println("All players are done. Waiting for the dealer...");
        }
    }

    private String generatePersonalizedGameState(HumanPlayer player) {
        StringBuilder state = new StringBuilder();

        state.append("Dealer: ");
        if (gameSession.getDealer().getHandCards().size() > 1) {
//            state.append(gameSession.getDealer().getHandCards().get(0));
//            state.append(", [HIDDEN]");
//
//            int visibleSum = gameSession.getDealer().getHandCards().get(0).getValue();
//            state.append(" (").append(visibleSum).append(" points)\n");

            if (gameSession.areAllPlayersDone()) {
                // Zeige alle Karten des Dealers an, wenn der Dealer dran ist
                state.append(gameSession.getDealer().handCardstoString());
                state.append(" (").append(gameSession.getDealer().calculateCardSum()).append(" points)\n");
            } else {
                // Zeige nur die erste Karte des Dealers an
                state.append(gameSession.getDealer().getHandCards().get(0));
                state.append(", [HIDDEN]");
                int visibleSum = gameSession.getDealer().getHandCards().get(0).getValue();
                state.append(" (").append(visibleSum).append(" points)\n");
            }
        } else {
            state.append(gameSession.getDealer().handCardstoString());
            state.append(" (").append(gameSession.getDealer().calculateCardSum()).append(" points)\n");
        }
        //state.append(" (").append(gameSession.getDealer().calculateCardSum()).append(" points)\n");

        for (HumanPlayer p : gameSession.getHumanPlayers()) {
            if (p.equals(player)) {
                state.append("Your Cards: ").append(p.handCardstoString());
                state.append(" (").append(p.calculateCardSum()).append(" points)\n");
            } else {
                state.append(p.getId()).append(": [Hidden Cards] (X points)\n");
            }
        }

        HumanPlayer currentTurnPlayer = gameSession.getCurrentTurnPlayer();
        if (currentTurnPlayer != null) {
            state.append("TURN: ").append(currentTurnPlayer.getId());
        }

        return state.toString();
    }

    public void checkAndPlayDealerTurn() {
        if (gameSession.areAllPlayersDone()) {
            System.out.println("All players are done. Dealer is now playing.");
            gameSession.getDealer().playTurn();
            broadcastGameState();
            System.out.println("Game over. Final state broadcasted.");
            handleGameOver();
        }
    }

    private void handleGameOver() {
        ResultEvaluator result = new ResultEvaluator();
        Map<String, String> results = result.evaluateWinner(gameSession, gameSession.getDealer(), gameSession.getHumanPlayers());
        String resultText = result.formatResults(results);
        for (ClientHandler handler : clientHandlers) {
            handler.sendUpdate(resultText);
            handler.sendUpdate("Game Over! Thanks for playing.");
        }
        System.out.println("Game Over! The server remains active for new games.");
        clientHandlers.clear();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start(12345);
    }
}
