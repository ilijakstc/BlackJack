package fhtw.blackjack;

/**
 * The {@code CardSuit} enum represents the four possible suits of a playing card.
 * Each suit has an associated name (e.g., "Heart", "Spade", "Diamond", "Club").
 * This enum is used in the {@link Card} class to define the suit of a card.
 *
 */
public enum CardSuit {
    /**
     * The Heart suit of a playing card.
     */
    HEART("Heart"),
    /**
     * The Spade suit of a playing card.
     */
    SPADE("Spade"),
    /**
     * The Diamond suit of a playing card.
     */
    DIAMOND("Diamond"),
    /**
     * The Club suit of a playing card.
     */
    CLUB("Club");

    /**
     * The name of the suit as a string representation.
     */
    private final String suit;

    /**
     * Constructs a {@code CardSuit} with the specified suit name.
     *
     * @param suit The name of the suit, e.g., "Heart", "Spade", "Diamond", or "Club".
     */
    CardSuit(String suit){
        this.suit = suit;
    }

    /**
     * Returns the name of the suit as a string.
     *
     * @return The name of the suit, e.g., "Heart", "Spade", "Diamond", or "Club".
     */
    public String getSuit(){
        return suit;
    }
}
