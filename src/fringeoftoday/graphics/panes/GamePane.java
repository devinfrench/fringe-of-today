package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.core.CollisionManager;
import fringeoftoday.entities.Enemy;
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
import fringeoftoday.graphics.GParagraph;
import fringeoftoday.graphics.Sprites;
import starter.GButtonMD;

public class GamePane extends GraphicsPane implements ActionListener {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH / 3;
	public static final int HEADER_HEIGHT = 196;
	public static final String FILE_PATH = "../media/textures/";
	public static final int DELAY_MS = 25;
	private static final int LEVEL_ALERT_X_SIZE = 600;
	private static final int LEVEL_ALERT_Y_SIZE = 150;
	private static final double SPEED_EFFECT = .25; // How effective the speed upgrades are, I've found .25 to be pretty
	// good
	public Direction direction;
	private Set<Integer> keysPressed = new HashSet<>();

	private ArrayList<GObject> minimap = new ArrayList<GObject>();

	private Font hdrFont = new Font("PKMN Mystery Dungeon", 0, 60);
	private int level = -1; // Work on this when we get it in
	private GRect minimapBox; // Minimap, left header
	private GImage bossIcon;
	private GOval playerOnMap;
	private GRect infoBox; // Center header
	private GParagraph infoText;// Center header content
	private GRect healthBox; // Right header
	private GButtonMD levelAlert;
	private GLabel healthLabel;
	private GRect backingColor;
	private Room room;
	private Player player;
	private CollisionManager collisionManager;
	private Timer t;
	private int counter;
	private ArrayList<GObject> pauseElements = new ArrayList<GObject>();
	private GButtonMD quitPauseBtn;

	public GamePane(MainApplication app) {
		super();
		program = app;
		loadFont();
		// HEADER
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);

		infoBox = new GRect(HEADER_WIDTH, 0, HEADER_WIDTH, HEADER_HEIGHT);

		healthBox = new GRect(HEADER_WIDTH * 2, 0, HEADER_WIDTH, HEADER_HEIGHT);

		// FIELD

		// Room
		room = program.getFloorManager().getSpawnRoom();

		// Player
		player = program.getEntityManager().getPlayer();

		// Collision
		collisionManager = new CollisionManager(program.getEntityManager(), room);

		// Timer
		t = new Timer(DELAY_MS, this);
		counter = 0;
	}

	private void infoDrawing() {
		backingColor = new GRect(0, 0, MainApplication.WINDOW_WIDTH, HEADER_HEIGHT);
		// Off black color
//		backingColor.setFillColor(new Color(0,1,11));
		// Pure black, which I (Alex R) prefer
		backingColor.setFillColor(Color.BLACK);
		backingColor.setFilled(true);
		program.add(backingColor);
		player.setMeleeDamage(1 + Integer.parseInt(PlayerData.getMap().get("MeleeUpgrades")));
		player.setRangedDamage(1 + Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")));
		player.setMoveSpeed(1 + Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));

		infoText = new GParagraph("Level: " + level + "\nMelee Damage: " + player.getMeleeDamage() + "\nRanged Damage: "
				+ player.getRangedDamage() + "\nMove Speed: " + player.getMoveSpeed(), 0, 0);

		infoText.setFont(hdrFont);
		infoText.setColor(Color.WHITE);
		infoText.move(infoBox.getX() + (infoBox.getWidth() - infoText.getWidth()) / 2,
				(infoBox.getY() - 50 + infoText.getHeight()) / 2);

		program.add(infoText);
	}

	private void initHealth() {
		player.setHealth(player.getMaxHealth());
		drawHealth(player.getHealth());
	}

	private void drawHealth(int health) {
		healthLabel = new GLabel("Health: " + health, HEADER_WIDTH * 2.25, HEADER_HEIGHT / 1.9);
		healthLabel.setFont(hdrFont);
		healthLabel.setColor(Color.WHITE);
		program.add(healthLabel);
	}

	private void drawLevelAlert() {
		levelAlert = new GButtonMD("Level " + level, (MainApplication.WINDOW_WIDTH - LEVEL_ALERT_X_SIZE) / 2,
				(MainApplication.WINDOW_HEIGHT) / 2, LEVEL_ALERT_X_SIZE, LEVEL_ALERT_Y_SIZE, "blue");
		levelAlert.setVisible(true);
		program.add(levelAlert);
	}

	@Override
	public void showContents() {// split showContents into showHeader and showField for clarity
		player.setMaxHealth(Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 3);
		infoDrawing();
		showHeader(); // Top bar
		createImageList();
		showField(); // Game field
		showPlayer();
		showEnemies();
		initHealth();
		drawLevelAlert();
		initPausing();
	}

	@Override
	public void hideContents() {
		removeHeader();
		removeField();
		removePlayer();
		removeEnemies();
		removeProjectiles();
		program.remove(healthLabel);
		program.remove(infoText);
	}

	public void showHeader() {
		program.add(minimapBox);
		program.add(infoBox);
		program.add(healthBox);
		minimapBuilder();
	}

	public void removeHeader() {
		program.remove(minimapBox);
		program.remove(infoBox);
		program.remove(infoText);
		program.remove(healthBox);
		program.remove(backingColor);
		minimapDestructor();
		program.remove(bossIcon);
		program.remove(playerOnMap);
		pauseElements = new ArrayList<>();
	}

	public void showField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				program.add(room.getSpace(i, j).getGObject());
			}
		}
	}

	public void removeField() {
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				program.remove(room.getSpace(i, j).getGObject());
			}
		}
	}

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

	public void showPlayer() {
		Image sprite = Sprites.loadSprite("player_sprite_temp.png");
		player.setGObject(new GImage(sprite, 24 * (FloorManager.SPACE_SIZE / 2), 16 * (FloorManager.SPACE_SIZE / 2)));
		program.add(player.getGObject());
	}

	public void removePlayer() {
		program.remove(player.getGObject());
	}

	public void showEnemies() {
		List<Enemy> enemies = program.getEntityManager().getEnemies();
		for (int i = 0; i < FloorManager.ROOM_ROWS; i++) {
			for (int j = 0; j < FloorManager.ROOM_COLS; j++) {
				Space space = room.getSpace(i, j);
				Enemy enemy = null;
				switch (space.getType()) {
				// TODO scaling
				case BASIC_SPAWN:
					enemy = new StandardEnemy();
					enemy.setDmgMult(0.5f);
					enemy.setFireRate(10);
					enemy.setHealth(1);
					enemy.setVelocity(1);
					break;
				case SHOTGUN_SPAWN:
					enemy = new ShotgunEnemy();
					enemy.setDmgMult(0.5f);
					enemy.setFireRate(15);
					enemy.setHealth(1);
					enemy.setVelocity(1);
					break;
				case SNIPER_SPAWN:
					enemy = new SniperEnemy();
					enemy.setDmgMult(1.0f);
					enemy.setFireRate(15);
					enemy.setHealth(1);
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

	public void removeEnemies() {
		List<Enemy> enemies = program.getEntityManager().getEnemies();
		for (Enemy enemy : enemies) {
			program.remove(enemy.getGObject());
		}
		enemies.clear();
	}

	public void removeProjectiles() {
		List<Projectile> projectiles = program.getEntityManager().getProjectiles();
		for (Projectile projectile : projectiles) {
			program.remove(projectile.getGObject());
		}
		projectiles.clear();
	}

	public void onDeath() {// Trigger this when player is dead, should add other functions - tally score,
		// etc.
		PlayerData.writeFile();
		t.stop();
		program.switchToDeath();
	}
	
	public void clearRoom() {
		//TODO Set room coords
		Room room = FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow(), FloorManager.getCurrentPlayerCol());
		
		//North
		if (room.getExits().contains(Exit.NORTH) && FloorManager.getCurrentPlayerRow() > 0 && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow() - 1, FloorManager.getCurrentPlayerCol()) != null) {
			openDoor(room.getSpace(0, (int) (Math.ceil(FloorManager.ROOM_COLS / 2))));
			FloorManager.addOpenExit(Exit.NORTH);
		}
		
		//South
		if (room.getExits().contains(Exit.SOUTH) && FloorManager.getCurrentPlayerRow() < FloorManager.ROOM_ROWS - 1 && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow() + 1, FloorManager.getCurrentPlayerCol()) != null) {
			openDoor(room.getSpace(FloorManager.ROOM_ROWS - 1, (int) (Math.ceil(FloorManager.ROOM_COLS / 2))));
			FloorManager.addOpenExit(Exit.SOUTH);
		}
		
		//East
		if (room.getExits().contains(Exit.EAST) && FloorManager.getCurrentPlayerCol() < FloorManager.ROOM_COLS - 1 && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow(), FloorManager.getCurrentPlayerCol() + 1) != null) {
			for (int i = FloorManager.ROOM_COLS - 1;; i--) {
				if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
					openDoor(room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i));
					FloorManager.addOpenExit(Exit.EAST);
					break;
				}
			}
		}
		
		//West
		if (room.getExits().contains(Exit.WEST) && FloorManager.getCurrentPlayerCol() > 0 && FloorManager.getFloor().getRoom(FloorManager.getCurrentPlayerRow(), FloorManager.getCurrentPlayerCol() - 1) != null) {
			for (int i = 0;; i++) {
				if (room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i).getType() == SpaceType.DOOR) {
					openDoor(room.getSpace((int) (Math.ceil(FloorManager.ROOM_ROWS / 2)), i));
					FloorManager.addOpenExit(Exit.WEST);
					break;
				}
			}
		}
		
		room.setCleared(true);
	}
	
	private void openDoor(Space space) {
		String path = variablePath(FILE_PATH);
		
		program.remove(space.getGObject());
		GImage openedDoor = new GImage(path + "ground.png", 
				(space.getNumCol() * FloorManager.SPACE_SIZE), (space.getNumRow() * FloorManager.SPACE_SIZE) + HEADER_HEIGHT);
		openedDoor.setSize(FloorManager.SPACE_SIZE, FloorManager.SPACE_SIZE);
		space.setGObject(openedDoor);
		program.add(space.getGObject());
		space.getGObject().sendToBack();
	}

	private void initPausing() {
		GImage backing = new GImage("../media/pause.png");
		pauseElements.add(backing);

		quitPauseBtn = new GButtonMD("Exit to Menu", (MainApplication.WINDOW_WIDTH - 200) / 2,
				MainApplication.getWindowHeight() / 2, 200, 100, "green");
		pauseElements.add(quitPauseBtn);

	}

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
			program.switchToMenu();
		}

		if (t.isRunning()) {
			for (Projectile p : player.attack(e.getX(), e.getY())) {
				program.getEntityManager().getProjectiles().add(p);
				program.add(p.getGObject());
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Direction dir = getDirection(key);
		if (dir != null) {
			keysPressed.add(key);
			direction = dir;
		} else if (key == KeyEvent.VK_ESCAPE) {
			if (t.isRunning()) {
				for (GObject o : pauseElements) {
					program.add(o);
				}
				t.stop();
			} else {
				for (GObject o : pauseElements) {
					program.remove(o);
				}
				t.start();
			}
		}
	}

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

	// Timer loop
	@Override
	public void actionPerformed(ActionEvent e) {
		counter++;
		movePlayer();
		
		animatePlayer();
		
		enemyMove();

		enemyAttack();

		checkProjectileCollision();

		if (player.getHealth() <= 0) {
			onDeath();
		}
		
		if (program.getEntityManager().getEnemies().size() == 0) {
			clearRoom();
		}
	}

	private void movePlayer() {
		double x = 0;
		double y = 0;
		if (direction == Direction.NORTH) {
			y = -1;
		} else if (direction == Direction.SOUTH) {
			y = 1;
		} else if (direction == Direction.WEST) {
			x = -1;
		} else if (direction == Direction.EAST) {
			x = 1;
		}
		/*
		 * Here the SPEED_EFFECT changes how quickly guy moves with speed upgrades. With
		 * it set to .25, as it is when I commit this, at 10 upgrades (max) he moves at
		 * 7.5 (1.5x the original speed of 5). We'll probably change this as we need to
		 * with balancing and such. This speed is easy to see compared to the projectile
		 */
		x *= (5 + SPEED_EFFECT * Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));
		y *= (5 + SPEED_EFFECT * Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));
		if ((x != 0 || y != 0) && collisionManager.playerCanMove(x, y)) {
			player.move(x, y);
			player.setIsMoving(true);
		}
		else {
			player.setIsMoving(false);
		}
	}
	
	private void animatePlayer() {
		
	}
	
	private void checkProjectileCollision() {
		for (Projectile p : program.getEntityManager().getProjectiles()) {
			p.move();
			boolean collision = false;
			if (collisionManager.isPlayerCollision(p)) {
				collision = true;
				player.setHealth(player.getHealth() - p.getDamage());
				healthLabel.setLabel("Health: " + player.getHealth());
			}
			for (Enemy enemy : program.getEntityManager().getEnemies()) {
				if (collisionManager.isEnemyCollision(enemy, p)) {
					collision = true;
					enemy.setHealth(enemy.getHealth() - p.getDamage());
					if (enemy.getHealth() <= 0) {
						program.remove(enemy.getGObject());
						program.getEntityManager().getEnemies().remove(enemy);
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

	private void enemyAttack() {
		GObject obj = player.getGObject();
		double x = obj.getX() + obj.getWidth() / 2;
		double y = obj.getY() + obj.getHeight() / 2;
		for (Enemy enemy : program.getEntityManager().getEnemies()) {
			for (Projectile p : enemy.attack(x, y)) {
				program.getEntityManager().getProjectiles().add(p);
				program.add(p.getGObject());
			}
		}
	}

	private void enemyMove() {
		for (Enemy enemy : program.getEntityManager().getEnemies()) {
			enemy.move(collisionManager, player);
		}
	}

	private void minimapDestructor() {
		for (GObject tile : minimap) {
			program.remove(tile);
		}
	}

	private void colorTile(Room r, GRect tile) {
		if (r == null) {
			tile.setVisible(false);
			// Colors pending
		} else if (r.getType() == RoomType.STANDARD) {
			tile.setFillColor(Color.LIGHT_GRAY);
			tile.setFilled(true);
		} else if (r.getType() == RoomType.SPAWN) {
			tile.setFillColor(Color.GREEN);
			playerOnMap = new GOval(tile.getX() + (tile.getWidth()-tile.getHeight()*.75)/2, tile.getY() +tile.getHeight()*.125, tile.getHeight()*.75, tile.getHeight()*.75);
			playerOnMap.setFillColor(Color.BLUE);
			playerOnMap.setFilled(true);
			tile.setFilled(true);
		} else if (r.getType() == RoomType.BOSS) {
			tile.setFillColor(Color.RED);
			bossIcon = new GImage("boss_icon.png", tile.getX() + (tile.getWidth()-tile.getHeight())/2, tile.getY());
			bossIcon.setSize(tile.getHeight(), tile.getHeight());
			tile.setFilled(true);
		}
	}

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

	private String variablePath(String path) {
		if (FloorManager.getFloor().getLevel() <= 5)
			path = path + "RockPath/";
		else if (FloorManager.getFloor().getLevel() >= 6 && FloorManager.getFloor().getLevel() <= 10)
			path = path + "SealedRuin/";
		else if (FloorManager.getFloor().getLevel() >= 11 && FloorManager.getFloor().getLevel() <= 15)
			path = path + "SteamCave/";
		else
			path = path + "DarkCrater/";
		
		return path;
	}
}
