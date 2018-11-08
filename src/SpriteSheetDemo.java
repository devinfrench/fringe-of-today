import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import fringeoftoday.entities.Projectile;

import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * Demonstration of getting an image from spritesheet and animating it
 * 
 * @author Alexander Ng
 *
 */
public class SpriteSheetDemo extends GraphicsProgram implements ActionListener {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	//dimensions of individual sprite
	public static final int SPRITE_WIDTH = 200;
	public static final int SPRITE_HEIGHT = 400;
	
	public static final String FILE_NAME = "spritesheetdemo.png";
	public static final int DELAY_MS = 250;
	
	private static BufferedImage spriteSheet;
	private static int frame = 0;
	private GImage image;
	
	//loads image using bufferedimage
	public static BufferedImage loadSprite(String file) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}
	
	//crops the image at coordinates
	public static BufferedImage getSprite(int xGrid, int yGrid) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite(FILE_NAME);
        }

        return spriteSheet.getSubimage(xGrid * SPRITE_WIDTH, yGrid * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
    }
	
	public void run() {
		image = new GImage(getSprite(0,0), 0, 0);
		add(image);
		addMouseListeners();
		Timer t = new Timer(DELAY_MS, this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (frame == 0) {
			frame = 2;
		}
		else {
			frame = 0;
		}
		image.setImage(getSprite(frame,0));
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	
	
}