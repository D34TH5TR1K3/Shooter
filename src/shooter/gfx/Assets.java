package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage enemy, map_temp, menu1, menu_layout1, menu2, menu_layout2, sliderKnob, player, map_1, map_1layout, Bullet;
    public static BufferedImage[] enemy_walk, enemy_walk_ak;

    public static void init() {
        SpriteSheet sprite1 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite1.png"));
        map_1 = ImageLoader.loadImage("/textures/Map1.png");
        enemy = ImageLoader.loadImage("/textures/player_1.png");
        map_1layout = ImageLoader.loadImage("/textures/Map1_layout.png");
        menu1 = ImageLoader.loadImage("/textures/menu3.png");
        menu_layout1 = ImageLoader.loadImage("/textures/menu_layout3.png");
        menu2 = ImageLoader.loadImage("/textures/menu4.png");
        menu_layout2 = ImageLoader.loadImage("/textures/menu_layout4.png");
        sliderKnob = ImageLoader.loadImage("/textures/slider.png");
        player = ImageLoader.loadImage("/textures/player_1.png");
        Bullet = ImageLoader.loadImage("/textures/bullet_4px.png");
        //levelMap = sheet.crop(0,0,1920,1080);

        enemy_walk = new BufferedImage[8];
        enemy_walk_ak = new BufferedImage[8];
        for(int x = 0; x < 8; x++){
            enemy_walk[x] = sprite1.crop(x*60, 0, 60, 60);
        }
        for(int x = 0; x < 8; x++){
            enemy_walk_ak[x] = sprite1.crop(x*60, 60, 60, 60);
        }
    }
}