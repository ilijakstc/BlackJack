package fhtw.blackjack;

public enum CardSuit {
    HEART("Heart"),
    SPADE("Spade"),
    DIAMOND("Diamond"),
    CLUB("Club");

    private final String suit;

    CardSuit(String suit){
        this.suit = suit;
    }

    public String getSuit(){
        return suit;
    }

}
