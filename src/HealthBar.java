import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

/**
 * A class representing the health bar. It displays character's health points as percentage.
 */
public class HealthBar {
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);

    private final Font font;
    private final int healthPoint;
    private final int maxHealth;
    private final double posX;
    private final double posY;

    /**
     * Creates a message reporting character's health points at position (posX, posY) in a given fontsize.
     * @param fontSize     the fontsize of the message
     * @param healthPoint  the current health points of the character
     * @param maxHealth    the maximum health points of the character
     * @param posX         the x-coordinate of the message
     * @param posY         the y-coordinate of the message
     */
    public HealthBar(int fontSize, int healthPoint, int maxHealth, double posX, double posY) {
        this.font = new Font("res/wheaton.otf", fontSize);
        this.healthPoint = healthPoint;
        this.maxHealth = maxHealth;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Method that renders character's current health points as a percentage on screen.
     */
    public void renderHealthPoints(){
        double percentageHP = ((double) healthPoint/maxHealth) * 100;
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
        }
        font.drawString(Math.round(percentageHP) + "%", posX, posY, COLOUR);
    }
}
