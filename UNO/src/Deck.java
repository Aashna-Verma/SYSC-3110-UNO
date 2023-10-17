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
                this.cards.add(new Card(Value.values()[j], Value.values()[j], Colour.BLUE, Colour.PINK));
                this.cards.add(new Card(Value.values()[j], Value.values()[j], Colour.GREEN, Colour.TEAL));
                this.cards.add(new Card(Value.values()[j], Value.values()[j], Colour.RED, Colour.ORANGE));
                this.cards.add(new Card(Value.values()[j], Value.values()[j], Colour.YELLOW, Colour.PURPLE));
            }
        }
        for(i = 0; i < 2; i++) {
            for (j = 0; j < 4; j++) {
                this.cards.add(new Card(Value.DRAW_ONE, Value.DRAW_FIVE, Colour.values()[j], Colour.values()[j+4]));
                this.cards.add(new Card(Value.REVERSE, Value.REVERSE, Colour.values()[j], Colour.values()[j+4]));
                this.cards.add(new Card(Value.SKIP, Value.SKIP_EVERYONE, Colour.values()[j], Colour.values()[j+4]));
                this.cards.add(new Card(Value.FLIP, Value.FLIP, Colour.values()[j], Colour.values()[j+4]));
            }
        }
        for(i = 0; i < 4; i++) {
            this.cards.add(new Card(Value.WILD, Value.WILD, Colour.WILD, Colour.WILD));
            this.cards.add(new Card(Value.WILD_DRAW_TWO, Value.WILD_DRAW_COLOUR, Colour.WILD, Colour.WILD));
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
        if(Card.getSide() == Side.LIGHT) {
            this.deck.addFirst(card);
        } else this.deck.addLast(card);
    }

    /**
     * Returns the current top card of the deck
     *
     * @return Card
     */
    public Card getTopCard(){
        if (Card.getSide() == Side.LIGHT){
            return this.deck.getFirst();
        } else return this.deck.getLast();
    }
}
