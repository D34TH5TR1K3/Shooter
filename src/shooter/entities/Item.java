package shooter.entities;

import java.awt.Graphics;
import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;

import shooter.Handler;
import shooter.gfx.World;
import shooter.sound.Sound;

public class Item extends Entity{
    private boolean visible = true;
    private int type;
    /*
    type in Form von Integern
    type 1: Stimpack
    type 2: Handgun
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
        switch(type) {
            case 1:
                ammo = 1;
                rpm = 300;
                break;
            case 2:
                ammo = 8000;
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
                ammo = 800;
                bulletSpeed = 20;
                rpm = 450;
                break;
            default:
                break;
        }
        bulletDelay = 60 / rpm * 1000;
        //bulletDelay = 1000;
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
                    buX = activator.getX() + CREATURESIZE/2 + (float) (Math.cos(Math.toRadians(activator.dir + Math.PI+20)) * offset);
                    buY = activator.getY() + CREATURESIZE/2 + (float) (Math.sin(Math.toRadians(activator.dir + Math.PI+20)) * offset);
                    world.getEntityManager().addEntitytemp(new Bullet(buX, buY, activator.getDir() + 180, bulletSpeed, handler, world));
                }
                break;
            case 3:
                //System.out.println(now - lastTime);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    //System.out.println(now - lastTime +"   "+bulletDelay);
                    lastTime = System.currentTimeMillis();
                    ammo--;
                    Sound.play("Uzi");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, handler, world));
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
                        world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE / 2, activator.getY() + CREATURESIZE / 2, activator.getDir() + 180 - 10 + dirOffset, bulletSpeed, handler, world));
                    }
                }
                break;
            case 5:
                //System.out.println(now - lastTime);
                if(ammo!=0 && now - lastTime > bulletDelay) {
                    //System.out.println(now - lastTime +"   "+bulletDelay);
                    lastTime = System.currentTimeMillis();
                    ammo--;
                    Sound.play("Ak");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, handler, world));
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
        //TODO render Item, render for each bullet
    }
}
