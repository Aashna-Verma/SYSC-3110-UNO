import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplayListener implements ActionListener {
    private Game game;

    /**
     * Constructor for ReplayListener
     *
     * @param game the Game model
     */
    public ReplayListener(Game game) { this.game = game; }

    /**
     * The actionPerformed when an event occurs
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) { game.replay(); }
}
