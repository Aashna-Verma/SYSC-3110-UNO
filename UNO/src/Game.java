import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game allows users to play a game of UNO Flip
 * 
 * @author Angus Jull
 * @version 1.0
 */
public class Game {
    private ArrayList<Player> players;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private static Scanner scanner = new Scanner(System.in); // input for game

    public Game() {
        players = new ArrayList<>();
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
    }

    /**
     * Plays a game of UNO Flip
     */
    public void playGame() {
        // Main game loop
        while (true) {
            int numPlayers = 0;
            do {
                System.out.println("Enter the number of players (2-4)");
                try {
                    numPlayers = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    numPlayers = -1;
                }
            } while (numPlayers < 2 || numPlayers > 4);

            // Get player names and create the players
            for (int i = 0; i < numPlayers; i++) {
                System.out.print("Enter a name for Player " + String.valueOf(i) + ": ");
                String name = scanner.nextLine();
                // Create the player and give them their starting conditions
                Player newPlayer = new Player(name);
                players.add(newPlayer);
                newPlayer.drawHand(currentDeck);
            }

            // Start the game
            int turnsTaken = 0;
            boolean gameOver = false;
            // Take a card off the deck to start
            Card topCard = currentDeck.getTopCard();
            do {
                for (Player player : players) {
                    if (turnsTaken == 0) {
                        System.out.println("Starting Card: " + topCard);
                    } else {
                        System.out.println("Played: " + topCard);
                    }
                    printRoundInfo(topCard, player);
                    
                    // Make a choice of what to play
                    Card choice = null;
                    do {
                        choice = player.playCard(topCard, currentDeck);
                        if (choice.validWith(topCard)) {
                            topCard = choice;
                        } else {
                            System.out.println("Invalid move, try again");
                            player.addCard(choice);
                            choice = null;
                        }
                    } while (choice == null);
                    topCard = choice;

                    // Run an action based on the card that was played

                    turnsTaken++;
                }
            } while (!gameOver);
        }
    }

    /**
     * Print out information for the current Player
     * 
     * @param played        the card that was just played
     * @param currentPlayer the player who's turn it is
     */
    private void printRoundInfo(Card played, Player currentPlayer) {
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Current Side: Light");
        System.out.println("Your cards:");
        System.out.println(currentPlayer.handToString());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }

}
