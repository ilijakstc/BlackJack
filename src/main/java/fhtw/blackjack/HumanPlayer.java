package fhtw.blackjack;

import java.util.List;

/**
 * The {@code HumanPlayer} class represents a human player in the Blackjack game.
 * It extends the {@link Player} class and manages player-specific actions.
 */
public class HumanPlayer extends Player {
    /**
     * Indicates whether the player has chosen to stand.
     */
    private boolean hasStood = false;
    /**
     * The game session the player is part of, used to access the deck and game state.
     */
    private GameSession gameSession;

    /**
     * Constructs a new HumanPlayer.
     *
     * @param id         the unique player ID
     * @param handCards  the initial hand of cards
     * @param gameSession the game session the player belongs to
     */
    public HumanPlayer(String id, List<Card> handCards, GameSession gameSession) {
        super(id, handCards);
        this.gameSession = gameSession;
    }

    /**
     * Executes the "HIT" action to draw a card.
     */
    public void hit() {
        if (!hasStood && !isBusted()) {
            Card drawnCard = gameSession.getDeck().drawCard();
            if (drawnCard != null) {
                getHandCards().add(drawnCard);
                System.out.println(getId() + " drew: " + drawnCard);

                if (calculateCardSum() > 21) {
                    System.out.println(getId() + " is busted!");
                    hasStood = true; // Spieler ist automatisch "gestanden", da er gebustet ist
                }
            } else {
                System.out.println("Deck is empty! Cannot draw more cards.");
            }
        }
    }

    /**
     * Executes the "STAND" action to end the player's turn.
     */
    public void stand() {
        hasStood = true;
        System.out.println(getId() + " stands.");
    }

    /**
     * Checks if the player has chosen to stand.
     *
     * @return {@code true} if the player has stood, {@code false} otherwise
     */
    public boolean hasStood() {
        return hasStood;
    }
}
