import java.util.ArrayList;

/**
 *This is the Player class within the UNO game which represents a player on the UNO game.
 *Each player has a hand compromised of cards they must play during the game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

public class Player {
    public static final int STARTING_HAND_SIZE = 7;
    private String name;
    private int score;
    private ArrayList<Card> hand;

    /**
     * Constructor for class Player
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        hand = new ArrayList<Card>();
    }

    /**
     * Getter for name attribute
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for score attribute
     * @param score the player's score
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Getter for score attribute
     * @return the player's score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Gets the # of cards in this player's hand
     * @return the number of cards the player has
     */
    public int getNumCards() {
        return this.hand.size();
    }
    
    /**
     * Remove a card from the Players hand
     *
     * @param i the index of the card to be removed
     * @return the card removed if valid, null otherwise
     */
    public Card removeCard(int i) {
        try{
            //card 0 is to pick up, however in the hand attribute 0 is the players' first card
            return this.hand.remove(i - 1);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Invalid card index\n");
            return null;
        }
    }

    /**
     * Resets the players hand to an empty hand
     */
    public void resetHand(){
        this.hand = new ArrayList<Card>();
    }

    /**
     * Calculates the points the player's hand is worth
     * @return the point worth of the players hand
     */
    public int getHandPoints(){
        int points = 0;
        for (Card card: hand){
            points += card.getScore();
        }
        return points;
    }

    /**
     * Add a card to a players hand
     * 
     * @param c the card added to the players hand
     * @return true if the add was successful, otherwise false
     */
    public boolean addCard(Card c) {
        return this.hand.add(c);
    }

    /**
     * Draw a card to the Players hand
     * 
     * @param drawn the card requested to be drawn
     * @return the card that was drawn and added to the hand, null if no card was drawn
     */
    public Card drawCard(Card drawn) {
        if (drawn != null){
            addCard(drawn);
            return drawn; 
        }
        return null;
    }

    /**
     * Draws the maximum number of cards the player can have to its hand
     * @param deck The deck to draw from
     */
    public void drawHand(Deck deck) {
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            drawCard(deck.removeCard());
        }
    }

    /**
     * Gets the players hand
     * @return the players hand as an ArrayList of Cards
     */
    public ArrayList<Card> getHand(){
        return this.hand;
    }

    /**
     * Represents the player and the cards in their hand
     * @return a string with the players name and hand
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(this.name + ":\n");
        string.append(handToString());
        return string.toString();
    }

    /**
     * Represents the cards of the player hand
     * @return a string with the player's hand
     */
    public String handToString() {
        StringBuilder string = new StringBuilder();
        int i = 1;
        for (Card card: this.hand){
            string.append(i +". ");
            string.append(card.toString() + "\n");
            i++;
        }
        return string.toString();
    }

    /**
     * Returns hand of the player
     * @return ArrayList of Cards in a players hand
     */
    public ArrayList<Card> getHand(){
        return hand;
    }
}
