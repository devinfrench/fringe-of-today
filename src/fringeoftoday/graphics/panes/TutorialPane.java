package fringeoftoday.graphics.panes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.graphics.GParagraph;

public class TutorialPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls

	private GParagraph header; // The instructions
	private GParagraph moveInstructions; // Instructions of the keys to use to move
	private GParagraph attackInstructions; // Instructions of the keys to use to attack
	private GLabel pressToContinue; // Label instructing the user how to progress
	private GImage moveKeys; // Keys to show which to click to move
	private GImage attackKeys; // Keys to show which to mouse pressed to make to attack

	public TutorialPane(MainApplication app) {

		this.program = app;
		header = new GParagraph(
				"Your goal is to defeat all the enemies in a room then progress to the next.\nFind the staircase to the next floor.\nPause with `ESC`",
				MainApplication.WINDOW_WIDTH / 4, 100);
		header.setFont("Arial-24");
		moveInstructions = new GParagraph("To move, use the\n\"WASD\" keys", MainApplication.WINDOW_WIDTH / 6 + 50,
				2 * MainApplication.WINDOW_HEIGHT / 3);
		moveInstructions.setFont("Arial-24");
		attackInstructions = new GParagraph("To fire, use the Left Mouse Button\nTo swing your sword,\nuse the Right Mouse Button",
				4 * MainApplication.WINDOW_WIDTH / 6 , 2 * MainApplication.WINDOW_HEIGHT / 3);
		attackInstructions.setFont("Arial-24");
		pressToContinue = new GLabel("Press any key or mouse button to continue...", MainApplication.WINDOW_WIDTH / 2 - 250,
				5 * MainApplication.WINDOW_HEIGHT / 6);
		pressToContinue.setFont("Arial-24");

		moveKeys = new GImage("WASD.png", MainApplication.WINDOW_WIDTH / 6, 2 * MainApplication.WINDOW_HEIGHT / 3);
		moveKeys.move(0, -1 * moveKeys.getHeight() - 100);
		attackKeys = new GImage("../media/mouse_buttons.png", 4 * MainApplication.WINDOW_WIDTH / 6 + 50, 2 * MainApplication.WINDOW_HEIGHT / 3);
		attackKeys.setSize(150, 260);
		attackKeys.move(0, -1 * attackKeys.getHeight() - 100);
	}

	@Override
	public void showContents() {
		program.add(header);
		program.add(moveInstructions);
		program.add(attackInstructions);
		program.add(pressToContinue);
		program.add(moveKeys);
		program.add(attackKeys);
	}

	@Override
	public void hideContents() {
		program.remove(header);
		program.remove(moveInstructions);
		program.remove(attackInstructions);
		program.remove(pressToContinue);
		program.remove(moveKeys);
		program.remove(attackKeys);
	}


	@Override
	public void mousePressed(MouseEvent e) {
		exit();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		exit();
	}

	private void exit() {
		if (Integer.parseInt(PlayerData.getMap().get("Tutorial")) != 101) {
			PlayerData.updateMap("Tutorial", Integer.parseInt(PlayerData.getMap().get("Tutorial")) - 100);
			PlayerData.writeFile();
			program.switchToMenu();
		}
		else if(Integer.parseInt(PlayerData.getMap().get("Tutorial")) == 101) {
			PlayerData.writeFile();
			program.switchToGame();
		}
	}
}
