/**
 *This is the Player class within the UNO game which represents a player on the UNO game.
 *Each player has a hand compromised of cards they must play during the game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

import java.util.*;

public class Player {
    public static final int STARTING_HAND_SIZE = 7;
    private String name;
    private int score;
    private ArrayList<Card> hand;

    /**
     *Constructor for class Player
     *
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
     * setter for score attribute
     * @param score the player's score
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * getter for score attribute
     * @return the player's score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Gets the number of cards in this player's hand
     * 
     * @return the number of cards the player has
     */
    public int getNumCards() {
        return this.hand.size();
    }
    
    /**
     * Play a card from the Players hand, make a play depending on the input provided by a human player
     * @param topCard the current card on top of the deck
     * @param deck the deck the player can draw from
     * @return the card that this player has decided to play
     */
    public Card playCard (Card topCard, Deck deck) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        System.out.println("Enter card index to play or 0 to draw a card:");
        try {
            // Subtract by one because the indexes given start at 1
            choice = sc.nextInt();
            sc.nextLine();
        } 
        catch (Exception e) {
            choice = -1;
            System.out.println ("Enter an integer index");
        }
        // If the player's choice is a card in their hand
        if (choice > 0 && choice <= hand.size()) {
            Card removed = removeCard(choice);
            if (removed != null) {
                sc.close();
                return removed;
            }
        }
        else if (choice == 0) {
            Card drawn = drawCard(deck);
            if (drawn != null) {
                System.out.println("Drew a card: " + drawn);
                sc.close();
                return null;
            }
            else {
                System.out.println("No cards to draw");
            }
        }
        else {
            System.out.println("Invalid choice, choose again");
        }
        sc.close();
        return null;
    }
    
    /**
     * Remove a card from the Players hand
     *
     * @param i the index of the card to be removed
     * @return the card removed if valid, null otherwise
     */
    private Card removeCard(int i) {
        try{
            return this.hand.remove(i - 1);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Invalid card index\n");
            return null;
        }
    }
    /**
     * Calculates the points the player's hand is worth
     *
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
     * @param card the card being returned to the player
     * @return true, always
     */
    public boolean addCard(Card c) {
        return this.hand.add(c);
    }

    /**
     * Play a card from the Players hand
     * 
     * @param deck the Deck to draw from
     * @return the card that was drawn and added to the hand, null if no card was drawn
     */
    public Card drawCard(Deck deck) {
        Card nextCard = deck.getTopCard();
        if (nextCard != null){
            addCard(nextCard);
            return nextCard; 
        }
        return null;
    }
    /**
     * Draws the maximum number of cards the player can have
     * @param deck The deck to draw from
     */
    public void drawHand(Deck deck) {
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            drawCard(deck);
        }
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
     * Output the cards of the player
     * 
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
}
