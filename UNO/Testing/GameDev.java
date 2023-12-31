import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Game allows users to play a game of UNO Flip
 *
 * @author Angus Jull
 * @author  Brian Tran - Modified for MVC
 * @author  Aashna Verma - Modified for Flip
 * @version 2.0
 */
public class GameDev {
    public enum Direction { FORWARD, BACKWARD }
    public final int WINNING_SCORE = 500;
    public GameDev.Direction direction;
    public ArrayList<Player> players;
    public int numPlayers;
    public Player currentPlayer;
    public Card topCard;
    public Deck currentDeck; // deck being played with
    public Deck pile; // discard pile
    public boolean gameOver;
    public boolean roundOver;
    public boolean skipNextPlayer; // For skip cards
    public boolean skipAllPlayers;
    public String statusString;
    public Card statusCard;
    public Side side;

    /**
     * Constructor for Game
     * @param newPlayers new player to add to the game
     */
    public GameDev(Collection<Player> newPlayers) {
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
        direction = GameDev.Direction.FORWARD;
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
        drawHands();

        statusString = null;
        statusCard = null;
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

    public Side getSide() {
        return side;
    }

    public void setSide() {
        side = Card.getSide();
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
     * Makes the current player draw a card and ends the round
     * If the deck is empty it will reshuffle the played cards
     */
    public void drawCard(){
        if (!roundOver && !gameOver) {
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
                    if (currentPlayer.getScore() >= WINNING_SCORE) {
                        // Generate win popup here
                        gameOver = true;
                    } else {
                        newRound();
                    }
                }
            } else {
                // The play was invalid, so give back the card
                currentPlayer.addCard(choice);
                statusString = "Invalid card choice.";
            }
        }
    }

    /**
     * Changes the current player in the current direction, and sets the round to no longer be over
     * @return true if the player was changed, false otherwise
     */
    public boolean advanceCurrentPlayer() {
        if (roundOver && !gameOver ) {
            if (skipAllPlayers){
                skipAllPlayers = false;
            } else {
                currentPlayer = nextPlayer(currentPlayer);
            }
            //
            statusString = currentPlayer.getName() + "'s Turn. Play a card or draw";
            // A skip card was played, so don't just go to the next player
            if (skipNextPlayer) {
                currentPlayer = nextPlayer(currentPlayer);
                skipNextPlayer = false;
            }
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
                statusString = "AI Action: " + statusString;
            }
            return true;
        }
        return false;
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
        statusCard = null;
        return player;
    }

    /**
     * Decide what to do based on the card played. Can change the current player and the top card.
     * Append to the status string with what is done with the player's choice
     * @param choice The card that was played on the currentPlayer's turn
     */
    public void processChoice(Card choice) {
        // Don't do anything with no choice
        if (choice == null) { return; }

        switch (choice.getValue()) {
            case DRAW_ONE -> {
                Card drawn = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                statusString += "Next player receives: " + drawn.getColour() + " " + drawn.getValue();
            }
            case DRAW_FIVE -> {
                StringBuilder s = new StringBuilder();
                for(int i=0; i<5; i++){
                    s.append(nextPlayer(currentPlayer).drawCard(currentDeck.removeCard()).toString() + "\n");
                }
                statusString += "Next player receives: \n" + s;
            }
            case SKIP -> {
                skipNextPlayer = true;
                statusString += "Next player is skipped.";
            }
            case SKIP_ALL -> {
                skipAllPlayers = true;
                statusString += "All players are skipped.";
            }
            case REVERSE -> {
                if (direction == Direction.FORWARD) direction = Direction.BACKWARD;
                else if (direction == Direction.BACKWARD) direction = Direction.FORWARD;
                statusString += "Direction reversed.";
            }
            case WILD_DRAW_TWO -> {
                Card drawn1 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                Card drawn2 = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                Colour c = handleWild(choice);
                if (c == null) { return; } // Don't go to the next round or continue handling
                statusString += c.toString() + " has been chosen. " + currentPlayer.getName() + " has to draw two cards due to Wild Draw Two.";
            }
            case WILD -> {
                Colour c = handleWild(choice);
                if (c == null) { return; } // Don't go to the next round or continue handling
                statusString += c.toString() + " has been chosen.";
            }
            case WILD_DRAW_COLOUR -> {
                Colour c = handleWild(choice);
                Card card;
                StringBuilder s = new StringBuilder();
                do{
                    card = nextPlayer(currentPlayer).drawCard(currentDeck.removeCard());
                    s.append(card.toString() + "\n");
                }while(card.getColour() != c);

                if (c == null) { return; } // Don't go to the next round or continue handling
                statusString += "Next player receives:\n" + s;
            }
            case FLIP -> {
                Card.flipSide();
                statusString += "Cards are flipped";
            }
            default -> {
            }
        }

        // The current card is no longer needed
        pile.addCard(topCard);
        // The top card is now the played card
        topCard = choice;
        roundOver = true;
    }


    /**
     * Handle the user input for playing a wild card, in choosing a colour and setting that colour
     * @return A card with the chosen colour, or the top card if the top card is not a wild card.
     */
    public Colour handleWild(Card wild) {
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