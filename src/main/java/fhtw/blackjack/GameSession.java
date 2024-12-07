package fhtw.blackjack;

import java.util.ArrayList;

/**
 * The {@code GameSession} class represents a single session of a Blackjack game.
 * It manages the dealer, player, and deck of cards, as well as the logic for starting the game.
 *
 * This class initializes the game environment, assigns a dealer and player, and starts the game
 * by distributing the initial cards to the participants.
 *
 */
public class GameSession {
    /**
     * The dealer responsible for managing the deck and handing out cards to players.
     */
    private Dealer dealer;
    /**
     * The human player participating in the Blackjack game.
     */
    private HumanPlayer player;
    /**
     * The deck of cards used in the current game session.
     */
    private Deck deck;

    /**
     * Constructs a new game session.
     * This initializes a new deck of cards, a dealer, and a player.
     * The dealer is assigned to manage the deck and hands out cards to the player and itself.
     */
    public GameSession() {
        this.deck = new Deck();
        this.dealer = new Dealer(new ArrayList<>(), deck);
        this.player = new HumanPlayer("Player1", new ArrayList<>(), this);

        dealer.addPlayer(player);
        dealer.addPlayer(dealer);
    }

    /**
     * Returns the dealer for the current game session.
     *
     * @return The {@link Dealer} responsible for managing the deck and dealing cards.
     */
    public Dealer getDealer() {
        return this.dealer;
    }

    /**
     * Returns the player for the current game session.
     *
     * @return The {@link HumanPlayer} representing the player in the Blackjack game.
     */
    public HumanPlayer getPlayer() {
        return this.player;
    }

    /**
     * Returns the deck of cards used in the current game session.
     *
     * The deck contains all the remaining cards that have not yet been dealt
     * to the players or the dealer. It is used to draw new cards during the game.
     *
     * @return The {@link Deck} object representing the current game's deck of cards.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Starts the Blackjack game.
     * The dealer distributes two cards to each participant in the game.
     */
    public void startGame(){
        this.dealer.handOutCards();
    }
}
