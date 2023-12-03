import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DrawCardListener is the ActionListener for Draw Card button
 *
 * @author Angus Jull
 * @version 2.0
 */

public class DrawCardListener implements ActionListener {
    private Game game;
    private GameController controller;

    /**
     * Constructor for DrawCardListener
     *
     * @param game the Game model
     */
    public DrawCardListener(GameController controller) { this.controller = controller; }
    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        game.drawCard();
    }
}