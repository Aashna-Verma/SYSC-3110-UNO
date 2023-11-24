/**
 *This is the firstValidAI class within the UNO game which represents an AI bot player on the UNO game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

public class firstValidAI extends Player implements AIBot {
    static int ID = 0;

    /**
     * Constructor for firstValidAI
     * Name is set as "firstValidAI" plus an ID which is incremented with each AI
     */
    public firstValidAI(){
        super("firstValidAI"+ ID);
        ID += 1;
    }

    /**
     * Implementation of AI logic for selecting a card
     * Selects the first valid choice within the bots hand
     *
     * @param topCard the card to be played on top of
     * @return the card chosen to be played, null if no card is playable (DRAW CARD IF return is null)
     */
    public Card selectCard(Card topCard){
        for(int i = 0; i< this.hand.size(); i++){
            if (hand.get(i).validWith(topCard)){
                return this.removeCard(i);
            }
        }
        return null;
    }
}
