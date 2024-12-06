package fhtw.blackjack;

import java.util.List;

public abstract class Player {
    String id;
    List<Card> handCards;

    public Player(String id, List<Card> handCards){
        this.id = id;
        this.handCards = handCards;
    }

    public int calculateCardSum(){
        int sum = 0;
        for(Card handCard : handCards){
            sum = sum + handCard.getValue().getValue();
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
        return this.handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }

    public String handCardstoString(){
        return this.handCards.toString();
    }

    public String cardSumtoString(){
        return String.valueOf(calculateCardSum());
    }
}
