/**
 * The test class for Game.
 *
 * @author  Aashna Verma 101225434
 * @version 1.0
 */
public class GameTest extends junit.framework.TestCase{
    Game game;


    protected void setUp() {
        c_green_one = new Card(Value.ONE, Colour.GREEN);
        c_wild_wild = new Card(Value.WILD, Colour.WILD);
        c_red_reverse = new Card(Value.REVERSE, Colour.RED);
    }

    public void testValue(){
        assertEquals("Value is incorrect", c_green_one.getValue(), Value.ONE);
        assertEquals("Value is incorrect", c_wild_wild.getValue(), Value.WILD);
        assertEquals("Value is incorrect", c_red_reverse.getValue(), Value.REVERSE);
    }

    public void testColour(){
        assertEquals("Colour is incorrect", c_green_one.getColour(), Colour.GREEN);
        assertEquals("Colour is incorrect", c_wild_wild.getColour(), Colour.WILD);
        assertEquals("Colour is incorrect", c_red_reverse.getColour(), Colour.RED);
    }

    public void testScore(){
        assertEquals("Score is incorrect", c_green_one.getScore(), 1);
        assertEquals("Score is incorrect", c_wild_wild.getScore(), 40);
        assertEquals("Score is incorrect", c_red_reverse.getScore(), 20);
    }

    public void testToString(){
        assertEquals("String is incorrect", c_green_one.toString(), "GREEN ONE");
        assertEquals("String is incorrect", c_wild_wild.toString(), "WILD WILD");
        assertEquals("String is incorrect", c_red_reverse.toString(), "RED REVERSE");
    }
}