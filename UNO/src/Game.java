import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Game allows users to play a game of UNO Flip
 * 
 * @author Angus Jull
 * @author  Brian Tran - Modified for MVC
 * @version 2.0
 */
public class Game {
    private enum Direction { FORWARD, BACKWARD };
    private final int WINNING_SCORE = 500;
    private Game.Direction direction;
    private ArrayList<Player> players;
    private int numPlayers;
    private Player currentPlayer;
    private Card topCard;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private boolean gameOver;
    private boolean roundOver;
    private boolean skipNextPlayer; // For skip cards
    private String statusString;
    private Card statusCard;
    private GameView gameView;

    /**
     * Constructor for Game
     * @param numPlayers the number of players in the game
     */
    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        players = new ArrayList<>();
        //initialize players and draw their hands
        for (int i = 0; i < numPlayers; i++) {
            // Create the player and give them their starting conditions
            String name = "Player " + (i + 1);
            Player newPlayer = new Player(name);
            players.add(newPlayer);

        }
        newRound();
    }
    public void newRound() {
        direction = Game.Direction.FORWARD;
        //populate deck to draw from
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
        topCard = currentDeck.removeCard();

        gameOver = false;
        roundOver = false;
        skipNextPlayer = false;

        currentPlayer = players.get(0);
        // Get the players to draw their hands
        for (Player p: players) {
            drawHands();
        }

        statusString = null;
        statusCard = null;
        update();
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void addView(GameView gameView){
        this.gameView = gameView;
        update();
    }
    /**
     * Updates every view
     */
    private void update(){
        if (gameView != null) {
            gameView.update(this);
        }
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Card getTopCard() {
        return topCard;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isRoundOver() {
        return roundOver;
    }
    public String getStatusString() {
        return statusString;
    }
    public Card getStatusCard() {
        return statusCard;
    }

    /**
     * Makes the players each draw a hand from the current deck
     */
    private void drawHands() {
        for (Player p: players) {
            p.resetHand();
            p.drawHand(currentDeck);
        }
    }

    /**
     * Makes the current player draw a card and ends the round
     */
    public void drawCard(){
        if (!roundOver && !gameOver) {
            Card drawn = currentDeck.removeCard();
            statusString = "Drew a card: " + drawn.getColour() + " " + drawn.getValue();
            statusCard = drawn;
            roundOver = true;
            currentPlayer.drawCard(drawn);
            update();
        }
    }

    /**
     * Plays a card from the players hand to the deck
     *
     * @param input the index of the card selected to play
     * @return false if the play was invalid
     */
    public void playCard(int input) {
        if (!roundOver && !gameOver) {
            Card choice = currentPlayer.removeCard(input);
            if (topCard.validWith(choice)) {
                statusString = "Played a card: " + choice.toString() + "\n";
                processChoice(choice);
                if (currentPlayer.getNumCards() == 0) {
                    currentPlayer.setScore(currentPlayer.getScore() + getPoints());
                    // update the view so that scores are accurate
                    update();
                    if (currentPlayer.getScore() >= WINNING_SCORE) {
                        // Generate win popup here
                        gameOver = true;
                        gameView.displayWinPopup(currentPlayer);
                    } else {
                        gameView.displayRoundWinPopup(currentPlayer);
                        newRound();
                    }
                }
            } else {
                // The play was invalid, so give back the card
                currentPlayer.addCard(choice);
                statusString = "Invalid card choice.";
            }
            update();
        }
    }

    /**
     * Changes the current player in the current direction, and sets the round to no longer be over
     * @return true if the player was changed, false otherwise
     */
    public boolean advanceCurrentPlayer() {
        if (roundOver && !gameOver) {
            statusString = currentPlayer.getName() + "'s Turn. Play a card or draw";
            currentPlayer = nextPlayer(currentPlayer);
            // A skip card was played, so don't just go to the next player
            if (skipNextPlayer) {
                currentPlayer = nextPlayer(currentPlayer);
                skipNextPlayer = false;
            }
            roundOver = false; // Next round starts
            statusCard = null;
            update();
            return true;
        }
        return false;
    }

    /**
     * Returns the next player to play in the game
     * @param player The player who's turn it currently is
     * @return The next player who's turn it is, or the current player if nobody is next
     */
    private Player nextPlayer(Player player) {
        if (direction == Game.Direction.FORWARD) {
            int nextPlayerIndex = players.indexOf(player) + 1;
            // The next index going forward is 0 if the index is outsize of the ArrayList
            return players.get(nextPlayerIndex < players.size() ? nextPlayerIndex : 0);
        }
        else if (direction == Game.Direction.BACKWARD) {
            int nextPlayerIndex = players.indexOf(player) - 1;
            return players.get(nextPlayerIndex >= 0 ? nextPlayerIndex : players.size() - 1);
        }
        statusCard = null;
        return player;
    }

    /**
     * Decide what to do based on the card played. Can change the current player and the top card.
     * Append to the status string with what is done with the player's choice
     * @param choice The card that was played on the currentPlayer's turn
     */
    private void processChoice(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }

        switch (choice.getValue()) {
            case DRAW_ONE -> {
                Card drawn = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                statusString += "Drew a card: " + drawn.getColour() + " " + drawn.getValue();
            }
            //updateStatus(drawn);
            case SKIP -> {
                // Set to the next player, which will then skip the player
                //updateStatus(NEXT PLAYER SKIPPED)
                skipNextPlayer = true;
                statusString += "Next player is skipped.";
            }
            case REVERSE -> {
                if (direction == Direction.FORWARD) direction = Direction.BACKWARD;
                else if (direction == Direction.BACKWARD) direction = Direction.FORWARD;
                statusString += "Direction reversed.";
            }
            case WILD_DRAW_TWO -> {
                Card drawn1 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                Card drawn2 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                choice = handleWild(choice);
                if (choice == null) { return; } // Don't go to the next round or continue handling
                statusString += choice.getColour() + " has been chosen. " + currentPlayer.getName() + " has to draw two cards due to Wild Draw Two.";
            }
            //updateSatus(NEW_COLOUR: choice.getColour(), drawn1, drawn2)
            case WILD -> {
                choice = handleWild(choice);
                if (choice == null) { return; } // Don't go to the next round or continue handling
                statusString += choice.getColour() + " has been chosen.";
            }
            //updateSatus(NEW_COLOUR: choice.getColour());
            default -> {
            }
        }
        //updatePlayerHand(GREY_OUT);
        //updateNextPlayerButton(UN_GREY);

        // The current card is no longer needed
        pile.addCard(topCard);
        // The top card is now the played card
        topCard = choice;
        roundOver = true;
        update();
    }


    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     * @return A card with the chosen colour, or the top card if the top card is not a wild card.
     */
    private Card handleWild(Card wild) {
        String chosen = GameView.viewPickWildCard();
        if (chosen == null) {
            return null;
        }
        else {
            return new Card(wild.getValue(), Colour.valueOf(chosen));
        }
    }

    /**
     * Calculates the points the current player gets from winning the round
     * @return the total points of all players hand EXCEPT the winning players
     */
    private int getPoints() {
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

}