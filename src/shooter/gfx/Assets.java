package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage levelMap, map_temp, menu1, menu_layout1, menu2, menu_layout2, sliderKnob, player, map_1, map_1layout, Bullet;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/map.png"));
        map_1 = ImageLoader.loadImage("/textures/Map1.png");
        map_1layout = ImageLoader.loadImage("/textures/Map1_layout.png");
        menu1 = ImageLoader.loadImage("/textures/menu3.png");
        menu_layout1 = ImageLoader.loadImage("/textures/menu_layout3.png");
        menu2 = ImageLoader.loadImage("/textures/menu4.png");
        menu_layout2 = ImageLoader.loadImage("/textures/menu_layout4.png");
        sliderKnob = ImageLoader.loadImage("/textures/slider.png");
        player = ImageLoader.loadImage("/textures/player_1.png");
        Bullet = ImageLoader.loadImage("/textures/bullet_4px.png");
        //levelMap = sheet.crop(0,0,1920,1080);
        map_temp = sheet.crop(0,0,128,72);
    }
}