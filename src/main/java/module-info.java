module fhtw.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens fhtw.blackjack to javafx.fxml;
    exports fhtw.blackjack;
}