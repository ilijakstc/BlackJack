package fhtw.blackjack;

import java.util.List;

public class HumanPlayer extends Player{
    public boolean hasStood;

    public HumanPlayer(String id, List<Card> handCards) {
        super(id, handCards);
    }

    //public Card hit(){
        //return cards
    //}

    public void stand(){
        this.hasStood = true;
    }
}
