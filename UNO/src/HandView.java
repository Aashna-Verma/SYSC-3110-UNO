import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HandView implements View {
    Player player;
    private JPanel handPanel;
    private ActionListener listener; // Store the listener, because this object regularly deletes and creates new buttons

    public HandView(){
        handPanel = new JPanel(new GridLayout(1,0));
    }
    public JPanel getView(){
        return handPanel;
    }
    public void newHand() {

    }

    @Override
    public void update(Game game){
        player = game.getCurrentPlayer();
        handPanel.removeAll();
        for(int i = player.getNumCards()-1; i >= 0; i--) {
            JButton button = new JButton();
            handPanel.add(button);
            Card card = player.getHand().get(i);
            // Cards are 1 indexed (from requirements)
            button.setActionCommand((i + 1) + "");
            button.setIcon(card.getImageIcon(0.2));
            button.setPreferredSize(new Dimension(60, 150));
            button.addActionListener(listener);
        }
        //gameFrame.add(handPanel, BorderLayout.SOUTH);
        handPanel.updateUI();
    }
    /**
     * Add an action listener to this view for the card buttons
     * @param l the listener to be added to the card buttons
     */
    public void addListener(ActionListener l) {
        this.listener = l;
    }
}
