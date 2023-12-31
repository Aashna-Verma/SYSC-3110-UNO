import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Game allows users to play a game of UNO Flip
 * 
 * @author Angus Jull
 * @author  Brian Tran - Modified for MVC
 * @author  Aashna Verma - Modified for Flip
 * @version 3.0
 */
public class Game implements Serializable{
    private enum Direction { FORWARD, BACKWARD }
    private final int WINNING_SCORE = 500;
    private Game.Direction direction;
    private ArrayList<Player> players;
    private int numPlayers;
    private Player currentPlayer;
    private Card topCard;
    private Card prevChoice;
    private Deck currentDeck; // deck being played with
    private Deck pile; // discard pile
    private boolean gameOver;
    private boolean roundOver;
    private boolean skipNextPlayer; // For skip cards
    private boolean skipAllPlayers;
    private boolean prevDrew;
    private String statusString;
    private Card statusCard;
    private transient GameView gameView;

    /**
     * Constructor for Game
     * @param newPlayers new player to add to the game
     */
    public Game(Collection<Player> newPlayers) {
        this.numPlayers = newPlayers.size();
        players = new ArrayList<>();
        //initialize players and draw their hands
        players.addAll(newPlayers);
        newRound();
    }

    /**
     * Generates a new round for the uno game
     */
    public void newRound() {
        direction = Game.Direction.FORWARD;
        //populate deck to draw from
        currentDeck = new Deck();
        pile = new Deck();
        currentDeck.populateDeck();
        topCard = currentDeck.removeCard();
        pile.addCard(topCard);

        gameOver = false;
        roundOver = false;
        skipNextPlayer = false;

        currentPlayer = players.get(0);
        // Get the players to draw their hands
        drawHands();

        statusString = null;
        statusCard = null;
        update();
    }

    /**
     * Getter for number of players in the uno game
      * @return number of players in the game
     */
    public int getNumPlayers(){
        return numPlayers;
    }

    /**
     * Getter for the ArrayList containing all Players within the game
     * @return the ArrayList of players in the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a view for the model to update
     * @param gameView the view for the game
     */
    public void addView(GameView gameView){
        this.gameView = gameView;
        update();
    }

    /**
     * Updates every view it is listening to
     */
    private void update(){
        if (gameView != null) {
            gameView.update(this);
        }
    }

    /**
     * Getter for current players whose turn it is
     * @return current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for the top card of the pile
     * @return top card
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Getter for the game status, if it is over or not
     * @return true if game is over, otherwise false
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Getter for the round status, if it is over or not
     * @return true if round is over, otherwise false
     */
    public boolean isRoundOver() {
        return roundOver;
    }

    /**
     * Determines if undo was clicked earlier
     *
     * @return true is there is a prev choice
     */
    public boolean hasPrevChoice() {return prevChoice != null || prevDrew ;}

    /**
     * Getter for the string containing the status of the game
     * @return status string
     */
    public String getStatusString() {
        return statusString;
    }

    /**
     * Getter for the status card of the game
     * @return status card
     */
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
     * If the deck is empty it will reshuffle the played cards
     */
    public void drawCard(){
        if (!roundOver && !gameOver) {
            prevDrew = false;
            prevChoice = null;
            if (currentDeck.getTopCard() == null){
                topCard = pile.removeCard();
                currentDeck = Deck.reshuffle(pile);
                pile.addCard(topCard);
            }
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
            prevChoice = null;
            prevDrew = false;
            if (topCard.validWith(choice)) {
                statusString = "Played a card: " + choice.toString() + "\n";
                processChoiceStatus(choice);
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
        if (roundOver && !gameOver ) {
            processChoice(topCard);

            if (skipAllPlayers){
                skipAllPlayers = false;
            } else {
                currentPlayer = nextPlayer(currentPlayer);
            }

            // A skip card was played, so don't just go to the next player
            if (skipNextPlayer) {
                currentPlayer = nextPlayer(currentPlayer);
                skipNextPlayer = false;
            }
            //statusString = currentPlayer.getName() + "'s Turn. Play a card or draw";
            skipAllPlayers = false;
            roundOver = false; // Next round starts
            statusCard = null;

            // Check if the next player is a bot, and get them to take their turn
            if (currentPlayer instanceof AIBot) {
                int botChoice = ((AIBot)currentPlayer).selectCard(this.getTopCard());
                if (botChoice < 0) {
                    this.drawCard();
                }
                else {
                    this.playCard(botChoice);
                }
                // Round will now be over, show that an AI just played in the status string
                statusString = "AI Action:\n" + statusString;
            }
            update();
            return true;
        }
        return false;
    }

    /**
     * Returns the next player to play in the game
     * @param player The player whose turn it currently is
     * @return The next player whose turn it is, or the current player if nobody is next
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
     * Changes the topCard and gets colour for wild cards
     * @param choice is the card played
     */
    private void processChoiceStatus(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }
        if (choice.getColour() == Colour.WILD) {
                Colour c = handleWild(choice);
                statusString += "Color is set to " + topCard.getColour() + "\n";
        }

        // The top card is now the played card
        topCard = choice;
        pile.addCard(topCard);
        roundOver = true;
        update();
    }

    /**
     * Decide what to do based on the card played.
     * Append to the status string with what is done with the player's choice
     * @param choice The card that was played on the currentPlayer's turn
     */
    private void processChoice(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }

        switch (choice.getValue()) {
            case DRAW_ONE -> {
                Card drawn = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                statusString += "Receiving: " + drawn.getColour() + " " + drawn.getValue() + "\n";
            }
            case DRAW_FIVE -> {
                StringBuilder s = new StringBuilder();
                for(int i=0; i<5; i++){
                    s.append(nextPlayer(currentPlayer).drawCard(currentDeck.removeCard()).toString() + "\n");
                }
                statusString += "Receiving: \n" + s + "\n";
            }
            case SKIP -> {
                skipNextPlayer = true;
                statusString += "Last player skipped.\n";
            }
            case SKIP_ALL -> {
                skipAllPlayers = true;
                statusString += "All players skipped.\n";
            }
            case REVERSE -> {
                if (direction == Direction.FORWARD) direction = Direction.BACKWARD;
                else if (direction == Direction.BACKWARD) direction = Direction.FORWARD;
                statusString += "Direction reversed.\n";
            }
            case WILD_DRAW_TWO -> {
                StringBuilder s = new StringBuilder();
                for(int i=0; i<2; i++){
                    s.append(nextPlayer(currentPlayer).drawCard(currentDeck.removeCard()).toString() + "\n");
                }
                statusString += "Receiving: \n" + s + "\n";
            }
            case WILD -> {
                statusString += "";
            }
            case WILD_DRAW_COLOUR -> {
                Card card;
                StringBuilder s = new StringBuilder();
                do{
                    card = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                    s.append(card.toString() + "\n");
                }while(card.getColour() != topCard.getColour());

                statusString += "Reciving:\n" + s;
            }
            case FLIP -> {
                Card.flipSide();
                statusString += "Cards are flipped";
            }
            default -> {
            }
        }
        update();
    }

    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     * @return A card with the chosen colour, or the top card if the top card is not a wild card.
     */
    private Colour handleWild(Card wild) {
        Colour chosen = currentPlayer.chooseWildColour(topCard);
        if (chosen != null) {
            wild.setWildColour(chosen);
        }
        return chosen;
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

    /**
     * Restarts the Uno game
     */
    public void replay(){
        statusString += "REPLAY!\n";
        int choice = GameView.displayReplayPopup();
        if (choice == JOptionPane.YES_OPTION) {
            for (Player p : this.players){
                p.setScore(0);
            }
            newRound();
            update();
        }
    }

    public GameView getGameView(){
        return gameView;
    }

    /**
     *Serializes the current state of the game.
     * @throws IOException
     */
    public void serialize() throws IOException {
        ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream("UserSave.ser"));
        try {
            oStream.writeObject(this);
            oStream.close();
            System.out.println("User Saved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the saved state of the game.
     * @return Loaded game
     * @throws IOException
     */
    public static Game deserialize() throws IOException {
        try {
            ObjectInputStream oInput = new ObjectInputStream(new FileInputStream("UserSave.ser"));
            System.out.println("User Loaded");
            Game loadedGame = (Game) oInput.readObject();
            oInput.close();
            return loadedGame;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Undo's the last move by player
     */
    public void undo(){
        statusString += "UNDO last action!\n";
        if(statusCard != null){
            currentDeck.addCard(statusCard);
            currentPlayer.removeCard(currentPlayer.getNumCards());
            statusCard = null;
            prevChoice = null;
            prevDrew = true;
        } else {
            prevChoice = topCard;
            currentPlayer.addCard(pile.removeCard());
            topCard = pile.getTopCard();
            prevDrew = false;
        }
        roundOver = false;
        update();
    }

    /**
     * Redo's the last undid action by player
     */
    public void redo(){
        statusString += "REDO last action!\n";
        if (prevChoice == null){
            statusCard = currentPlayer.drawCard(currentDeck.removeCard());
        } else {
            pile.addCard(prevChoice);
            topCard = prevChoice;
            currentPlayer.removeCard( currentPlayer.getHand().indexOf(prevChoice) + 1);
        }

        prevDrew = false;
        prevChoice = null;
        roundOver = true;
        update();
    }

}