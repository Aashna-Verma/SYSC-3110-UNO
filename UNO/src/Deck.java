/**
 * The Deck class
 *
 * @author Darren Wallace 101233334
 * @version 1.0
 */

import java.io.Serializable;
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
public class Deck implements Serializable {
    private ArrayDeque<Card> deck;

    /**
     * Constructor for Deck
     */
    public Deck() {
        deck = new ArrayDeque<>();
    }

    /**
     * Populates and shuffles cards into a ready to play deck
     */
    public void populateDeck(){
        ArrayList<Card> cards = new ArrayList<>();

        int i;
        int j;
        for(i = 0; i < 2; i++) {
            for(j = 0; j < 9; j++) {
                cards.add(new Card(Value.values()[j], Colour.BLUE, Value.values()[8-j], Colour.PINK));
                cards.add(new Card(Value.values()[j], Colour.GREEN, Value.values()[8-j], Colour.TEAL));
                cards.add(new Card(Value.values()[j], Colour.RED, Value.values()[8-j], Colour.ORANGE));
                cards.add(new Card(Value.values()[j], Colour.YELLOW, Value.values()[8-j], Colour.PURPLE));
            }
        }
        for(i = 0; i < 2; i++) {
            for (j = 0; j < 4; j++) {
                cards.add(new Card(Value.DRAW_ONE, Colour.values()[j], Value.REVERSE, Colour.values()[j+4]));
                cards.add(new Card(Value.REVERSE, Colour.values()[j], Value.SKIP_ALL, Colour.values()[j+4]));
                cards.add(new Card(Value.SKIP, Colour.values()[j], Value.DRAW_FIVE, Colour.values()[j+4]));
                cards.add(new Card(Value.FLIP, Colour.values()[j], Value.FLIP, Colour.values()[j+4]));
            }
        }

        for(i = 0; i < 4; i++) {
            cards.add(new Card(Value.WILD, Colour.WILD, Value.WILD_DRAW_COLOUR, Colour.WILD));
            cards.add(new Card(Value.WILD_DRAW_TWO, Colour.WILD, Value.WILD, Colour.WILD));
        }

        Collections.shuffle(cards);
        this.deck.addAll(cards);
    }

    /**
     * Takes a pile of cards and reshuffles them in a new deck
     * @param pile
     * @return reshuffled deck of cards
     */
    public static Deck reshuffle(Deck pile){
        Deck newDeck = new Deck();
        ArrayList<Card> cards = new ArrayList<>();

        while (pile.getTopCard() != null){
            cards.add(pile.removeCard());
        }
        Collections.shuffle(cards);

        newDeck.deck.addAll(cards);
        return newDeck;
    }

    /**
     * Adds a card to the top of the deck
     * @param card Card to be added to the top of deck
     */
    public boolean addCard(Card card){
        return this.deck.offerFirst(card);
    }

    /**
     * Removes the card from the top of the deck and returns it, if not card returns null
     * @return The top card of the deck, null if it is empty
     */
    public Card removeCard(){
        return deck.pollFirst();
    }

    /**
     * Returns the current top card of the deck, null if it is empty
     * @return The top card of the deck, null if it is empty
     */
    public Card getTopCard() {
        return this.deck.peekFirst();
    }
}
