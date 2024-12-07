package fhtw.blackjack;
/**
 * The {@code Card} class represents a playing card with a specific value and suit.
 * A card consists of a card value ({@link CardValue}) and a card suit ({@link CardSuit}).
 * This class is used in a Blackjack game to represent the playing cards.
 *
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
     * @param suit The suit of the card, e.g., Hearts, Spades, Diamonds, Clubs.
     */
    public Card(CardValue value, CardSuit suit){
        this.value = value;
        this.suit = suit;
    }

    /**
     * Returns the value of this card.
     *
     * @return The value of the card, e.g., Two, Three, Ace, etc.
     */
    public CardValue getValue() {
        return this.value;
    }

    /**
     * Returns the suit of this card.
     *
     * @return The suit of the card, e.g., Hearts, Spades, Diamonds, Clubs.
     */
    public CardSuit getSuit() {
        return this.suit;
    }

    /**
     * Returns a string representation of the card.
     * The format of the representation is: "Suit - Value", e.g., "Hearts - Ace".
     *
     * @return A string representation of the card that combines the suit and value.
     */
    @Override
    public String toString() {
        return this.suit + " - " + this.value;
    }
}
