import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;

public class HandView implements View {
    private JScrollPane scrollPane;
    private JPanel handPanel;
    private ActionListener listener; // Store the listener, because this object regularly deletes and creates new buttons
    private ArrayList<JButton> cardButtons;

    public HandView(){
        handPanel = new JPanel(new GridLayout(1,0));
        scrollPane = new JScrollPane(handPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardButtons = new ArrayList<>();
    }
    public JScrollPane getView(){
        return scrollPane;
    }

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
