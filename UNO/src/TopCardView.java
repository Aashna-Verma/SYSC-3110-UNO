import javax.swing.*;

public class TopCardView implements View{
    private JLabel currCard;
    public TopCardView(){
        currCard = new JLabel();
    }
    public JLabel getView(){
        return currCard;
    }
    @Override
    public void update(Game game) {
        //Updates Current player title
        currCard.setBorder(BorderFactory.createTitledBorder(game.getCurrentPlayer().getName()));
        //Updates top card of pile
        currCard.setText(game.getTopCard().toString());
        currCard.setIcon(game.getTopCard().getImageIcon(0.6));
    }
}
