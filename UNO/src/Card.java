/**
 * The Card class
 *
 * @author  Aashna
 * @version 1.0
 */
public class Card {
    private static Side side;
    private final Value LIGHT_VALUE;
    private final Value DARK_VALUE;
    private final Colour LIGHT_COLOUR;
    private final Colour DARK_COLOUR;

    /**
     * Constructor for Card
     *
     * @param l_val the Value of the light side of the card
     * @param d_val the Value of the dark side of the card
     * @param l_col the Colour of the light side of the card
     * @param d_col the Color of the dark side of the card
     */
    public Card(Value l_val, Value d_val, Colour l_col, Colour d_col){
        this.LIGHT_VALUE = l_val;
        this.DARK_VALUE = d_val;
        this.LIGHT_COLOUR = l_col;
        this.DARK_COLOUR = d_col;
    }

    /**
     * Flips the Side of the card
     *
     * @return new Side of the card
     */
    public static Side flip(){
        side = (side == Side.LIGHT) ? Side.DARK : Side.LIGHT;
        return getSide();
    }

    /**
     * Shows the current Side of the Card
     *
     * @return the current Side of the cards
     */
    public static Side getSide(){
        return side;
    }

    /**
     * Shows the current Value of the Card
     *
     * @return the current Value of the cards
     */
    public Value getValue(){
        return (getSide() == Side.LIGHT) ? this.LIGHT_VALUE : this.DARK_VALUE;
    }

    /**
     * Shows the current Colour of the Card
     *
     * @return the current Colour of the cards
     */
    public Colour getColour(){
        return (getSide() == Side.LIGHT) ? this.LIGHT_COLOUR : this.DARK_COLOUR;
    }

    /**
     * Shows the current Colour and Value of the Card
     *
     * @return a String of the current Colour and Value of the card
     */
    @Override
    public String toString(){
        return this.getColour().toString() + " " + this.getValue().toString();
    }
}
