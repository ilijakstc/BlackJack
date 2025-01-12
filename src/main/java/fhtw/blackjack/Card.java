package fhtw.blackjack;

/**
 * The {@code Card} class represents a playing card with a specific value and suit.
 * A card consists of a card value ({@link CardValue}) and a card suit ({@link CardSuit}).
 */
/**
 * Class description.
 */
public class Card {
    /**
     * The value of the card, e.g., Two, Three, Ace, etc.
     */
    private final CardValue value;

    /**
     * The suit of the card, e.g., Hearts, Spades, Diamonds, Clubs.
     */
    private final CardSuit suit;

    /**
     * Constructs a new card with a specified value and suit.
     *
     * @param value The value of the card, e.g., Two, Three, Ace, etc.
     * @param suit  The suit of the card, e.g., Hearts, Spades, Diamonds, Clubs.
     */
    /**
     * Method description.
     *
     * @param args Description of parameters.
     * @return Description of return value.
     */
    public Card(CardValue value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Returns the value of this card.
     *
     * @return The numerical value of the card, e.g., 2 for Two, 10 for King, etc.
     */
    /**
     * Method description.
     *
     * @param args Description of parameters.
     * @return Description of return value.
     */
    public int getValue() {
        switch (value) {
            case ACE:
                return 11; // Default to 11 for simplicity
            case KING:
            case QUEEN:
            case JACK:
                return 10; // Face cards are worth 10
            default:
                return value.ordinal() + 2; // Numeric cards (Two = 2, Three = 3, etc.)
        }
    }

    /**
     * Returns the card's value as a string.
     *
     * @return The card's value (e.g., "Two", "King", "Ace").
     */
    /**
     * Method description.
     *
     * @param args Description of parameters.
     * @return Description of return value.
     */
    public String getValueString() {
        return value.name();
    }

    /**
     * Returns the card's suit as a string.
     *
     * @return The card's suit (e.g., "Hearts", "Spades").
     */
    /**
     * Method description.
     *
     * @param args Description of parameters.
     * @return Description of return value.
     */
    public String getSuitString() {
        return suit.name();
    }

    /**
     * Converts this card to a string representation.
     *
     * @return A string representation of the card (e.g., "Ace of Hearts").
     */
    @Override
/**
 * Method description.
 *
 * @param args Description of parameters.
 * @return Description of return value.
 */
    public String toString() {
        return value.name() + " of " + suit.name();
    }
}
