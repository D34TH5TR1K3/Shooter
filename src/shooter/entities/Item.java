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
        this.type = type;
        this.world = world;
        switch(type) {
            case 1:
                ammo = 1;
                break;
            case 2:
                ammo = 800;
                break;
            default:
                break;
        }
    }

    public void activate(Entity activator) {
        //System.out.println("activated");
        //System.out.println(type);
        switch(type) {
            case 2:
                System.out.println(ammo);
                if(ammo!=0) {
                    ammo--;
                    //System.out.println("shooting");
                    world.getEntityManager().addEntitytemp(new Bullet(activator.getX() + CREATURESIZE/2, activator.getY() + CREATURESIZE/2, activator.getDir() + 180, handler, world));
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
