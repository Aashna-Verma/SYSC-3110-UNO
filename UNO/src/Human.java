public class Human extends Player{

    public Human(String name){
        super(name);
    }

    protected Card removeCard(int i){
        try {
            //card 0 is to pick up, however in the hand attribute 0 is the players' first card
            return this.hand.remove(i - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid card index\n");
            return null;
        }
    }
}
