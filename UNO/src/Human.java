public class Human extends Player{

    public Human(String name){
        super(name);
    }

    public Colour chooseWildColour(Card topCard, Side side) {
        return Colour.valueOf(GameView.viewPickWildCard());
    }
}
