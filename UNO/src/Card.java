/**
 * The Card class within the UNO game which represents a card in the UNO game.
 * Each card has a value and a colour
 *
 * @author  Aashna Verma 101225434
 * @version 1.0
 */
import java.util.HashMap;

public class Card {
    private final Value VALUE;
    private final Colour COLOUR;

    /**
     * Constructor for Card
     *
     * @param value the Value of the card
     * @param colour the Value of the card
     */
    public Card(Value value, Colour colour){
        this.VALUE = value;
        this.COLOUR = colour;
    }

    /**
     * Shows the Value of the Card
     *
     * @return the Value of the card
     */
    public Value getValue(){
        return this.VALUE;
    }

    /**
     * Shows the Colour of the Card
     *
     * @return the Colour of the card
     */
    public Colour getColour(){
        return this.COLOUR;
    }

    /**
     * Gets the score related to the card
     *
     * @return an Int of the cards score
     */
    public int getScore(){
        return switch (getValue()) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case DRAW_ONE -> 10;
            case WILD -> 40;
            case WILD_DRAW_TWO -> 50;
            default -> 20;
        };
    }

    /**
     * Shows the Colour and Value of the Card
     *
     * @return a String of the Colour and Value of the card
     */
    @Override
    public String toString(){
        return this.getColour().toString() + " " + this.getValue().toString();
    }

    /**
     * Checks if compareTo could be played on this card or the other way around
     * @param compareTo the card to be compared with
     * @return true if playing compareTo on this would be a valid move, false otherwise
     */
    public boolean validWith(Card compareTo) {
        if (compareTo == null) {
            return false;
        }
        // A wild can be played on anything
        else if (compareTo.getColour() == Colour.WILD || this.getColour() == Colour.WILD) {
            return true;
        }
        else if (compareTo.getValue() == this.getValue()) {
            return true;
        }
        else if (compareTo.getColour() == this.getColour()) {
            return true;
        }
        return false;
    }
}
