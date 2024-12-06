package fhtw.blackjack;

public class Card {
    private final CardValue value;
    private final CardSuit suit;

    public Card(CardValue value, CardSuit suit){
        this.value = value;
        this.suit = suit;
    }

    public CardValue getValue() {
        return this.value;
    }

    public CardSuit getSuit() {
        return this.suit;
    }

    @Override
    public String toString() {
        return this.suit + " - " + this.value;
    }
}
