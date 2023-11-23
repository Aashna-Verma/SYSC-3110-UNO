/**
 * The test class for Card.
 *
 * @author  Aashna Verma 101225434
 * @version 1.0
 */
public class CardTest extends junit.framework.TestCase{
    Card c_green_one;
    Card c_wild_wild;
    Card c_red_reverse;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    protected void setUp() {
        c_green_one = new Card(Value.ONE, Colour.GREEN, Value.EIGHT, Colour.PINK);
        c_wild_wild = new Card(Value.WILD, Colour.WILD,  Value.WILD, Colour.WILD);
        c_red_reverse = new Card(Value.REVERSE, Colour.RED,  Value.EIGHT, Colour.PINK);
    }

    /**
     * Makes sure the value set to the card remains the same value
     */
    public void testValue(){
        assertEquals("Value is incorrect", c_green_one.getValue(), Value.ONE);
        assertEquals("Value is incorrect", c_wild_wild.getValue(), Value.WILD);
        assertEquals("Value is incorrect", c_red_reverse.getValue(), Value.REVERSE);
    }

    /**
     * Makes sure the colour set to the card remains same colour
     */
    public void testColour(){
        assertEquals("Colour is incorrect", c_green_one.getColour(), Colour.GREEN);
        assertEquals("Colour is incorrect", c_wild_wild.getColour(), Colour.WILD);
        assertEquals("Colour is incorrect", c_red_reverse.getColour(), Colour.RED);
    }

    /**
     * Makes sure the score is as expected
     */
    public void testScore(){
        assertEquals("Score is incorrect", c_green_one.getScore(), 1);
        assertEquals("Score is incorrect", c_wild_wild.getScore(), 40);
        assertEquals("Score is incorrect", c_red_reverse.getScore(), 20);
    }

    /**
     * Makes sure the toString method returns the correct UNO notation
     */
    public void testToString(){
        assertEquals("String is incorrect", c_green_one.toString(), "GREEN ONE");
        assertEquals("String is incorrect", c_wild_wild.toString(), "WILD WILD");
        assertEquals("String is incorrect", c_red_reverse.toString(), "RED REVERSE");
    }

}