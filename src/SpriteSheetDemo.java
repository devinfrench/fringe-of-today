import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Demonstration of getting an image from spritesheet
 * 
 * @author Alexander Ng
 *
 */
public class SpriteSheetDemo extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int SPRITE_WIDTH = 35;
	public static final int SPRITE_HEIGHT = 60;
	public static final String FILE_NAME = "spritesheetdemo.png";
	private static BufferedImage spriteSheet;
	private GImage image;
	public static BufferedImage loadSprite(String file) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}
	
	public static BufferedImage getSprite(int xGrid, int yGrid) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite(FILE_NAME);
        }

        return spriteSheet.getSubimage(xGrid * SPRITE_WIDTH, yGrid * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
    }
	
	public void run() {
		image = new GImage(getSprite(0,0), 0, 0);
		add(image);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	
	
}