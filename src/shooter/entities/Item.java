package shooter.entities;

import shooter.Handler;
import shooter.gfx.Assets;
import shooter.utils.Sound;
import shooter.world.Level;

import java.awt.*;

public class Item extends Entity{
    //indicates whether an item is visible
    private boolean visible = true;
    //saves the type of the item
    private final int type;
    /*
    type in Form von Integern
    type 1: Handgun
    type 2: Ak
    type 3: m16
    type 4: shotgun
    type 5: rpg
    type 6:
    */
    //saves the ammo count
    private int ammo = 0;
    private int ammoMax = 0;
    //saves the speed of a bullet and the rate of fire
    private short bulletSpeed;
    private float rpm;
    //saves the time it takes to reload or use an item
    private short reloadTime;
    private final float bulletDelay;
    //logical variable
    private long lastTime = 0;
    //saves the value of the offset between an item and a fired bullet
    private short offset = 1;
    //indicates whether an item is melee
    private boolean melee = false;

    //this constructor initializes the values
    public Item(float posX, float posY, int type, Handler handler, Level level) {
        super(posX,posY,2, handler, level);
        this.type = type;
        active = true;
        switch(type) {
            case 1:
                //Knife
                melee = true;
                rpm = 120;
                ammoMax = 1;
                ammo = ammoMax;
                break;
            case 2:
                //Machete
                melee = true;
                rpm = 60;
                ammoMax = 1;
                ammo = ammoMax;
                break;
            case 3:
                //Pistol
                ammoMax = 17;
                ammo = ammoMax;
                bulletSpeed = 25;
                rpm = 400;
                reloadTime = 90;
                offset = 100;
                break;
            case 4:
                //Silenced Pistol
                ammoMax = 8;
                ammo = ammoMax;
                bulletSpeed = 35;
                rpm = 85;
                reloadTime = 120;
                offset = 70;
                break;
            case 5:
                //Machine Pistol
                ammoMax = 25;
                ammo = ammoMax;
                bulletSpeed = 20;
                rpm = 600;
                reloadTime = 120;
                offset = 80;
                break;
            case 6:
                //Rifle
                ammoMax = 30;
                ammo = ammoMax;
                bulletSpeed = 35;
                rpm = 120;
                reloadTime = 180;
                offset = 50;
                break;
            case 7:
                //Shotgun
                ammoMax = 6;
                ammo = ammoMax;
                bulletSpeed = 20;
                rpm = 60;
                reloadTime = 320;
                offset = 70;
                break;
            case 8:
                //Rocket Launcher
                ammoMax = 1;
                ammo = ammoMax;
                bulletSpeed = 25;
                rpm = 1;
                reloadTime = 320;
                break;
            default:
                break;
        }
        bulletDelay = 60 / rpm * 1000;
    }

    //ticks nothing
    @Override
    public void tick() { }
    //renders the item if it is active
    @Override
    public void render(Graphics g) {
        if(active){
            switch(type) {
                case 1:
                    g.drawImage(Assets.item_knife, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 80, 80, null);
                    break;
                case 2:
                    g.drawImage(Assets.item_machete, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 70, 70, null);
                    break;
                case 3:
                    g.drawImage(Assets.item_handgun, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 100, 100, null);
                    break;
                case 4:
                    g.drawImage(Assets.item_silencer, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 100, 100, null);
                    break;
                case 5:
                    if(ammo == 0)
                        g.drawImage(Assets.item_uzi_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 100, 100, null);
                    else
                        g.drawImage(Assets.item_uzi_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 100, 100, null);
                    break;
                case 6:
                    if(ammo == 0)
                        g.drawImage(Assets.item_rifle_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 80, 80, null);
                    else
                        g.drawImage(Assets.item_rifle_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 80, 80, null);
                    break;
                case 7:
                    g.drawImage(Assets.item_shotgun, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 80, 80, null);
                    break;
                case 8:
                    if(ammo == 0)
                    g.drawImage(Assets.item_rpg_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    else
                        g.drawImage(Assets.item_rpg_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    break;
                default:
                    break;
            }
        }
    }

    //method to reload ammo (usage in a tick method required)
    public void reload() {
        switch(type) {
            case 3:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 17;
                    reloadTime = 90;
                }
                break;
            case 4:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 8;
                    reloadTime = 120;
                }
                break;
            case 5:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 25;
                    reloadTime = 120;
                }
                break;
            case 6:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 30;
                    reloadTime = 180;
                }
                break;
            case 7:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 6;
                    reloadTime = 320;
                }
                break;
            case 8:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 1;
                    reloadTime = 320;
                }
                break;
            default:
                break;
        }
    }
    //method to drop an item
    public void drop(Entity activator){
        active = true;
        posX = activator.getX();
        posY = activator.getY();
    }
    //method to pick an item up
    public void pick_up(Entity activator){
        active = false;
        posX = activator.getX();
        posY = activator.getY();
    }
    //method to use an item
    public void activate(Entity activator) {
        float buX, buY;
        long now = System.currentTimeMillis();
        if(ammo!=0&&now-lastTime>bulletDelay) {
            lastTime = now;
            ammo--;
            switch(type) {
                case 1:
                    ammo++;
                    Sound.play("Uzi");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * 60);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * 60);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, 3, handler, level));
                    break;
                case 2:
                    ammo++;
                    Sound.play("Uzi");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * 60);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * 60);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, 3, handler, level));
                    break;
                case 3:
                    Sound.play("Uzi");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, (activator.getClass().equals(Enemy.class) ? 2 : 1), handler, level));
                    break;
                case 4:
                    Sound.play("Uzi");
                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    break;
                case 5:
                    Sound.play("Uzi");
                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));

                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI -2)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI -2)) * offset);
                    float dirOffset_uzi = (float) (Math.random() * 8);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180f - 4f + dirOffset_uzi, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    break;
                case 6:
                    Sound.play("Ak");
                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));

                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY))
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));

                    break;
                case 7:
                    Sound.play("Shotgun");
                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));

                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    if(!level.lineCollision((int)activator.getX(), (int)activator.getY(), (int)buX, (int)buY)) {
                        for (int i = 0; i < 6; i++) {
                            float dirOffset_shotgun = (float) (Math.random() * 20);
                            level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180 - 10 + dirOffset_shotgun, bulletSpeed, (activator.getClass().equals(Enemy.class) ? 2 : 1), handler, level));
                        }
                    }
                    break;
                case 8:
                    Sound.play("RocketLaunch");
                    level.getEntityManager().addEntity(new Bullet(activator.getX(), activator.getY(), activator.getDir() + 180, bulletSpeed, 0, handler, level));
                    break;
                default:
                    break;
            }
        }
    }

    //getters and setters
    public int getType() { return type; }
    public int getAmmo(){ return ammo; }
    public void setAmmo(int ammo){ this.ammo = ammo; }
    public String getData(){ return ""; }
    public boolean isMelee(){ return melee; }
    public int getAmmoMax() {
        return ammoMax;
    }
}
