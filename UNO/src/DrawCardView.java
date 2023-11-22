import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * DrawCaardView the general JFrame of the UNO game holding the inner view components
 *
 * @author Darren Wallace
 * @version 1.0
 */
public class DrawCardView implements View {
    private JPanel drawCardPanel;
    private JButton drawCard;
    private JTextArea scoreLabel;

    /**
     * Constructor for Draw Card view
     */
    public DrawCardView(){
        drawCardPanel = new JPanel(new BorderLayout());
        drawCard = new JButton("Draw Card");
        drawCard.setActionCommand("DrawCard");
        drawCard.setPreferredSize(new Dimension(150, 50));
        scoreLabel = new JTextArea();
        scoreLabel.setEditable(false);
        scoreLabel.setBorder(BorderFactory.createTitledBorder("GAME SCORE"));
        drawCardPanel.add(scoreLabel, BorderLayout.CENTER);
        drawCardPanel.add(drawCard, BorderLayout.SOUTH);
    }

    /**
     * Gets the DrawnCardPanel
     * @return the panel holding the drawn card
     */
    public JPanel getView(){
        return drawCardPanel;
    }

    /**
     * Add ActionListener to the drawCard panel
     * @param l the ActionListener
     */
    public void addListener(ActionListener l) {
        drawCard.addActionListener(l);
    }

    /**
     * Implementation of update for the DrawCardView updating the drawn card
     * @param game the model of the game
     */
    @Override
    public void update(Game game) {
        String text = "";
        for (Player p: game.getPlayers()) {
            text += p.getName() + ": " + p.getScore() + "\n";
        }
        scoreLabel.setText(text);
        drawCard.setEnabled(!game.isRoundOver());
    }
}
