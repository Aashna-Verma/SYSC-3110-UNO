import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game allows users to play a game of UNO Flip
 * 
 * @author Angus Jull
 * @version 1.0
 */
public class Game {
    private enum Direction { FORWARD, BACKWARD };
    private final int WINNING_SCORE = 500;
    private Game.Direction direction;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Card topCard;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private boolean gameOver;
    private String statusString;
    private Card statusCard1;
    private Card statusCard2;
    private ArrayList<View> views;

    /**
     * Constructor for Game
     * @param numPlayers the number of players in the game
     */
    public Game(int numPlayers) {
        // Populate players list later
        players = new ArrayList<>();
        direction = Game.Direction.FORWARD;

        //populate deck to draw from
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
        topCard = currentDeck.removeCard();

        gameOver = false;

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
        statusCard1 = null;

        update();
        //updatePlayerLabelView(currentPLayer.getName());
        //updateTopCardView(topCard);
        //updateHandView(currentPlayer.getHand());
        //Status panel should start empty
        //updateNextPlayerButton(GREY_OUT)
    }
    /**
     * Adds a view to listen to the model
     */
    public void addView(View view){
        views.add(view);
    }

    /**
     * Updates every view
     */
    private void update(){
        for (View v: views){
            v.update();
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

    public String getStatusString() {
        return statusString;
    }

    public Card getStatusCard1() {
        return statusCard1;
    }

    public Card getStatusCard2() {
        return statusCard2;
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
     * Triggered when player clicks draw card
     */
    private void drawCard(){
        Card drawn = currentDeck.removeCard();
        statusString = "Drew a card: " + drawn.getColour() + " " + drawn.getValue();
        statusCard1 = drawn;

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
    private void processChoice(Card choice) {
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
                if (direction == Game.Direction.FORWARD) direction = Game.Direction.BACKWARD;
                else if (direction == Game.Direction.BACKWARD) direction = Game.Direction.FORWARD;
                statusString = "Direction reversed.";
            case WILD_DRAW_TWO:
                Card drawn1 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                Card drawn2 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                choice = handleWild(choice);
                statusString = choice.getColour() + " has been chosen. " + currentPlayer.getName() + " has to draw two cards due to Wild Draw Two.";
                statusCard1 = drawn1;
                statusCard2 = drawn2;
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
        update();
    }


    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     * @return A card with the chosen colour, or the top card if the top card is not a wild card.
     */
    private Card handleWild(Card wild) {
        Colour chosenColour = null;
        //chosenColour = getNewColourPopUp() <- METHOD IN CONTROLLER

        // Create a new wild card with the same value, but with the chosen colour
        return new Card(wild.getValue(), chosenColour);
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