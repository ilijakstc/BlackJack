# BlackJack - Game

## English Version

### Introduction
The *BlackJack - Game* is a card game implemented in Java. It adheres to the core rules of Blackjack, allowing players to connect via a server, play the game through a GUI, and compete for the best card hand without exceeding 21. This project demonstrates an object-oriented architecture and includes advanced features like exception handling, multithreading, and custom user interfaces.

### Features
- Multiplayer support via a `GameServer`, allowing 1–7 players.
- Dynamic gameplay with real-time card distribution by the `Dealer`.
- Robust GUI implementation for card updates and results display.
- Custom exception handling (`TooManyPlayersException`) to ensure game constraints.
- Object-oriented design with reusable classes for `Card`, `Deck`, `Player`, and `GameSession`.

### Project Structure

#### 1. Core Components
- **GameServer**:  
  Manages client connections and game processes.

- **GameSession**:  
  Handles the overall game logic and player management.

- **Dealer**:  
  Responsible for card distribution and enforcing game rules.

- **Deck**:  
  Represents the deck of cards and shuffling logic.

- **Player (Abstract Class)**:  
  The base class for all players, implemented specifically as `HumanPlayer`.

- **Card**:  
  Defines a playing card with a value and suit.

- **HumanPlayer**
  Represents a human-controlled player in the game.

- **CardSuit**
  Represents the suit of a card (Hearts, Spades, Diamonds, or Clubs).

- **CardValue**
  Represents the value of a card (2, 3, 4, ..., King, Ace) and its corresponding Blackjack value.

- **GuiController**
  Controls the graphical user interface (GUI) of the game using JavaFX.

#### 2. GUI
- **GUIController**:  
  Manages graphical interface updates for displaying game state and results.

#### 3. Exception Handling
- **TooManyPlayersException**:  
  Ensures the maximum player limit is not exceeded.

---

## Deutsche Version

### Einführung
Das *BlackJack - Spiel* ist ein Multiplayer-Kartenspiel, das in Java implementiert wurde. Es basiert auf den grundlegenden Blackjack-Regeln und ermöglicht es, dass Spieler über einen Server verbunden sind, um das Spiel in einer GUI zu steuern und Kartenhände gegeneinander zu vergleichen, ohne 21 zu überschreiten. Das Projekt zeigt ein objektorientiertes Design und beinhaltet fortgeschrittene Features wie Exception-Handling, Multithreading und benutzerdefinierte Oberflächen.

### Funktionen
- Unterstützung für Multiplayer über einen `GameServer` (1–7 Spieler).
- Dynamisches Gameplay mit Kartenverteilung in Echtzeit durch den `Dealer`.
- GUI zur Anzeige von Karten, Spielzustand und Spieleraktionen.
- Benutzerdefinierte Ausnahmebehandlung (`TooManyPlayersException`) für Spielregeln.
- Wiederverwendbare Klassen für `Card`, `Deck`, `Player` und `GameSession`.

### Projektstruktur

#### 1. Kernkomponenten
- **GameServer**:  
  Verwaltet Verbindungen und Spielereignisse.

- **GameSession**:  
  Verantwortlich für Spielstatus und Spielerlogik.

- **Dealer**:  
  Zuständig für Kartenverteilung und Regelüberwachung.

- **Deck**:  
  Repräsentiert das Kartendeck mit Mischlogik.

- **Player (Abstrakte Klasse)**:  
  Basisklasse für alle Spieler, implementiert als `HumanPlayer`.

- **Card**:  
  Definiert eine Spielkarte mit Wert und Farbe.

#### 2. GUI
- **GUIController**:  
  Steuert grafische Oberflächenaktualisierungen.

#### 3. Exception Handling
- **TooManyPlayersException**:  
  Verhindert, dass die Spieleranzahl überschritten wird.

---
