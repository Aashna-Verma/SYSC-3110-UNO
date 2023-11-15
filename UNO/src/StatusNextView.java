import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatusNextView implements View {
    private JPanel statusNext;
    private JButton nextPlayer;
    private JTextArea gameStatus;
    private JLabel drawImg;

    public StatusNextView(){
        statusNext = new JPanel(new BorderLayout());
        gameStatus = new JTextArea();
        drawImg = new JLabel();

        nextPlayer = new JButton("Next Player");
        nextPlayer.setActionCommand("NextPlayer");
        nextPlayer.setPreferredSize(new Dimension(200, 50));
        nextPlayer.setEnabled(false);
        gameStatus.setLineWrap(true);
        gameStatus.setEditable(false);
        statusNext.add(gameStatus, BorderLayout.CENTER);
        statusNext.add(nextPlayer, BorderLayout.SOUTH);
    }
    public JPanel getView(){
        return statusNext;
    }
    @Override
    public void update(Game game) {
        nextPlayer.setEnabled(game.isRoundOver());
        gameStatus.setText(game.getStatusString());
        if(game.getStatusCard() != null){
            drawImg.setIcon(game.getStatusCard().getImageIcon(0.2));
            statusNext.add(drawImg, BorderLayout.NORTH);
        }else {
            statusNext.remove(drawImg);
        }
        statusNext.updateUI();
    }
    /**
     * Add an action listener to this view for the next player button
     * @param l the listener to be added to the next player button
     */
    public void addListener(ActionListener l) {
        nextPlayer.addActionListener(l);
    }
}
