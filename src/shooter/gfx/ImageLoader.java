package shooter.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
    //static method to get an Image from a File
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
