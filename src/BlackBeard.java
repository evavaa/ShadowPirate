import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * A class representing the Blackbeard.
 */
public class BlackBeard extends Enemy{
    private final Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final Image BLACKBEARD_HIT_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");
    private final Image BLACKBEARD_HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final double pirateWidth = BLACKBEARD_RIGHT.getWidth();
    private final double pirateHeight = BLACKBEARD_RIGHT.getHeight();

    private final static int MAX_HEALTH = 90;
    private final static int COOL_DOWN = 1500;
    private final static int ATTACK_RANGE = 200;
    private int healthPoint = 90;
    private long startCoolDown;
    private long startInvincible;

    /**
     * Creates a blackbeard at a particular (posX, posY) top-left position.
     * @param posX the x-coordinate of blackbeard
     * @param posY the y-coordinate of blackbeard
     */
    public BlackBeard(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Update the state of the blackbeard and its interaction with blocks, bombs, sailor, projectiles and level bounds.
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
                projectiles.add(new ProjectileBlack(this.getPosX(), this.getPosY(), sailor));
            }

            // check if the pirate is attacked by the sailor
            if (sailor.isInAttackMode() && !this.isInvincible() && this.checkCollision(sailor)) {
                healthPoint = healthPoint - sailor.getDamagePoint();
                startInvincible = System.currentTimeMillis();
                this.setInvincible(true);

                // generate log
                String log = this.damageLog("Sailor", "Blackbeard", sailor.getDamagePoint(),
                        healthPoint, MAX_HEALTH);
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

            // draw blackbeard
            if (this.isLeft() && !this.isInvincible()) {
                BLACKBEARD_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else if (!this.isLeft() && !this.isInvincible()) {
                BLACKBEARD_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else if (this.isLeft() && this.isInvincible()) {
                BLACKBEARD_HIT_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
            } else {
                BLACKBEARD_HIT_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
            }

            // generate health bar on top of blackbeard's image
            HealthBar healthBar = new HealthBar(this.getFONT_SIZE(), healthPoint, MAX_HEALTH, this.getPosX(),
                    this.getPosY() - this.getHEALTH_OFFSET());
            healthBar.renderHealthPoints();
        }
    }

    /**
     * Creates a Rectangle around the blackbeard image.
     * @return the Rectangle created.
     */
    @Override
    public Rectangle getBoundingBox() {
        Point centre = new Point(this.getPosX() + pirateWidth/2,this.getPosY() + pirateHeight/2);
        return BLACKBEARD_RIGHT.getBoundingBoxAt(centre);
    }
}
