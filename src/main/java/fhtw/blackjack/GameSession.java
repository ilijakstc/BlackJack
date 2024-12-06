package fhtw.blackjack;

import java.util.ArrayList;

public class GameSession {

    private Dealer dealer;
    private HumanPlayer player;
    private Deck deck;

    public GameSession() {
        this.deck = new Deck();
        this.dealer = new Dealer(new ArrayList<>(), deck);
        this.player = new HumanPlayer("Player1", new ArrayList<>());

        dealer.addPlayer(player);
        dealer.addPlayer(dealer);
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public HumanPlayer getPlayer() {
        return this.player;
    }

    public void startGame(){
        this.dealer.handOutCards();
    }
}
