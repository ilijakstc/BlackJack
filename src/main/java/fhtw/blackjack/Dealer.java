package fhtw.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Dealer} class represents the dealer in a Blackjack game.
 * The dealer is responsible for managing the deck, handing out cards to players,
 * and overseeing the flow of the game.
 * This class extends the {@link Player} class and has additional responsibilities unique to the dealer role.
 *
 */
public class Dealer extends Player{
    /**
     * The minimum hand value at which the dealer will stop drawing cards.
     * In Blackjack, the dealer must draw cards until reaching or exceeding this value.
     */
    int threshold = 17;
    /**
     * The deck of cards that the dealer manages and uses to hand out cards to players.
     */
    Deck deck;
    /**
     * The list of players in the game who receive cards from the dealer.
     */
    List<Player> players;

    /**
     * Constructs a new dealer with a specified hand of cards and a deck.
     *
     * @param handCards The initial hand of the dealer (typically empty at the start of the game).
     * @param deck The deck of cards managed by the dealer.
     */
    public Dealer(List<Card> handCards, Deck deck) {
        super("Dealer", handCards);
        this.deck = deck;
        this.players = new ArrayList<>();
    }

    /**
     * Shuffles the deck and deals two cards to each player in the game.
     * The dealer draws cards from the top of the deck and gives one card to each player in each round.
     */
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

    /**
     * Adds a new player to the list of players managed by the dealer.
     *
     * @param player The player to be added to the game.
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Returns the list of players currently in the game.
     *
     * @return A list of {@link Player} objects representing the players in the game.
     */
    public List<Player> getPlayers() {
        return players;
    }
}
