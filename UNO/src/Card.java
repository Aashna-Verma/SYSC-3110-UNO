/**
 * The Card class within the UNO game which represents a card in the UNO game.
 * Each card has a value and a colour
 *
 * @author  Aashna Verma 101225434
 * @version 1.0
 */
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
