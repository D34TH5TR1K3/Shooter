package shooter.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import shooter.Handler;
import shooter.gfx.World;

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
    private int rpm;
    private float bulletDelay;
    private float now = 0;
    private float lastTime = 0;

    public Item(float posX, float posY, int type, int width, int height, Handler handler, World world) {
        super(posX,posY,2, handler, world);
        this.type = type;
        this.world = world;
        switch(type) {
            case 1:
                ammo = 1;
                break;
            case 2:
                ammo = 800;
                bulletSpeed = 10;
                rpm = 300;
                break;
            case 3:
                ammo = 800;
                bulletSpeed = 20;
                break;
            case 4:
                ammo = 800;
                bulletSpeed = 20;
                break;
            default:
                break;
        }

        bulletDelay = 60 / rpm;
    }

    public void activate(Entity activator) {
        //System.out.println("activated");
        //System.out.println(type);
        now = System.currentTimeMillis();
        switch(type) {
            case 2:
                //System.out.println(ammo);
                if(ammo!=0 && now - lastTime > bulletDelay * 1000) {
                    lastTime = now;
                    ammo--;
                    //System.out.println("shooting");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, handler, world));
                }
                break;
            case 3:
                //System.out.println(ammo);
                if(ammo!=0) {
                    ammo--;
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, bulletSpeed, handler, world));
                }
                break;
            case 4:
                if(ammo!=0) {
                    ammo--;
                    for(int i = 0; i < 6; i++) {
                        float dirOffset = (float)(Math.random() * 20);
                        world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE / 2, activator.getY() + CREATURESIZE / 2, activator.getDir() + 180 - 10 + dirOffset, bulletSpeed, handler, world));
                    }
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
