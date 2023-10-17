/*
*This is the Player class within the UNO game which represents a player on the UNO game.
*
*Each player has a hand compromised of cards they must play during the game.
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    private String name;
    prviate ArrayList<Card> hand;

    /*
    *Constructor for class Player
    *
    * @param name The name of the player
    */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    /*
    * Getter for name attribute
    * @return the player's name
    */
    public getName() {
        return this.name;
    }

    /*
     * Play a card from the Players hand
     * @param
     * @return
     */
    public playCard() {

    }

    /*
     * Play a card from the Players hand
     * @param deck the Deck to draw from
     * @return true if a card was successfully drawn
     * @return false if a card was unsuccessfully drawn
     */
    public DrawCard(Deck deck) {
        Card nextCard = (Card) deck.nextCard();
        if (nextCard != null){
            hand.add(nextCard);
            return true;
        }
        return false;
    }
}
