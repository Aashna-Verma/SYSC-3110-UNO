import javax.swing.*;
import java.awt.*;

public class HandView implements View{
    Player player;
    private JPanel handPanel;

    public HandView(){
        handPanel = new JPanel(new GridLayout(1,0));
    }
    public JPanel getView(){
        return handPanel;
    }
    @Override
    public void update(Game game){
        player = game.getCurrentPlayer();
        for(int i = player.getNumCards()-1; i >= 0; i--){
            JButton button = new JButton();
            handPanel.add(button);
            Card card = player.getHand().get(i);
            button.setActionCommand(i+"");
            button.setIcon(card.getImageIcon(0.2));
            button.setPreferredSize(new Dimension(60, 150));
        }
        //gameFrame.add(handPanel, BorderLayout.SOUTH);
    }
}
