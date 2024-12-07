package fhtw.blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.swing.*;

/**
 * The {@code GuiController} class serves as the controller for the JavaFX user interface of the Blackjack game.
 * It handles interactions between the GUI and the game logic, such as displaying player and dealer cards and updating their scores.
 * This class uses the {@link GameSession} to manage the flow of the game and updates the displayed information in the GUI.
 *
 */
public class GuiController {

    /**
     * The text area where the dealer's cards are displayed.
     */
    @FXML
    private TextArea dealerTextArea;

    /**
     * The text area where the player's cards are displayed.
     */
    @FXML
    private TextArea playerTextArea;

    /**
     * The text area where the dealer's total card value is displayed.
     */
    @FXML
    private TextArea dealerCardSumTextArea;

    /**
     * The text area where the player's total card value is displayed.
     */
    @FXML
    private TextArea playerCardSumTextArea;

    /**
     * The current game session, which manages the dealer, player, and deck.
     */
    private GameSession gameSession;

    /**
     * Initializes the GUI controller and starts a new game session.
     * This method is automatically called by JavaFX when the GUI is loaded.
     * It creates a new {@link GameSession}, starts the game, and updates the GUI with the initial dealer and player cards.
     */
    @FXML
    public void initialize(){
        System.out.println("Habe gestartet");
        gameSession = new GameSession();

        gameSession.startGame();
        updateDealerCards();
        updatePlayerCards();
    }

    /**
     * Updates the dealer's card information displayed in the GUI.
     * It sets the dealer's current hand and card total in the appropriate text areas.
     */
    public void updateDealerCards(){
        Dealer dealer = gameSession.getDealer();
        this.dealerTextArea.setText(dealer.handCardstoString());
        this.dealerCardSumTextArea.setText(dealer.cardSumtoString());
    }

    /**
     * Updates the player's card information displayed in the GUI.
     * It sets the player's current hand and card total in the appropriate text areas.
     */
    public void updatePlayerCards(){
        HumanPlayer player = gameSession.getPlayer();
        this.playerTextArea.setText(player.handCardstoString());
        this.playerCardSumTextArea.setText(player.cardSumtoString());
    }
}
