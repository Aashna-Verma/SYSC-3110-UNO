import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * The status panel for UNO Flip
 *
 * @author Darren Wallace
 * @version 1.0
 */
public class StatusNextView implements View {
    private JPanel statusNext;
    private JPanel buttonPanel;
    private JPanel statusPanel;
    private JButton nextPlayer;
    private JButton undo;
    private JButton redo;
    private JButton save;
    private JButton load;
    private JTextArea gameStatus;
    private JLabel drawImg;

    /**
     * Constructor for class StatusNextView
     */
    public StatusNextView(){
        statusNext = new JPanel(new BorderLayout());
        gameStatus = new JTextArea();
        gameStatus.setPreferredSize(new Dimension(200, 300));
        drawImg = new JLabel();

        nextPlayer = new JButton("Next Player");
        nextPlayer.setActionCommand("NextPlayer");
        nextPlayer.setPreferredSize(new Dimension(200, 50));
        nextPlayer.setEnabled(false);

        undo = new JButton("Undo");
        undo.setActionCommand("Undo");
        undo.setPreferredSize(new Dimension(90, 50));

        redo = new JButton("Redo");
        redo.setActionCommand("Redo");
        redo.setPreferredSize(new Dimension(90, 50));

        save = new JButton("Save");
        save.setActionCommand("Save");
        save.setPreferredSize(new Dimension(90, 50));

        load = new JButton("Load");
        load.setActionCommand("Load");
        load.setPreferredSize(new Dimension(90, 50));

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(undo);
        buttonPanel.add(redo);
        buttonPanel.add(save);
        buttonPanel.add(load);
        buttonPanel.add(nextPlayer);

        gameStatus.setLineWrap(true);
        gameStatus.setEditable(false);

        statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(gameStatus, BorderLayout.CENTER);

        statusNext.add(statusPanel, BorderLayout.NORTH);
        statusNext.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Getter method for the status Panel;
     * @return the status JPanel
     */
    public JPanel getView(){
        return statusNext;
    }

    /**
     * update method implementation to update the status view
     * @param game the game model that manipulates the game data
     */
    @Override
    public void update(Game game) {
        nextPlayer.setEnabled(game.isRoundOver());
        gameStatus.setText(game.getStatusString());
        if(game.getStatusCard() != null){
            gameStatus.setPreferredSize(new Dimension(200, 150));
            drawImg.setIcon(game.getStatusCard().getImageIcon(0.2));
            statusPanel.add(drawImg, BorderLayout.SOUTH);
        }else {
            gameStatus.setPreferredSize(new Dimension(200, 300));
            statusPanel.remove(drawImg);
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

    /**
     * Add an action listener to this view for the next player button
     * @param l the listener to be added to the next player button
     */
    public void addURListener(ActionListener l) {
        undo.addActionListener(l);
        redo.addActionListener(l);
    }
}
