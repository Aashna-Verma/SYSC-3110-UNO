import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private ArrayList<JButton> buttons;
    private JPanel buttonPanel;
    //initialize model

    /**
     * Constructor creates initial game frame that will start by asking how many players
     */
    public GameView(){
        super("UNO");
        buttons = new ArrayList<>();
        buttonPanel = new JPanel( new GridLayout(3, 3) );
        this.setLayout(new GridLayout());
        this.setSize(1000, 650);

        viewPlayerCount();

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int viewPlayerCount(){
        Object[] options = {2, 3, 4};
        Object selectionObject = JOptionPane.showInputDialog(null, "How many players", "UNO", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return (int) selectionObject;
    }

    public void updateGameView(Player player){
        for(int i = player.getNumCards()-1; i >= 0; i--){
            JButton button = new JButton();
            button.setText(player.getHand().get(i).toString());
            button.setBounds(0, i*50, 100, 50);
            buttonPanel.add( button );
            buttons.add(button);
        }
        add(buttonPanel, BorderLayout.LINE_START);
    }

    public static void main(String[] args)  {
        Deck deck = new Deck();
        deck.populateDeck();
        Player player = new Player("tester");
        player.drawHand(deck);
        GameView view = new GameView();
        view.updateGameView(player);
    }
}
