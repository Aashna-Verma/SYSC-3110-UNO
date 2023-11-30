import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * NextPlayerListener is the ActionListener for the Next Player button
 *
 * @author Angus Jull
 * @version 2.0
 */

public class NextPlayerListener implements ActionListener {
    private Game game;
    private GameView gameView;

    /**
     * Constructor for NextPlayerListener
     *
     * @param game the Game model
     */
    public NextPlayerListener(Game game) { this.game = game; }

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
        else if (Objects.equals(e.getActionCommand(), "Save")) {
            try {
                this.game.serialize();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (Objects.equals(e.getActionCommand(), "Load")) {
            gameView = game.getGameView();
            try {
                this.game = Game.deserialize();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            game.addView(gameView);
            game.getGameView().update(game);
        }
    }
}