package shooter.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    //saves the textures
    public static BufferedImage Map1_walls, shell, item_pistol, item_mp, item_shotgun_empty, item_shotgun_full, item_ak_empty, item_ak_full, enemy, map_temp, menu1, menu_layout1, menu2, menu_layout2, sliderKnob, player, map_1, map_1layout, Bullet;
    public static BufferedImage enemy_die_shotgun, overlay, bullet_img, buckshot_img,
            item_knife, item_machete, item_handgun, item_silencer, item_uzi_full, item_uzi_empty, item_rifle_empty, item_rifle_full, item_shotgun, item_rpg_empty, item_rpg_full;
    //saves the animations
    public static BufferedImage[] enemy_walk, enemy_walk_ak, rocket, explosion, particles1;//old stuff
    public static BufferedImage[]
            player_legs, player_walk, player_walk_handgun, player_walk_uzi, player_walk_knife, player_walk_shotgun, player_walk_rifle, player_walk_silencer, player_walk_machete,
            player_attack_unarmed, player_attack_handgun, player_attack_uzi, player_attack_knife, player_attack_shotgun, player_attack_rifle, player_attack_silencer, player_attack_machete,
            enemy_legs, enemy_walk_knife, enemy_walk_shotgun, enemy_walk_mp, enemy_walk_silencer, enemy_attack_knife, enemy_attack_shotgun, enemy_attack_silencer, enemy_attack_rifle, enemy_die_knife,
            player_die;
    //saves the maps and their layouts
    public static BufferedImage[] maps = new BufferedImage[2];

    //initializes the games textures
    public static void init() {
        SpriteSheet sprite_32 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite_32.png"));
        SpriteSheet sprite_45 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite_45.png"));
        SpriteSheet sprite_40 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite_40.png"));
        SpriteSheet sprite_24 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite_24.png"));

        overlay = ImageLoader.loadImage("/textures/overlay.png");
        buckshot_img = sprite_40.crop(46, 45, 46, 69);
        bullet_img = sprite_40.crop(0, 45, 46, 69);

        item_knife = sprite_32.crop(0 * 32, 11 * 32, 32, 32);
        item_machete = sprite_32.crop(1 * 32, 11 * 32, 32, 32);
        item_handgun = sprite_32.crop(2 * 32, 11 * 32, 32, 32);
        item_silencer = sprite_32.crop(3 * 32, 11 * 32, 32, 32);
        item_uzi_full = sprite_32.crop(4 * 32, 11 * 32, 32, 32);
        item_uzi_empty = sprite_32.crop(4 * 32, 12 * 32, 32, 32);
        item_rifle_full = sprite_32.crop(5 * 32, 11 * 32, 32, 32);
        item_rifle_empty = sprite_32.crop(5 * 32, 12 * 32, 32, 32);
        item_shotgun = sprite_32.crop(6 * 32, 11 * 32, 32, 32);
        item_rpg_empty = sprite_32.crop(7 * 32, 12 * 32, 160, 32);
        item_rpg_full = sprite_32.crop(7 * 32, 11 * 32, 160, 32);

        player_die = new BufferedImage[4];
        player_legs = new BufferedImage[16];

        player_walk = new BufferedImage[8];
        player_walk_knife = new BufferedImage[8];
        player_walk_machete = new BufferedImage[8];
        player_walk_handgun = new BufferedImage[8];
        player_walk_uzi = new BufferedImage[8];
        player_walk_shotgun = new BufferedImage[8];
        player_walk_rifle = new BufferedImage[8];
        player_walk_silencer = new BufferedImage[8];

        player_attack_unarmed = new BufferedImage[7];
        player_attack_knife = new BufferedImage[9];
        player_attack_machete = new BufferedImage[8];
        player_attack_handgun = new BufferedImage[2];
        player_attack_uzi = new BufferedImage[2];
        player_attack_shotgun = new BufferedImage[13];
        player_attack_rifle = new BufferedImage[2];
        player_attack_silencer = new BufferedImage[2];

        player_die[0] = sprite_32.crop(0 * 32 * 3, 7 * 32, 60, 32);
        player_die[1] = sprite_32.crop(1 * 32 * 3, 7 * 32, 60, 32);
        player_die[2] = sprite_32.crop(2 * 32 * 3, 7 * 32, 60, 32);
        player_die[3] = sprite_32.crop(3 * 32 * 3, 7 * 32, 60, 32);

        for (int x = 0; x < 16; x++)
            player_legs[x] = sprite_32.crop(x * 32, 0 * 32, 32, 32);

        for (int x = 0; x < 8; x++)
            player_walk[x] = sprite_32.crop(x * 32, 3 * 32, 32, 32);
        for (int x = 0; x < 8; x++)
            player_walk_knife[x] = sprite_45.crop(x * 32, 0 * 45, 32, 45);
        for (int x = 0; x < 8; x++)
            player_walk_machete[x] = sprite_45.crop(x * 45, 1 * 45, 45, 45);
        for (int x = 0; x < 8; x++)
            player_walk_handgun[x] = sprite_32.crop(x * 45, 1 * 32, 45, 32);
        for (int x = 0; x < 8; x++)
            player_walk_uzi[x] = sprite_32.crop(x * 45, 4 * 32, 45, 32);
        for (int x = 0; x < 8; x++)
            player_walk_shotgun[x] = sprite_32.crop(x * 45, 2 * 32, 45, 32);
        for (int x = 0; x < 8; x++)
            player_walk_rifle[x] = sprite_32.crop(x * 45, 5 * 32, 45, 32);
        for (int x = 0; x < 8; x++)
            player_walk_silencer[x] = sprite_32.crop(x * 45, 6 * 32, 45, 32);

        for (int x = 0; x < 7; x++)
            player_attack_unarmed[x] = sprite_45.crop(x * 60, 3 * 45, 60, 45);
        for (int x = 0; x < 9; x++)
            player_attack_knife[x] = sprite_45.crop(x * 45, 2 * 45, 45, 45);
        for (int x = 0; x < 8; x++)
            player_attack_machete[x] = sprite_45.crop(x * 48, 4 * 45, 48, 45);
        for (int x = 0; x < 2; x++)
            player_attack_handgun[x] = sprite_32.crop(x * 45 + 8 * 45, 1 * 32, 45, 32);
        for (int x = 0; x < 2; x++)
            player_attack_uzi[x] = sprite_32.crop(x * 45 + 8 * 45, 4 * 32, 45, 32);
        for (int x = 0; x < 13; x++)
            player_attack_shotgun[x] = sprite_32.crop(x * 45 + 8 * 45, 2 * 32, 45, 32);
        for (int x = 0; x < 2; x++)
            player_attack_rifle[x] = sprite_32.crop(x * 45 + 8 * 45, 5 * 32, 45, 32);
        for (int x = 0; x < 2; x++)
            player_attack_silencer[x] = sprite_32.crop(x * 48 + 8 * 45, 6 * 32, 48, 32);

        enemy_legs = new BufferedImage[16];

        enemy_walk_knife = new BufferedImage[8];
        enemy_walk_shotgun = new BufferedImage[8];
        enemy_walk_mp = new BufferedImage[8];
        enemy_walk_silencer = new BufferedImage[8];

        enemy_attack_knife = new BufferedImage[9];
        enemy_attack_shotgun = new BufferedImage[13];
        enemy_attack_rifle = new BufferedImage[2];
        enemy_attack_silencer = new BufferedImage[2];

        enemy_die_knife = new BufferedImage[6];
        enemy_die_shotgun = sprite_32.crop(0 * 50, 9 * 32, 50, 32);

        for (int x = 0; x < 16; x++)
            enemy_legs[x] = sprite_32.crop(x * 32, 10 * 32, 32, 32);

        for (int x = 0; x < 8; x++)
            enemy_walk_knife[x] = sprite_32.crop(x * 32, 8 * 32, 32, 32);
        for (int x = 0; x < 8; x++)
            enemy_walk_shotgun[x] = sprite_24.crop(x * 36, 1 * 24, 36, 24);
        for (int x = 0; x < 8; x++)
            enemy_walk_mp[x] = sprite_24.crop(x * 32, 0 * 24, 32, 24);
        for (int x = 0; x < 8; x++)
            enemy_walk_silencer[x] = sprite_24.crop(x * 45, 2 * 24, 45, 24);

        for (int x = 0; x < 9; x++)
            enemy_attack_knife[x] = sprite_45.crop(x * 45, 5 * 45, 45, 45);
        for (int x = 0; x < 13; x++)
            enemy_attack_shotgun[x] = sprite_24.crop(8 * 36 + x * 45, 1 * 24, 45, 24);
        for (int x = 0; x < 2; x++)
            enemy_attack_rifle[x] = sprite_24.crop(8 * 32 + x * 40, 0 * 24, 40, 24);
        for (int x = 0; x < 2; x++)
            enemy_attack_silencer[x] = sprite_24.crop((8 + x) * 45, 2 * 24, 45, 24);
        for (int x = 0; x < 6; x++)
            enemy_die_knife[x] = sprite_40.crop(x * 60, 0 * 40, 60, 40);


        SpriteSheet sprite1 = new SpriteSheet(ImageLoader.loadImage("/textures/sprite1.png"));
        SpriteSheet sheetRocket = new SpriteSheet(ImageLoader.loadImage("/textures/Rocket_80_10.png"));
        SpriteSheet sheet_explosion = new SpriteSheet(ImageLoader.loadImage("/textures/explosion_sprite.png"));
        SpriteSheet sprite_rpg = new SpriteSheet(ImageLoader.loadImage("/textures/200xSprite.png"));
        SpriteSheet sprite_particles1 = new SpriteSheet(ImageLoader.loadImage("/textures/particles1.png"));

        Map1_walls = ImageLoader.loadImage("/textures/Map1_walls.png");
        item_ak_empty = sprite1.crop(8 * 60, 0, 60, 60);
        item_ak_full = sprite1.crop(9 * 60, 0, 60, 60);
        item_shotgun_empty = sprite1.crop(8 * 60, 60, 60, 60);
        item_shotgun_full = sprite1.crop(9 * 60, 60, 60, 60);
        item_mp = sprite1.crop(9 * 60, 60 * 3, 60, 60);
        shell = ImageLoader.loadImage("/textures/shell.png");
        item_pistol = sprite1.crop(9 * 60, 60 * 2, 60, 60);
        map_1 = ImageLoader.loadImage("/textures/Map1.png");
        enemy = ImageLoader.loadImage("/textures/player_1.png");
        map_1layout = ImageLoader.loadImage("/textures/Map1_layout.png");
        menu1 = ImageLoader.loadImage("/textures/menuNeu_main.png");
        menu_layout1 = ImageLoader.loadImage("/textures/menu_layout_main.png");
        menu2 = ImageLoader.loadImage("/textures/menuNeu.png");
        menu_layout2 = ImageLoader.loadImage("/textures/menu_layout1.png");
        sliderKnob = ImageLoader.loadImage("/textures/slider.png");
        player = ImageLoader.loadImage("/textures/player_1.png");
        Bullet = ImageLoader.loadImage("/textures/bullet_4px.png");

        item_rpg_empty = sprite_rpg.crop(0, 0, 200, 200);
        item_rpg_full = sprite_rpg.crop(200, 0, 200, 200);

        //enemy_walk = new BufferedImage[8];
        enemy_walk_ak = new BufferedImage[8];
        rocket = new BufferedImage[5];
        explosion = new BufferedImage[48];
        particles1 = new BufferedImage[5];

        for (int x = 0; x < 5; x++)
            particles1[x] = sprite_particles1.crop(x * 30, 0, 30, 30);

        //for(int x = 0; x < 8; x++)
        //    enemy_walk[x] = sprite1.crop(x*60, 0, 60, 60);
        for (int x = 0; x < 8; x++)
            enemy_walk_ak[x] = sprite1.crop(x * 60, 60, 60, 60);
        int x = 0;
        for (int i = 0; i < 5; i++)
            for (int z = 0; z < 7; z++) {
                x++;
                explosion[x] = sheet_explosion.crop(56 * z, 56 * i, 56, 56); // 56 x 56
            }
        for (int i = 0; i < 5; i++)
            rocket[i] = sheetRocket.crop(80 * i, 0, 80, 10);

        maps[0] = ImageLoader.loadImage("/textures/Map1.png");
        maps[1] = ImageLoader.loadImage("/textures/Map1_layout.png");
    }
}