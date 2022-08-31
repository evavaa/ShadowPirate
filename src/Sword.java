import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the sword, an item that can be picked up by the sailor.
 */
public class Sword extends Item {
    private final Image SWORD = new Image("res/items/sword.png");
    private final Image SWORD_ICON = new Image("res/items/swordIcon.png");
    private final double swordWidth = SWORD.getWidth();
    private final double swordHeight = SWORD.getHeight();
    private final static int DAMAGE_INCREASE = 15;

    /**
     * Creates a sword at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of sword
     * @param posY the y-coordinate of sword
     */
    public Sword(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Performs state update.
     * If it collides with the sailor, it increases sailor's damage points.
     * @param sailor the player
     */
    public void update(Sailor sailor) {
        if (!this.isPickedUp()) {
            SWORD.drawFromTopLeft(this.getPosX(), this.getPosY());

            // check if the sailor finds the item
            if (this.checkCollision(sailor)) {
                this.setPickedUp(true);
                sailor.setDamagePoint(sailor.getDamagePoint() + DAMAGE_INCREASE);

                // generate log
                String log = String.format("Sailor finds Sword. Sailor's damage points increased to %d",
                        sailor.getDamagePoint());
                System.out.println(log);
            }
        }
    }

    /**
     * Creates a Rectangle around the sword image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + swordWidth/2,this.getPosY() + swordHeight/2);
        return SWORD.getBoundingBoxAt(centre);
    }

    /**
     * Renders the sword icon.
     * @param offset the value the image has to shift in y-coordinate
     */
    @Override
    public void drawIcon(double offset) {
        SWORD_ICON.drawFromTopLeft(this.getICON_X(), this.getICON_Y() + offset);
    }

}
