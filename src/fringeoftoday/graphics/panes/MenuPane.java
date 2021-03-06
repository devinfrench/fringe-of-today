package fringeoftoday.graphics.panes;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.audio.AudioPlayer;
import fringeoftoday.graphics.GButtonMD;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuPane extends GraphicsPane {
    // all of the GraphicsProgram calls
    public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
    public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;
    private MainApplication program; // you will use program to get access to
    private GButtonMD btnPlay;
    private GButtonMD btnShop;
    private GButtonMD btnExit;
    private GButtonMD btnLevelMaker;
    private GButtonMD btnTutorial;
    private GImage title;
    private GImage btnAudio;
    private GLabel lastRun;
    private GLabel bestRun;
    private GButtonMD btnNewFile;
    private GLabel confirm;
    private GImage backing;

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
        confirm = new GLabel("Click again to PERMANENTLY delete your player file", 0, 400 + BUTTON_HEIGHT * 1.20);
        confirm.setFont("Arial-24");
        confirm.move(MainApplication.WINDOW_WIDTH / 2 - confirm.getWidth() / 2, 0);
        confirm.setColor(Color.RED);
        confirm.setVisible(false);

        // Shop button
        btnShop = new GButtonMD("Shop", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 525, BUTTON_WIDTH,
          BUTTON_HEIGHT, "green");

        // Level Making button
        btnLevelMaker = new GButtonMD("Level Maker",(MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 650, BUTTON_WIDTH,
                BUTTON_HEIGHT, "green");
        
        // Exit button
        btnExit = new GButtonMD("Exit", (MainApplication.WINDOW_WIDTH - BUTTON_WIDTH) / 2, 775, BUTTON_WIDTH,
          BUTTON_HEIGHT, "green");
        
        // Tutorial button
        btnTutorial = new GButtonMD("?", 0, 0, 100, 100);
        
        // Blurred backing
        backing = new GImage("../media/backing.png");

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
			btnAudio = new GImage("../media/soundon.png", MainApplication.WINDOW_WIDTH - BUTTON_HEIGHT, 0);
		} else {
			btnAudio = new GImage("../media/soundoff.png", MainApplication.WINDOW_WIDTH - BUTTON_HEIGHT, 0);
		}
		btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
	}

    @Override
    public void showContents() {
        program.add(backing);
        program.add(btnAudio);
        rightAudioimage();
        program.add(title);
        program.add(btnPlay);
        program.add(btnShop);
        program.add(btnExit);
        program.add(btnLevelMaker);
        program.add(btnTutorial);
        program.add(btnNewFile);
        program.add(confirm);
        confirm.setVisible(false);
        scoreboard();
        AudioPlayer.getInstance().playMusic(AudioPlayer.MUSIC_FOLDER, "menumusic.mp3");
    }

    private void rightAudioimage() {
    	int sounds = Integer.parseInt(PlayerData.getMap().get("Sounds"));
		if (sounds == 1) {
			btnAudio.setImage("../media/soundon.png");
		} else {
			btnAudio.setImage("../media/soundoff.png");
		}
		btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
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
        program.remove(btnLevelMaker);
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
		} else if (obj == btnLevelMaker) {
			program.remove(backing);
			program.switchToMaker();
		} else if (obj == btnNewFile) {
			if (confirm.isVisible()) {
				PlayerData.newFile();
				PlayerData.readPlayerFile();
				confirm.setVisible(false);
				AudioPlayer.getInstance().playMusic(AudioPlayer.MUSIC_FOLDER, "menumusic.mp3");
				btnAudio.setImage("soundon.png");
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
				audio.pauseMusic();
				btnAudio.setImage("soundoff.png");
				btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
				PlayerData.updateMap("Sounds", 0);
			} else {
				audio.resumeMusic();
				btnAudio.setImage("soundon.png");
				btnAudio.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
				PlayerData.updateMap("Sounds", 1);
			}
			PlayerData.writeFile();
		}

	}
}
