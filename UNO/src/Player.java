/**
 *This is the Player class within the UNO game which represents a player on the UNO game.
 *Each player has a hand compromised of cards they must play during the game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

import java.util.*;

public class Player {
    public static final int MAX_HAND_SIZE = 7;
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
     * Play a card from the Players hand
     * Make a play depending on the input provided by a human player
     * @param topCard the current card on top of the deck
     * @return the card that this player has decided to play
     */ 
    public Card playCard (Card topCard, Deck deck) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        Card removed = null;
        do {
            System.out.println("Enter card index to play or 0 to draw a card:");
            try {
                // Subtract by one because the indexes given start at 1
                choice = sc.nextInt() - 1;
            } 
            catch (Exception e) {
                choice = -1;
                System.out.println ("Enter an integer index");
            }
            if (choice >= 0 && choice < 7) {
                if (choice == 0) {
                    System.out.println("Drew a card: " + drawCard(deck));
                }
                else {
                    removed = removeCard(choice);
                    if (removed != null) {
                        return removed;
                    }
                }
            }
            System.out.println("Invalid choice, choose again");
        } while (true);
    }
    
    /**
     * Remove a card from the Players hand
     *
     * @param i the index of the card to be removed
     *
     * @return the card removed if valid, null otherwise
     */
    public Card removeCard(int i) {
        try{
            Card removed_card = this.hand.get(i - 1);
            this.hand.remove(i - 1);
            return removed_card;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Invalid card index\n");
            return null;
        }
    }
    /**
     * Return a card to a player's hand. Called if the player tries to make an invalid move.
     * @param card the card being returned to the player
     */
    public boolean addCard(Card c) {
        if (hand.size() < MAX_HAND_SIZE) {
            this.hand.add(c);
            return true;
        }
        return false;
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
     * Represents the player and the cards in their hand
     * @return a string with the players name and hand
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(this.name + ": ");
        int i = 1;
        for (Card card: this.hand){
            string.append(i +". ");
            string.append(card.toString() + "\n");
            i++;
        }
        return string.toString();
    }
}
