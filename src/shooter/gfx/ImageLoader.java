package shooter.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {    //loadImage liest die Datei am übergebenen Pfad ein und übergibt sie als Bild
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
