package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.entities.Player;
import fringeoftoday.graphics.GButton;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final int MAX_UPGRADES = 10;
	public static final int IMAGE_SIZE = 250;
	public static final int LEFT_BTN = MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2;
	public static final int RIGHT_BTN = 3 * MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2;
	public static final int UP_BTN = 12 * MainApplication.WINDOW_HEIGHT / 22 - BUTTON_HEIGHT - 10;
	public static final int DOWN_BTN = MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT - 10;
	public static final int LABEL_WIDTH = 190;

	private int cheatCtr = 0;

	private GLabel title;
	private GButton btnBack;
	private GLine headerSeparator;
	private GLabel coinCtr;
	private GLine horizSeparator;
	private GLine vertSeperator;

	private GButton hpBtn;
	private GButton meleeBtn;
	private GButton rangedBtn;
	private GButton speedBtn;

	private GImage hpImg;
	private GImage meleeImg;
	private GImage rangedImg;
	private GImage speedImg;

	private GLabel hpLabel;
	private GLabel meleeLabel;
	private GLabel rangedLabel;
	private GLabel speedLabel;

	private GButton coinCheat;

	public ShopPane(MainApplication app) {
		this.program = app;
		initObjs();

		// Button to cheat and add coins
		coinCheat = new GButton("Add 10 coin", MainApplication.WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2,
				MainApplication.WINDOW_HEIGHT / 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
	}

	private void initObjs() {
		// Title for the shop
		title = new GLabel("Shop", MainApplication.WINDOW_WIDTH / 2 - 50, MainApplication.WINDOW_HEIGHT / 18);
		title.setFont("Arial-46");

		// Back button
		btnBack = new GButton("Back", 1, 1, BUTTON_WIDTH, BUTTON_HEIGHT * .75);

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
		hpImg = new GImage("hp_upgrade.png", MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - IMAGE_SIZE * 1.5);

		// Melee Image
		meleeImg = new GImage("melee_upgrade.png", 3 * MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - IMAGE_SIZE * 1.5);

		// Ranged Image
		rangedImg = new GImage("ranged_upgrade.png", MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				MainApplication.WINDOW_HEIGHT - IMAGE_SIZE * 1.5);

		// Speed Image
		speedImg = new GImage("movement_speed_upgrade.png", 3 * MainApplication.WINDOW_WIDTH / 4 - IMAGE_SIZE / 2,
				MainApplication.WINDOW_HEIGHT - IMAGE_SIZE * 1.5);

		// Coin counter at the top left
		coinCtr = new GLabel("Coin: " + PlayerData.getMap().get("Coin"), MainApplication.WINDOW_WIDTH - 300,
				MainApplication.WINDOW_HEIGHT / 18);
		coinCtr.setFont("Arial-46");

		// HP Upgrade Button
		int hpCost = (Integer.parseInt(PlayerData.getMap().get("HPUpgrades")) + 1) * 10;
		hpBtn = new GButton("Cost: " + hpCost, LEFT_BTN, UP_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (hpCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			hpBtn.setColor(Color.red);
		}

		// Melee Damage Upgrade Button
		int meleeCost = (Integer.parseInt(PlayerData.getMap().get("MeleeUpgrades")) + 1) * 10;
		meleeBtn = new GButton("Cost: " + meleeCost, RIGHT_BTN, UP_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (meleeCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			meleeBtn.setColor(Color.red);
		}

		// Ranged Damage Upgrade Button
		int rangedCost = (Integer.parseInt(PlayerData.getMap().get("RangedUpgrades")) + 1) * 10;
		rangedBtn = new GButton("Cost: " + rangedCost, LEFT_BTN, DOWN_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (rangedCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			rangedBtn.setColor(Color.red);
		}

		// Speed Movement Upgrade Button
		int speedCost = (Integer.parseInt(PlayerData.getMap().get("SpeedUpgrades")) + 1) * 10;
		speedBtn = new GButton("Cost: " + speedCost, RIGHT_BTN, DOWN_BTN, BUTTON_WIDTH, BUTTON_HEIGHT);
		if (speedCost > Integer.parseInt(PlayerData.getMap().get("Coin"))) {
			speedBtn.setColor(Color.red);
		}

		// HP label to tell how much there is
		hpLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("HPUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH / 2 - LABEL_WIDTH, MainApplication.WINDOW_HEIGHT / 8);
		hpLabel.setFont("Arial-24");

		// Melee label to tell how much there is
		meleeLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("MeleeUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH - LABEL_WIDTH, MainApplication.WINDOW_HEIGHT / 8);
		meleeLabel.setFont("Arial-24");

		// Ranged label to tell how much there is
		rangedLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("RangedUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH / 2 - LABEL_WIDTH,
				MainApplication.WINDOW_HEIGHT / 14 + MainApplication.WINDOW_HEIGHT / 2);
		rangedLabel.setFont("Arial-24");

		// Speed label to tell how much there is
		speedLabel = new GLabel("Upgrades: " + PlayerData.getMap().get("SpeedUpgrades") + "/" + MAX_UPGRADES,
				MainApplication.WINDOW_WIDTH - LABEL_WIDTH,
				MainApplication.WINDOW_HEIGHT / 14 + MainApplication.WINDOW_HEIGHT / 2);
		speedLabel.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(btnBack);
		program.add(headerSeparator);
		program.add(coinCtr);
		program.add(horizSeparator);
		program.add(vertSeperator);
		program.add(hpBtn);
		program.add(meleeBtn);
		program.add(rangedBtn);
		program.add(speedBtn);
		program.add(hpImg);
		program.add(meleeImg);
		program.add(rangedImg);
		program.add(speedImg);
		program.add(hpLabel);
		program.add(meleeLabel);
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
		program.remove(meleeBtn);
		program.remove(rangedBtn);
		program.remove(speedBtn);
		program.remove(hpImg);
		program.remove(meleeImg);
		program.remove(rangedImg);
		program.remove(speedImg);
		program.remove(hpLabel);
		program.remove(meleeLabel);
		program.remove(rangedLabel);
		program.remove(speedLabel);
	}

	private void purchase(GObject obj) {
		String type = "";
		if (obj == hpBtn) {
			type = "HPUpgrades";
		} else if (obj == meleeBtn) {
			type = "MeleeUpgrades";
		} else if (obj == rangedBtn) {
			type = "RangedUpgrades";
		} else if (obj == speedBtn) {
			type = "SpeedUpgrades";
		}
		int cost = (Integer.parseInt(PlayerData.getMap().get(type)) + 1) * 10;
		if (cost <= Integer.parseInt(PlayerData.getMap().get("Coin"))) {
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
			program.switchToMenu();
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
		} else if (obj instanceof GButton) {
			purchase(obj);
		}
	}
}