import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * NextPlayerListener is the ActionListener for the Next Player button
 *
 * @author Angus Jull
 * @version 2.0
 */

public class ReplayListener implements ActionListener {
    private Game game;
    private GameController controller;

    /**
     * Constructor for NextPlayerListener
     *
     * @param game the Game model
     */
    public ReplayListener(GameController controller) { this.controller = controller; }

    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { game.replay(); }
}