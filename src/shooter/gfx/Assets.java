package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage levelMap, map_temp;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/map.png"));
        //levelMap = sheet.crop(0,0,1920,1080);
        map_temp = sheet.crop(0,0,128,72);
    }
}
