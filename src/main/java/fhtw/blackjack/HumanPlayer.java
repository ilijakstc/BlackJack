package fhtw.blackjack;

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

    /**
     * Constructs a new human player with a specified player ID and an initial hand of cards.
     *
     * @param id The unique identifier for the player (e.g., the player's name or "Player1").
     * @param handCards The list of initial hand cards assigned to the player (typically empty at the start of the game).
     */
    public HumanPlayer(String id, List<Card> handCards) {
        super(id, handCards);
    }

    //public Card hit(){
        //return cards
    //}

    /**
     * Sets the player's status to "stand", indicating that they do not wish to receive any more cards.
     * This action is a core decision a player makes in Blackjack.
     */
    public void stand(){
        this.hasStood = true;
    }
}
