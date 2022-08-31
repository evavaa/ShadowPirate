import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing a game entity. This is the superclass for all entities in the game.
 */
public abstract class GameEntity {
    private double posX;
    private double posY;

    /**
     * Creates an entity at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of entity
     * @param posY the y-coordinate of entity
     */
    public GameEntity(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the x-coordinate of the entity.
     * @return the x-coordinate of the entity
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Gets the y-coordinate of the entity.
     * @return the y-coordinate of the entity
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Moves the entity to another position (x, y).
     */
    protected void moveTo(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Detects whether the entity is out of level bounds.
     */
    protected boolean isOutOfBound(Point topLeft, Point bottomRight) {
        return posX<topLeft.x || posX>bottomRight.x || posY<topLeft.y || posY>bottomRight.y;
    }

    /**
     * An abstract method that creates a Rectangle around the entity.
     * @return the Rectangle created
     */
    public abstract Rectangle getBoundingBox();

    /**
     * Checks whether the entity collides with another entity.
     * @param entity the object that is collided with
     * @return the boolean indicating whether there is a collision
     */
    public boolean checkCollision(GameEntity entity) {
        Rectangle selfBox = getBoundingBox();
        Rectangle entityBox = entity.getBoundingBox();
        return selfBox.intersects(entityBox);
    }

    protected String damageLog(String a, String b, int damagePoint, int healthPoint, int maxHealth) {
        return String.format("%s inflicts " + "%d damage points on %s. %s’s current health: %d/%d",
                a, damagePoint, b, b, healthPoint, maxHealth);
    }
}
