import java.util.ArrayList;

/**
 * A class representing moving entities in the game.
 */
public abstract class MovingEntity extends GameEntity {

    /**
     * Creates a moving entity at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of the entity
     * @param posY the y-coordinate of the entity
     */
    public MovingEntity(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Checks if there is a collision between characters and blocks.
     */
    protected boolean blockCollision(ArrayList<Block> blocks) {
        for (Block current: blocks) {
            if (checkCollision(current)) {
                return true;
            }
        }
        return false;
    }
}
