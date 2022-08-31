import bagel.Image;
import bagel.util.Point;

/**
 * A class representing the projectile fired by blackbeard.
 */
public class ProjectileBlack extends Projectile{
    private final Image PROJECTILE_BLACK = new Image("res/blackbeard/blackbeardProjectile.png");
    private final static double SPEED = 0.8;
    private final static int DAMAGE = 20;
    private double moveX;
    private double moveY;

    /**
     * Creates a projectile at a particular (posX, posY) top-left position towards the sailor.
     * @param posX    the x-coordinate of projectile
     * @param posY    the y-coordinate of projectile
     * @param sailor  the player
     */
    public ProjectileBlack(double posX, double posY, Sailor sailor) {
        super(posX, posY, sailor);
    }

    @Override
    protected void setDirection(Sailor sailor) {
        double length = new Point(sailor.getPosX(), sailor.getPosY()).distanceTo(this.getOriginalPos());
        moveX = SPEED * (sailor.getPosX() - this.getOriginalPos().x) / length;
        moveY = SPEED * (sailor.getPosY() - this.getOriginalPos().y) / length;
    }

    /**
     * Update the state of the projectile and its interaction with the sailor and level bounds.
     * @param sailor       the player
     * @param topLeft      the top-left coordinates of the level bound
     * @param bottomRight  the bottom-right coordinates of the level bound
     */
    @Override
    public void update(Sailor sailor, Point topLeft, Point bottomRight) {
        if (!this.isShouldDisappear()) {
            this.moveTo(this.getPosX() + moveX, this.getPosY() + moveY);
            PROJECTILE_BLACK.drawFromTopLeft(this.getPosX(), this.getPosY(), this.getRotation());
        }

        // check if the projectile hits the sailor
        if (!this.isShouldDisappear() && this.checkCollision(sailor)) {
            this.setShouldDisappear(true);
            sailor.setHealthPoint(sailor.getHealthPoint() - DAMAGE);

            // generate log
            String log = this.damageLog("Blackbeard", "Sailor", DAMAGE,
                    sailor.getHealthPoint(), sailor.getMaxHealth());
            System.out.println(log);
        }

        // check if the projectile reaches the boundary
        if (!this.isShouldDisappear() && this.isOutOfBound(topLeft, bottomRight)) {
            this.setShouldDisappear(true);
        }
    }
}
