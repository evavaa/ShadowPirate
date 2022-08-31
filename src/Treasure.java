import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the treasure.
 */
public class Treasure extends GameEntity{
    private final Image TREASURE = new Image("res/treasure.png");
    private final double treasureWidth = TREASURE.getWidth();
    private final double treasureHeight = TREASURE.getHeight();

    /**
     * Creates a treasure at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of treasure
     * @param posY the y-coordinate of treasure
     */
    public Treasure(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Creates a Rectangle around the treasure image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + treasureWidth/2,this.getPosY() + treasureHeight/2);
        return TREASURE.getBoundingBoxAt(centre);
    }

    /**
     * Performs state update of the block.
     */
    public void update() {
        TREASURE.drawFromTopLeft(this.getPosX(), this.getPosY());
    }
}
