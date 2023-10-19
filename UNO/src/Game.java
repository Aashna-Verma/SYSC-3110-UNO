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
     * Checks if a player's play is a valid move
     * 
     * @param play the card that is being played on the deck
     */
    public boolean isValidMove(Card play) {
        return false;
    }
    /**
     * Plays a game of UNO Flip
     */
    public void playGame() {
        // Main game loop
        while(true) { 
            int numPlayers = 0;
            do {
                System.out.println("Enter the number of players (2-4)");
                try {
                    numPlayers = scanner.nextInt();
                }
                catch (Exception e) {
                    numPlayers = -1;
                }
            } while (numPlayers < 2 || numPlayers > 4);
            
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Enter a name for Player " + String.valueOf(i));
                String name = scanner.nextLine();    
                players.add(new Player(name));
            }

            // Start the game
            int turnsTaken = 0;
            boolean gameOver = false;
            // Take a card off the deck to start
            Card topCard = currentDeck.getTopCard(); 
            do {

                for (Player player: players) {
                    if (turnsTaken == 0) {
                        System.out.println("Starting Card: " + topCard);
                    } 
                    else {
                        System.out.println("Played: " + topCard);    
                    }
                    printRoundInfo(topCard, player);

                    // Have the player choose a card to play

                    // Validate their move, add it back if it's not valid
                    
                    // Change the top card to their move

                    // Run an action based on the card that was played
                    
                    turnsTaken++;
                }
            } while (!gameOver);


        }
    }
    /**
     * Print out information for the current Player
     * @param played the card that was just played
     * @param currentPlayer the player who's turn it is
     */
    private void printRoundInfo (Card played, Player currentPlayer) {
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Current Side: Light");
        System.out.println("Your cards:");
        System.out.println(currentPlayer);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }

}
