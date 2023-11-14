import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawCardListener implements ActionListener {
    private Game game;
    public DrawCardListener(Game game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.drawCard();
    }
}