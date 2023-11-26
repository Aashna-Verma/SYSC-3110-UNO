import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Gameview the general JFrame of the UNO game holding the inner view components
 *
 * @author Darren Wallace
 * @version 1.0
 */
public class GameView extends JFrame implements View {
    private static JFrame gameFrame;
    private static Game game;
    private HandView hand;
    private DrawCardView drawCardPanel;
    private TopCardView currCard;
    private StatusNextView statusNext;

    /**
     * Constructor creates initial game frame that will start by asking how many players
     * @param game
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

    /**
     * Generates a popup to select number of human players
     * @return the number of players selected
     */
    static public int getHumans(){
        Object[] options = {2, 3, 4, 5, 6};
        Object selectionObject = JOptionPane.showInputDialog(null, "How many human players", "UNO", JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        if (selectionObject == null){
            System.exit(0);
        }
        return (int) selectionObject;
    }

    /**
     * Generates a popup to select number of AI players
     * @return the number of AI players
     */
    static public int getAI(){
        Object[] options = {0, 1, 2, 3, 4, 5, 6};
        Object selectionObject = JOptionPane.showInputDialog(null, "How many AI players", "UNO", JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        if (selectionObject == null){
            System.exit(0);
        }
        return (int) selectionObject;
    }

    /**
     * Generates a popup to select a colour for the wild card
     * @return the colour selected
     */
    static public String viewPickWildCard(){
        Object[] colours;
        if (Card.getSide() == Side.LIGHT) {
            colours = new Object[]{"RED", "BLUE", "GREEN", "YELLOW"};
        } else{
            colours = new Object[]{"PINK", "TEAL", "PURPLE", "ORANGE"};
        }
        Object selectionObject = colours[JOptionPane.showOptionDialog(null, "Choose a Colour", "Wild Card Colour", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, colours , colours[0])];
        if (selectionObject == null) { return null; }
        return selectionObject.toString();
    }

    /**
     * Generates the popup after a player wins a round
     * @param p the player who won the round
     */
    public void displayRoundWinPopup(Player p) {
        JOptionPane.showMessageDialog(null,"Round Win: "+ p.getName() + "\nStarting Next Round");
    }

    /**
     * Generates the popup after a player wins the game
     * @param p the player who won the game
     */
    public void displayWinPopup(Player p) {
        JOptionPane.showMessageDialog(null,"WINNER: "+ p.getName());
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Add actionlistener for statusNext
     * @param l Actionalistener
     */
    public void addNextListener (ActionListener l) {
        statusNext.addListener(l);
    }

    /**
     * Add actionlistener for drawCardPanel
     * @param l Actionalistener
     */
    public void addDrawListener (ActionListener l) {
        drawCardPanel.addListener(l);
    }

    /**
     * Add actionlistener for hand
     * @param l Actionalistener
     */
    public void addHandListener (ActionListener l) {
        hand.addListener(l);
    }

    /**
     * Update implementation for the Game view, updates all the inner views
     * @param game the model of the game
     */
    @Override
    public void update(Game game) {
        currCard.update(game);
        hand.update(game);
        drawCardPanel.update(game);
        statusNext.update(game);
    }

}
