import javax.swing.*;
import java.awt.*;

public class DrawCardView implements View{
    private JPanel drawCardPanel;
    private JButton drawCard;
    public void DrawCardView(){
        drawCardPanel = new JPanel(new BorderLayout());
        drawCard = new JButton("Draw Card");
        drawCard.setPreferredSize(new Dimension(150, 50));
        drawCardPanel.add(drawCard, BorderLayout.SOUTH);
    }
    public JPanel getView(){
        return drawCardPanel;
    }
    @Override
    public void update(Game game) {
        //draw out button when turn over TODO
    }
}
