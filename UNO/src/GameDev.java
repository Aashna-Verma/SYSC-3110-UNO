import java.util.ArrayList;
import java.util.Scanner;

/**
 * GameDev is the all accessible version of the Game class
 * This is used for the developers so tests can be run when changes are made
 *
 * @author Angus Jull, Aashna Verma
 * @version 1.0
 */
public class GameDev {
    public enum Direction { FORWARD, BACKWARD };
    private final int WINNING_SCORE = 500;
    private Direction direction;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Card topCard;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private static Scanner scanner = new Scanner(System.in); // input for game

    public GameDev() {
        // Populate players list later
        players = new ArrayList<>();
        direction = Direction.FORWARD;
        currentDeck = new Deck();
        pile = new Deck();
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
            int rounds = 0;
            boolean gameOver = false;
            // Game loop
            do {
                // Set up the round
                boolean roundOver = false;
                currentDeck = new Deck();
                pile = new Deck();
                currentDeck.populateDeck();
                topCard = currentDeck.removeCard();
                drawHands();
                // Round loop
                System.out.println("");
                do {
                    if (turnsTaken == 0) {
                        System.out.println("Starting Card: " + topCard);
                    } else {
                        System.out.println("Played: " + topCard);
                    }
                    processChoice(playCard());

                    // Check if this player has won
                    if (currentPlayer.getNumCards() == 0) {
                        System.out.println("Round over: " + currentPlayer.getName() + " has won");
                        currentPlayer.setScore(currentPlayer.getScore() + getPoints());
                        roundOver = true;
                        turnsTaken = 0;
                        rounds++;

                        for (Player player: players){
                            System.out.println(player.getName() + "'s score:" + player.getScore());
                        }
                        System.out.println("\nEntering Round " + rounds);

                    }
                    // The deck is empty, so end the game
                    else if (currentDeck.getTopCard() == null) {
                        System.out.println("Round over, deck empty. No winner");
                        roundOver = true;
                    }
                    // Move onto the next player if nobody has won yet
                    else {
                        // Move onto the next player
                        currentPlayer = nextPlayer(currentPlayer);
                        // Keep track of how many turns have passed
                        turnsTaken++;
                    }
                } while (!roundOver);
                for (Player p: players) {
                    if (p.getScore() >= WINNING_SCORE) {
                        System.out.println(p.getName() + " has won! With a score of " + p.getScore());
                        gameOver = true;
                    }
                }
            } while (!gameOver);

        }
    }
    /**
     * Returns the next player to play in the game
     * player The player who's turn it currently is
     * @return The next player who's turn it is, or the current player if nobody is next
     */
    public Player nextPlayer(Player player) {
        if (direction == Direction.FORWARD) {
            int nextPlayerIndex = players.indexOf(player) + 1;
            // The next index going forward is 0 if the index is outsize of the ArrayList
            return players.get(nextPlayerIndex < players.size() ? nextPlayerIndex : 0);
        }
        else if (direction == Direction.BACKWARD) {
            int nextPlayerIndex = players.indexOf(player) - 1;
            return players.get(nextPlayerIndex >= 0 ? nextPlayerIndex : players.size() - 1);
        }
        return player;
    }
    /**
     * Uses the console to add new players to the game
     *
     * @return True if players have been added, false otherwise
     */
    public boolean configurePlayers() {
        int numPlayers = 0;
        do {
            System.out.print("Enter the number of players (2-4): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
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
        }
        currentPlayer = players.get(0);
        return true;
    }

    /**
     * Makes the players each draw a hand from the current deck
     */
    public void drawHands() {
        for (Player p: players) {
            p.drawHand(currentDeck);
        }
    }

    /**
     * Calculates the points the current player gets from winning the round
     * @return the total points of all players hand EXCEPT the winning players
     */
    public int getPoints() {
        int total = 0;
        // Confirm the player has won
        if (currentPlayer.getNumCards() == 0) {
            for (Player p: players) {
                if (p != currentPlayer) {
                    total += p.getHandPoints();
                }
            }
        }
        return total;
    }

    /**
     * Decide what to do based on the card played. Can change the current player and the top card
     *
     * @param choice The card that was played on the currentPlayer's turn
     */
    public void processChoice(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }

        switch (choice.getValue()) {
            case DRAW_ONE:
                System.out.println(nextPlayer(currentPlayer).getName() + " has to draw one card due to Draw One: " + nextPlayer(currentPlayer).drawCard(currentDeck.removeCard()));
                break;
            case SKIP:
                // Set to the next player, which will then skip the player
                currentPlayer = nextPlayer(currentPlayer);
                break;
            case REVERSE:
                if (direction == Direction.FORWARD) direction = Direction.BACKWARD;
                else if (direction == Direction.BACKWARD) direction = Direction.FORWARD;
                break;
            case WILD_DRAW_TWO:
                System.out.println(nextPlayer(currentPlayer).getName() + " has to draw two cards due to Wild Draw Two: " + nextPlayer(currentPlayer).drawCard(currentDeck.removeCard())
                        + " " + nextPlayer(currentPlayer).drawCard(currentDeck.removeCard()));
                choice = handleWild(choice);
                break;
            case WILD:
                choice = handleWild(choice);
                break;
            default:
                break;
        }
        // The current card is no longer needed
        pile.addCard(topCard);
        // The top card is now the played card
        topCard = choice;
    }

    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     *
     * @return A card with the chosen colour, or the top card if the top card is not a wild card.
     */
    public Card handleWild(Card wild) {
        if (wild.getColour() == Colour.WILD) {
            Colour chosenColour = null;
            while (chosenColour == null) {
                System.out.println("Choose a colour: BLUE GREEN RED YELLOW");
                String input = scanner.nextLine();
                for (Colour c: Colour.values()) {
                    if (c.toString().equals(input) && !c.equals("WILD")) {
                        chosenColour = c;
                    }
                }
            }
            // Create a new wild card with the same value, but with the chosen colour
            return new Card(wild.getValue(), chosenColour);
        }
        // This card isn't a wild, so just send it back
        return wild;
    }

    /**
     * Print out information for the current Player
     */
    public void printRoundInfo() {
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println("Current Side: Light");
        System.out.println("Your cards:");
        System.out.println(currentPlayer.handToString());
        System.out.println("Top card: " + topCard);
    }

    /**
     * Play a card from the current players hand, make a play depending on the input provided by a human player, continues asking for input
     * until an integer is provided
     *
     * @return A number representing the player's choice of what to do
     */
    public int getPlayerInput () {
        int choice = -1;
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            }
            catch (Exception e) {
                System.out.println("Enter an integer value");
                return -1;
            }
        } while (choice == -1);
        return choice;
    }

    /**
     * Gets the current player to play a card, and then returns the card they play
     * @return
     */
    public Card playCard() {
        // The card being played
        Card choice = null;
        boolean chosen = false;
        // Ask player to make a choice of what to play, then validate that choice
        do {
            printRoundInfo();
            // choice = currentPlayer.performAction(, currentDeck);
            System.out.println("Enter card index to play or 0 to draw a card:");
            int input = getPlayerInput();
            System.out.println("");
            if (input == 0) {
                // Draw a card
                Card drawn = currentDeck.removeCard();
                System.out.println("Drew a card: " + drawn);
                // Check if the card is valid
                if (topCard.validWith(drawn)) {
                    System.out.println("Enter 0 to keep the card, or any number above 0 to play it");
                    if (getPlayerInput() > 0) {
                        choice = drawn;
                    }
                    else {
                        currentPlayer.drawCard(drawn);
                    }
                }
                else {
                    currentPlayer.drawCard(drawn);
                }
                // Drawing a card is the last action a player performs
                chosen = true;
            }
            else {
                choice = currentPlayer.removeCard(input);
                if (choice != null) {
                    if (topCard.validWith(choice)) {
                        chosen = true;
                    }
                    else {
                        System.out.println("Choice not valid with top card, try again");
                        currentPlayer.addCard(choice);
                        choice = null;
                    }
                }
            }
        } while (!chosen);
        return choice;
    }

    public static void main(String[] args) {
        GameDev game = new GameDev();
        game.playGame();
    }

    public void setDirection(Direction d){this.direction = d;}
    public void setPlayers(ArrayList<Player> p_list){this.players = p_list;}
    public void setCurrentPlayer(Player p){this.currentPlayer = p;}
    public void setTopCard(Card c){this.topCard = c;}
    public void setCurrentDeck(Deck d){this.currentDeck = d;}
    public void setPile(Deck p){this.pile = p;}
    public Direction getDirection(){return this.direction;}

    public ArrayList<Player> getPlayers(){return this.players;}
    public Player getCurrentPlayer(){return this.currentPlayer;}
    public Card getTopCard(){return this.topCard;}
    public Deck getCurrentDeck(){return this.currentDeck;}
    public Deck getPile(){return this.pile;}
}

