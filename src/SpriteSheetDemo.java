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
	public static final int DELAY_MS = 25;
	
	private static BufferedImage spriteSheet;
	private static int frame = 0;
	private GImage image;
	public boolean keyPressed = false;
	public String direction;
	public String facing;
	public int numTimes=0;
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
		addKeyListeners();
		Timer t = new Timer(DELAY_MS, this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		if (direction == "up" && keyPressed) {
			
			image.setImage(getSprite(numTimes%3,1));
			image.move(0, -5);
		}
		else if (direction =="down" && keyPressed) {
			
			image.setImage(getSprite(numTimes%3,0));
			image.move(0, 5);
		}
		else if (direction == "left" && keyPressed) {
			
			image.setImage(getSprite(numTimes%3,2));
			image.move(-5, 0);
		}
		else if (direction == "right" && keyPressed) {
			
			image.setImage(flipHoriz(getSprite(numTimes%3,2)));
			image.move(5, 0);
		}
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keyPressed = true;
		if (key == KeyEvent.VK_W) {
			direction = "up";
		}
		else if (key == KeyEvent.VK_S) {
			direction = "down";
		}
		else if (key == KeyEvent.VK_A) {
			direction = "left";
		}
		else if (key == KeyEvent.VK_D) {
			direction = "right";
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		direction = "stop";
		keyPressed = false;
	}
	public static BufferedImage flipHoriz(BufferedImage image) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    BufferedImage mimg = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_ARGB);
	    for (int y = 0; y < height; y++) 
        { 
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) 
            { 
                // lx starts from the left side of the image 
                // rx starts from the right side of the image 
                // lx is used since we are getting pixel from left side 
                // rx is used to set from right side 
                // get source pixel value 
                int p = image.getRGB(lx, y); 
  
                // set mirror image pixel value 
                mimg.setRGB(rx, y, p); 
            } 
        }
	    return mimg;
	}
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	
	
}