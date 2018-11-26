package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.entities.Player;
import fringeoftoday.graphics.GButton;
import starter.GButtonMD;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
	public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;
	public static final int MAX_UPGRADES = 10;
	public static final int IMAGE_SIZE = 250;
	public static final int LEFT_BTN = MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2;
	public static final int RIGHT_BTN = 3 * MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2;
	public static final int UP_BTN = 12 * MainApplication.WINDOW_HEIGHT / 22 - BUTTON_HEIGHT - 10;
	public static final int DOWN_BTN = MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT - 10;
	public static final int LABEL_WIDTH = 190;
	public static final String FILE_PATH = "../media/shop/";
	
	private int cheatCtr = 0;

	private GLabel title;
	private GButtonMD btnBack;
	private GLine headerSeparator;
	private GLabel coinCtr;
	private GLine horizSeparator;
	private GLine vertSeperator;

	private GButtonMD hpBtn;
	private GButtonMD fireBtn;
	private GButtonMD rangedBtn;
	private GButtonMD speedBtn;

	private GImage hpImg;
	private GImage fireImg;
	private GImage rangedImg;
	private GImage speedImg;

	private GLabel hpLabel;
	private GLabel fireLabel;
	private GLabel rangedLabel;
	private GLabel speedLabel;

	private GButton coinCheat;

	public ShopPane(MainApplication app) {
		this.program = app;
		loadFont();

		// Button to cheat and add coins
		coinCheat = new GButton("Add 10 coin", MainApplication.WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2,
				MainApplication.WINDOW_HEIGHT / 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
	}

	private void initObjs() {
		// Title for the shop
		title = new GLabel("Shop", MainApplication.WINDOW_WIDTH / 2 - 50, MainApplication.WINDOW_HEIGHT / 18);
		title.setFont(new Font("PKMN Mystery Dungeon", 0, 72));

		// Back button
		btnBack = new GButtonMD("Back", 1, 1, BUTTON_WIDTH, (int) (BUTTON_HEIGHT * .75));

		// Header separator
		headerSeparator = new GLine(0, MainApplication.WINDOW_HEIGHT / 11, MainApplication.WINDOW_WIDTH,
				MainApplication.WINDOW_HEIGHT / 11);

		// Horizontal Separator
		horizSeparator = new GLine(MainApplication.WINDOW_WIDTH / 2, MainApplication.WINDOW_HEIGHT / 11,
				MainApplication.WINDOW_WIDTH / 2, MainApplication.WINDOW_HEIGHT);

		// Vertical Separator
		vertSeperator = new GLine(0, 12 * MainApplication.WINDOW_HEIGHT / 22, MainApplication.WINDOW_WIDTH,
				12 * MainApplication.WINDOW_HEIGHT / 22);

		// HP Image
		hpImg = new GImage(FILE_PATH + "hp_upgrade.png", MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - IMAGE_SIZE * 1.5);

		// Fire speed Image
		fireImg = new GImage(FILE_PATH + "firing_speed_upgrade.png", 3 * MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - IMAGE_SIZE * 1.5);

		// Ranged Image
		rangedImg = new GImage(FILE_PATH + "ranged_upgrade.png", MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				MainApplication.WINDOW_HEIGHT - IMAGE_SIZE * 1.5);

		// Speed Image
		speedImg = new GImage(FILE_PATH + "movement_speed_upgrade.png", 3 * MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				MainApplication.WINDOW_HEIGHT - IMAGE_SIZE * 1.5);

		// Coin counter at the top left
		coinCtr = new GLabel("Coin: " + PlayerData.getMap().get("Coin"), MainApplication.WINDOW_WIDTH - 300,
				MainApplication.WINDOW_HEIGHT / 18);
		coinCtr.setFont(new Font("PKMN Mystery Dungeon", 0, 72));

		// HP Upgrade Button
		int hpCost = (Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 1) * 10;
		hpBtn = new GButtonMD("Cost: " + hpCost, LEFT_BTN, UP_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (hpCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			hpBtn.setColor(Color.red);
		}
		if (Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) == MAX_UPGRADES) {
			hpBtn.setVisible(false);			
		}

		// Fire Speed Upgrade Button
		int meleeCost = (Integer.parseInt(PlayerData.getMap().get("FireSpeedUpgrades")) + 1) * 10;
		fireBtn = new GButtonMD("Cost: " + meleeCost, RIGHT_BTN, UP_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (meleeCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			fireBtn.setColor(Color.red);
		}
		if (Integer.parseInt(PlayerData.getMap().get("FireSpeedUpgrades")) == MAX_UPGRADES) {
			fireBtn.setVisible(false);			
		}

		// Ranged Damage Upgrade Button
		int rangedCost = (Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")) + 1) * 10;
		rangedBtn = new GButtonMD("Cost: " + rangedCost, LEFT_BTN, DOWN_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (rangedCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			rangedBtn.setColor(Color.red);
		}
		if (Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")) == MAX_UPGRADES) {
			rangedBtn.setVisible(false);			
		}

		// Speed Movement Upgrade Button
		int speedCost = (Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")) + 1) * 10;
		speedBtn = new GButtonMD("Cost: " + speedCost, RIGHT_BTN, DOWN_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (speedCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			speedBtn.setColor(Color.red);
		}
		if (Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")) == MAX_UPGRADES) {
			speedBtn.setVisible(false);			
		}

		// HP label to tell how much there is
		hpLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("HPUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH / 2 - LABEL_WIDTH, MainApplication.WINDOW_HEIGHT / 8);
		hpLabel.setFont("Terminal-24");

		// Fire label to tell how much there is
		fireLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("FireSpeedUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH - LABEL_WIDTH, MainApplication.WINDOW_HEIGHT / 8);
		fireLabel.setFont("Terminal-24");

		// Ranged label to tell how much there is
		rangedLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("RangedUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH / 2 - LABEL_WIDTH,
				MainApplication.WINDOW_HEIGHT / 14 + MainApplication.WINDOW_HEIGHT / 2);
		rangedLabel.setFont("Terminal-24");

		// Speed label to tell how much there is
		speedLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("SpeedUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH - LABEL_WIDTH,
				MainApplication.WINDOW_HEIGHT / 14 + MainApplication.WINDOW_HEIGHT / 2);
		speedLabel.setFont("Terminal-24");
	}

	@Override
	public void showContents() {
		initObjs();
		program.add(title);
		program.add(btnBack);
		program.add(headerSeparator);
		program.add(coinCtr);
		program.add(horizSeparator);
		program.add(vertSeperator);
		program.add(hpBtn);
		program.add(fireBtn);
		program.add(rangedBtn);
		program.add(speedBtn);
		program.add(hpImg);
		program.add(fireImg);
		program.add(rangedImg);
		program.add(speedImg);
		program.add(hpLabel);
		program.add(fireLabel);
		program.add(rangedLabel);
		program.add(speedLabel);
		coinCheat.sendToFront();
	}

	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(btnBack);
		program.remove(headerSeparator);
		program.remove(coinCtr);
		program.remove(horizSeparator);
		program.remove(vertSeperator);
		program.remove(hpBtn);
		program.remove(fireBtn);
		program.remove(rangedBtn);
		program.remove(speedBtn);
		program.remove(hpImg);
		program.remove(fireImg);
		program.remove(rangedImg);
		program.remove(speedImg);
		program.remove(hpLabel);
		program.remove(fireLabel);
		program.remove(rangedLabel);
		program.remove(speedLabel);
	}

	private void purchase(GObject obj) {
		String type = "";
		if (obj == hpBtn) {
			type = "HPUpgrades";
		} else if (obj == fireBtn) {
			type = "FireSpeedUpgrades";
		} else if (obj == rangedBtn) {
			type = "RangedUpgrades";
		} else if (obj == speedBtn) {
			type = "SpeedUpgrades";
		}
		int cost = (Integer.parseInt(PlayerData.getMap().get(type)) + 1) * 10;
		if (cost <= Integer.parseInt(PlayerData.getMap().get("Coin")) && (Integer.parseInt(PlayerData.getMap().get(type)) < MAX_UPGRADES)) {
			hideContents();
			PlayerData.updateMap("Coin", Integer.parseInt(PlayerData.getMap().get("Coin")) - cost);
			PlayerData.updateMap(type, (Integer.parseInt(PlayerData.getMap().get(type)) + 1));
			initObjs();
			showContents();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnBack) {
			PlayerData.writeFile();
			program.switchToMenu();
			program.remove(coinCheat);
		} else if (obj == coinCheat) {
			hideContents();
			PlayerData.updateMap("Coin", Integer.parseInt(PlayerData.getMap().get("Coin")) + 10);
			initObjs();
			showContents();
		} else if (obj == coinCtr) {
			if (cheatCtr % 2 == 0) {
				program.add(coinCheat);
			} else {
				program.remove(coinCheat);
			}
			cheatCtr++;
		} else if (obj instanceof GButtonMD) {
			purchase(obj);
		}
	}
}