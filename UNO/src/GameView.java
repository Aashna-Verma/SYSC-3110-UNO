import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Objects;

public class GameView extends JFrame implements View {
    private JFrame gameFrame;
    private Game game;
    private HandView hand;
    private DrawCardView drawCardPanel;
    private TopCardView currCard;
    private StatusNextView statusNext;

    /**
     * Constructor creates initial game frame that will start by asking how many players
     */
    public GameView(Game game){
        this.game = game;
        //views
        hand = new HandView();
        drawCardPanel = new DrawCardView();
        currCard = new TopCardView();
        statusNext = new StatusNextView();

        //main frame
        gameFrame = new JFrame();

        gameFrame.setTitle("UNO");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1000, 650);

        gameFrame.add(currCard.getView(), BorderLayout.CENTER);
        gameFrame.add(statusNext.getView(), BorderLayout.WEST);
        gameFrame.add(drawCardPanel.getView(), BorderLayout.EAST);
        gameFrame.add(hand.getView(), BorderLayout.SOUTH);

        gameFrame.setVisible(true);
    }

    static public int viewPlayerCount(){
        Object[] options = {2, 3, 4};
        Object selectionObject = JOptionPane.showInputDialog(null, "How many players", "UNO", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return (int) selectionObject;
    }
    public String viewPickWildCard(){
        Object[] colours = {"RED", "BLUE", "GREEN", "YELLOW"};
        Object selectionObject = JOptionPane.showInputDialog(null, "Choose a colour:", "Wild Card Colour", JOptionPane.PLAIN_MESSAGE, null, colours, colours[0]);
        System.out.println(selectionObject.toString());
        return selectionObject.toString();
    }
    public void addListener (ActionListener l) {
        statusNext.addListener(l);
        hand.addListener(l);
        drawCardPanel.addListener(l);
    }
    @Override
    public void update(Game game) {
        currCard.update(game);
        hand.update(game);
        drawCardPanel.update(game);
    }

    public static void main(String[] args)  {
        /*
        Deck deck = new Deck();
        deck.populateDeck();
        Card card = new Card(Value.ONE, Colour.RED);
        Player player = new Player("Player 1");
        player.drawHand(deck);
        System.out.println(player.getHand().toString());

        Game game = new Game(2);
        GameView view = new GameView(game);
        game.addView(view);
        view.update(game);
        view.viewPickWildCard();

         */
    }
}
