import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * HandListener is the ActionListener for all cards in  players hand
 *
 * @author Angus Jull
 * @version 2.0
 */

public class HandListener implements ActionListener, ControllerListener {
    private Game game;
    private GameController controller;

    /**
     * Constructor for HandListener
     *
     * @param controller the Game controller
     */
    public HandListener(GameController controller) { this.controller = controller; }
    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = Integer.parseInt(e.getActionCommand());
        game.playCard(index);
    }
}