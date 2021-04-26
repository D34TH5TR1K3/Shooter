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

    public Item(float posX, float posY, int type, int width, int height, Handler handler, World world) {
        super(posX,posY,2, handler, world);
        switch(type) {
            case 1:
                ammo = 1;
                break;
            case 2:
                ammo = 8;
                break;
            default:
                break;
        }
    }

    public void activate(Entity activator) {
        System.out.println("activated");
        switch(type) {
            case 1:

                break;
            case 2:
                if(ammo!=0) {
                    ammo--;
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX(), activator.getY(), activator.getDir() + 180, handler, world));
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
