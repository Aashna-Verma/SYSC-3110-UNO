import java.util.ArrayList;
import java.util.Random;

/**
 * GameController initialize the setup of the UNO game and connects the model and the view for and interactive experience through a GUI
 *
 * @author Angus Jull
 * @version 2.0
 */
public class GameController {
    private Game model;
    private GameView view;
    private HandListener handListener;
    private DrawCardListener drawListener;
    private StatusListener statusListener;

    /**
     * constructor for GameController
     *
     * @param model the Game model
     * @param view the GameView view
     */
    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;

        handListener = new HandListener(this);
        drawListener = new DrawCardListener(this);
        statusListener = new StatusListener(this);

        // Listen to the view for all events
        view.addHandListener(handListener);
        view.addDrawListener(drawListener);
        view.addStatusListener(statusListener);
        // Set the model of all listeners and this object
        this.setModel(model);
    }

    /**
     * Sets the model for the controller
     * @param model the model for the game
     */
    public void setModel(Game model) {
        this.model = model;
        handListener.setModel(model);
        drawListener.setModel(model);
        statusListener.setModel(model);

        model.addView(view);
    }

    /**
     * The main function that runs the game UNO
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        // Add the number of players chosen by the human
        int humans = GameView.getHumans();
        for (int i = 0; i < humans; i++) {
            players.add(new Human());
        }
        int bots = GameView.getAI();
        Random random = new Random();
        for (int i = 0; i < bots; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    players.add(new FirstValidAI());
                    break;
                case 1:
                    players.add(new HighestValueAI());
                    break;
                case 2:
                    players.add(new LowestValueAI());
                    break;
            }
        }
        Game game = new Game(players);
        GameView view = new GameView(game);
        new GameController(game, view);
    }
}

