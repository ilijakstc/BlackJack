package fhtw.blackjack;

import java.util.List;

/**
 * The {@code Dealer} class represents the dealer in the Blackjack game.
 * The dealer plays automatically after all players have acted.
 */
public class Dealer extends Player {
    private Deck deck;

    /**
     * Constructs a new Dealer with an empty hand and a specified deck.
     *
     * @param handCards the dealer's initial hand
     * @param deck      the deck of cards used in the game
     */
    public Dealer(List<Card> handCards, Deck deck) {
        super("Dealer", handCards);
        this.deck = deck;
    }

    /**
     * Distributes initial cards to all players.
     *
     * @param players the list of players to deal cards to
     */
    public void handOutCards(List<Player> players) {
        for (int i = 0; i < 2; i++) { // Two cards per player
            for (Player player : players) {
                if (!deck.getDeck().isEmpty()) {
                    Card card = deck.getDeck().remove(0);
                    player.getHandCards().add(card);
                    System.out.println(player.getId() + " received card: " + card); // Debug log
                }
            }
        }
        System.out.println("Dealer hand after distribution: " + handCardstoString());
    }

    /**
     * Executes the dealer's turn according to Blackjack rules.
     * The dealer must draw cards until reaching a total of 17 or higher.
     */
    public void playTurn() {
        while (calculateCardSum() < 17) {
            if (!deck.getDeck().isEmpty()) {
                Card card = deck.getDeck().remove(0);
                getHandCards().add(card);
                System.out.println("Dealer drew a card: " + card);
            } else {
                break; // Stop if the deck is empty
            }
        }
        System.out.println("Dealer's turn is over. Final hand: " + handCardstoString());
    }
}
