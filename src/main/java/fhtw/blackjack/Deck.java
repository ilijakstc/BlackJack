package fhtw.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Deck} class represents a standard deck of cards used in the game.
 * It supports card shuffling and drawing operations.
 */
public class Deck {
    private List<Card> deck;

    /**
     * Constructs a new shuffled deck of cards.
     */
    public Deck() {
        this.deck = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.add(new Card(value, suit));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the deck to randomize the order of cards.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Draws a card from the deck.
     *
     * @return the top card, or {@code null} if the deck is empty
     */
    public Card drawCard() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        }
        System.out.println("Deck is empty!");
        return null;
    }

    /**
     * Retrieves the current list of cards in the deck.
     *
     * @return the list of cards
     */
    public List<Card> getDeck() {
        return deck;
    }
}
