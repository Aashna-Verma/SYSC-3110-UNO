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
        super("firstValidAI "+ ID);
        ID += 1;
    }

    /**
     * Implementation of AI logic for selecting a card
     * Selects the first valid choice within the bots hand
     *
     * @param topCard the card to be played on
     * @return the card chosen to be played, -1 if no card is playable (DRAW CARD IF return is -1)
     */
    public int selectCard(Card topCard){
        for(int i = 0; i< this.hand.size(); i++){
            if (topCard.validWith(hand.get(i))){
                return i + 1;
            }
        }
        return -1;
    }
}
