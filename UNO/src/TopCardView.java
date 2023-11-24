import javax.swing.*;

/**
 * TopCard View top card for the UNO Flip
 *
 * @author Darren Wallace
 * @version 1.0
 */
public class TopCardView implements View{
    private JLabel currCard;

    /**
     * Consturctor for TopCardView
     */
    public TopCardView(){
        currCard = new JLabel();
    }

    /**
     * getter for the top card view
     * @return current card JLabel
     */
    public JLabel getView(){
        return currCard;
    }

    /**
     * update method implementation for TopCard View
     * @param game the game providing the view with the top card
     */
    @Override
    public void update(Game game) {
        //Updates Current player title
        currCard.setBorder(BorderFactory.createTitledBorder(game.getCurrentPlayer().getName()));
        //Updates top card of pile
        currCard.setText(game.getTopCard().toString());
        currCard.setIcon(game.getTopCard().getImageIcon(0.5));
    }
}
