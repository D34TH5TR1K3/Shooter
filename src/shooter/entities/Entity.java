package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Tile;
import shooter.gfx.World;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class Entity {
    //saves the entities position and default size
    protected float posX;
    protected float posY;
    private final int posZ;
    public static final short CREATURESIZE = 180;
    /*
    posZ in Form von Integern
    posZ 0: Level
    posZ 1: Corpse
    posZ 2: Item
    posZ 3: Bullet
    posZ 4: Player/Enemies
    posZ 5: Interactables
    */
    //saves the direction the entity faces towards
    protected float dir = 0;
    //indicates whether the entity is solid or active
    protected boolean solid = false;
    protected boolean active = false;
    //world and handler distribute variables
    protected World world;
    protected Handler handler;
    //saves the Animation to render
    protected Animation activeAnimation;

    //these constructors initialize the values
    public Entity(float posX, float posY, int posZ, Handler handler, World world) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.handler = handler;
    }
    public Entity(float posX, float posY, int posZ, float dir, Handler handler, World world) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dir = dir;
        this.handler = handler;
    }

    //abstract tick and render without bodies
    public abstract void tick();
    public abstract void render(Graphics g);

    //method to check if a line of sight is clear
    public boolean checkLineOfSight(Line2D.Float line){
        for(int x=0;x<64*world.getMapsize();x++)
            for(int y=0;y<36*world.getMapsize();y++){
                Tile temptile = world.getTiles(x,y);
                if(line.intersects(temptile.getHitbox())&& temptile.isSolid())
                    return true;
            }
        return false;
    }
    //method to move a certain amount
    public void move(float amtX, float amtY) {
        posX += amtX;
        posY += amtY;
    }
    //method to move to a certain position
    public void moveAbs(float amtX, float amtY) {
        posX = amtX;
        posY = amtY;
    }
    //method to face a certain direction
    public void face(float amt) {
        dir = amt;
    }

    //getters and setters
    public void setInActive() { active = false; }
    public void setActive() { active = true; }
    public boolean isSolid() { return solid; }
    public float getX() { return posX; }
    public float getY() { return posY; }
    public int getZ() { return posZ; }
    public float facing() { return dir; }
    public float getDir() { return dir; }
    public void setDir(float dir) { this.dir = dir; }
    public abstract String getData();
}
