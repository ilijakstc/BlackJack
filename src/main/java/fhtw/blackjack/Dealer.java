package fhtw.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dealer extends Player{
    int threshold = 17;
    Deck deck;
    List<Player> players;

    public Dealer(List<Card> handCards, Deck deck) {
        super("Dealer", handCards);
        this.deck = deck;
        this.players = new ArrayList<>();
    }

    public void handOutCards(){

        Collections.shuffle(this.deck.getDeck());
        for(int i = 0; i < 2; i++){
            for(Player player : this.players) {
                if(!this.deck.getDeck().isEmpty()){
                    Card card = this.deck.getDeck().remove(0);
                    player.getHandCards().add(card);
                }
            }
        }

    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
