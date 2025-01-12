package fhtw.blackjack;

/**
 * The {@code CardValue} enum represents the possible values of a playing card in a Blackjack game.
 * Each card has an associated numeric value that is used to calculate the player's score.
 * This enum is used in the {@link Card} class to define the value of a card.
 *
 */
public enum CardValue {
    /**
     * The card value Two, which has a numeric value of 2.
     */
    TWO(2),
    /**
     * The card value Three, which has a numeric value of 3.
     */
    THREE(3),
    /**
     * The card value Four, which has a numeric value of 4.
     */
    FOUR(4),
    /**
     * The card value Five, which has a numeric value of 5.
     */
    FIVE(5),
    /**
     * The card value Six, which has a numeric value of 6.
     */
    SIX(6),
    /**
     * The card value Seven, which has a numeric value of 7.
     */
    SEVEN(7),
    /**
     * The card value Eight, which has a numeric value of 8.
     */
    EIGHT(8),
    /**
     * The card value Nine, which has a numeric value of 9.
     */
    NINE(9),
    /**
     * The card value Ten, which has a numeric value of 10.
     */
    TEN(10),
    /**
     * The card value Jack, which has a numeric value of 10.
     */
    JACK(10),
    /**
     * The card value Queen, which has a numeric value of 10.
     */
    QUEEN(10),
    /**
     * The card value King, which has a numeric value of 10.
     */
    KING(10),
    /**
     * The card value Ace, which has a numeric value of 11.
     */
    ACE(11);

    /**
     * The numeric value associated with the card value.
     */
    final private int value;

    /**
     * Constructs a {@code CardValue} with the specified numeric value.
     *
     * @param value The numeric value of the card, e.g., 2, 3, 10, or 11.
     */
    CardValue(int value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of this card value.
     *
     * @return The numeric value of the card, e.g., 2, 3, 10, or 11.
     */
    /**
     * Method description.
     *
     * @param args Description of parameters.
     * @return Description of return value.
     */
    public int getValue() {
        return value;
    }
}
