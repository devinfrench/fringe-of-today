package fringeoftoday.graphics.panes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import acm.graphics.GObject;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.audio.AudioPlayer;
import fringeoftoday.graphics.GButton;
import fringeoftoday.graphics.panes.GraphicsPane;
import starter.GButtonMD;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
	public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;

	private GButtonMD btnPlay;
	private GButtonMD btnShop;
	private GButtonMD btnExit;
	private GButtonMD btnTutorial;
	private GImage title;
	private GImage btnAudio;
	private GLabel lastRun;
	private GLabel bestRun;
	private GButtonMD btnNewFile;
	private GLabel confirm;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		// Title banner - maybe use GImage instead?
		title = new GImage("../media/logo_transparent.png", (MainApplication.WINDOW_WIDTH - 600) / 2, 30);
		title.setSize(600, 300);

		// Play button
		btnPlay = new GButtonMD("Play", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 400, BUTTON_WIDTH,
				BUTTON_HEIGHT, "blue");

		// New file button
		btnNewFile = new GButtonMD("NEW", (MainApplication.WINDOW_WIDTH + BUTTON_WIDTH + 20) / 2, 400, BUTTON_HEIGHT,
				BUTTON_HEIGHT, "blue");

		// Confirm text, so the file won't be deleted the first time
		confirm = new GLabel("Click again to PERMINATELY delete your player file", 0, 400 + BUTTON_HEIGHT * 1.33);
		confirm.setFont("Arial-24");
		confirm.move(MainApplication.WINDOW_WIDTH / 2 - confirm.getWidth() / 2, 0);
		confirm.setColor(Color.RED);
		confirm.setVisible(false);

		// Shop button
		btnShop = new GButtonMD("Shop", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 550, BUTTON_WIDTH,
				BUTTON_HEIGHT, "green");

		// Exit button
		btnExit = new GButtonMD("Exit", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 700, BUTTON_WIDTH,
				BUTTON_HEIGHT, "green");

		// Tutorial button
		btnTutorial = new GButtonMD("?", 0, 0, 100, 100);

		// Audio button
		audioStarter();

	}

	private void scoreboard() {
		// Latest Score
		lastRun = new GLabel("On your last run, you got to floor: " + PlayerData.getMap().get("PreviousRun"),
				MainApplication.WINDOW_WIDTH - 450, MainApplication.WINDOW_HEIGHT - 40);
		lastRun.setFont(new Font("PKMN Mystery Dungeon", 0, 40));

		// Best Score
		PlayerData.updateMap("GOAT", Math.max(Integer.parseInt(PlayerData.getMap().get("PreviousRun")),
				Integer.parseInt(PlayerData.getMap().get("GOAT"))));
		bestRun = new GLabel("On your best run, you got to floor: " + PlayerData.getMap().get("GOAT"),
				MainApplication.WINDOW_WIDTH - 450, MainApplication.WINDOW_HEIGHT - 15);
		bestRun.setFont(new Font("PKMN Mystery Dungeon", 0, 40));
		program.add(lastRun);
		program.add(bestRun);
	}

	private void audioStarter() {
		int sounds;
		try {
			sounds = Integer.parseInt(PlayerData.getMap().get("Sounds"));
		} catch (NumberFormatException e) {
			PlayerData.updateMap("Sounds", 1);
			sounds = 1;
		}
		if (sounds == 1) {
			btnAudio = new GImage("../media/soundon.jpg", MainApplication.WINDOW_WIDTH - BUTTON_HEIGHT, 0);
		} else {
			btnAudio = new GImage("../media/soundoff.jpg", MainApplication.WINDOW_WIDTH - BUTTON_HEIGHT, 0);
		}
		btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(btnPlay);
		program.add(btnShop);
		program.add(btnExit);
		program.add(btnTutorial);
		program.add(btnAudio);
		program.add(btnNewFile);
		program.add(confirm);
		confirm.setVisible(false);
		scoreboard();
	}

	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(btnPlay);
		program.remove(btnShop);
		program.remove(btnExit);
		program.remove(btnTutorial);
		program.remove(btnAudio);
		program.remove(lastRun);
		program.remove(bestRun);
		program.remove(btnNewFile);
		program.remove(confirm);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == btnPlay) {
			program.switchToGame();
		} else if (obj == btnShop) {
			program.switchToShop();
		} else if (obj == btnExit) {
			program.exitProgram();
		} else if (obj == btnTutorial) {
			program.switchToTutorial();
		} else if (obj == btnNewFile) {
			if (confirm.isVisible()) {
				PlayerData.newFile();
				PlayerData.readPlayerFile();
				confirm.setVisible(false);
				AudioPlayer audio = AudioPlayer.getInstance();
				audio.playSound("sounds", "menumusic.mp3");
				btnAudio.setImage("soundon.jpg");
				btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
				PlayerData.updateMap("Sounds", 1);
				program.remove(bestRun);
				program.remove(lastRun);
				scoreboard();
			} else {
				confirm.setVisible(true);
			}
		} else if (obj == btnAudio) {
			AudioPlayer audio = AudioPlayer.getInstance();
			int sounds;
			sounds = Integer.parseInt(PlayerData.getMap().get("Sounds"));
			if (sounds == 1) {
				audio.stopSound("sounds", "menumusic.mp3");
				btnAudio.setImage("soundoff.jpg");
				btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
				PlayerData.updateMap("Sounds", 0);
			} else {
				audio.playSound("sounds", "menumusic.mp3");
				btnAudio.setImage("soundon.jpg");
				btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
				PlayerData.updateMap("Sounds", 1);
			}
			PlayerData.writeFile();
		}

	}
}
