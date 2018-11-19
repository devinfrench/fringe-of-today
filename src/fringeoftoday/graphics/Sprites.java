package fringeoftoday.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprites {

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

}
