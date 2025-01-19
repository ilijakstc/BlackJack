package fhtw.blackjack;

import java.util.List;

/**
 * The {@code Player} class serves as a base class for all players in the game,
 * including both human players and the dealer.
 */
public abstract class Player {
    /**
     * The unique identifier for the player.
     */
    private String id;

    /**
     * Indicates whether the player is "busted" (has a card total exceeding 21).
     */
    private boolean isBusted = false;

    /**
     * The player's current hand of cards.
     */
    private List<Card> handCards;

    /**
     * Constructs a new Player with a unique ID and an initial hand of cards.
     *
     * @param id        the unique identifier for the player
     * @param handCards the player's initial hand of cards
     */
    public Player(String id, List<Card> handCards) {
        this.id = id;
        this.handCards = handCards;
    }

    /**
     * Retrieves the player's unique ID.
     *
     * @return the player's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the player's current hand of cards.
     *
     * @return the player's hand of cards
     */
    public List<Card> getHandCards() {
        return handCards;
    }

    /**
     * Converts the player's hand of cards to a string.
     *
     * @return the player's hand of cards as a string
     */
    public String handCardstoString() {
        StringBuilder hand = new StringBuilder();
        for (Card card : handCards) {
            hand.append(card.toString()).append(", ");
        }
        return hand.length() > 0 ? hand.substring(0, hand.length() - 2) : "No cards";
    }

    /**
     * Calculates the total value of the player's cards.
     *
     * @return the total value of the cards
     */
    public int calculateCardSum() {
        int sum = 0;
        for (Card card : handCards) {
            sum += card.getValue(); // Properly adds the card's value to the sum
        }
        return sum;
    }

    /**
     * Checks if the player is "busted" (i.e., their card total exceeds 21).
     * Updates the {@code isBusted} field based on the card total.
     *
     * @return {@code true} if the player is busted, {@code false} otherwise
     */
    public boolean isBusted() {
        if (calculateCardSum() > 21) {
            isBusted = true;
        } else {
            isBusted = false;
        }
        return isBusted;
    }
}
