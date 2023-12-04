import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UndoRedoListener is the ActionListener for the Undo and Redo button
 *
 * @author Aashna Verma
 * @version 2.0
 */

public class UndoListener implements ActionListener {
    private Game game;
    private GameController controller;
    /**
     * Constructor for NextPlayerListener
     *
     * @param GameController the Game controller
     */
    public UndoListener(GameController controller) { this.controller = controller; }
    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { game.undo(); }
}