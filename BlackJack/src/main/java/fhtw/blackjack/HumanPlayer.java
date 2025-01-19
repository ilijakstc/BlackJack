package fhtw.blackjack;

import java.util.List;

/**
 * The {@code HumanPlayer} class represents a human player in the Blackjack game.
 * It extends the {@link Player} class and manages player-specific actions.
 */
public class HumanPlayer extends Player {
    /**
     * Indicates whether the player has chosen to stand.
     */
    private boolean hasStood = false;
    /**
     * The amount the player has bet for the current round.
     * This is used to determine the player's wager for the round.
     */
    private double bet;
    /**
     * The player's current balance or total money available for betting.
     * This value is adjusted based on wins, losses, and the player's actions during the game.
     */
    private double balance;

    private GameSession gameSession;


    /**
     * Constructs a new HumanPlayer.
     *
     * @param id         the unique player ID
     * @param handCards  the initial hand of cards
     * @param gameSession the game session the player belongs to
     * @param initialBalance  the initial hand of cards
     */
    public HumanPlayer(String id, List<Card> handCards, GameSession gameSession, double initialBalance) {
        super(id, handCards);
        this.gameSession = gameSession;
        this.bet=0.0;
        this.balance = initialBalance;
    }

    /**
     * Executes the "HIT" action to draw a card.
     */
    public void hit() {
        if (!hasStood && !isBusted()) {
            Card drawnCard = gameSession.getDeck().drawCard();
            if (drawnCard != null) {
                getHandCards().add(drawnCard);
                System.out.println(getId() + " drew: " + drawnCard);

                if (calculateCardSum() > 21) {
                    System.out.println(getId() + " is busted!");
                    hasStood = true; // Spieler ist automatisch "gestanden", da er gebustet ist
                }
            } else {
                System.out.println("Deck is empty! Cannot draw more cards.");
            }
        }
    }

    /**
     * Executes the "STAND" action to end the player's turn.
     */
    public void stand() {
        hasStood = true;
        System.out.println(getId() + " stands.");
    }

    /**
     * Checks if the player has chosen to stand.
     *
     * @return {@code true} if the player has stood, {@code false} otherwise
     */
    public boolean hasStood() {
        return hasStood;
    }

    /**
     * Sets the player's bet for the current round.
     * Ensures that the bet amount is positive.
     * If the bet is invalid (negative or zero), an exception is thrown.
     *
     * @param bet The amount the player wants to bet.
     * @throws IllegalArgumentException if the bet is not positive.
     */
    public void setBet(double bet) {
        if (bet > 0) {
            this.bet = bet;
        } else {
            throw new IllegalArgumentException("Bet amount must be positive.");
        }
    }

    /**
     * Returns the player's current balance (total money available).
     *
     * @return The current balance of the player.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Subtracts a specified amount from the player's balance.
     * Throws an exception if the player does not have enough funds to complete the transaction.
     *
     * @param amount The amount to subtract from the balance.
     * @throws IllegalArgumentException if the player does not have enough balance.
     */
    public void subtractBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Nicht genügend Guthaben.");
        }
    }
    /**
     * Adds a specified amount to the player's balance.
     *
     * @param amount The amount to add to the balance.
     */
    public void addBalance(double amount) {
        balance += amount;
    }
    /**
     * Checks if the player has enough balance to cover a specified amount.
     *
     * @param amount The amount to check against the player's balance.
     * @return true if the player has enough funds, false otherwise.
     */
    public boolean hasSufficientFunds(double amount) {
        return balance >= amount;
    }

}
