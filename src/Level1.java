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
 * A class representing level 1 of the game.
 */

public class Level1 extends Level{
    private final static String WORLD_FILE1 = "res/level1.csv";
    private final Image BACKGROUND_1 = new Image("res/background1.png");
    private final static String WIN_MESSAGE_LEVEL1 = "CONGRATULATIONS";
    private final static int ICON_OFFSET = 40;
    private Point topLeft;
    private Point bottomRight;

    // characters and items
    private Sailor sailor;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<Enemy> pirates = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Treasure treasure;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();

    private boolean gameOn = false;
    private boolean gameWin = false;
    private boolean gameLose = false;

    /**
     * Constructs level 1 with given instruction messages.
     * @param INSTRUCTION instruction messages that need to be displayed before level starts
     */
    public Level1(String[] INSTRUCTION) {
        super(INSTRUCTION);
        readCSV(WORLD_FILE1);
    }

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
                    bombs.add(new Bomb(pos.x, pos.y));
                } else if (sections[0].equals("Pirate")){
                    pirates.add(new Pirate(pos.x, pos.y));
                } else if (sections[0].equals("Blackbeard")){
                    pirates.add(new BlackBeard(pos.x, pos.y));
                } else if (sections[0].equals("TopLeft")){
                    topLeft = pos;
                } else if (sections[0].equals("BottomRight")){
                    bottomRight = pos;
                } else if (sections[0].equals("Potion")){
                    items.add(new Potion(pos.x, pos.y));
                } else if (sections[0].equals("Elixir")){
                    items.add(new Elixir(pos.x, pos.y));
                } else if (sections[0].equals("Sword")){
                    items.add(new Sword(pos.x, pos.y));
                } else if (sections[0].equals("Treasure")){
                    treasure = new Treasure(pos.x, pos.y);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Check if the sailor finds the treasure and completes level 1 successfully.
     */
    private boolean winLevel1(Treasure treasure) {
        return sailor.checkCollision(treasure);
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

        // when level 0 is running
        if (gameOn && !gameLose && !gameWin) {
            BACKGROUND_1.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for (Bomb bomb : bombs) {
                bomb.update(sailor);
            }
            sailor.update(input, null, topLeft, bottomRight);
            treasure.update();
            for (Enemy pirate : pirates) {
                pirate.update(null, topLeft, bottomRight, sailor, projectiles, bombs);
            }
            for (Projectile projectile : projectiles) {
                projectile.update(sailor, topLeft, bottomRight);
            }

            // add item to inventory if it is picked up
            for (Item item: items) {
                item.update(sailor);
                if (item.isPickedUp() && !item.isInInventory()) {
                    inventory.add(item);
                    item.setInInventory(true);
                }
            }
            // render inventory
            for (int i=0; i<inventory.size(); i++) {
                inventory.get(i).drawIcon(i*ICON_OFFSET);
            }

            // check if the game ends
            if (sailor.isDead()){
                gameLose = true;
            }
            if (winLevel1(treasure)){
                gameWin = true;
            }
        }

        // display messages when level 0 ends
        if (gameWin) {
            this.printEndScreen(WIN_MESSAGE_LEVEL1);
        }
        if (gameLose) {
            this.printEndScreen(this.getLOSE_MESSAGE());
        }
    }
}
