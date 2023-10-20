import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game allows users to play a game of UNO Flip
 * 
 * @author Angus Jull
 * @version 1.0
 */
public class Game {
    private enum DIRECTIONS { FORWARD, BACKWARD };
    private DIRECTIONS direction;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Card topCard;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private static Scanner scanner = new Scanner(System.in); // input for game

    public Game() {
        // Populate players list later
        players = new ArrayList<>();
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
        topCard = currentDeck.getTopCard();
        direction = DIRECTIONS.FORWARD;
    }

    /**
     * Plays a game of UNO Flip
     */
    public void playGame() {
        configurePlayers();
        // Main game loop
        while (true) {
            // Start the game
            int turnsTaken = 0;
            boolean gameOver = false;

            do {
                if (turnsTaken == 0) {
                    System.out.println("Starting Card: " + topCard);
                } else {
                    System.out.println("Played: " + topCard);
                }
                // Ask player to make a choice of what to play, then validate that choice
                Card choice = null;
                do {
                    printRoundInfo();
                    choice = currentPlayer.playCard(topCard, currentDeck);
                    if (choice == null) {
                        continue;
                    }
                    else if (choice.validWith(topCard)) {
                        topCard = choice;
                    } else {
                        System.out.println("Card doesn't match the top card, try again");
                        currentPlayer.addCard(choice);
                        choice = null;
                    }
                } while (choice == null);
                // The current card is no longer needed
                pile.addCard(topCard);
                // The top card is now the played card
                topCard = choice;
                // Run an action based on the card that was played, and advance the player
                processChoice(topCard);
                // Check if this player has won
                if (currentPlayer.getNumCards() == 0) {
                    System.out.println("Game ended: " + currentPlayer.getName() + " has won");
                    gameOver = true;
                }
                // Move onto the next player
                currentPlayer = nextPlayer(currentPlayer);
                // Keep track of how many turns have passed
                turnsTaken++;
            } while (!gameOver);
        }
    }
    /**
     * Returns the next player to play in the game
     * player The player who's turn it currently is
     * @return The next player who's turn it is, or the current player if nobody is next
     */
    private Player nextPlayer(Player player) {
        if (direction == DIRECTIONS.FORWARD) {
            int nextPlayerIndex = players.indexOf(player) + 1;
            // The next index going forward is 0 if the index is outsize of the ArrayList
            return players.get(nextPlayerIndex < players.size() ? nextPlayerIndex : 0);    
        }
        else if (direction == DIRECTIONS.BACKWARD) {
            int nextPlayerIndex = players.indexOf(player) - 1;
            return players.get(nextPlayerIndex >= 0 ? nextPlayerIndex : players.size()); 
        }
        return player;
    }
    /**
     * Uses the console to add new players to the game
     * 
     * @return True if players have been added, false otherwise
     */
    private boolean configurePlayers() {
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
        currentPlayer = players.get(0);
        return true;
    }
    /**
     * Decide what to do based on the card played. Can change the current player and the top card
     * 
     * @param choice The card that was played on the currentPlayer's turn
     */
    private void processChoice(Card choice) {
        switch (choice.getValue()) {
            case DRAW_ONE:
                System.out.println(nextPlayer(currentPlayer).getName() + " has to draw one card due to Draw One " + nextPlayer(currentPlayer).drawCard(currentDeck));
                break;
            case SKIP:
                // Set to the next player, which will then skip the player
                currentPlayer = nextPlayer(currentPlayer); 
                break;
            case REVERSE:
                if (direction == DIRECTIONS.FORWARD) direction = DIRECTIONS.BACKWARD;
                else if (direction == DIRECTIONS.BACKWARD) direction = DIRECTIONS.FORWARD;
                break;
            case WILD:
                handleWild();
                break;
            case WILD_DRAW_TWO:
                handleWild();
                System.out.println(nextPlayer(currentPlayer).getName() + " has to draw two cards due to Wild Draw Two " + nextPlayer(currentPlayer).drawCard(currentDeck) 
                                    + " " + nextPlayer(currentPlayer).drawCard(currentDeck));
                break;
            default:
                break;
        }
    }

    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     * 
     * @return The colour chosen for the wild card
     */
    private void handleWild() {
        if (topCard.getColour() == Colour.WILD) {
            String colours = "";
            for (Colour c: Colour.values()) {
                colours += c + " ";
            }
            Colour chosenColour = null;
            System.out.println("Choose a colour: " + colours);
            while (chosenColour == null) {
                String input = scanner.nextLine();
                for (Colour c: Colour.values()) {
                    if (c.toString().equals(input)) {
                        chosenColour = c;
                    }
                }
            }
            topCard = new Card(topCard.getValue(), chosenColour);
        }
    }

    /**
     * Print out information for the current Player
     */
    private void printRoundInfo() {
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Current Side: Light");
        System.out.println("Your cards:");
        System.out.println(currentPlayer.handToString());
        System.out.println("Top card: " + topCard);
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
