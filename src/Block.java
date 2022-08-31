import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the block object.
 */
public class Block extends GameEntity {
    private final Image BLOCK = new Image("res/block.png");
    private final double blockWidth = BLOCK.getWidth();
    private final double blockHeight = BLOCK.getHeight();

    /**
     * Creates a block at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of block
     * @param posY the y-coordinate of block
     */
    public Block(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Creates a Rectangle around the block image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + blockWidth/2,this.getPosY() + blockHeight/2);
        return BLOCK.getBoundingBoxAt(centre);
    }

    /**
     * Performs state update of the block.
     */
    public void update() {
        BLOCK.drawFromTopLeft(this.getPosX(), this.getPosY());
    }

}
