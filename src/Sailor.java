import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * A class representing the sailor (player) in the game.
 */
public class Sailor extends MovingEntity{
    private final Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");
    private final Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final double sailorWidth = SAILOR_RIGHT.getWidth();
    private final double sailorHeight = SAILOR_RIGHT.getHeight();
    private final static int SPEED = 1;
    private final static int COOL_DOWN = 2000;
    private final static int ATTACK = 1000;
    private final static int HEALTH_X = 10;
    private final static int HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;

    private int healthPoint = 100;
    private int maxHealth = 100;
    private int damagePoint = 15;
    private boolean inAttackMode = false;
    private boolean inCoolDown = false;
    private boolean isLeft = false;
    private long startAttack;
    private long startCoolDown;
    private Point oldPosition;

    /**
     * Creates a sailor at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of the sailor
     * @param posY the y-coordinate of the sailor
     */
    public Sailor(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Update the state of the sailor based on keyboard input and its interaction with blocks and level bounds.
     * @param input       user input from keyboard
     * @param blocks      a list of blocks in the game
     * @param topLeft     the top-left coordinates of the level bound
     * @param bottomRight the bottom-right coordinates of the level bound
     */
    public void update(Input input, ArrayList<Block> blocks, Point topLeft, Point bottomRight) {
        // store old position before moving
        oldPosition = new Point(this.getPosX(), this.getPosY());

        if (input.isDown(Keys.UP)) {
            this.moveTo(this.getPosX(), this.getPosY() - SPEED);
        } else if (input.isDown(Keys.DOWN)) {
            this.moveTo(this.getPosX(), this.getPosY() + SPEED);
        } else if (input.isDown(Keys.LEFT)) {
            this.moveTo(this.getPosX() - SPEED, this.getPosY());
            isLeft = true;
        } else if (input.isDown(Keys.RIGHT)) {
            this.moveTo(this.getPosX() + SPEED, this.getPosY());
            isLeft = false;
        }

        // enter attack mode
        if (input.wasPressed(Keys.S) && !inCoolDown) {
            inAttackMode = true;
            inCoolDown = true;
            startAttack = System.currentTimeMillis();
            startCoolDown = startAttack + ATTACK;
        }

        // check if the cool down and attack mode ends
        long currentTime = System.currentTimeMillis();
        if (currentTime - startAttack > ATTACK) {
            inAttackMode = false;
        }
        if (currentTime - startCoolDown > COOL_DOWN) {
            inCoolDown = false;
        }

        // check if the sailor collides with blocks or reaches the boundary
        if ((blocks != null && this.blockCollision(blocks)) || this.isOutOfBound(topLeft, bottomRight)) {
            this.moveTo(oldPosition.x, oldPosition.y);
        }

        // draw sailor
        if (isLeft && !inAttackMode) {
            SAILOR_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
        } else if (!isLeft && !inAttackMode) {
            SAILOR_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
        } else if (isLeft && inAttackMode) {
            SAILOR_HIT_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
        } else {
            SAILOR_HIT_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
        }

        // render health bar
        HealthBar healthBar = new HealthBar(FONT_SIZE, healthPoint, maxHealth, HEALTH_X, HEALTH_Y);
        healthBar.renderHealthPoints();
    }

    /**
     * Creates a Rectangle around the sailor image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + sailorWidth/2,this.getPosY() + sailorHeight/2);
        return SAILOR_RIGHT.getBoundingBoxAt(centre);
    }

    /**
     * Checks if sailor's health is less than or equal to 0.
     * @return the boolean indicating whether sailor's health <= 0
     */
    public boolean isDead() {
        return healthPoint <= 0;
    }

    /**
     * Gets the original position of the sailor before current movement.
     * @return the Point indicating sailor's original position
     */
    public Point getOldPosition() {
        return oldPosition;
    }

    /**
     * Sets sailor's health points to a new value.
     * @param healthPoint the new health points of the sailor
     */
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    /**
     * Sets sailor's maximum health points to a new value.
     * @param maxHealth the new maximum health points of the sailor
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Sets sailor's damage points to a new value.
     * @param damagePoint the new damage points of the sailor
     */
    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }

    /**
     * Gets sailor's current health points.
     * @return sailor's current health points
     */
    public int getHealthPoint() {
        return healthPoint;
    }

    /**
     * Checks if the sailor is in attack mode.
     * @return the boolean indicating whether the sailor is in attack mode.
     */
    public boolean isInAttackMode() {
        return inAttackMode;
    }

    /**
     * Gets sailor's current damage points.
     * @return sailor's current damage points
     */
    public int getDamagePoint() {
        return damagePoint;
    }

    /**
     * Gets sailor's maximum health points.
     * @return sailor's maximum health points
     */
    public int getMaxHealth() {
        return maxHealth;
    }
}
