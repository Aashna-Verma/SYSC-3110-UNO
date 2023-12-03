/**
 * Real human player of the game of UNO Flip. Inherits the Player class
 *
 * @author  Brian Tran - Modified for MVC
 * @author  Aashna Verma - Modified for Flip
 * @version 2.0
 */

public class Human extends Player{

    /**
     * Constructor for Human Class
     * @param name the name of the player
     */
    public Human(){
        super("Human" + ID);
        ID += 1;
    }

    /**
     *
     * @param topCard the card to  be played on
     * @return the wild colour chosen by the player
     */
    @Override
    public Colour chooseWildColour(Card topCard) {
        return Colour.valueOf(GameView.viewPickWildCard());
    }
}
