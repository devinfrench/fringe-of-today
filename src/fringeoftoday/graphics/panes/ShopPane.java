package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.graphics.GButton;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final int MAX_UPGRADES = 10;
	public static final int IMAGE_SIZE = 250;

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

	private GButton coinCheat;

	public ShopPane(MainApplication app) {
		this.program = app;
		initObjs();
		coinCheat.setVisible(false);
	}

	private void initObjs() {
		// Title for the shop
		title = new GLabel("Shop", MainApplication.WINDOW_WIDTH / 2 - 50, MainApplication.WINDOW_HEIGHT / 18);
		title.setFont("Arial-46");

		// Back button
		btnBack = new GButton("Back", 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT * .75);

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

		// Button to cheat and add coins
		coinCheat = new GButton("Add 10 coin", MainApplication.WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2,
				MainApplication.WINDOW_HEIGHT / 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Coin counter at the top left
		coinCtr = new GLabel("Coin: " + MainApplication.getMap().get("Coin"), MainApplication.WINDOW_WIDTH - 300,
				MainApplication.WINDOW_HEIGHT / 18);
		coinCtr.setFont("Arial-46");

		// HP Upgrade Button
		int hpCost = (Integer.parseInt(MainApplication.getMap().get("HPUpgrades")) + 1) * 10;
		hpBtn = new GButton("Cost: " + hpCost, MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Melee Damage Upgrade Button
		int meleeCost = (Integer.parseInt(MainApplication.getMap().get("MeleeUpgrades")) + 1) * 10;
		meleeBtn = new GButton("Cost: " + meleeCost, 3 * MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2,
				12 * MainApplication.WINDOW_HEIGHT / 22 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Ranged Damage Upgrade Button
		int rangedCost = (Integer.parseInt(MainApplication.getMap().get("RangedUpgrades")) + 1) * 10;
		rangedBtn = new GButton("Cost: " + rangedCost, MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2,
				MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Speed Movement Upgrade Button
		int speedCost = (Integer.parseInt(MainApplication.getMap().get("SpeedUpgrades")) + 1) * 10;
		speedBtn = new GButton("Cost: " + speedCost, 3 * MainApplication.WINDOW_WIDTH / 4 - BUTTON_WIDTH / 2,
				MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		program.add(coinCheat);
	}

	@Override
	public void hideContents() {
		coinCheat.setVisible(false);
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
		program.remove(coinCheat);
	}

	private boolean purchaseAble(GObject obj) {
			
		return false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnBack) {
			program.switchToMenu();
		}
		else if (obj == coinCheat) {
			hideContents();
			MainApplication.updateMap("Coin", Integer.parseInt(MainApplication.getMap().get("Coin")) + 10);
			initObjs();
			showContents();
		}
		else if (obj == coinCtr) {
			coinCheat.setVisible(!coinCheat.isVisible());
			}
		else if (obj instanceof GButton) {
			purchaseAble(obj);
		}
	}
}