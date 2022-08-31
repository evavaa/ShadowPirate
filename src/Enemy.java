import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing enemy. This is the superclass for pirate and blackbeard.
 */
public abstract class Enemy extends MovingEntity{
    private final static int INVINCIBLE = 1500;
    private final static double MAX_SPEED = 0.7;
    private final static double MIN_SPEED = 0.2;
    private final static int FONT_SIZE = 15;
    private final static int HEALTH_OFFSET = 6;
    private final double speed = Math.random() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED;
    private final static String[] DIRECTIONS =  {"up", "down", "left", "right"};
    private final String direction;
    private boolean isLeft = false;
    private boolean shouldDisappear = false;
    private boolean inCoolDown = false;
    private boolean isInvincible = false;
    private double moveX;
    private double moveY;

    /**
     * Creates an enemy at a particular (posX, posY) top-left position.
     * Randomly generates the initial moving direction and the speed for the enemy.
     * @param posX the x-coordinate of bomb
     * @param posY the y-coordinate of bomb
     */
    public Enemy(double posX, double posY) {
        super(posX, posY);
        Random random = new Random();
        this.direction = (DIRECTIONS[random.nextInt(DIRECTIONS.length)]);
        moveX = this.getMovement().x;
        moveY = this.getMovement().y;
    }

    private Point getMovement() {
        if (direction.equals("up")) {
            return new Point(0, -1 * speed);
        } else if (direction.equals("down")) {
            return new Point(0, speed);
        } else if (direction.equals("left")) {
            return new Point(-1 * speed, 0);
        } else {
            return new Point(speed, 0);
        }
    }

    /**
     * Creates an attack box around the enemy based on its position and attack range.
     */
    protected Rectangle getAttackBox(double pirateWidth, double pirateHeight, int ATTACK_RANGE) {
        Point centre = new Point(this.getPosX() + pirateWidth/2,this.getPosY() + pirateHeight/2);
        return new Rectangle(centre.x-ATTACK_RANGE/2.0, centre.y-ATTACK_RANGE/2.0, ATTACK_RANGE, ATTACK_RANGE);
    }

    /**
     * Checks if the enemy collides with the bomb.
     */
    private boolean bombCollision(ArrayList<Bomb> bombs) {
        for (Bomb current: bombs) {
            if (!current.isShouldDisappear() && checkCollision(current)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the enemy collides with any obstacles.
     */
    private boolean obstacleCollision(ArrayList<Bomb> bombs, ArrayList<Block> blocks) {
        if (bombs != null && bombCollision(bombs)) {
            return true;
        }
        if (blocks != null && this.blockCollision(blocks)) {
            return true;
        }
        return false;
    }

    /**
     * Performs the movement of an enemy.
     */
    protected void move(ArrayList<Block> blocks, ArrayList<Bomb> bombs, Point topLeft, Point bottomRight) {
        this.moveTo(this.getPosX() + moveX, this.getPosY() + moveY);
        isLeft = moveX < 0;

        // change direction if collides with a block or a bomb or reaches the boundary
        if (obstacleCollision(bombs, blocks) || this.isOutOfBound(topLeft, bottomRight)) {
            moveX = -1 * moveX;
            moveY = -1 * moveY;
        }
    }

    /**
     * An abstract method that update the state of an enemy and
     * its interaction with blocks, bombs, sailor, projectiles and boundary.
     * @param blocks      a list of obstacles in the game
     * @param topLeft     the top-left coordinates of the level bound
     * @param bottomRight the bottom-right coordinates of the level bound
     * @param sailor      the player
     * @param projectiles a list of projectiles that is fired by pirates
     * @param bombs       a list of obstacles in the game
     */
    public abstract void update(ArrayList<Block> blocks, Point topLeft, Point bottomRight, Sailor sailor,
                                ArrayList<Projectile>projectiles, ArrayList<Bomb> bombs);


    protected int getHEALTH_OFFSET() {
        return HEALTH_OFFSET;
    }

    protected boolean isLeft() {
        return isLeft;
    }

    protected boolean isShouldDisappear() {
        return shouldDisappear;
    }

    protected void setShouldDisappear(boolean shouldDisappear) {
        this.shouldDisappear = shouldDisappear;
    }

    protected boolean isInCoolDown() {
        return inCoolDown;
    }

    protected void setInCoolDown(boolean inCoolDown) {
        this.inCoolDown = inCoolDown;
    }

    protected boolean isInvincible() {
        return isInvincible;
    }

    protected void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    protected int getFONT_SIZE() {
        return FONT_SIZE;
    }

    protected int getINVINCIBLE() {
        return INVINCIBLE;
    }
}
