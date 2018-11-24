package fringeoftoday.graphics.panes;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.core.CollisionManager;
import fringeoftoday.entities.Enemy;
import fringeoftoday.entities.Player;
import fringeoftoday.entities.Projectile;
import fringeoftoday.entities.StandardEnemy;
import fringeoftoday.floor.Direction;
import fringeoftoday.floor.FloorManager;
import fringeoftoday.floor.Room;
import fringeoftoday.floor.Space;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.GParagraph;
import fringeoftoday.graphics.Sprites;
import starter.GButtonMD;

public class GamePane extends GraphicsPane implements ActionListener {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
	public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;
	public static final int HEADER_WIDTH = MainApplication.WINDOW_WIDTH / 3;
	public static final int HEADER_HEIGHT = 196;
	public static final String FILE_PATH = "../media/textures/";
	public int numTimes = 0; // Timer stuff
	public static final int DELAY_MS = 25;
	private static final int LEVEL_ALERT_X_SIZE = 600;
	private static final int LEVEL_ALERT_Y_SIZE = 150;
	private static final double SPEED_EFFECT = .25; // How effective the speed upgrades are, I've found .25 to be pretty
	// good
	public Direction direction;
	private Set<Integer> keysPressed = new HashSet<>();

	private Font hdrFont = new Font("PKMN Mystery Dungeon", 0, 60);
	private int level = -1; // Work on this when we get it in
	private GButton btnDie; // Debug, remove when done
	private GRect minimapBox; // Minimap, left header
	private GRect infoBox; // Center header
	private GParagraph infoText;// Center header content
	private GRect healthBox; // Right header
	private GButtonMD levelAlert;
	private GLabel healthLabel;
	private Room room;
	private Player player;
	private CollisionManager collisionManager;
	private Timer t;

	public GamePane(MainApplication app) {
		super();
		program = app;
		loadFont();
		// HEADER
		minimapBox = new GRect(0, 0, HEADER_WIDTH, HEADER_HEIGHT);

		infoBox = new GRect(HEADER_WIDTH, 0, HEADER_WIDTH, HEADER_HEIGHT);

		healthBox = new GRect(HEADER_WIDTH * 2, 0, HEADER_WIDTH, HEADER_HEIGHT);

		// FIELD

		// OTHER
		btnDie = new GButton("DIE", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2,
				(MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Room
		room = program.getFloorManager().getSpawnRoom();

		// Player
		player = program.getEntityManager().getPlayer();

		// Collision
		collisionManager = new CollisionManager(program.getEntityManager(), room);

		// Timer
		t = new Timer(DELAY_MS, this);
	}

	private void infoDrawing() {
		player.setMeleeDamage(1 + Integer.parseInt(PlayerData.getMap().get("MeleeUpgrades")));
		player.setRangedDamage(1 + Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")));
		player.setMoveSpeed(1 + Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")));

		infoText = new GParagraph(
				"Level: " + level
				+ "\nMelee Damage: " + player.getMeleeDamage()
				+ "\nRanged Damage: " + player.getRangedDamage()
				+ "\nMove Speed: " + player.getMoveSpeed(),0,0);

		infoText.setFont(hdrFont);
		infoText.move(infoBox.getX() + (infoBox.getWidth() - infoText.getWidth()) / 2,
				(infoBox.getY() + infoText.getHeight()) / 2);

		program.add(infoText);
	}

	private void changeHealth(boolean up) {
		if (up) {
			player.setHealth(player.getHealth() + 1);
		} else {
			player.setHealth(player.getHealth() - 1);
		}
		healthLabel.setLabel("Health: " + player.getHealth());
	}

	private void initHealth() {
		player.setHealth(player.getMaxHealth());
		drawHealth(player.getHealth());
	}

	private void drawHealth(int health) {
		healthLabel = new GLabel("Health: " + health, HEADER_WIDTH * 2.25, HEADER_HEIGHT / 1.9);
		healthLabel.setFont("Arial-48");
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
		showHeader(); // Top bar
		createImageList();
		showField(); // Game field
		showPlayer();
		showEnemies();
		// program.add(btnDie);//Testing death screen, remove when things are added
		initHealth();
		infoDrawing();
		drawLevelAlert();
	}

	@Override
	public void hideContents() {
		removeHeader();
		removeField();
		removePlayer();
		removeEnemies();
		removeProjectiles();
		// program.remove(btnDie);//Testing death screen, remove when things are added
		program.remove(healthLabel);
		program.remove(infoText);
	}

	public void showHeader() {
		program.add(minimapBox);
		program.add(infoBox);
		program.add(healthBox);
	}

	public void removeHeader() {
		program.remove(minimapBox);
		program.remove(infoBox);
		program.remove(infoText);
		program.remove(healthBox);
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
		String path = FILE_PATH + "DarkCrater/";
		// TODO Add switch cases for different file paths based on level

		room.setFilePaths();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Space space = room.getSpace(i, j);
				temp = new GImage(
						path + space.getFilePath(),
						(j * FloorManager.SPACE_SIZE),
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
				case BASIC_SPAWN:
					enemy = new StandardEnemy();
					// TODO set health and damage based on level
					break;
				case SHOTGUN_SPAWN:
					break;
				case SNIPER_SPAWN:
					break;
				}
				if (enemy != null) {
					enemy.getGObject().setLocation(
							(j * FloorManager.SPACE_SIZE), 
							(i * FloorManager.SPACE_SIZE) + HEADER_HEIGHT
							);
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
		program.getEntityManager().getProjectiles().forEach(p -> program.remove(p.getGObject()));
	}

	public void onDeath() {// Trigger this when player is dead, should add other functions - tally score,
		// etc.
		PlayerData.writeFile();
		program.switchToDeath();
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

		// GObject obj = program.getElementAt(e.getX(), e.getY());
		// if (obj == btnDie) {
		// onDeath();
		// }
		// else {
		// changeHealth(false);
		// }
		// if (player.getHealth() == 0) {
		// onDeath();
		// }
		for (Projectile p : player.attack(e.getX(), e.getY())) {
			program.getEntityManager().getProjectiles().add(p);
			program.add(p.getGObject());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Direction dir = getDirection(key);
		if (dir != null) {
			keysPressed.add(key);
			direction = dir;
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
		movePlayer();

		checkProjectileCollision();
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
		}
	}

	private void checkProjectileCollision() {
		program.getEntityManager().getProjectiles().forEach((p) -> {
			p.move();
			if (collisionManager.isTerrainCollision(p)) {
				program.getEntityManager().getProjectiles().remove(p);
				program.remove(p.getGObject());
			}
		});
	}

}
