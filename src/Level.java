import bagel.Font;
import bagel.Window;

/**
 * A class representing the level of the game.
 */
public abstract class Level {
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y = 402;
    private final static int MESSAGE_OFFSET = 70;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final static String LOSE_MESSAGE = "GAME OVER" ;
    private final String[] INSTRUCTION;
    private final int NUM_INSTRUCTION;

    /**
     * Constructs a level with a given instruction message.
     * @param INSTRUCTION instruction messages that need to be displayed before level starts
     */
    public Level(String[] INSTRUCTION) {
        this.INSTRUCTION = INSTRUCTION;
        this.NUM_INSTRUCTION = INSTRUCTION.length;
    }

    protected String getLOSE_MESSAGE() {
        return LOSE_MESSAGE;
    }

    /**
     * Displays instruction messages before the level starts.
     */
    protected void printStartScreen() {
        int posY = FONT_Y;
        for (int i=0; i<NUM_INSTRUCTION; i++) {
            FONT.drawString(INSTRUCTION[i], (Window.getWidth() - FONT.getWidth(INSTRUCTION[i])) / 2, posY);
            posY += MESSAGE_OFFSET;
        }
    }

    /**
     * Displays the given messages after the level ends.
     */
    protected void printEndScreen(String message){
        FONT.drawString(message, (Window.getWidth() - FONT.getWidth(message))/2, FONT_Y);
    }

}
