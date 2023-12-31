import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test class for Player
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

public class PlayerTest{
    private Player player;

    @Before
    public void setUp(){
        this.player = new Player("Player T");
    }

    @Test
    public void testGetName(){
        assertEquals("Name is incorrect", "Player T", this.player.getName());
    }

    @Test
    public void testGetScore(){
        assertEquals("Wrong Score!", 0, player.getScore());
    }
    @Test
    public void testSetScore(){
        player.setScore(10);
        assertEquals("Wrong score!", 10, player.getScore());
    }

    @Test
    public void test_getNumCards(){
        assertEquals("Getting hand size incorrectly", 0, player.getNumCards());
    }

    @Test
    public void testAddCard(){
        Card card1 = new Card(Value.ONE, Colour.BLUE, Value.EIGHT, Colour.ORANGE);
        Card card2 = new Card(Value.FIVE, Colour.RED,  Value.TWO, Colour.TEAL);
        player.addCard(card1);
        player.addCard(card2);
        assertEquals("Adding cards incorrectly!", 2, player.getNumCards());
    }

}
