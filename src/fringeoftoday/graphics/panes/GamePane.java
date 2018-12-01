package fringeoftoday.graphics.panes;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.audio.AudioPlayer;
import fringeoftoday.core.CollisionManager;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.Entity;
import fringeoftoday.entities.Player;
import fringeoftoday.entities.Projectile;
import fringeoftoday.entities.ShotgunEnemy;
import fringeoftoday.entities.SniperEnemy;
import fringeoftoday.entities.StandardEnemy;
import fringeoftoday.floor.Direction;
import fringeoftoday.floor.Exit;
import fringeoftoday.floor.FloorManager;
import fringeoftoday.floor.Room;
import fringeoftoday.floor.RoomType;
import fringeoftoday.floor.Space;
import fringeoftoday.floor.SpaceType;
import fringeoftoday.graphics.GButtonMD;
import fringeoftoday.graphics.GParagraph;
import fringeoftoday.graphics.Sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles rendering the game screen and all gameplay-related tasks
 *
 * @author Devin French
 * @author Alexander Ng
 * @author Alex Reynen
 * @author Jacob Shour
 */
public class GamePane extends GraphicsPane implements ActionListener {
    public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH / 3;
    public static final int HEADER_HEIGHT = 196;
    public static final String FILE_PATH = "../media/textures/";
    public static final int DELAY_MS = 25;
    private static final int LEVEL_ALERT_X_SIZE = 600;
    private static final int LEVEL_ALERT_Y_SIZE = 150;
    private static final double SPEED_EFFECT = .25;  // Effectiveness of speed upgrades
    private static final int CAP_LEVEL = 11;
    public Direction direction;
    private MainApplication program;  // Used to access MainApplication methods
    private Set<Integer> keysPressed = new HashSet<>();
    private ArrayList<GObject> minimap = new ArrayList<GObject>();
	private Font hdrFont = new Font("PKMN Mystery Dungeon", 0, 60);
	private int level = 1; // Work on this when we get it in
	private GRect minimapBox; // Minimap, left header
	private GImage bossIcon;
	private GOval playerOnMap;
	private GRect infoBox; // Center header
	private GParagraph infoText;// Center header content
	private GRect healthBox; // Right header
	private GButtonMD levelAlert;
	private GLabel healthLabel;
	private GImage btnAudio;
	private GRect backingColor;
	private Room room;
	private Player player;
	private CollisionManager collisionManager;
	private Timer t;
	private int counter;
	private ArrayList<GObject> pauseElements = new ArrayList<GObject>();
	private GButtonMD quitPauseBtn;
	private Entity killer;
	private String floorMusicFile;
	private String bossMusicFile;

    /**
     * Main initialization function
     *
     * @param app Provides access to MainApplication methods
     */
    public GamePane(MainApplication app) {
        super();
        program = app;
        loadFont();

        // Header
        minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);
        infoBox = new GRect(HEADER_WIDTH, 0, HEADER_WIDTH, HEADER_HEIGHT);
        healthBox = new GRect(HEADER_WIDTH * 2, 0, HEADER_WIDTH, HEADER_HEIGHT);

        // Room
        room = program.getFloorManager().getSpawnRoom();

        // Player
        player = program.getEntityManager().getPlayer();

        // Collision
        collisionManager = new CollisionManager(program.getEntityManager(), room);

        // Music
        generateMusic();

        // Timer
        t = new Timer(DELAY_MS, this);
        counter = 0;
    }


    // =========== SHOW METHODS ============ //
    /*
	 * Methods that handle initialization and rendering of elements
	 */

    /**
     * Renders all game elements
     */
    @Override
    public void showContents() {
        player.setMaxHealth(Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 3);
        initHealth();
        showHeader(); // Top bar
        createImageList();
        showField(); // Game field
        showPlayer();
        showEnemies();
        drawLevelAlert();
        initPausing();
        playMusic();
    }

    /**
     * Initializes player health
     */
    private void initHealth() {
        player.setHealth(player.getMaxHealth());
        drawHealth(player.getHealth());
    }

    /**
     * Renders header
     */
    public void showHeader() {
        program.add(minimapBox);
        infoDrawing();
        drawHealth(player.getHealth());
        program.add(infoBox);
        program.add(healthBox);
        program.add(healthLabel);
        minimapBuilder();
    }

    /**
     * Renders minimap
     *
     * @author Alex Reynen
     */
    private void minimapBuilder() {
        int startY = 10;
        int startX = 10;
        int moveX = (HEADER_WIDTH - 20) / FloorManager.FLOOR_COLS;
        int moveY = (HEADER_HEIGHT - 20) / FloorManager.FLOOR_ROWS;
        for (int row = 0; row < FloorManager.FLOOR_ROWS; row++) {
            for (int col = 0; col < FloorManager.FLOOR_COLS; col++) {
                GRect tile = new GRect(startX, startY, moveX, moveY);
                Room r = FloorManager.getFloor().getRoom(row, col);
                colorTile(r, tile);
                minimap.add(tile);
                startX += moveX;
            }
            startX = 10;
            startY += moveY;
        }
        for (GObject tile : minimap) {
            program.add(tile);
        }
        program.add(bossIcon);
        program.add(playerOnMap);
    }


    /**
     * Helper method for minimapBuilder(); colors minimap tiles
     *
     * @param r    Room linked to the tile
     * @param tile Tile to color
     * @author Alex Reynen
     */
    private void colorTile(Room r, GRect tile) {
        if (r == null) {
            tile.setVisible(false);
        } else if (r.getType() == RoomType.STANDARD) {
            tile.setFillColor(Color.LIGHT_GRAY);
            tile.setFilled(true);
        } else if (r.getType() == RoomType.SPAWN) {
            tile.setFillColor(Color.GREEN);
            playerOnMap = new GOval(tile.getX() + (tile.getWidth() - tile.getHeight() * .75) / 2,
              tile.getY() + tile.getHeight() * .125, tile.getHeight() * .75, tile.getHeight() * .75);
            playerOnMap.setFillColor(Color.BLUE);
            playerOnMap.setFilled(true);
            tile.setFilled(true);
        } else if (r.getType() == RoomType.BOSS) {
            tile.setFillColor(Color.RED);
            bossIcon = new GImage("boss_icon.png", tile.getX() + (tile.getWidth() - tile.getHeight()) / 2, tile.getY());
            bossIcon.setSize(tile.getHeight(), tile.getHeight());
            tile.setFilled(true);
        }
    }

    /**
     * Renders health tracker
     *
     * @param health Player health
     */
    private void drawHealth(int health) {
        healthLabel = new GLabel("Health: " + health, HEADER_WIDTH * 2.25, HEADER_HEIGHT / 1.9);
        healthLabel.setFont(hdrFont);
        healthLabel.setColor(Color.WHITE);
    }

    /**
     * Sets GOBjects stored in the current room's spaces to proper images
     *
     * @author Jacob Shour
     */
    public void createImageList() {
        int rows = FloorManager.ROOM_ROWS;
        int cols = FloorManager.ROOM_COLS;
        GImage temp;

        String path = variablePath(FILE_PATH);

        room.setFilePaths();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Space space = room.getSpace(i, j);
                temp = new GImage(path + space.getFilePath(), (j * FloorManager.SPACE_SIZE),
                  (i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
                temp.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
                space.setGObject(temp);
            }
        }
    }


    /**
     * Selects a tileset for an image based on floor level
     *
     * @param path Image path to build off of
     * @return Image path, now with tileset
     * @author Jacob Shour
     */
    private String variablePath(String path) {
        if (level <= 3)
            path = path + "RockPath/";
        else if (level >= 4 && level <= 6)
            path = path + "SealedRuin/";
        else if (level >= 7 && level <= 9)
            path = path + "SteamCave/";
        else
            path = path + "DarkCrater/";

        return path;
    }


    /**
     * Renders the current room
     */
    public void showField() {
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                program.add(room.getSpace(i, j).getGObject());
            }
        }
    }

    /**
     * Renders the player
     */
    public void showPlayer() {
        Image sprite = Sprites.loadSprite("../media/sprites/player/player_standing_south.png");
        player.setGObject(new GImage(sprite, 24 * (FloorManager.SPACE_SIZE / 2), 16 * (FloorManager.SPACE_SIZE / 2)));
        program.add(player.getGObject());
    }

    /**
     * Spawns in and renders enemies; applies stat scaling to enemies based on floor level
     *
     * @author Devin French (rendering)
     * @author Alex Reynen (scaling)
     */
    public void showEnemies() {
        List<Enemy> enemies = program.getEntityManager().getEnemies();
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                Enemy enemy = null;
                switch (space.getType()) {
                    case BASIC_SPAWN:
                        enemy = new StandardEnemy();
                        enemy.setDmgMult(level * 0.5f);
                        enemy.setFireRate(10 - (.5 * (level - 1)));
                        if (level > CAP_LEVEL) {
                            enemy.setDmgMult(CAP_LEVEL + (level - CAP_LEVEL) * 0.25f);
                            enemy.setFireRate(5);
                        }
                        enemy.setHealth(1 * level);
                        enemy.setVelocity(1);
                        enemy.setSpriteSet("charmander");
                        break;
                    case SHOTGUN_SPAWN:
                        enemy = new ShotgunEnemy();
                        enemy.setDmgMult(level * 0.5f);
                        enemy.setFireRate(15 - (.25 * (level - 1)));
                        enemy.setVelocity(1 + 0.05 * (level - 1));
                        if (level > CAP_LEVEL) {
                            enemy.setDmgMult(CAP_LEVEL + (level - CAP_LEVEL) * 0.25f);
                            enemy.setFireRate(7.5);
                            enemy.setVelocity(1.5);
                        }
                        enemy.setHealth(2 * level);
                        enemy.setSpriteSet("pikachu");
                        break;
                    case SNIPER_SPAWN:
                        enemy = new SniperEnemy();
                        enemy.setDmgMult(level * 1.0f);
                        enemy.setFireRate(15);
                        if (level > CAP_LEVEL) {
                            enemy.setDmgMult(CAP_LEVEL + (level - CAP_LEVEL) * 0.5f);
                            enemy.setFireRate(7.5);
                        }
                        enemy.setHealth(1 * level);
                        enemy.setSpriteSet("squirtle");
                        break;
                    default:
                        break;
                }
                if (enemy != null) {
                    double x = (j * FloorManager.SPACE_SIZE);
                    double y = (i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT;
                    enemy.getGObject().setLocation(x, y);
                    program.add(enemy.getGObject());
                    enemies.add(enemy);
                }
            }
        }
    }

    /**
     * Renders upgrade info box
     *
     * @author Alex Reynen
     */
    private void infoDrawing() {
        backingColor = new GRect(0, 0, MainApplication.WINDOW_WIDTH, HEADER_HEIGHT);
        // Off black color
        //backingColor.setFillColor(new Color(0,1,11));
        // Pure black, which I (Alex R) prefer
        backingColor.setFillColor(Color.BLACK);
        backingColor.setFilled(true);
        program.add(backingColor);
        player.setFireDamage(1 + Integer.parseInt(PlayerData.getMap().get("FireSpeedUpgrades")));
        player.setRangedDamage(1 + Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")));
        player.setMoveSpeed(1 + Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));

        infoText = new GParagraph("Level: " + level + "\nFiring Speed: " + player.getFireSpeed() + "\nRanged Damage: "
          + player.getRangedDamage() + "\nMove Speed: " + player.getMoveSpeed(), 0, 0);

        infoText.setFont(hdrFont);
        infoText.setColor(Color.WHITE);
        infoText.move(infoBox.getX() + (infoBox.getWidth() - infoText.getWidth()) / 2,
          (infoBox.getY() - 50 + infoText.getHeight()) / 2);

        program.add(infoText);
    }

    /**
     * Renders level banner when reaching new floor
     */
    private void drawLevelAlert() {
        levelAlert = new GButtonMD("Level " + level, (MainApplication.WINDOW_WIDTH - LEVEL_ALERT_X_SIZE) / 2,
          (MainApplication.WINDOW_HEIGHT) / 3, LEVEL_ALERT_X_SIZE, LEVEL_ALERT_Y_SIZE, "blue");
        levelAlert.setVisible(true);
        program.add(levelAlert);
        levelAlert.sendToFront();
    }

    /**
     * Handles pause menu
     */
    private void initPausing() {
        GImage backing = new GImage("../media/pause.png");
        pauseElements.add(backing);

        quitPauseBtn = new GButtonMD("Exit to Menu", (MainApplication.WINDOW_WIDTH - 200) / 2,
          MainApplication.getWindowHeight() / 2, 200, 100, "green");
        pauseElements.add(quitPauseBtn);

        btnAudio = new GImage("soundoff_white.png", MainApplication.WINDOW_WIDTH - 100, 0);
        int sounds = Integer.parseInt(PlayerData.getMap().get("Sounds"));
        if (sounds == 0) {
            btnAudio.setImage("soundoff_white.png");
        } else {
            btnAudio.setImage("soundon_white.png");
        }
        btnAudio.setSize(100, 100);
        pauseElements.add(btnAudio);
    }


    // =========== REMOVE METHODS ============ //
	/*
	 * Methods that handle removing elements
	 */

    /**
     * Removes all game elements
     */
    @Override
    public void hideContents() {
        removeHeader();
        removeField();
        removePlayer();
        removeEnemies();
        removeProjectiles();
    }

    /**
     * Removes header
     */
    public void removeHeader() {
        program.remove(minimapBox);
        program.remove(infoBox);
        program.remove(infoText);
        program.remove(healthBox);
        program.remove(healthLabel);
        program.remove(backingColor);
        minimapDestructor();
        program.remove(bossIcon);
        program.remove(playerOnMap);
        pauseElements = new ArrayList<>();
    }

    /**
     * Removes minimap
     */
    private void minimapDestructor() {
        for (GObject tile : minimap) {
            program.remove(tile);
        }
        program.remove(playerOnMap);
        program.remove(bossIcon);
        minimap.clear();
    }

    /**
     * Removes room display
     */
    public void removeField() {
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                program.remove(room.getSpace(i, j).getGObject());
            }
        }
    }

    /**
     * Removes player
     */
    public void removePlayer() {
        program.remove(player.getGObject());
    }

    /**
     * Removes all active enemies
     */
    public void removeEnemies() {
        List<Enemy> enemies = program.getEntityManager().getEnemies();
        for (Enemy enemy : enemies) {
            program.remove(enemy.getGObject());
        }
        enemies.clear();
    }

    /**
     * Removes all active projectiles
     */
    public void removeProjectiles() {
        List<Projectile> projectiles = program.getEntityManager().getProjectiles();
        for (Projectile projectile : projectiles) {
            program.remove(projectile.getGObject());
        }
        projectiles.clear();
    }


    // ===== TIMER ACTIONS ====== //
	/*
	 * Methods performed constantly by the timer
	 */

    /**
     * Actions run constantly by the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        movePlayer();
        enemyMove();
        enemyAttack();
        checkProjectileCollision();

        // Clears room if all enemies defeated
        if (program.getEntityManager().getEnemies().size() == 0) {
            clearRoom();
        }

        // Game over
        if (player.getHealth() <= 0) {
            onDeath();
        }
    }

    /**
     * Opens all doors (and stairs) that can be legally opened in the current room
     *
     * @author Jacob Shour
     */
    public void clearRoom() {
        if (room.getType() == RoomType.BOSS) {
            for (int row = 0; row < FloorManager.ROOM_ROWS; row++) {
                for (int col = 0; col < FloorManager.ROOM_COLS; col++) {
                    if (room.getSpace(row, col).getType() == SpaceType.STAIRS) {
                        Space space = room.getSpace(row, col);

                        program.remove(space.getGObject());
                        GImage openedStairs = new GImage("../media/stairs.png",
                          (space.getNumCol() * FloorManager.SPACE_SIZE),
                          (space.getNumRow() * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
                        openedStairs.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
                        space.setGObject(openedStairs);
                        program.add(space.getGObject());
                        player.getGObject().sendToFront();
                    }
                }
            }
        }

        // North
        if (room.getExits().contains(Exit.NORTH) && FloorManager.getCurrentPlayerRow() > 0 && FloorManager.getFloor()
          .getRoom(FloorManager.getCurrentPlayerRow() - 1, FloorManager.getCurrentPlayerCol()) != null) {
            openDoor(room.getSpace(0, (int) (Math.ceil(FloorManager.ROOM_COLS / 2))));
            FloorManager.addOpenExit(Exit.NORTH);
        }

        // South
        if (room.getExits().contains(Exit.SOUTH) && FloorManager.getCurrentPlayerRow() < FloorManager.FLOOR_ROWS - 1
          && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow() + 1,
          FloorManager.getCurrentPlayerCol()) != null) {
            openDoor(room.getSpace(FloorManager.ROOM_ROWS - 1, (int) (Math.ceil(FloorManager.ROOM_COLS / 2))));
            FloorManager.addOpenExit(Exit.SOUTH);
        }

        // East
        if (room.getExits().contains(Exit.EAST) && FloorManager.getCurrentPlayerCol() < FloorManager.FLOOR_COLS - 1
          && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow(),
          FloorManager.getCurrentPlayerCol() + 1) != null) {
            for (int i = FloorManager.ROOM_COLS - 1; ; i--) {
                if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
                    openDoor(room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i));
                    FloorManager.addOpenExit(Exit.EAST);
                    break;
                }
            }
        }

        // West
        if (room.getExits().contains(Exit.WEST) && FloorManager.getCurrentPlayerCol() > 0 && FloorManager.getFloor()
          .getRoom(FloorManager.getCurrentPlayerRow(), FloorManager.getCurrentPlayerCol() - 1) != null) {
            for (int i = 0; ; i++) {
                if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
                    openDoor(room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i));
                    FloorManager.addOpenExit(Exit.WEST);
                    break;
                }
            }
        }

        room.setCleared(true);
    }

    /**
     * Opens a single door at the given space
     *
     * @param space Space to open a door at
     * @author Jacob Shour
     */
    private void openDoor(Space space) {
        String path = variablePath(FILE_PATH);

        program.remove(space.getGObject());
        GImage openedDoor = new GImage(path + "ground.png", (space.getNumCol() * FloorManager.SPACE_SIZE),
          (space.getNumRow() * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
        openedDoor.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
        space.setGObject(openedDoor);
        program.add(space.getGObject());
        player.getGObject().sendToFront();
    }


    /**
     * Handles all needed actions on player death
     */
    public void onDeath() {
        PlayerData.writeFile();
        direction = null;
        t.stop();
        resetGame();
        PlayerData.updateMap("PreviousRun", level);
        level = 1;
        program.switchToDeath(killer);
    }

    /**
     * Resets several elements to prepare for returning to the menu
     */
    public void resetGame() {
        removeField();
        FloorManager.generateNewFloor();
        FloorManager.resetOpenExits();
        room = program.getFloorManager().getSpawnRoom();
        collisionManager.setRoom(room);
    }


    // ===== PLAYER CONTROL ====== //
	/*
	 * Methods that handle controlling the player
	 */

    /**
     * Moves player based on keyboard input and handles transitions between rooms and floors
     *
     * @author Devin French (movement)
     * @author Jacob Shour (room/floor transitions)
     */
    private void movePlayer() {
        double x = 0;
        double y = 0;
        if (direction == Direction.NORTH) {
            y = -1;
            player.setFacing("north");// Sets facing for player rendering
        } else if (direction == Direction.SOUTH) {
            y = 1;
            player.setFacing("south");
        } else if (direction == Direction.WEST) {
            x = -1;
            player.setFacing("west");
        } else if (direction == Direction.EAST) {
            x = 1;
            player.setFacing("east");
        }
		/*
		 * Here the SPEED_EFFECT changes how quickly guy moves with speed upgrades. With
		 * it set to .25, as it is when I commit this, at 10 upgrades (max) he moves at
		 * 7.5 (1.5x the original speed of 5). We'll probably change this as we need to
		 * with balancing and such. This speed is easy to see compared to the projectile
		 */
        x *= (5 + SPEED_EFFECT * Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));
        y *= (5 + SPEED_EFFECT * Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));

        if (room.isCleared() && collisionManager.getPlayerSpaceType(x, y) == SpaceType.DOOR) {
            Space space = getPlayerSpace();

            // North
            if (FloorManager.getOpenExits().contains(Exit.NORTH)
              && space.getNumCol() == Math.ceil(FloorManager.ROOM_COLS / 2)
              && space.getNumRow() < Math.ceil(FloorManager.ROOM_ROWS / 2)) {
                playerOnMap.move(0, -(HEADER_HEIGHT - 20) / FloorManager.FLOOR_ROWS);
                moveRoom(Exit.NORTH);
            }

            // South
            else if (FloorManager.getOpenExits().contains(Exit.SOUTH)
              && space.getNumCol() == (Math.ceil(FloorManager.ROOM_COLS / 2))
              && space.getNumRow() > Math.ceil(FloorManager.ROOM_ROWS / 2)) {
                playerOnMap.move(0, (HEADER_HEIGHT - 20) / FloorManager.FLOOR_ROWS);
                moveRoom(Exit.SOUTH);
            }

            // East
            else if (FloorManager.getOpenExits().contains(Exit.EAST)
              && space.getNumRow() == (Math.ceil(FloorManager.ROOM_ROWS / 2))
              && space.getNumCol() > Math.ceil(FloorManager.ROOM_COLS / 2)) {
                playerOnMap.move((HEADER_WIDTH - 20) / FloorManager.FLOOR_COLS, 0);
                moveRoom(Exit.EAST);
            }

            // West
            else if (FloorManager.getOpenExits().contains(Exit.WEST)
              && space.getNumRow() == (Math.ceil(FloorManager.ROOM_ROWS / 2))
              && space.getNumCol() < Math.ceil(FloorManager.ROOM_COLS / 2)) {
                playerOnMap.move(-(HEADER_WIDTH - 20) / FloorManager.FLOOR_COLS, 0);
                moveRoom(Exit.WEST);
            }
            removeProjectiles();
        } else if (room.isCleared() && collisionManager.getPlayerSpaceType(x, y) == SpaceType.STAIRS) {
            level++;
            FloorManager.resetOpenExits();
            resetGame();
            generateMusic();
            playMusic();
            createImageList();
            showField();
            player.getGObject().setLocation(Math.ceil(FloorManager.ROOM_COLS / 2) * FloorManager.SPACE_SIZE,
              Math.ceil(FloorManager.ROOM_ROWS / 2) * FloorManager.SPACE_SIZE + HEADER_HEIGHT);
            // player.getGObject().sendToFront();
            t.stop();
            showField();
            drawLevelAlert();
            removeHeader();
            showHeader();
            initPausing();
        } else {
            if ((x != 0 || y != 0) && collisionManager.playerCanMove(x, y)) {
                player.move(x, y);
                player.setIsMoving(true);// Sets player move state
            } else {
                player.setIsMoving(false);
            }
        }
        animatePlayer();// Animating player
    }

    /**
     * Moves player to an adjacent room
     *
     * @param exit The direction the player left the previous room
     * @author Jacob Shour
     */
    private void moveRoom(Exit exit) {
        int temp;
        FloorManager.resetOpenExits();
        removeField();

        switch (exit) {
            case NORTH:
                FloorManager.setCurrentPlayerRow(FloorManager.getCurrentPlayerRow() - 1);
                break;

            case SOUTH:
                FloorManager.setCurrentPlayerRow(FloorManager.getCurrentPlayerRow() + 1);
                break;

            case EAST:
                FloorManager.setCurrentPlayerCol(FloorManager.getCurrentPlayerCol() + 1);
                break;

            case WEST:
                FloorManager.setCurrentPlayerCol(FloorManager.getCurrentPlayerCol() - 1);
                break;
        }

        room = FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow(), FloorManager.getCurrentPlayerCol());
        collisionManager.setRoom(room);

        playMusic();

        switch (exit) {
            case NORTH:
                player.getGObject().setLocation((Math.ceil(FloorManager.ROOM_COLS / 2)) * FloorManager.SPACE_SIZE,
                  (FloorManager.ROOM_ROWS - 2) * FloorManager.SPACE_SIZE + HEADER_HEIGHT);
                break;

            case SOUTH:
                player.getGObject().setLocation((Math.ceil(FloorManager.ROOM_COLS / 2)) * FloorManager.SPACE_SIZE,
                  FloorManager.SPACE_SIZE + HEADER_HEIGHT);
                break;

            case EAST:
                for (int i = 0; ; i++) {
                    if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
                        temp = i + 1;
                        break;
                    }
                }
                player.getGObject().setLocation(temp * FloorManager.SPACE_SIZE,
                  (Math.ceil(FloorManager.ROOM_ROWS / 2)) * FloorManager.SPACE_SIZE + HEADER_HEIGHT);
                break;

            case WEST:
                for (int i = FloorManager.ROOM_COLS - 1; ; i--) {
                    if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
                        temp = i - 1;
                        break;
                    }
                }
                player.getGObject().setLocation(temp * FloorManager.SPACE_SIZE,
                  (Math.ceil(FloorManager.ROOM_ROWS / 2)) * FloorManager.SPACE_SIZE + HEADER_HEIGHT);
                break;
        }

        createImageList();
        showField();
        if (!room.isCleared())
            showEnemies();
        player.getGObject().sendToFront();
    }

    /**
     * Gets the player's current space
     *
     * @return The space the player is present at
     * @author Jacob Shour
     */
    private Space getPlayerSpace() {
        for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
            for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
                Space space = room.getSpace(i, j);
                double x = player.getX() + FloorManager.SPACE_SIZE / 2;
                double y = player.getY() + FloorManager.SPACE_SIZE / 2;
                if (space.getGObject().contains(x, y)) {
                    return space;
                }
            }
        }
        return null;
    }

    /**
     * Converts keyboard input into a Direction enum
     *
     * @return The direction the player should move
     * @author Devin French
     */
    private Direction getDirection(int key) {
        if (key == KeyEvent.VK_W) {
            return Direction.NORTH;
        } else if (key == KeyEvent.VK_S) {
            return Direction.SOUTH;
        } else if (key == KeyEvent.VK_A) {
            return Direction.WEST;
        } else if (key == KeyEvent.VK_D) {
            return Direction.EAST;
        }
        return null;
    }

    /**
     * Renders the walking animation as the player moves
     *
     * @author Alexander Ng
     */
    private void animatePlayer() {
        if (player.getIsMoving()) {
            if (counter % 7 == 0) {
                ((GImage) player.getGObject())
                  .setImage("../media/sprites/player/player_walking_" + player.getFacing() + "_1.png");
            } else if (counter % 7 == 3) {
                ((GImage) player.getGObject())
                  .setImage("../media/sprites/player/player_standing_" + player.getFacing() + ".png");

            } else if (counter % 7 == 6) {
                ((GImage) player.getGObject())
                  .setImage("../media/sprites/player/player_walking_" + player.getFacing() + "_2.png");
            }
        } else {
            ((GImage) player.getGObject())
              .setImage("../media/sprites/player/player_standing_" + player.getFacing() + ".png");

        }
    }

    /**
     * Handles actions on click
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Remove Level indicator and start timer
        if (levelAlert.isVisible()) {
            levelAlert.setVisible(false);
            program.remove(levelAlert);
            t.start();
            return;
        }
        GObject obj = program.getElementAt(e.getX(), e.getY());
        if (obj == quitPauseBtn) {
            PlayerData.writeFile();
            for (GObject o : pauseElements) {
                program.remove(o);
            }
            resetGame();
            PlayerData.updateMap("PreviousRun", level);
            level = 1;
            program.switchToMenu();
        } else if (obj == btnAudio) {
            int sounds = Integer.parseInt(PlayerData.getMap().get("Sounds"));
            if (sounds == 1) {
                btnAudio.setImage("soundoff_white.png");
                btnAudio.setSize(100, 100);
                PlayerData.updateMap("Sounds", 0);
            } else {
                AudioPlayer.getInstance().pauseMusic();
                btnAudio.setImage("soundon_white.png");
                btnAudio.setSize(100, 100);
                PlayerData.updateMap("Sounds", 1);
            }
            PlayerData.writeFile();
        }

        if (t.isRunning()) {
            for (Projectile p : player.attack(e.getX(), e.getY())) {
                program.getEntityManager().getProjectiles().add(p);
                program.add(p.getGObject());
            }
        }
    }


    /**
     * Handles actions on key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Direction dir = getDirection(key);
        if (dir != null) {
            keysPressed.add(key);
            direction = dir;
        } else if (key == KeyEvent.VK_ESCAPE && !levelAlert.isVisible()) {
            if (t.isRunning()) {
                for (GObject o : pauseElements) {
                    program.add(o);
                }
                direction = null;
                t.stop();
                AudioPlayer.getInstance().pauseMusic();
            } else {
                for (GObject o : pauseElements) {
                    program.remove(o);
                }
                t.start();
                if (Integer.parseInt(PlayerData.getMap().get("Sounds")) == 1) {
                    AudioPlayer.getInstance().resumeMusic();
                }
            }
        }
    }

    /**
     * Handles actions on key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (keysPressed.contains(key)) {
            keysPressed.remove(key);
        }
        if (keysPressed.isEmpty()) {
            direction = null;
        } else {
            direction = getDirection(keysPressed.iterator().next());
        }
    }


    // =========== ENEMY AI ============ //
	/*
	 * Methods that handle how enemies behave
	 */

    /**
     * Automatically moves all enemies in the room
     */
    private void enemyMove() {
        for (Enemy enemy : program.getEntityManager().getEnemies()) {
            enemy.move(collisionManager, player);
            animateEnemy(enemy);
        }
    }

    /**
     * Renders the enemy walking animation as an enemy moves
     *
     * @param enemy Enemy to animate
     * @author Alexander Ng
     */
    private void animateEnemy(Enemy enemy) {
        if (enemy.getIsMoving()) {

            if (counter % 15 == 0) {
                ((GImage) enemy.getGObject())
                  .setImage("../media/sprites/" + enemy.getSpriteSet() + "/" + enemy.getSpriteSet() + "_walking_" + enemy.getFacing() + "_1.png");
            } else if (counter % 15 == 7) {
                ((GImage) enemy.getGObject())
                  .setImage("../media/sprites/" + enemy.getSpriteSet() + "/" + enemy.getSpriteSet() + "_standing_" + enemy.getFacing() + ".png");

            } else if (counter % 15 == 14) {
                ((GImage) enemy.getGObject())
                  .setImage("../media/sprites/" + enemy.getSpriteSet() + "/" + enemy.getSpriteSet() + "_walking_" + enemy.getFacing() + "_2.png");
            }
            // Enemy is always moving

        } else {
            ((GImage) enemy.getGObject())
              .setImage("../media/sprites/" + enemy.getSpriteSet() + "/" + enemy.getSpriteSet() + "_standing_" + enemy.getFacing() + ".png");
        }

    }

    /**
     * Makes all enemies attempt to attack the player
     */
    private void enemyAttack() {
        for (Enemy enemy : program.getEntityManager().getEnemies()) {
            for (Projectile p : enemy.attack(player.getCenterX(), player.getCenterY())) {
                program.getEntityManager().getProjectiles().add(p);
                program.add(p.getGObject());
            }
        }
    }


    // =========== PROJECTILE HANDLING ============ //

    /**
     * Handles all enemy and player projectile collision
     *
     * @author Devin French
     */
    private void checkProjectileCollision() {
        for (Projectile p : program.getEntityManager().getProjectiles()) {
            p.move();
            boolean collision = false;
            if (collisionManager.isPlayerCollision(p)) {
                collision = true;
                player.setHealth(player.getHealth() - p.getDamage());
                healthLabel.setLabel("Health: " + player.getHealth());
                killer = p.getSource();
            }
            for (Enemy enemy : program.getEntityManager().getEnemies()) {
                if (collisionManager.isEnemyCollision(enemy, p)) {
                    collision = true;
                    enemy.setHealth(enemy.getHealth() - p.getDamage());
                    if (enemy.getHealth() <= 0) {
                        program.remove(enemy.getGObject());
                        program.getEntityManager().getEnemies().remove(enemy);
                        PlayerData.updateMap("Coin", Integer.parseInt(PlayerData.getMap().get("Coin")) + 1);
                    }
                    break;
                }
            }
            if (collisionManager.isTerrainCollision(p)) {
                collision = true;
            }
            if (collision) {
                program.getEntityManager().getProjectiles().remove(p);
                program.remove(p.getGObject());
            }
        }
    }

    // =========== MUSIC HANDLING ============ //

    private void generateMusic() {
        floorMusicFile = AudioPlayer.getInstance().getFloorMusic();
        bossMusicFile = AudioPlayer.getInstance().getBossMusic();
        System.out.println("-- Music --");
        System.out.println("Floor: " + floorMusicFile + " Boss: " + bossMusicFile);
    }

    private void playMusic() {
        if (room.getType() == RoomType.BOSS) {
            AudioPlayer.getInstance().playMusic(AudioPlayer.MUSIC_FOLDER, bossMusicFile, 0.05);
        } else {
            AudioPlayer.getInstance().playMusic(AudioPlayer.MUSIC_FOLDER, floorMusicFile, 0.06);
        }
    }

}
