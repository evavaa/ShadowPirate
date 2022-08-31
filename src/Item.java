/**
 * A class representing the item. This is the superclass for potion, elixir and sword.
 */
public abstract class Item extends GameEntity{
    private boolean isPickedUp = false;
    private boolean inInventory = false;
    private final static int ICON_X = 10;
    private final static int ICON_Y = 45;

    /**
     * Creates an item at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of the item
     * @param posY the y-coordinate of the item
     */
    public Item(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * An abstract method that update the state of the item and its interaction with the sailor.
     * @param sailor the player
     */
    public abstract void update(Sailor sailor);

    /**
     * Checks if the item is picked up by the sailor.
     * @return the boolean indicating whether the item is picked up by the sailor
     */
    public boolean isPickedUp() {
        return isPickedUp;
    }

    protected void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    /**
     * Checks if the item is in the inventory.
     * @return the boolean indicating whether the item is in the inventory
     */
    public boolean isInInventory() {
        return inInventory;
    }

    /**
     * Sets whether the item is in the inventory.
     * @param inInventory the boolean indicating whether the item is in the inventory
     */
    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }

    /**
     * An abstract method that renders the item icon on screen.
     * @param offset the value the image has to shift in y-coordinate
     */
    public abstract void drawIcon(double offset);

    protected int getICON_X() {
        return ICON_X;
    }

    protected int getICON_Y() {
        return ICON_Y;
    }
}
