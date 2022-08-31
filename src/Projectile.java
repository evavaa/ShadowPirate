import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the projectile fired by pirates.
 */
public class Projectile extends MovingEntity{
    private final Image PROJECTILE = new Image("res/pirate/pirateProjectile.png");
    private final double projectileWidth = PROJECTILE.getWidth();
    private final double projectileHeight = PROJECTILE.getHeight();

    private final static double SPEED = 0.4;
    private final static int DAMAGE = 10;
    private final static int BOX_SIZE = 1;
    private final Point originalPos;
    private final DrawOptions rotation = new DrawOptions();
    private double moveX;
    private double moveY;
    private boolean shouldDisappear = false;

    /**
     * Creates a projectile at a particular (posX, posY) top-left position towards the sailor.
     * @param posX    the x-coordinate of projectile
     * @param posY    the y-coordinate of projectile
     * @param sailor  the player
     */
    public Projectile(double posX, double posY, Sailor sailor) {
        super(posX, posY);
        originalPos = new Point(posX, posY);
        setDirection(sailor);
        double radian = Math.atan((sailor.getPosY() - originalPos.y)/(sailor.getPosX() - originalPos.x));
        rotation.setRotation(radian);
    }

    /**
     * Update the state of the projectile and its interaction with the sailor and level bounds.
     * @param sailor       the player
     * @param topLeft      the top-left coordinates of the level bound
     * @param bottomRight  the bottom-right coordinates of the level bound
     */
    public void update(Sailor sailor, Point topLeft, Point bottomRight) {
        if (!shouldDisappear) {
            this.moveTo(this.getPosX() + moveX, this.getPosY() + moveY);
            PROJECTILE.drawFromTopLeft(this.getPosX(), this.getPosY(), rotation);
        }

        // check if the projectile hits the sailor
        if (!shouldDisappear && this.checkCollision(sailor)) {
            shouldDisappear = true;
            sailor.setHealthPoint(sailor.getHealthPoint() - DAMAGE);

            // generate log
            String log = this.damageLog("Pirate", "Sailor", DAMAGE,
                    sailor.getHealthPoint(), sailor.getMaxHealth());
            System.out.println(log);
        }

        // check if the projectile reaches the boundary
        if (!shouldDisappear && this.isOutOfBound(topLeft, bottomRight)) {
            shouldDisappear = true;
        }
    }

    /**
     * Creates a 1*1 Rectangle around the centre of the projectile image.
     * @return the Rectangle created
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + projectileWidth/2,this.getPosY() + projectileHeight/2);
        return new Rectangle(centre.x-BOX_SIZE/2.0, centre.y-BOX_SIZE/2.0, BOX_SIZE, BOX_SIZE);
    }

    /**
     * Sets the moving direction of the projectile.
     */
    protected void setDirection(Sailor sailor) {
        double length = new Point(sailor.getPosX(), sailor.getPosY()).distanceTo(originalPos);
        moveX = SPEED * (sailor.getPosX() - originalPos.x) / length;
        moveY = SPEED * (sailor.getPosY() - originalPos.y) / length;
    }

    protected boolean isShouldDisappear() {
        return shouldDisappear;
    }

    protected void setShouldDisappear(boolean shouldDisappear) {
        this.shouldDisappear = shouldDisappear;
    }

    protected Point getOriginalPos() {
        return originalPos;
    }

    protected DrawOptions getRotation() {
        return rotation;
    }
}
