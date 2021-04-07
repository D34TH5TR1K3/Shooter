package shooter.entities;

import java.awt.Graphics;

public abstract class Entity {
    private float posX, posY;
    private int posZ = 0;
    /*
    posZ in Form von Integern
    posZ 0: Level
    posZ 1: Corpse
    posZ 2: Item
    posZ 3: Bullet
    posZ 4: Player/Enemies
    posZ 5: Interactables
    */
    private float dir = 0;
    private boolean solid = false;
    protected boolean active = true;

    public Entity(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public Entity(float posX, float posY, float dir) {
        this.posX = posX;
        this.posY = posY;
        this.dir = dir;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void move(float amtX, float amtY) {
        posX += amtX;
        posY += amtY;
    }
    public void face(float amt) {
        dir = amt;
    }

    public void setInActive() { active = false; }
    public void setZ(int posZ) { this.posZ = posZ; }
    public boolean isSolid() { return solid; }
    public float getX() { return posX; }
    public float getY() { return posY; }
    public float facing() { return dir; }
}