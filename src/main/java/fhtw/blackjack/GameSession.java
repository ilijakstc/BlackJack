package fhtw.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the game session, including players, the dealer, and the deck.
 */
public class GameSession {
    private Dealer dealer;
    private List<HumanPlayer> players = new ArrayList<>();
    private Deck deck;

    /**
     * Initializes the game session with a new deck and dealer.
     */
    public GameSession() {
        this.deck = new Deck();
        this.dealer = new Dealer(new ArrayList<>(), deck);
    }

    /**
     * Adds a player to the session if the maximum limit is not reached.
     *
     * @param player the player to add
     */
    public void addPlayer(HumanPlayer player) {
        if (players.size() < 7) {
            players.add(player);
        } else {
            System.out.println("Cannot add more players. Maximum player limit reached.");
        }
    }

    /**
     * Starts the game by shuffling the deck and distributing cards.
     */
    public void startGame() {
        deck.shuffle();
        System.out.println("Deck shuffled. Starting the game...");
        dealer.handOutCards(getAllPlayers());
    }

    /**
     * Retrieves all players, including the dealer.
     *
     * @return a list of all players
     */
    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(dealer);
        allPlayers.addAll(players);
        return allPlayers;
    }

    /**
     * Retrieves the player whose turn it currently is.
     *
     * @return the current turn player or null if no players are eligible
     */
    public HumanPlayer getCurrentTurnPlayer() {
        for (HumanPlayer player : players) {
            if (!player.hasStood() && !player.isBusted()) {
                return player; // Nächster gültiger Spieler
            }
        }
        return null; // Alle Spieler sind fertig
    }

    /**
     * Checks if all players are done (either stood or busted).
     *
     * @return true if all players are done, otherwise false
     */
    public boolean areAllPlayersDone() {
        for (HumanPlayer player : players) {
            if (!player.hasStood() && !player.isBusted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the dealer for the session.
     *
     * @return the dealer
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Retrieves the list of human players.
     *
     * @return the list of players
     */
    public List<HumanPlayer> getHumanPlayers() {
        return players;
    }

    /**
     * Retrieves the deck used in this game session.
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }
}
