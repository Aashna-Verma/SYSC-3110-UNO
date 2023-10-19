/**
 * The Deck class
 *
 * @author Darren
 * @version 1.1
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Light side:
 * 18 Blue - 1-9
 * 18 Green - 1-9
 * 18 Red - 1-9
 * 18 Yellow - 1-9
 * 8 Draw One - 2 each colour
 * 8 Reverse - 2 each colour
 * 8 Skip - 2 each colour
 * 8 Flip - 2 each colour
 * 4 Wild
 * 4 Wild Draw 2
 *
 * Dark side:
 * 18 Pink - 1-9
 * 18 Teal - 1-9
 * 18 Orange - 1-9
 * 18 Purple - 1-9
 * 8 Draw Five - 2 each colour
 * 8 Reverse - 2 each colour
 * 8 Skip Everyone - 2 each colour
 * 8 Flip - 2 each colour
 * 4 Wild
 * 4 Wild Draw Colour
 */
public class Deck {
    private ArrayList<Card> cards;
    private ArrayDeque<Card> deck;

    /**
     * Constructor for Deck
     */
    public Deck() {
        cards = new ArrayList<>();
        deck = new ArrayDeque<>();
    }

    /**
     * Populates and shuffles cards into a ready to play deck
     */
    public void populateDeck(){
        int i;
        int j;
        for(i = 0; i < 2; i++) {
            for(j = 0; j <= 8; j++) {
                this.cards.add(new Card(Value.values()[j], Colour.BLUE));
                this.cards.add(new Card(Value.values()[j], Colour.GREEN));
                this.cards.add(new Card(Value.values()[j], Colour.RED));
                this.cards.add(new Card(Value.values()[j], Colour.YELLOW));
            }
        }
        for(i = 0; i < 2; i++) {
            for (j = 0; j < 4; j++) {
                this.cards.add(new Card(Value.DRAW_ONE, Colour.values()[j]));
                this.cards.add(new Card(Value.REVERSE, Colour.values()[j]));
                this.cards.add(new Card(Value.SKIP, Colour.values()[j]));
            }
        }
        for(i = 0; i < 4; i++) {
            this.cards.add(new Card(Value.WILD, Colour.WILD));
            this.cards.add(new Card(Value.WILD_DRAW_TWO, Colour.WILD));
        }
        Collections.shuffle(cards);
        this.deck.addAll(cards);
    }

    /**
     * Adds a card to the top of the deck
     *
     * @param card Card to be added to the top of deck
     */
    public void addCard(Card card){
        this.deck.addFirst(card);
    }

    /**
     * Removes the card from the top of the deck and returns it, if not card returns null
     * @return Card
     */
    public Card removeCard(){
        return deck.pollFirst();
    }

    /**
     * Returns the current top card of the deck
     *
     * @return Card
     */
    public Card getTopCard() {
        return deck.getFirst();
    }
}
