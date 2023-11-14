import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DrawCardView implements View{
    private JPanel drawCardPanel;
    private JButton drawCard;
    private JTextArea scoreLabel;
    public DrawCardView(){
        drawCardPanel = new JPanel(new BorderLayout());
        drawCard = new JButton("Draw Card");
        drawCard.setActionCommand("DrawCard");
        drawCard.setPreferredSize(new Dimension(150, 50));
        scoreLabel = new JTextArea();
        scoreLabel.setBorder(BorderFactory.createTitledBorder("GAME SCORE"));
        drawCardPanel.add(scoreLabel, BorderLayout.CENTER);
        drawCardPanel.add(drawCard, BorderLayout.SOUTH);
    }
    public JPanel getView(){
        return drawCardPanel;
    }
    public void addListener(ActionListener l) {
        drawCard.addActionListener(l);
    }
    @Override
    public void update(Game game) {
        String text = "";
        for (Player p: game.getPlayers()) {
            text += p.getName() + ": " + p.getScore() + "\n";
        }
        scoreLabel.setText(text);
        if(game.isRoundOver()){
            drawCard.setEnabled(false);
        }
    }
}
