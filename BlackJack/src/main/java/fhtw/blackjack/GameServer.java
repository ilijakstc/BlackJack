package fhtw.blackjack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * The {@code GameServer} class manages the Blackjack game session and handles player connections.
 * It is responsible for starting the server, accepting new players, managing the game state,
 * and broadcasting updates to clients.
 */
public class GameServer {
    /**
     * The server socket that listens for client connections.
     */
    private ServerSocket serverSocket;
    /**
     * The current game session, managing the players and the dealer.
     */
    private GameSession gameSession = new GameSession();
    /**
     * A list of {@code ClientHandler} objects for managing communication with connected clients.
     */
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    /**
     * The maximum number of players allowed in a game session.
     */
    private final int MAX_PLAYERS = 7;
    /**
     * A timer for starting the game session after a set duration.
     */
    private Timer timer;
    /**
     * A flag indicating whether the game start timer has been initiated.
     */
    private boolean timerStarted = false;
    /**
     * A flag indicating whether the game session has started.
     */
    private boolean gameStarted = false;

    /**
     * Represents the current total amount of money in the pot for the ongoing game round.
     * The pot is updated as players place their bets and accumulates the total amount of all players' bets.
     * This value is used to display the total pot to the players and may be used to determine the winner at the end of the round.
     */
    private double currentPot = 0.0;  // Pot-Variable


    /**
     * Starts the server and listens for client connections.
     * If the game has not started and the maximum player limit is not reached,
     * new connections are accepted and handled.
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

    /**
     * Handles a new client connection by creating a new player and starting a {@code ClientHandler}.
     *
     * @param clientSocket the socket for the new client
     */
    private void handleNewConnection(Socket clientSocket) {
        try {
            String playerId = "Player" + (gameSession.getHumanPlayers().size() + 1);
            double initialBalance = 100.00;
            HumanPlayer newPlayer = new HumanPlayer(playerId, new ArrayList<>(), gameSession, initialBalance);
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

    /**
     * Starts a timer to begin the game session after a delay if the maximum player count is not reached.
     */
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

    /**
     * Starts the game session by distributing cards and initializing the game.
     */
    private void startGameSession() {
        if (gameStarted) {
            return;
        }
        gameStarted = true;

        for (HumanPlayer player : gameSession.getHumanPlayers()) {
            if (!player.hasSufficientFunds(5)) {
                System.out.println(player.getId() + " hat nicht genug Guthaben, um zu spielen.");
                for (ClientHandler handler : clientHandlers) {
                    handler.sendUpdate(player.getId() + " hat nicht genug Guthaben für die Runde.");
                }
                return; // Stoppt die Runde, wenn ein Spieler nicht genug Guthaben hat
            }
            player.subtractBalance(5); // Abziehen von 5 Euro vom Guthaben
        }

        if (timer != null) {
            timer.cancel();
        }

        System.out.println("Distributing cards and initializing the game...");
        gameSession.startGame();
        broadcastGameState();
        System.out.println("Game session started with " + gameSession.getAllPlayers().size() + " players.");
    }

    /**
     * Sends the current game state to all connected clients.
     */
    public synchronized void broadcastGameState() {
        if (!gameStarted) {
            System.out.println("Game hasn't started yet.");
            return; // Verhindern, dass der Status vor dem Start gesendet wird
        }
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

    /**
     * Generates a personalized game state for the given player.
     *
     * @param player the player for whom the state is generated
     * @return a string representation of the game state for the player
     */
    private String generatePersonalizedGameState(HumanPlayer player) {
        StringBuilder state = new StringBuilder();

        state.append("Dealer: ");
        if (gameSession.getDealer().getHandCards().size() > 1) {

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

    /**
     * Checks if all players are done and plays the dealer's turn if necessary.
     */
    public void checkAndPlayDealerTurn() {
        if (gameSession.areAllPlayersDone()) {
            System.out.println("All players are done. Dealer is now playing.");
            gameSession.getDealer().playTurn();
            broadcastGameState();
            System.out.println("Game over. Final state broadcasted.");
            handleGameOver();
        }
    }

    /**
     * Handles the end of the game by evaluating results and notifying players.
     */
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

    /**
     * The main entry point of the server application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start(12345);
    }

    /**
     * Increases the current pot by the specified bet amount.
     * This method is called whenever a player places a bet to update the total amount in the pot.
     *
     * @param bet The amount to increase the pot by, usually the player's current bet.
     */
    public void updatePot(double bet) {
        currentPot += bet;  // Pot erhöhen
    }

    /**
     * Sends the current total pot amount to all connected clients.
     * This method broadcasts the updated pot value to all players so they can see the current total.
     */
    public void broadcastPot() {
        for (ClientHandler handler : clientHandlers) {
            handler.sendUpdate("Current Pot: " + currentPot + " €");  // Sende den Pot an alle Clients
        }
    }

}
