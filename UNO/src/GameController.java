public class GameController {
    private Game model;
    private GameView view;
    private HandListener handListener;
    private DrawCardListener drawListener;
    private NextPlayerListener nextListener;

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
    public static void main(String[] args) {
        Game game = new Game(GameView.viewPlayerCount());
        GameView view = new GameView(game);
        new GameController(game, view);
    }
}

