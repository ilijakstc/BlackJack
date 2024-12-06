package fhtw.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BlackJack.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("BlackJack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Deck deck = new Deck();

        HumanPlayer p1 = new HumanPlayer("Ilija", new ArrayList<>());
        HumanPlayer p2 = new HumanPlayer("Nicole", new ArrayList<>());
        Dealer dealer = new Dealer(new ArrayList<>(), deck);

        dealer.addPlayer(p1);
        dealer.addPlayer(p2);
        dealer.addPlayer(dealer);

        dealer.handOutCards();

        for(Player player : dealer.getPlayers()){
            System.out.println(player.getId() + "'s hand " + player.getHandCards() + " value " + player.calculateCardSum());
        }
    }
}