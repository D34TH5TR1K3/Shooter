package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage levelMap;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        levelMap = sheet.crop(0,0,1920,1080);
    }
}
