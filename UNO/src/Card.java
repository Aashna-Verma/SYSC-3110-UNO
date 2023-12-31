import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Card class within the UNO game which represents a card in the UNO game.
 * Each card has a dark and light side, each with their own value and colour
 * @author  Aashna Verma 101225434 - modified for flip
 * @version 3.0
 */

public class Card implements Serializable {
    private final Value LIGHT_VALUE;
    private final Colour LIGHT_COLOUR;
    private final Value DARK_VALUE;
    private final Colour DARK_COLOUR;
    private transient ImageIcon LIGHT_ICON_IMAGE;
    private transient ImageIcon DARK_ICON_IMAGE;
    private Colour wildColour;
    private static Side side;


    /**
     * Constructor for card
     * @param light_value value of the light side of the card
     * @param light_colour color of the light side of the card
     * @param dark_value value of the dark side of the card
     * @param dark_colour colour of the dark side of the card
     */
    public Card(Value light_value, Colour light_colour, Value dark_value, Colour dark_colour){
        this.LIGHT_VALUE = light_value;
        this.LIGHT_COLOUR = light_colour;
        this.DARK_VALUE = dark_value;
        this.DARK_COLOUR = dark_colour;
        this.side = Side.LIGHT;
        this.LIGHT_ICON_IMAGE = getImageIconResource(Side.LIGHT);
        this.DARK_ICON_IMAGE = getImageIconResource(Side.DARK);
    }

    /**
     * Creates an image icon for the image a card
     * @param s the side that the game is on
     * @return the ImageIcon
     */
    private ImageIcon getImageIconResource(Side s){
        Value v = Side.LIGHT == s ? LIGHT_VALUE : DARK_VALUE;
        Colour c = Side.LIGHT == s ? LIGHT_COLOUR : DARK_COLOUR;
        StringBuilder str = new StringBuilder();
        if (c == Colour.WILD){
            str.append("WILD");
            str.append(Side.LIGHT == s ? "" : "_DARK");
        } else{
            str.append(c.toString());
        }
        return new ImageIcon(Card.class.getResource( "cardImgs/" + v.toString() + "_" + str + ".png"));
    }

    /**
     * Sets the Colour of this Card's wildColour
     * @param c the Colour chosen for the wildColour
     */
    public void setWildColour(Colour c){
        wildColour = c;
    }

    /**
     * Getter for this Card's wildColour
     * @return the chosen wildColor
     */
    public Colour getWildColour(){
        return this.wildColour;
    }

    /**
     * Shows the Value of the Card
     * @return the Value of the card
     */
    public Value getValue(){
        return Side.LIGHT == side ? LIGHT_VALUE : DARK_VALUE;
    }

    /**
     * Shows the Colour of the Card
     * @return the Colour of the card
     */
    public Colour getColour(){
        return Side.LIGHT == side ? LIGHT_COLOUR : DARK_COLOUR;
    }

    /**
     * Getter for side of the game
     * @return the side the game is on
     */
    public static Side getSide(){
        return side;
    }

    /**
     * flips the card's side
     */
    public static void flipSide(){
        side = Side.LIGHT == side ? Side.DARK : Side.LIGHT;
    }

    /**
     * Gets the score related to the card
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
            case SKIP_ALL -> 30;
            case WILD -> 40;
            case WILD_DRAW_TWO -> 50;
            case WILD_DRAW_COLOUR -> 60;
            default -> 20;
        };
    }

    /**
     * Shows the Colour and Value of the Card
     * @return a String of the Colour and Value of the card
     */
    @Override
    public String toString(){
        return this.getColour().toString() + " " + this.getValue().toString()  + ((wildColour != null) ? " " + wildColour.toString() : "");
    }

    /**
     * Get scaled image of the card
     * @param scale float of scaling value
     * @return an ImageIcon of the card
     */
    public ImageIcon getImageIcon(double scale){
        return new ImageIcon((Side.LIGHT == side ? LIGHT_ICON_IMAGE : DARK_ICON_IMAGE).getImage().getScaledInstance((int)(scale * 500), (int)(scale * 750), Image.SCALE_SMOOTH));
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
        else if (this.getWildColour() == null && (compareTo.getColour() == Colour.WILD || this.getColour() == Colour.WILD)) {
            return true;
        }
        else if (compareTo.getValue() == this.getValue()) {
            return true;
        }
        else if (compareTo.getColour() == this.getColour() || compareTo.getColour() == this.getWildColour()) {
            return true;
        }
        return false;
    }
    @Serial
    private void readObject(ObjectInputStream istream) throws ClassNotFoundException, IOException {
        istream.defaultReadObject();
        this.LIGHT_ICON_IMAGE = getImageIconResource(Side.LIGHT);
        this.DARK_ICON_IMAGE = getImageIconResource(Side.DARK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return LIGHT_VALUE == card.LIGHT_VALUE && LIGHT_COLOUR == card.LIGHT_COLOUR && DARK_VALUE == card.DARK_VALUE && DARK_COLOUR == card.DARK_COLOUR && wildColour == card.wildColour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(LIGHT_VALUE, LIGHT_COLOUR, DARK_VALUE, DARK_COLOUR, wildColour);
    }
}
