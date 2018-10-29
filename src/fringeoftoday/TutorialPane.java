package fringeoftoday;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class TutorialPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	
	private GParagraph header;				//The instructions
	private GParagraph moveInstrucitons;	//Instruction of the keys to use to move
	private GParagraph atackInstructions;	//Instruction of the keys to use to attack

	public TutorialPane(MainApplication app) {
		this.program = app;
		header = new GParagraph("Your goal is to defeat all the enemies in a room then progress to the next.\nFind the staircase to the next floor.", 100, 100);
		header.setFont("Arial-24");		
	}

	@Override
	public void showContents() {
		program.add(header);
		
	}

	@Override
	public void hideContents() {
		program.remove(header);
		
	}
}
