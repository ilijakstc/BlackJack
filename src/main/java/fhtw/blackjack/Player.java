package fhtw.blackjack;

import java.util.List;

/**
 * The {@code Player} class is an abstract representation of a player in a Blackjack game.
 * It serves as a base class for different types of players, such as the {@link HumanPlayer} and {@link Dealer}.
 *
 * This class manages the player's ID, their hand of cards, and provides methods
 * for calculating the total card value of the player's hand.
 *
 */
public abstract class Player {
    /**
     * The unique identifier for the player, such as a player name or "Dealer" for the dealer.
     */
    String id;
    /**
     * The list of cards currently in the player's hand.
     */
    List<Card> handCards;

    /**
     * Constructs a player with a specified ID and an initial hand of cards.
     *
     * @param id The unique identifier for the player (e.g., player name or "Dealer").
     * @param handCards The initial list of cards in the player's hand (typically empty at the start of the game).
     */
    public Player(String id, List<Card> handCards){
        this.id = id;
        this.handCards = handCards;
    }

    /**
     * Calculates the total sum of the card values in the player's hand.
     * Each card in the player's hand is evaluated using its {@link CardValue}.
     *
     * @return The total sum of the card values in the player's hand.
     */
    public int calculateCardSum(){
        int sum = 0;
        for(Card handCard : handCards){
            sum = sum + handCard.getValue().getValue();
        }

        return sum;
    }

    /**
     * Returns the unique identifier for the player.
     *
     * @return The player's ID, such as their name or "Dealer" for the dealer.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the player.
     *
     * @param id The new identifier for the player.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the list of cards currently in the player's hand.
     *
     * @return A list of {@link Card} objects representing the player's hand.
     */
    public List<Card> getHandCards() {
        return this.handCards;
    }

    /**
     * Sets the list of cards currently in the player's hand.
     *
     * @param handCards The new list of cards to assign to the player's hand.
     */
    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }

    /**
     * Returns a string representation of the player's current hand.
     * The string contains all the cards currently in the player's hand.
     *
     * @return A string representation of the player's current hand.
     */
    public String handCardstoString(){
        return this.handCards.toString();
    }

    /**
     * Returns a string representation of the total value of the player's hand.
     * This value is calculated using the {@link #calculateCardSum()} method.
     *
     * @return The total value of the player's hand as a string.
     */
    public String cardSumtoString(){
        return String.valueOf(calculateCardSum());
    }
}
