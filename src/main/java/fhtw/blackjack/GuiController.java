package fhtw.blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.swing.*;

public class GuiController {

    @FXML
    private TextArea dealerTextArea;

    @FXML
    private TextArea playerTextArea;

    @FXML
    private TextArea dealerCardSumTextArea;

    @FXML
    private TextArea playerCardSumTextArea;

    private GameSession gameSession;

    @FXML
    public void initialize(){
        System.out.println("Habe gestartet");
        gameSession = new GameSession();

        gameSession.startGame();
        updateDealerCards();
        updatePlayerCards();
    }

    public void updateDealerCards(){
        Dealer dealer = gameSession.getDealer();
        this.dealerTextArea.setText(dealer.handCardstoString());
        this.dealerCardSumTextArea.setText(dealer.cardSumtoString());
    }

    public void updatePlayerCards(){
        HumanPlayer player = gameSession.getPlayer();
        this.playerTextArea.setText(player.handCardstoString());
        this.playerCardSumTextArea.setText(player.cardSumtoString());
    }
}
