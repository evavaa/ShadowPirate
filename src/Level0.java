import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class representing level 0 of the game.
 */
public class Level0 extends Level{
    private final static String WORLD_FILE0 = "res/level0.csv";
    private final Image BACKGROUND_0 = new Image("res/background0.png");
    private final static String WIN_MESSAGE_LEVEL0 = "LEVEL COMPLETE!";
    private final static int WIN_PAUSE = 3000;
    private final static int WIN_X = 990;
    private final static int WIN_Y = 630;
    private Point topLeft;
    private Point bottomRight;
    private Sailor sailor;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Pirate> pirates = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    private boolean gameOn = false;
    private boolean gameWin = false;
    private boolean gameLose = false;
    private boolean gameEnd = false;
    private long currentTime;
    private long winTime;

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;
            if ((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                if (sections[0].equals("Sailor")){
                    sailor = new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
            }

            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                Point pos = new Point(Double.parseDouble(sections[1]), Double.parseDouble(sections[2]));
                if (sections[0].equals("Block")){
                    blocks.add(new Block(pos.x, pos.y));
                } else if (sections[0].equals("Pirate")){
                    pirates.add(new Pirate(pos.x, pos.y));
                } else if (sections[0].equals("TopLeft")){
                    topLeft = pos;
                } else if (sections[0].equals("BottomRight")){
                    bottomRight = pos;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Constructs level 0 with given instruction messages.
     * @param INSTRUCTION instruction messages that need to be displayed before level starts
     */
    public Level0(String[] INSTRUCTION) {
        super(INSTRUCTION);
        readCSV(WORLD_FILE0);
    }

    /**
     * Checks if the player wins level 0.
     * @return the boolean indicating whether the player wins level 0
     */
    public boolean isGameWin() {
        return gameWin;
    }

    /**
     * Check if the player loses the game.
     * @return the boolean indicating whether the player loses the game.
     */
    public boolean isGameEnd() {
        return gameEnd;
    }

    /**
     * Check if the sailor completes level 0 successfully.
     */
    private boolean winLevel0() {
        return (sailor.getPosX() >= WIN_X) && (sailor.getPosY() > WIN_Y);
    }

    /**
     * Performs state update.
     * @param input user input from keyboard
     */
    public void update(Input input) {

        // display instruction messages
        if (!gameOn) {
            this.printStartScreen();
            if (input.wasPressed(Keys.SPACE)){
                gameOn = true;
            }
        }

        // pause 3 seconds before enter level 1
        currentTime = System.currentTimeMillis();
        if (gameLose || gameWin) {
            if (currentTime - winTime >= WIN_PAUSE) {
                gameEnd = true;
            }
        }

        // when level 0 is running
        if (gameOn && !gameLose && !gameWin) {
            BACKGROUND_0.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for (Block block : blocks) {
                block.update();
            }
            sailor.update(input, blocks, topLeft, bottomRight);
            for (Pirate pirate : pirates) {
                pirate.update(blocks, topLeft, bottomRight, sailor, projectiles, null);
            }
            for (Projectile projectile : projectiles) {
                projectile.update(sailor, topLeft, bottomRight);
            }

            // check if the game ends
            if (sailor.isDead()){
                gameLose = true;
            }
            if (winLevel0()){
                gameWin = true;
                winTime = System.currentTimeMillis();
            }
        }

        // display messages when level 0 ends
        if (gameWin) {
            this.printEndScreen(WIN_MESSAGE_LEVEL0);
        }
        if (gameLose) {
            this.printEndScreen(this.getLOSE_MESSAGE());
        }
    }
}
