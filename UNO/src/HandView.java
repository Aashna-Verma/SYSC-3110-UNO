import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;

/**
 * HandView the view holding the players cards within their hand for game of UNO Flip
 *
 * @author Darren Wallace
 * @version 1.0
 */
public class HandView implements View {
    private JScrollPane scrollPane;
    private JPanel handPanel;
    private ActionListener listener; // Store the listener, because this object regularly deletes and creates new buttons
    private ArrayList<JButton> cardButtons;

    /**
     * COnsturctor for HandView
     */
    public HandView(){
        handPanel = new JPanel(new GridLayout(1,0));
        scrollPane = new JScrollPane(handPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardButtons = new ArrayList<>();
    }

    /**
     * Get the JScrollPannl for the players hand
     * @return the panel holding the players cards
     */
    public JScrollPane getView(){
        return scrollPane;
    }

    /**
     * the update implementation for Handview
     * @param game model that provides the view with the cards within the players hand
     */
    @Override
    public void update(Game game){
        Player player = game.getCurrentPlayer();
        handPanel.removeAll();
        cardButtons.clear();
        for(int i = player.getNumCards()-1; i >= 0; i--) {
            JButton button = new JButton();
            cardButtons.add(button);
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
        for (JButton b: cardButtons) {
            b.setEnabled(!game.isRoundOver());
        }
    }
    /**
     * Add an action listener to this view for the card buttons
     * @param l the listener to be added to the card buttons
     */
    public void addListener(ActionListener l) {
        this.listener = l;
    }
}
