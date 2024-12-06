package fhtw.blackjack;

import java.util.List;

public abstract class Player {
    String id;
    List<Card> handCards;

    public int calculateCardSum(){
        int sum = 0;
        for(Card handCard : handCards){
            //calculate the sum of all cards that are in hand of the player
        }

        return sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }

}
