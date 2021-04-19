package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage levelMap, map_temp, menu1, menu_layout1, menu2, menu_layout2;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/map.png"));
        menu1 = ImageLoader.loadImage("/textures/menu3.png");
        menu_layout1 = ImageLoader.loadImage("/textures/menu_layout3.png");
        menu2 = ImageLoader.loadImage("/textures/menu4.png");
        menu_layout2 = ImageLoader.loadImage("/textures/menu_layout4.png");
        //levelMap = sheet.crop(0,0,1920,1080);
        map_temp = sheet.crop(0,0,128,72);
    }
}