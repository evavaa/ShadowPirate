import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the potion, an item that can be picked up by the sailor.
 */
public class Potion extends Item{
    private final Image POTION = new Image("res/items/potion.png");
    private final Image POTION_ICON = new Image("res/items/potionIcon.png");
    private final double potionWidth = POTION.getWidth();
    private final double potionHeight = POTION.getHeight();
    private final static int HEALTH_INCREASE = 25;

    /**
     * Creates a potion at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of potion
     * @param posY the y-coordinate of potion
     */
    public Potion(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Performs state update.
     * If it collides with the sailor, it increases sailor's health points.
     * @param sailor the player
     */
    public void update(Sailor sailor) {
        if (!this.isPickedUp()) {
            POTION.drawFromTopLeft(this.getPosX(), this.getPosY());

            // check if the sailor finds the item
            if (this.checkCollision(sailor)) {
                this.setPickedUp(true);
                sailor.setHealthPoint(Math.min(sailor.getMaxHealth(), sailor.getHealthPoint() + HEALTH_INCREASE));

                // generate log
                String log = String.format("Sailor finds Potion. Sailor's current health: %d/%d",
                        sailor.getHealthPoint(), sailor.getMaxHealth());
                System.out.println(log);
            }
        }
    }

    /**
     * Creates a Rectangle around the potion image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + potionWidth/2,this.getPosY() + potionHeight/2);
        return POTION.getBoundingBoxAt(centre);
    }

    /**
     * Renders the potion icon.
     * @param offset the value the image has to shift in y-coordinate
     */
    @Override
    public void drawIcon(double offset) {
        POTION_ICON.drawFromTopLeft(this.getICON_X(), this.getICON_Y() + offset);
    }
}
