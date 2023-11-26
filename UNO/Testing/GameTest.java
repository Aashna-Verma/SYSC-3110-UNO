//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * The test class for Game.
// *
// * @author  Aashna Verma 101225434
// * @version 1.0
// */
//public class GameTest extends junit.framework.TestCase {
//    private GameDev game;
//
//    protected void setUp() {
//        Collection<Player> p = new ArrayList<Player>();
//        p.add(new Player("p1"));
//        p.add(new Player("p2"));
//
//        game = new GameDev(p);
//    }
//
//    public void testNumPlayers_2(){
//        assertEquals("2 plays have not been created", game.getPlayers().size(), 2);
//        assertEquals("2 plays have not been created", game.getNumPlayers(), 2);
//
//    }
//    public void testNumPlayers_3(){
//        Collection<Player> p = new ArrayList<Player>();
//        p.add(new Player("p1"));
//        p.add(new Player("p2"));
//        p.add(new Player("p3"));
//        game = new GameDev(p);
//
//        assertEquals("3 plays have not been created", game.getPlayers().size(), 3);
//        assertEquals("3 plays have not been created", game.getNumPlayers(), 3);
//
//    }
//
//    public void testNumPlayers_4(){
//        Collection<Player> p = new ArrayList<Player>();
//        p.add(new Player("p1"));
//        p.add(new Player("p2"));
//        p.add(new Player("p3"));
//        p.add(new Player("p4"));
//        game = new GameDev(p);
//
//        assertEquals("4 plays have not been created", game.getPlayers().size(), 4);
//        assertEquals("4 plays have not been created", game.getNumPlayers(), 4);
//
//    }
//
//
//    public void testPlayGame(){
//        game = new GameDev(2);
//    }
//
//    public void testNextPlayer(){
//        Collection<Player> p = new ArrayList<Player>();
//        p.add(new Player("p1"));
//        p.add(new Player("p2"));
//        p.add(new Player("p3"));
//        p.add(new Player("p4"));
//        game = new GameDev(p);
//
//        ArrayList<Player> players = game.getPlayers();
//
//        // forward direction
//        assertEquals("Game did not move forward from p1 to p2", game.nextPlayer(players.get(0)), players.get(1));
//        assertEquals("Game did not move forward from p2 to p3", game.nextPlayer(players.get(1)), players.get(2));
//        assertEquals("Game did not move forward from p3 to p4", game.nextPlayer(players.get(2)), players.get(3));
//        assertEquals("Game did not move forward from p4 to p1", game.nextPlayer(players.get(3)), players.get(0));
//
//        // backward direction
//        game.direction = GameDev.Direction.BACKWARD;
//        assertEquals("Game did not move backwards from p1 to p4", game.nextPlayer(players.get(0)), players.get(3));
//        assertEquals("Game did not move backwards from p4 to p3", game.nextPlayer(players.get(3)), players.get(2));
//        assertEquals("Game did not move backwards from p3 to p2", game.nextPlayer(players.get(2)), players.get(1));
//        assertEquals("Game did not move backwards from p2 to p1", game.nextPlayer(players.get(1)), players.get(0));
//    }
//
//    public void testDrawHands_playerGetCards() {
//        ArrayList<Player> players = game.getPlayers();
//
//        for(int j=0; j<7; j++) {
//            assertNotNull("There are not at least " + j + " cards in p1's hand", players.get(0).removeCard(1));
//            assertNotNull("There are not at least " + j + " cards in p2's hand", players.get(1).removeCard(1));
//        }
//
//        assertNull("There were exactly 7 cards in p1's hand", players.get(0).removeCard(1));
//        assertNull("There were exactly 7 cards in p2's hand", players.get(1).removeCard(1));
//    }
//
//    public void testGetPoints() {
//        Collection<Player> p = new ArrayList<Player>();
//        p.add(new Player("p1"));
//        p.add(new Player("p2"));
//        p.add(new Player("p3"));
//        game = new GameDev(p);
//
//        ArrayList<Player> players = game.getPlayers();
//        for (int i = 0; i < 21; i++) {
//            game.currentDeck.addCard(new Card(Value.ONE, Colour.RED,  Value.EIGHT, Colour.PINK));
//        }
//        game.drawHands();
//        for (int j = 0; j < 7; j++) {
//            players.get(0).removeCard(1);
//        }
//
//        // p1 = 0 cards, p2 = 7*RED ONE, p2 = 8*RED ONE
//        assertEquals("Total points were not summed correctly", 14, game.getPoints());
//    }
//
//}