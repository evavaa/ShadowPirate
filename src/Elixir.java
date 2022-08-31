import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the elixir, an item that can be picked up by the sailor.
 */
public class Elixir extends Item{
    private final Image ELIXIR = new Image("res/items/elixir.png");
    private final Image ELIXIR_ICON = new Image("res/items/elixirIcon.png");
    private final double elixirWidth = ELIXIR.getWidth();
    private final double elixirHeight = ELIXIR.getHeight();
    private final static int MAX_HEALTH_INCREASE = 35;

    /**
     * Creates an elixir at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of elixir
     * @param posY the y-coordinate of elixir
     */
    public Elixir(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Performs state update.
     * If it collides with the sailor, it increases sailor's maximum health points and
     * increases the current health point to the new maximum.
     * @param sailor the player
     */
    public void update(Sailor sailor) {
        if (!this.isPickedUp()) {
            ELIXIR.drawFromTopLeft(this.getPosX(), this.getPosY());

            // check if the sailor finds the item
            if (this.checkCollision(sailor)) {
                this.setPickedUp(true);
                sailor.setMaxHealth(sailor.getMaxHealth() + MAX_HEALTH_INCREASE);
                sailor.setHealthPoint(sailor.getMaxHealth());

                // generate log
                String log = String.format("Sailor finds Elixir. Sailor's current health: %d/%d",
                        sailor.getHealthPoint(), sailor.getMaxHealth());
                System.out.println(log);
            }
        }
    }

    /**
     * Creates a Rectangle around the elixir image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + elixirWidth/2,this.getPosY() + elixirHeight/2);
        return ELIXIR.getBoundingBoxAt(centre);
    }

    /**
     * Renders the elixir icon.
     * @param offset the value the image has to shift in y-coordinate
     */
    @Override
    public void drawIcon(double offset) {
        ELIXIR_ICON.drawFromTopLeft(this.getICON_X(), this.getICON_Y() + offset);
    }

}
