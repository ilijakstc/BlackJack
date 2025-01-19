
# Blackjack Multiplayer Game

## Overview

This project is a multiplayer Blackjack game implemented in Java. It features a server-side game logic with a GUI for players to interact with. Players can perform actions like "HIT" and "STAND," while the dealer follows Blackjack rules automatically. The server manages player connections, game state, and determines the winner at the end of each game session.

## Project Structure

- **`fhtw.blackjack`**: Main package containing the core classes of the project.
  - **`GameServer`**: Manages player connections, starts the game, and coordinates the game state.
  - **`GameSession`**: Represents a game session, including players, the dealer, and the deck.
  - **`Player`**: Abstract base class for all players (both human players and the dealer).
  - **`HumanPlayer`**: Represents a human player with actions like "HIT" and "STAND."
  - **`Dealer`**: Represents the dealer, who manages the deck and plays according to Blackjack rules.
  - **`Deck`**: Manages the deck of cards used in the game, including shuffling and card distribution.
  - **`Card`**: Represents an individual playing card with a suit and value.
  - **`GuiController`**: Handles the GUI logic and connects the user interface to the server.
  - **`ResultEvaluator`**: Evaluates game results, determines winners, and saves results to a file.

## Features

- Polymorphism realised in humanPlayer and dealer.
- Multiplayer-threading functionality with TCP server-client architecture.
- Turn-based game logic adhering to Blackjack rules.
- Automated dealer actions.
- Player actions including "HIT" and "STAND."
- JavaFX-based GUI interaction for players.
- Results are saved to a file after each game via FileIO.
- Access rights applied to corresponding methods e.g.
- Exception Handling applied in needed code-sections.

## How to Run

1. **Server**:
  - Navigate to the project directory.
  - Run the `GameServer` class.
  - The server listens for player connections on port `12345`.

2. **Client**:
  - Launch the GUI by running the `GuiController`.
  - Click "Start Game" to connect to the server.
  - Interact with the game using the provided buttons ("HIT" and "STAND").

## Prerequisites

- Java 8 or higher.
- JavaFX (for the GUI).

## Game Flow

1. Players connect to the server using the GUI.
2. The server waits for players to join, up to a maximum of 7.
3. Once all players are ready, the game begins:
  - Each player receives two cards.
  - Players take turns to "HIT" or "STAND."
  - The dealer plays automatically after all players have completed their turns.
4. The game results are evaluated and saved to a file.

## Result File

Game results are saved in `game_results.txt` with details such as:
- Date and time of the game.
- Number of players.
- Winner information.

## Known Limitations

- The server must be restarted for a new game session.
- Only one game session is supported at a time.

## Contributors

- **Ilija Kostic**
- **Cemre Bayram**
- **Safija Cehajic**
