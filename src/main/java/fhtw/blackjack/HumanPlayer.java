package fhtw.blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 * The {@code HumanPlayer} class represents a human player in the Blackjack game.
 * This class extends the {@link Player} class and adds player-specific actions
 * such as "stand" to indicate that the player does not want any more cards.
 *
 */
public class HumanPlayer extends Player{
    /**
     * A flag indicating whether the player has chosen to "stand" (i.e., stop drawing more cards).
     * When {@code true}, the player will no longer receive additional cards.
     */
    public boolean hasStood;
    private GameSession gameSession;

    /**
     * Constructs a new human player with a specified player ID and an initial hand of cards.
     *
     * @param id The unique identifier for the player (e.g., the player's name or "Player1").
     * @param handCards The list of initial hand cards assigned to the player (typically empty at the start of the game).
     */
    public HumanPlayer(String id, List<Card> handCards, GameSession gameSession) {
        super(id, handCards);
        this.gameSession = gameSession;
    }

    /**
     * Allows the player to draw a card from the deck and adds it to their hand.
     *
     * This method retrieves the current deck from the {@link GameSession}
     * and draws the top card if the deck is not empty.
     * The drawn card is added to the player's hand, updating their list of hand cards.
     *
     * If the deck is empty, no card is drawn, and no changes are made to the player's hand.
     *
     * @see GameSession#getDeck()
     * @see GameSession#getPlayer()
     */
    public void hit(){
        Deck deck = gameSession.getDeck();
        HumanPlayer player = gameSession.getPlayer();
        if(!deck.getDeck().isEmpty()){
            Card card = deck.getDeck().remove(0);
            player.getHandCards().add(card);
        }
    }

    /**
     * Sets the player's status to "stand", indicating that they do not wish to receive any more cards.
     * This action is a core decision a player makes in Blackjack.
     */
    public void stand(){
        this.hasStood = true;
    }
}
