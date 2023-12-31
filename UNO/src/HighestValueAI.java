/**
 *This is the HighestValueAI class within the UNO game which represents an AI bot player on the UNO game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

public class HighestValueAI extends Player implements AIBot {
    private static int id = 0;

    /**
     * Constructor for firstValidAI
     * Name is set as "firstValidAI" plus an ID which is incremented with each AI
     */

    public HighestValueAI(){
        super("HighestValueAI"+ id);
        id += 1;
    }

    /**
     * Implementation of AI logic for selecting a card
     * Selects the highest scoring choice within the bots hand
     *
     * @param topCard the card to be played on top of
     * @return the card chosen to be played, -1 if no card is playable (DRAW CARD IF return is -1)
     */
    public int selectCard(Card topCard){
        int selectedCard = -1;
        int highestPoint = 0;

        for(int i = 0; i < this.hand.size(); i++){
            if (topCard.validWith(hand.get(i))){
                if(highestPoint < hand.get(i).getScore()) {
                    selectedCard = i + 1;
                    highestPoint = hand.get(i).getScore();
                }
            }
        }
        return selectedCard;
    }
}
