import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UndoRedoListener is the ActionListener for the Redo button
 *
 * @author Aashna Verma
 * @version 2.0
 */

public class RedoListener implements ActionListener {
    private Game game;
    private GameController controller;

    /**
     * Constructor for NextPlayerListener
     *
     * @param GameController the controller this listener belongs to
     */
    public RedoListener(GameController controller) { this.controller = controller; }
    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { game.redo(); }
}