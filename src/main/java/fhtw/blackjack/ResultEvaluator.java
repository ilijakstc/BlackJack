package fhtw.blackjack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultEvaluator {

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
                results.put(player.getId(), "Delaer won");
            } else {
                results.put(player.getId(), "Push");
            }
        }

        return results;
    }

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
}
