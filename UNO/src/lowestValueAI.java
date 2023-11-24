/**
 *This is the lowestValueAI class within the UNO game which represents an AI bot player on the UNO game.
 *
 * @author Brian Tran 101231003
 * @version 1.0
 */

public class lowestValueAI extends Player implements AIBot {
    static int ID = 0;

    /**
     * Constructor for lowestValueAI
     * Name is set as "lowestValueAI" plus an ID which is incremented with each AI
     */
    public lowestValueAI(){
        super("lowestValueAI"+ ID);
        ID += 1;
    }

    /**
     * Implementation of AI logic for selecting a card
     * Selects the highest scoring choice within the bots hand
     *
     * @param topCard the card to be played on top of
     * @return the card chosen to be played, null if no card is playable (DRAW CARD IF return is null)
     */
    public Card selectCard(Card topCard){
        Card selectedCard = null;
        int lowestPoint = 100;

        for(int i = 0; i< this.hand.size(); i++){
            if (hand.get(i).validWith(topCard)){
                if(lowestPoint > hand.get(i).getScore()) {
                    selectedCard = hand.get(i);
                    lowestPoint = selectedCard.getScore();
                }
            }
        }
        return selectedCard;
    }
}
