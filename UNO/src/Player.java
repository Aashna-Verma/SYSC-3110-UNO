/**
 *This is the Player class within the UNO game which represents a player on the UNO game.
 *Each player has a hand compromised of cards they must play during the game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

import java.util.*;

public class Player {

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
        this.score =0;
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
     * getter for hand attribute
     * @return an ArrayList representing the Player's hand
     */
    public ArrayList<Card> getHand(){
        return this.hand;
    }

    /**
     * Play a card from the Players hand
     *
     * @param i the index of the card wished to be played
     *
     * @return the card removed if valid, null if invalid card index
     */

    public Card playCard(int i) {
        try{
            Card removed_card = this.hand.get(i-1);
            this.hand.remove(i-1);
            return removed_card;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Invalid card index\n");
            return null;
        }
    }

    /**
     * Play a card from the Players hand
     * @param deck the Deck to draw from
     * @return true if a card was successfully draw, false if a card was unsuccessfully drawn
     */
    public boolean drawCard(Deck deck) {
        Card nextCard = deck.getTopCard();
        if (nextCard != null){
            this.hand.add(nextCard);
            return true;
        }
        return false;
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
