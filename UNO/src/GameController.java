import java.util.ArrayList;

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
    private NextPlayerListener nextListener;

    /**
     * constructor for GameController
     *
     * @param model the Game model
     * @param view the GameView view
     */
    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        handListener = new HandListener(model);
        drawListener = new DrawCardListener(model);
        nextListener = new NextPlayerListener(model);

        // Listen to the view for all events
        view.addHandListener(handListener);
        view.addDrawListener(drawListener);
        view.addNextListener(nextListener);
        // Add a view and update it to start the game
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
            players.add(new Human("Human " + (i + 1)));
        }
        int bots = GameView.getAI();
        for (int i = 0; i < bots; i++) {
            switch (i % 3) {
                case 0:
                    players.add(new firstValidAI());
                    break;
                case 1:
                    players.add(new highestValueAI());
                    break;
                case 2:
                    players.add(new lowestValueAI());
                    break;
            }
        }
        Game game = new Game(players);
        GameView view = new GameView(game);
        new GameController(game, view);
    }
}

