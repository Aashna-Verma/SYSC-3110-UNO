import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HandListener implements ActionListener {
    private Game game;
    public HandListener(Game game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = Integer.parseInt(e.getActionCommand());
        game.playCard(index);
    }
}