package fhtw.blackjack;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Deck} class represents a standard deck of playing cards.
 * The deck contains all possible combinations of {@link CardValue} and {@link CardSuit}.
 * This class is used in a Blackjack game to manage and provide access to the deck of cards.
 *
 */
public class Deck {
    /**
     * The list of cards that make up the deck.
     * It contains one card for every combination of {@link CardValue} and {@link CardSuit}.
     */
    private final List<Card> deck;

    /**
     * Constructs a new deck of cards.
     * The deck is initialized with 52 cards (13 values for each of the 4 suits).
     */
    public Deck(){
        this.deck = new ArrayList<>();
        for(CardValue value : CardValue.values()){
            for(CardSuit suit : CardSuit.values()){
                this.deck.add(new Card(value, suit));
            }
        }
    }

    /**
     * Returns the list of cards in the deck.
     *
     * @return A list of {@link Card} objects representing the current state of the deck.
     */
    public List<Card> getDeck(){
        return this.deck;
    }
}
