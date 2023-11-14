import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextPlayerListener implements ActionListener {
    private Game game;
    public NextPlayerListener(Game game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.advanceCurrentPlayer();
    }
}