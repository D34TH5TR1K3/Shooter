package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Tile;
import shooter.gfx.World;

import java.awt.*;

public abstract class Entity {
    protected float posX;
    protected float posY;
    private int posZ = 0;
    private int width, height;
    public static final int CREATURESIZE = 180;
    /*
    posZ in Form von Integern
    posZ 0: Level
    posZ 1: Corpse
    posZ 2: Item
    posZ 3: Bullet
    posZ 4: Player/Enemies
    posZ 5: Interactables
    */
    protected float dir = 0;
    private boolean solid = false;
    protected boolean active = false;
    protected World world;
    protected Handler handler;
    protected Animation activeAnimation;

    public Entity(float posX, float posY, int posZ, Handler handler, World world) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.width = width;
        this.height = height;
        this.handler = handler;
    }
    public Entity(float posX, float posY, int posZ, float dir, Handler handler, World world) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dir = dir;
        this.width = width;
        this.height = height;
        this.handler = handler;
    }

    public boolean collisionCheck(Rectangle rect){
        //System.out.println(rect.getBounds());
        for(int y = (int) (0 + rect.getY()/30); y < 4 + rect.getY()/30; y++){
            for(int x = (int) (0 + rect.getX()/30); x < 4 + rect.getX()/30; x++){
                if(x >= 0 && x < world.getTiles().length && y >= 0 && y < world.getTiles()[0].length){
                    Tile temptile = world.getTiles(x, y);
                    if(temptile.isSolid() && temptile.getHitbox().intersects(rect)){
                        //System.out.println(temptile.getTposX() +"  "+ temptile.getTposY());
                        return true;
                    }
                    //System.out.println(temptile.getTposX()*30 + "   " + temptile.getTposY()*30+"   ");
                }
            }
            //System.out.println();
        }
        return false;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void move(float amtX, float amtY) {
        //System.out.println(amtX +"   "+amtY);
        posX += amtX;
        posY += amtY;
    }
    public void moveAbs(float amtX, float amtY) {
        //System.out.println(amtX +"   "+amtY);
        posX = amtX;
        posY = amtY;
    }
    public void face(float amt) {
        dir = amt;
    }

    public void setInActive() { active = false; }
    public boolean isSolid() { return solid; }
    public float getX() { return posX; }
    public float getY() { return posY; }
    public int getZ() { return posZ; }
    public float facing() { return dir; }

    public float getDir() {
        return dir;
    }

    public void setDir(float dir) {
        this.dir = dir;
    }
}
