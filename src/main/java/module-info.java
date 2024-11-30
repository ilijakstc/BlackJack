module fhtw.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens fhtw.blackjack to javafx.fxml;
    exports fhtw.blackjack;
}