import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * StatusListener is the ActionListener for the Next Player button
 *
 * @author Angus Jull
 * @version 2.0
 */

public class StatusListener implements ActionListener, ControllerListener {
    private Game game;
    private GameController controller;

    /**
     * Constructor for StatusListener
     *
     * @param controller the controller that owns this listener
     */
    public StatusListener(GameController controller) { this.controller = controller; }
    public void setModel(Game game) { this.game = game; }
    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "NextPlayer")) {
            game.advanceCurrentPlayer();
        }
        else if (Objects.equals(e.getActionCommand(), "Undo")) {
            game.undo();
        }
        else if (Objects.equals(e.getActionCommand(), "Redo")) {
            game.redo();
        }
        else if (Objects.equals(e.getActionCommand(), "Save")) {
            try {
                game.serialize();
                GameView.displaySavedGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (Objects.equals(e.getActionCommand(), "Load")) {
            try {
                game = Game.deserialize();
                controller.setModel(game);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        else if (Objects.equals(e.getActionCommand(), "Replay")) {
            game.replay();
        }
    }
}