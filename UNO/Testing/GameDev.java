import java.util.ArrayList;
import java.util.Scanner;

/**
 * GameDev allows users to play a game of UNO Flip
 *
 * @author Angus Jull
 * @author  Brian Tran - Modified for MVC
 * @version 2.0
 */
public class GameDev {
    public enum Direction { FORWARD, BACKWARD };
    public final int WINNING_SCORE = 500;
    public GameDev.Direction direction;
    public ArrayList<Player> players;
    public Player currentPlayer;
    public Card topCard;
    public Deck currentDeck; // deck being played with
    public Deck pile; // discard pile
    public boolean gameOver;
    public boolean roundOver;
    public String statusString;
    public Card statusCard;
    public GameView gameView;

    /**
     * Constructor for Game
     * @param numPlayers the number of players in the game
     */
    public GameDev(int numPlayers) {
        // Populate players list later
        players = new ArrayList<>();
        direction = GameDev.Direction.FORWARD;

        //populate deck to draw from
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
        topCard = currentDeck.removeCard();

        gameOver = false;
        roundOver = false;
        //initialize players and draw their hands
        for (int i = 0; i < numPlayers; i++) {
            // Create the player and give them their starting conditions
            String name = "Player " + (i+1);
            Player newPlayer = new Player(name);
            players.add(newPlayer);
            drawHands();
        }
        currentPlayer = players.get(0);
        statusString = null;
        statusCard = null;
        //updatePlayerLabelView(currentPLayer.getName());
        //updateTopCardView(topCard);
        //updateHandView(currentPlayer.getHand());
        //Status panel should start empty
        //updateNextPlayerButton(GREY_OUT)
    }
    public int getNumPlayers(){
        return players.size();
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
    public int update(){
        return 1;
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
    public void drawHands() {
        for (Player p: players) {
            p.resetHand();
            p.drawHand(currentDeck);
        }
    }

    /**
     * Triggered when player clicks draw card
     */
    public void drawCard(){
        Card drawn = currentDeck.removeCard();
        statusString = "Drew a card: " + drawn.getColour() + " " + drawn.getValue();
        statusCard = drawn;
        roundOver = true;

        //updateStatusPanel(drawn);
        //updateNextPlayerButton(UN-GREY);
        update();
        currentPlayer.drawCard(drawn);
    }

    /**
     * Plays a card from the players hand to the deck
     * @param input the index of the card selected to play
     * @return true if the play was valid
     * @return false if the play was invalid
     */
    public boolean playCard(int input) {
        Card choice = currentPlayer.removeCard(input);
        if (topCard.validWith(choice)) {
            topCard = choice;
            processChoice(choice);
            if (currentPlayer.getNumCards() == 0){
                currentPlayer.setScore(currentPlayer.getScore() + getPoints());
                if (currentPlayer.getScore() == WINNING_SCORE) {
                    //Call method IN CONTROLLER to generate popup displaying winner
                    roundOver = true;
                    gameOver = true;
                }
                else{
                    //Call method IN CONTROLLER to generate popup displaying scores
                }
                update();
            }
            return true;
        }
        else {
            statusString = "Invalid card choice.";

            update();
            //updateStatusPanel(Invalid choice);
            return false;
        }
    }

    /**
     * Returns the next player to play in the game
     * @param player The player who's turn it currently is
     * @return The next player who's turn it is, or the current player if nobody is next
     */
    public Player nextPlayer(Player player) {
        if (direction == GameDev.Direction.FORWARD) {
            int nextPlayerIndex = players.indexOf(player) + 1;
            // The next index going forward is 0 if the index is outsize of the ArrayList
            return players.get(nextPlayerIndex < players.size() ? nextPlayerIndex : 0);
        }
        else if (direction == GameDev.Direction.BACKWARD) {
            int nextPlayerIndex = players.indexOf(player) - 1;
            return players.get(nextPlayerIndex >= 0 ? nextPlayerIndex : players.size() - 1);
        }
        update();
        //updateCurrentPlayerLabel();
        //UpdateTopCardView
        //updateNextPlayerButton(GreyOut);
        return player;
    }

    /**
     * Decide what to do based on the card played. Can change the current player and the top card
     * @param choice The card that was played on the currentPlayer's turn
     */
    public void processChoice(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }

        switch (choice.getValue()) {
            case DRAW_ONE:
                Card drawn = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                statusString = "Drew a card: " + drawn.getColour() + " " + drawn.getValue();
                //updateStatus(drawn);
            case SKIP:
                // Set to the next player, which will then skip the player
                //updateStatus(NEXT PLAYER SKIPPED)
                currentPlayer = nextPlayer(currentPlayer);
                statusString = "Next player is skipped.";
            case REVERSE:
                if (direction == GameDev.Direction.FORWARD) direction = GameDev.Direction.BACKWARD;
                else if (direction == GameDev.Direction.BACKWARD) direction = GameDev.Direction.FORWARD;
                statusString = "Direction reversed.";
            case WILD_DRAW_TWO:
                Card drawn1 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                Card drawn2 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                choice = handleWild(choice);
                statusString = choice.getColour() + " has been chosen. " + currentPlayer.getName() + " has to draw two cards due to Wild Draw Two.";
                //updateSatus(NEW_COLOUR: choice.getColour(), drawn1, drawn2)
                break;
            case WILD:
                choice = handleWild(choice);
                statusString = choice.getColour() + " has been chosen.";
                //updateSatus(NEW_COLOUR: choice.getColour());
                break;
            default:
                break;
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
    public Card handleWild(Card wild) {
        Colour chosenColour = null;
        //chosenColour = getNewColourPopUp() <- METHOD IN CONTROLLER

        // Create a new wild card with the same value, but with the chosen colour
        return new Card(wild.getValue(), chosenColour);
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
}