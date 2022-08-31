import bagel.*;

/**
 * SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @Luyao Chen
 *
 * This is a class representing the "ShadowPirate" game.
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    private final String[] INSTRUCTION_0 = {"PRESS SPACE TO START", "PRESS S TO ATTACK", "USE ARROW KEY TO FIND LADDER"};
    private final String[] INSTRUCTION_1 = {"PRESS SPACE TO START", "PRESS S TO ATTACK", "FIND THE TREASURE"};

    private boolean level0Win = false;
    private boolean skipLevel0 = false;
    private Level0 level0 = new Level0(INSTRUCTION_0);
    private Level1 level1 = new Level1(INSTRUCTION_1);

    /**
     * Creates the game window with the window size of (1024*768) and the title "ShadowPirate".
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Allows the game to skip level 0 when the "W" key is pressed.
     * @param input user input from keyboard
     */
    @Override
    public void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        if (input.wasPressed(Keys.W)) {
            skipLevel0 = true;
        }

        if (skipLevel0 || level0Win) {
            level1.update(input);
        } else if (level0.isGameEnd() && !level0.isGameWin()){
            level0.update(input);
        } else {
            level0.update(input);
        }

        // proceed to level 1 if the player wins level 0
        if (level0.isGameEnd() && level0.isGameWin()) {
            level0Win = true;
        }
    }

}
