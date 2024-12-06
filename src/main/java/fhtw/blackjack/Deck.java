package fhtw.blackjack;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck(){
        this.deck = new ArrayList<>();
        for(CardValue value : CardValue.values()){
            for(CardSuit suit : CardSuit.values()){
                this.deck.add(new Card(value, suit));
            }
        }
    }

    public List<Card> getDeck(){
        return this.deck;
    }
}
