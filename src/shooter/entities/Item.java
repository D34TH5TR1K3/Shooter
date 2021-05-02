package shooter.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.World;
import shooter.sound.Sound;

public class Item extends Entity{
    private boolean visible = true;
    private int type;
    /*
    type in Form von Integern
    type 1: Handgun
    type 2: Ak
    type 3: m16
    type 4: shotgun
    type 5: rpg
    type 6:
    */
    //TODO add gun types
    private int ammo = 0;
    private ArrayList<Bullet> bullets;
    private World world;
    private int bulletSpeed;
    private float rpm;
    private float bulletDelay;
    private long now = 0;
    private long lastTime = 0;
    private int offset;

    public Item(float posX, float posY, int type, int width, int height, Handler handler, World world) {
        super(posX,posY,2, handler, world);
        this.type = type;
        this.world = world;
        active = true;
        switch(type) {
            case 1:
                ammo = 1;
                rpm = 300;
                break;
            case 2:
                ammo = 30;
                bulletSpeed = 20;
                rpm = 900;
                offset = 50;
                break;
            case 3:
                ammo = 800;
                bulletSpeed = 20;
                rpm = 300;
                break;
            case 4:
                ammo = 800;
                bulletSpeed = 20;
                rpm = 50;
                break;
            case 5:
                ammo = 1;
                bulletSpeed = 20;
                rpm = 30;
                break;
            default:
                break;
        }
        bulletDelay = 60 / rpm * 1000;
        //bulletDelay = 1000;
    }

    public void drop(Entity activator){
        active = true;
        posX = activator.getX();
        posY = activator.getY();
    }

    public void pick_up(Entity activator){
        active = false;
        posX = activator.getX();
        posY = activator.getY();
    }

    public void activate(Entity activator) {
        float buX, buY;
        //System.out.println(activator.getX()+CREATURESIZE/2+"   "+ activator.getY()+CREATURESIZE/2);
        //System.out.println("activated");
        //System.out.println(type);
        now = System.currentTimeMillis();
        //now = System.nanoTime() * 1000000;
        switch(type) {
            case 2:
                //System.out.println(ammo);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    lastTime = now;
                    ammo--;
                    Sound.play("Ak");
                    //System.out.println("shooting");
                    buX = activator.getX() + CREATURESIZE/2 + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI+0)) * offset);
                    buY = activator.getY() + CREATURESIZE/2 + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI+0)) * offset);
                    world.getEntityManager().addEntitytemp(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, 0, handler, world));
                }
                break;
            case 3:
                //System.out.println(now - lastTime);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    //System.out.println(now - lastTime +"   "+bulletDelay);
                    lastTime = System.currentTimeMillis();
                    ammo--;
                    Sound.play("Uzi");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, 0, handler, world));
                }
                break;
            case 4:
                //System.out.println(now);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    lastTime = System.currentTimeMillis();
                    ammo--;
                    Sound.play("Shotgun");
                    for(int i = 0; i < 6; i++) {
                        float dirOffset = (float)(Math.random() * 20);
                        world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE / 2, activator.getY() + CREATURESIZE / 2, activator.getDir() + 180 - 10 + dirOffset, bulletSpeed, 0, handler, world));
                    }
                }
                break;
            case 5:
                //System.out.println(now - lastTime);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    //System.out.println(now - lastTime +"   "+bulletDelay);
                    lastTime = System.currentTimeMillis();
                    ammo--;
                    Sound.play("RocketLaunch");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, 1, handler, world));
                }
                break;
            default:
                break;
        }
        //TODO implement different ammotypes
    }

    @Override
    public void tick() { 
        //TODO tick for each bullet
    }
    @Override
    public void render(Graphics g) {
        if(active){
            switch(type) {
                case 1:
                    break;
                case 2:
                    if(ammo > 0)
                        g.drawImage(Assets.item_ak_full, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    else if(ammo == 0)
                        g.drawImage(Assets.item_ak_empty, (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), 120, 120, null);
                    //System.out.println(posX+"  "+posY);
                    break;
                case 3:
                    break;
                case 4:
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
        //TODO render Item, render for each bullet
    }

    public int getType() {
        return type;
    }
}
