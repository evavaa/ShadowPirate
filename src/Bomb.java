import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * A class representing the bomb object.
 */
public class Bomb extends GameEntity{
    private final Image BOMB = new Image("res/bomb.png");
    private final Image EXPLOSION = new Image("res/explosion.png");
    private final double bombWidth = BOMB.getWidth();
    private final double bombHeight = BOMB.getHeight();
    private final static int EXPLOSION_TIME = 500;

    private final static int DAMAGE_POINT = 10;
    private boolean inExplosion = false;
    private boolean shouldDisappear= false;
    private long startExplode;
    private long currentTime;

    /**
     * Creates a bomb at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of bomb
     * @param posY the y-coordinate of bomb
     */
    public Bomb(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Update the state of the bomb.
     * The bomb explodes and inflicts damage on the sailor when it collides with the sailor.
     * @param sailor the player
     */
    public void update(Sailor sailor) {
        currentTime = System.currentTimeMillis();

        if (!inExplosion) {
            BOMB.drawFromTopLeft(this.getPosX(), this.getPosY());
        } else if (!shouldDisappear){
            EXPLOSION.drawFromTopLeft(this.getPosX(), this.getPosY());

            // when bomb is in explosion, it prevents sailor from passing through
            Point sailorPos = sailor.getOldPosition();
            sailor.moveTo(sailorPos.x, sailorPos.y);

            // the bomb disappears after 500 milliseconds
            if (currentTime-startExplode >= EXPLOSION_TIME) {
                shouldDisappear = true;
            }
        }

        // explodes when the sailor collides with the bomb
        if (!inExplosion && this.checkCollision(sailor)) {
            inExplosion = true;
            startExplode = System.currentTimeMillis();

            // generate log
            sailor.setHealthPoint(sailor.getHealthPoint() - DAMAGE_POINT);
            String damageMessage = this.damageLog("Bomb", "Sailor", DAMAGE_POINT,
                    sailor.getHealthPoint(), sailor.getMaxHealth());
            System.out.println(damageMessage);
        }
    }

    /**
     * Creates a Rectangle around the bomb image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + bombWidth/2,this.getPosY() + bombHeight/2);
        return BOMB.getBoundingBoxAt(centre);
    }

    /**
     * Check whether the bomb should disappear or not.
     * @return the boolean indicating whether the bomb should disappear
     */
    public boolean isShouldDisappear() {
        return shouldDisappear;
    }
}
