import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * A class representing the pirate.
 */
public class Pirate extends Enemy{
    private final Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final Image PIRATE_HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");
    private final Image PIRATE_HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final double pirateWidth = PIRATE_RIGHT.getWidth();
    private final double pirateHeight = PIRATE_RIGHT.getHeight();

    private final static int ATTACK_RANGE = 100;
    private final static int MAX_HEALTH = 45;
    private final static int COOL_DOWN = 3000;
    private int healthPoint = 45;
    private long startCoolDown;
    private long startInvincible;

    /**
     * Creates a pirate at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of pirate
     * @param posY the y-coordinate of pirate
     */
    public Pirate(double posX, double posY) {
        super(posX, posY);
    }


    /**
     * Update the state of the pirate and its interaction with blocks, bombs, sailor, projectiles and level bounds.
     * @param blocks      a list of obstacles in the game
     * @param topLeft     the top-left coordinates of the level bound
     * @param bottomRight the bottom-right coordinates of the level bound
     * @param sailor      the player
     * @param projectiles a list of projectiles that is fired by pirates
     * @param bombs       a list of obstacles in the game
     */
    @Override
    public void update(ArrayList<Block> blocks, Point topLeft, Point bottomRight, Sailor sailor,
                       ArrayList<Projectile>projectiles, ArrayList<Bomb> bombs) {

        // the pirate disappear if health points reduce to 0
        if (healthPoint<=0) {
            this.setShouldDisappear(true);
        }

        if (!this.isShouldDisappear()) {
            this.move(blocks, bombs, topLeft, bottomRight);

            // fire the projectile if the sailor enters the pirate's attack range
            Rectangle attackBox = getAttackBox(pirateWidth, pirateHeight, ATTACK_RANGE);
            Rectangle sailorBox = sailor.getBoundingBox();
            if (attackBox.intersects(sailorBox) && !this.isInCoolDown()) {
                this.setInCoolDown(true);
                startCoolDown = System.currentTimeMillis();
                projectiles.add(new Projectile(this.getPosX(), this.getPosY(), sailor));
            }

            // check if the pirate is attacked by the sailor
            if (sailor.isInAttackMode() && !this.isInvincible() && this.checkCollision(sailor)) {
                healthPoint = healthPoint - sailor.getDamagePoint();
                startInvincible = System.currentTimeMillis();
                this.setInvincible(true);

                // generate log
                String log = this.damageLog("Sailor", "Pirate", sailor.getDamagePoint(), healthPoint, MAX_HEALTH);
                System.out.println(log);
            }

            // check time of the pirate in cool_down or invincible mode
            long currentTime = System.currentTimeMillis();
            if (currentTime - startCoolDown > COOL_DOWN) {
                this.setInCoolDown(false);
            }
            if (currentTime - startInvincible > this.getINVINCIBLE()) {
                this.setInvincible(false);
            }

            // draw pirate
            if (this.isLeft() && !this.isInvincible()) {
                PIRATE_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else if (!this.isLeft() && !this.isInvincible()) {
                PIRATE_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else if (this.isLeft() && this.isInvincible()) {
                PIRATE_HIT_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else {
                PIRATE_HIT_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
            }

            // generate health bar on top of pirate's image
            HealthBar healthBar = new HealthBar(this.getFONT_SIZE(), healthPoint, MAX_HEALTH, this.getPosX(),
                    this.getPosY() - this.getHEALTH_OFFSET());
            healthBar.renderHealthPoints();
        }
    }

    /**
     * Creates a Rectangle around the pirate image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + pirateWidth/2,this.getPosY() + pirateHeight/2);
        return PIRATE_RIGHT.getBoundingBoxAt(centre);
    }


}
