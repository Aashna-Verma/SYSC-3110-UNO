import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatusNextView implements View {
    private JPanel statusNext;
    private JButton nextPlayer;
    private JTextArea gameStatus;

    public StatusNextView(){
        statusNext = new JPanel(new BorderLayout());
        gameStatus = new JTextArea();

        nextPlayer = new JButton("Next Player");
        nextPlayer.setActionCommand("NextPlayer");
        nextPlayer.setPreferredSize(new Dimension(150, 50));
        gameStatus.setLineWrap(true);
        gameStatus.setEditable(false);
        gameStatus.setText("*THIS IS SAMPLE TEXT*\nStatus:\n Player X Drew a Red One");
        statusNext.add(gameStatus, BorderLayout.CENTER);
        statusNext.add(nextPlayer, BorderLayout.SOUTH);
    }
    public JPanel getView(){
        return statusNext;
    }
    @Override
    public void update(Game game) {
        //disable next player button beginning of turn TODO
    }
    /**
     * Add an action listener to this view for the next player button
     * @param l the listener to be added to the next player button
     */
    public void addListener(ActionListener l) {
        nextPlayer.addActionListener(l);
    }
}
