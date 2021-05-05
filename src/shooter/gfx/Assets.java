package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage Map1_walls, shell, item_pistol, item_uzi, item_shotgun_empty, item_shotgun_full, item_ak_empty, item_ak_full, enemy, map_temp, menu1, menu_layout1, menu2, menu_layout2, sliderKnob, player, map_1, map_1layout, Bullet, item_rpg_empty, item_rpg_full;
    public static BufferedImage[] enemy_walk, enemy_walk_ak, rocket, explosion, particles1;

    public static void init() {
        SpriteSheet sprite1 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite1.png"));
        SpriteSheet sheetRocket = new SpriteSheet(ImageLoader.loadImage("/textures/Rocket_80_10.png"));
        SpriteSheet sheet_explosion = new SpriteSheet(ImageLoader.loadImage("/textures/explosion_sprite.png"));
        SpriteSheet sprite_rpg = new SpriteSheet(ImageLoader.loadImage("/textures/200xSprite.png"));
        SpriteSheet sprite_particles1 = new SpriteSheet(ImageLoader.loadImage("/textures/particles1.png"));

        Map1_walls = ImageLoader.loadImage("/textures/Map1_walls.png");
        item_ak_empty = sprite1.crop(8*60, 0, 60, 60);
        item_ak_full = sprite1.crop(9*60, 0, 60, 60);
        item_shotgun_empty = sprite1.crop(8*60, 60, 60, 60);
        item_shotgun_full = sprite1.crop(9*60, 60, 60, 60);
        item_uzi = sprite1.crop(9*60, 60*3, 60, 60);
        shell = sprite1.crop(9*60, 60*4, 60, 60);
        item_pistol = sprite1.crop(9*60, 60*2, 60, 60);
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

        item_rpg_empty = sprite_rpg.crop(0, 0, 200, 200);
        item_rpg_full = sprite_rpg.crop(200, 0, 200, 200);

        enemy_walk = new BufferedImage[8];
        enemy_walk_ak = new BufferedImage[8];
        rocket = new BufferedImage[5];
        explosion = new BufferedImage[48];
        particles1 = new BufferedImage[5];

        for(int x = 0; x < 5; x++){
            particles1[x] = sprite_particles1.crop(x*30, 0, 30, 30);
        }

        for(int x = 0; x < 8; x++){
            enemy_walk[x] = sprite1.crop(x*60, 0, 60, 60);
        }
        for(int x = 0; x < 8; x++){
            enemy_walk_ak[x] = sprite1.crop(x*60, 60, 60, 60);
        }
        int x = 0;
        for(int i = 0; i < 5 ; i++) {
            for(int z = 0; z < 7 ; z++) {
                x++;
                explosion[x] = sheet_explosion.crop(56 * z,  56 * i, 56,  56); // 56 x 56
            }
        }
        for(int i = 0; i < 5 ; i++) {
            rocket[i] = sheetRocket.crop(80 * i,  0, 80,  10);
        }
    }
}