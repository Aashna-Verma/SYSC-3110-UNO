import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Objects;

public class GameView extends JFrame {
    private JFrame gameFrame;
    private JPanel handPanel;
    private JPanel drawCardPanel;
    private JButton drawCard;
    private JPanel statusNext;
    private JButton nextPlayer;
    private JTextArea gameStatus;
    private JLabel currCard;

    //initialize model

    /**
     * Constructor creates initial game frame that will start by asking how many players
     */
    public GameView(){
        viewPlayerCount();

        gameFrame = new JFrame();
        handPanel = new JPanel(new GridLayout(1,0));
        drawCardPanel = new JPanel(new BorderLayout());
        statusNext = new JPanel(new BorderLayout());
        gameStatus = new JTextArea();
        currCard = new JLabel();

        gameFrame.setTitle("UNO");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1000, 650);

        drawCard = new JButton("Draw Card");
        drawCard.setPreferredSize(new Dimension(150, 50));
        drawCardPanel.add(drawCard, BorderLayout.SOUTH);

        nextPlayer = new JButton("Next Player");
        nextPlayer.setPreferredSize(new Dimension(150, 50));
        gameStatus.setLineWrap(true);
        gameStatus.setEditable(false);
        gameStatus.setText("*THIS IS SAMPLE TEXT*\nStatus:\n Player X Drew a Red One");
        statusNext.add(gameStatus, BorderLayout.CENTER);
        statusNext.add(nextPlayer, BorderLayout.SOUTH);

        gameFrame.add(currCard, BorderLayout.CENTER);
        gameFrame.add(statusNext, BorderLayout.WEST);
        gameFrame.add(drawCardPanel, BorderLayout.EAST);
        gameFrame.add(handPanel, BorderLayout.SOUTH);

        gameFrame.setVisible(true);
    }

    public int viewPlayerCount(){
        Object[] options = {2, 3, 4};
        Object selectionObject = JOptionPane.showInputDialog(null, "How many players", "UNO", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return (int) selectionObject;
    }
    public String viewPickWildCard(){
        Object[] colours = {"RED", "BLUE", "GREEN", "YELLOW"};
        Object selectionObject = JOptionPane.showInputDialog(null, "Choose a colour:", "Wild Card Colour", JOptionPane.PLAIN_MESSAGE, null, colours, colours[0]);
        System.out.println(selectionObject.toString());
        return selectionObject.toString();
    }

    public void updateGameView(Player player, Card topCard){
        //Updates Current player title
        currCard.setBorder(BorderFactory.createTitledBorder(player.getName()));
        //Updates top card of pile
        //currCard.setText(topCard.toString());
        currCard.setIcon(topCard.getImageIcon(0.8));
        //Disable skipping turn before playing
        nextPlayer.setEnabled(false);

        updateHandView(player);

        updateStatusText();
    }
    private void updateHandView(Player player){
        for(int i = player.getNumCards()-1; i >= 0; i--){
            JButton button = new JButton();
            handPanel.add(button);
            Card card = player.getHand().get(i);
            //button.setText(card.toString());
            button.setIcon(card.getImageIcon(0.25));
            button.setPreferredSize(new Dimension(60, 150));
        }
        gameFrame.add(handPanel, BorderLayout.SOUTH);
    }
    private void updateStatusText(){

    }

    public static void main(String[] args)  {
        Deck deck = new Deck();
        deck.populateDeck();
        Card card = new Card(Value.ONE, Colour.RED);
        Player player = new Player("Player 1");
        player.drawHand(deck);
        GameView view = new GameView();
        view.updateGameView(player, card);
        view.viewPickWildCard();
    }
}
