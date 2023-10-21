/**
 * Test class for Deck
 *
 * @author Darren
 * @version 1.0
 */

public class DeckTest extends junit.framework.TestCase{
    Deck emptyDeck, deck, deck2, deck3;
    Card card, card2, card3;

    protected void setUp() {
        emptyDeck = new Deck();
        deck = new Deck(); deck.populateDeck();
        deck2 = new Deck(); deck2.populateDeck();
        deck3 = new Deck(); deck3.populateDeck();
        card = deck.getTopCard();
        card2 = deck2.removeCard();
        card3 = new Card(Value.FOUR, Colour.RED);
    }
    public void test_populateDeck() {
        assertNull(emptyDeck.getTopCard());
        assertNotSame(deck, deck2);
    }

    public void test_addCard() {
        assertTrue(deck3.addCard(card3));
    }

    public void test_getTopCard() {
        assertEquals(card, deck.getTopCard());
        assertNotSame(card2, deck2.getTopCard());
    }
}