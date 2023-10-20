import java.util.ArrayList;

/**
 * The test class for Game.
 *
 * @author  Aashna Verma 101225434
 * @version 1.0
 */
public class GameTest extends junit.framework.TestCase {
    GameDev game;

    protected void setUp() {
        game = new GameDev();
    }

    public void testGame(){
        assertEquals("The direction is not Forward", game.getDirection(), GameDev.Direction.FORWARD);
        assertNull("The current deck was not created", game.getCurrentDeck().getTopCard());
        assertNull("The discard pile was not created", game.getPile().getTopCard());
    }

    public void testPlayGame(){

    }

    public void testNextPlayer(){
        // set up
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        ArrayList<Player> p_list = new ArrayList<>();
        p_list.add(p1);
        p_list.add(p2);
        p_list.add(p3);
        p_list.add(p4);
        game.setPlayers(p_list);

        // forward direction
        assertEquals("Game did not move forward from p1 to p2", game.nextPlayer(p1), p2);
        assertEquals("Game did not move forward from p2 to p3", game.nextPlayer(p2), p3);
        assertEquals("Game did not move forward from p3 to p4", game.nextPlayer(p3), p4);
        assertEquals("Game did not move forward from p4 to p1", game.nextPlayer(p4), p1);

        // forward direction
        assertEquals("Game did not move backwards from p1 to p4", game.nextPlayer(p1), p4);
        assertEquals("Game did not move backwards from p4 to p3", game.nextPlayer(p4), p3);
        assertEquals("Game did not move backwards from p3 to p2", game.nextPlayer(p3), p2);
        assertEquals("Game did not move backwards from p2 to p1", game.nextPlayer(p2), p1);

    }

}