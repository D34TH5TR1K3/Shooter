package shooter.entities;

import shooter.Handler;
import shooter.gfx.Assets;
import shooter.world.Level;
import shooter.utils.Sound;

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
    //saves the speed of a bullet and the rate of fire
    private short bulletSpeed;
    private float rpm;
    //saves the time it takes to reload or use an item
    private short reloadTime;
    private final float bulletDelay;
    //logical variable
    private long lastTime = 0;
    //saves the value of the offset between an item and a fired bullet
    private short offset;

    //this constructor initializes the values
    public Item(float posX, float posY, int type, Handler handler, Level level) {
        super(posX,posY,2, handler, level);
        this.type = type;
        active = true;
        switch(type) {
            case 1:
                ammo = 8;
                bulletSpeed = 20;
                rpm = 200;
                reloadTime = 120;
                offset = 70;
                break;
            case 2:
                ammo = 30;
                bulletSpeed = 20;
                rpm = 450;
                reloadTime = 180;
                offset = 50;
                break;
            case 3:
                ammo = 25;
                bulletSpeed = 15;
                rpm = 600;
                reloadTime = 120;
                offset = 80;
                break;
            case 4:
                ammo = 6;
                bulletSpeed = 20;
                rpm = 50;
                reloadTime = 240;
                offset = 70;
                break;
            case 5:
                ammo = 1;
                bulletSpeed = 20;
                rpm = 30;
                reloadTime = 240;
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
                    g.drawImage(Assets.item_pistol, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 30, 30, null);
                    break;
                case 2:
                    if(ammo > 0)
                        g.drawImage(Assets.item_ak_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    else if(ammo == 0)
                        g.drawImage(Assets.item_ak_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    //System.out.println(posX+"  "+posY);
                    break;
                case 3:
                    g.drawImage(Assets.item_uzi, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    break;
                case 4:
                    g.drawImage(Assets.item_shotgun_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    break;
                case 5:
                    if(ammo > 0)
                        g.drawImage(Assets.item_rpg_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    if(ammo == 0)
                        g.drawImage(Assets.item_rpg_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    break;
                default:
                    break;
            }
        }
    }

    //method to reload ammo (usage in a tick method required)
    public void reload() {
        switch(type) {
            case 1:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 8;
                    reloadTime = 120;
                }
                break;
            case 2:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 30;
                    reloadTime = 150;
                }
                break;
            case 3:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 25;
                    reloadTime = 120;
                }
                break;
            case 4:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 6;
                    reloadTime = 240;
                }
                break;
            case 5:
                reloadTime--;
                if(reloadTime == 0){
                    ammo = 1;
                    reloadTime = 240;
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
                    Sound.play("Uzi");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    break;
                case 2:
                    Sound.play("Ak");

                    level.getEntityManager().addEntity(new Particle(((int) (activator.getX())), ((int) (activator.getY())), activator.getDir(), Assets.shell, handler, level, 600));

                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    break;
                case 3:
                    Sound.play("Uzi");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI -2)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI -2)) * offset);
                    float dirOffset_uzi = (float) (Math.random() * 8);
                    level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180f - 4f + dirOffset_uzi, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    break;
                case 4:
                    Sound.play("Shotgun");
                    buX = activator.getX() + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    buY = activator.getY() + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI + 0)) * offset);
                    for (int i = 0; i < 6; i++) {
                        float dirOffset_shotgun = (float) (Math.random() * 20);
                        level.getEntityManager().addEntity(new Bullet(buX, buY, activator.getDir() + 180 - 10 + dirOffset_shotgun, bulletSpeed, (activator.getClass().equals(Enemy.class)?2:1), handler, level));
                    }
                    break;
                case 5:
                    Sound.play("RocketLaunch");
                    level.getEntityManager().addEntity(new Bullet(activator.getX() + (float) CREATURESIZE / 2, activator.getY() + (float) CREATURESIZE / 2, activator.getDir() + 180, bulletSpeed, 0, handler, level));
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
}
