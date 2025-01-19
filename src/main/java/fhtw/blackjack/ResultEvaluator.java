package fhtw.blackjack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code ResultEvaluator} class evaluates the results of the game,
 * determines winners, and saves the results to a file.
 */
public class ResultEvaluator {
    /**
     * The file where game results are saved.
     */
    private static final String RESULT_FILE = "game_results.txt";

    /**
     * Evaluates the winner of the game based on the final card sums of the players and the dealer.
     *
     * @param gameSession the game session containing the players and game state
     * @param dealer      the dealer of the game
     * @param players     the list of human players
     * @return a map of player IDs to their respective results (e.g., "won", "lost", "push")
     * @throws IllegalStateException if the game session is not finished
     */
    public Map<String, String> evaluateWinner(GameSession gameSession, Dealer dealer, List<HumanPlayer> players){

        Map<String, String> results = new LinkedHashMap<>();

        if (!gameSession.areAllPlayersDone()) {
            throw new IllegalStateException("Game session is not finished yet.");
        }

        for (HumanPlayer player : players) {
            if(player.isBusted()){
                results.put(player.getId(), "Delaer won");
            } else if(dealer.isBusted()){
                results.put(player.getId(), player.getId() + " won");
            } else if (player.calculateCardSum() > dealer.calculateCardSum()) {
                results.put(player.getId(), player.getId() + " won");
            } else if (player.calculateCardSum() < dealer.calculateCardSum()) {
                results.put(player.getId(), "Dealer won");
            } else {
                results.put(player.getId(), "Push");
            }
        }
        saveResultsToFile(results, players.size());
        return results;
    }

    /**
     * Formats the results map into a readable string format.
     *
     * @param results the map of player IDs to their results
     * @return a string representation of the results
     */
    public String formatResults(Map<String, String> results) {
        StringBuilder resultString = new StringBuilder();
        for (Map.Entry<String, String> entry : results.entrySet()) {
            resultString.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return resultString.toString();
    }

    /**
     * Saves the game results to a file with a timestamp and player count.
     *
     * @param results     the map of results
     * @param playerCount the number of players in the game
     */
    public void saveResultsToFile(Map<String, String> results, int playerCount) {
        // Format the date and time
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Build the output string
        StringBuilder output = new StringBuilder();
        output.append("Date and Time: ").append(timestamp).append("\n");
        output.append("Player Count: ").append(playerCount).append("\n");
        output.append("Results:\n");
        output.append(formatResults(results)).append("\n");

        // Save to file
        try (FileWriter writer = new FileWriter(new File(RESULT_FILE))) {
            writer.write(output.toString());
            System.out.println("Results successfully saved to: " + Paths.get(RESULT_FILE).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing results to file: " + e.getMessage());
        }
    }
}



