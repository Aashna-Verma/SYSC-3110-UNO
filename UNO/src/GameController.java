public class GameController {
    private Game model;
    private GameView view;

    public GameController() {
        int numPlayers = GameView.viewPlayerCount();
        model = new Game(numPlayers);
        view = new GameView(model);
        model.addView(view);
    }
    public static void main(String[] args) {
        new GameController();
    }
}