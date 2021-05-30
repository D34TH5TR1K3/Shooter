package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Tile;
import shooter.gfx.World;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class Entity {
    protected float posX;                       //die Position der Entitaet
    protected float posY;
    private final int posZ;                     //die Position auf der Z-Achse (relevant zum rendern)
    public static final int CREATURESIZE = 180; //ein Wert in Pixeln, wie gross eine Creature default ist
    /*
    posZ in Form von Integern
    posZ 0: Level
    posZ 1: Corpse
    posZ 2: Item
    posZ 3: Bullet
    posZ 4: Player/Enemies
    posZ 5: Interactables
    */
    protected float dir = 0;                    //die Blickrichtung der Entitaet in Grad
    protected boolean solid = false;            //ob die Entitaet solide ist oder nicht
    protected boolean active = false;           //ob die Entitaet ueberhaupt aktiv ist
    protected World world;                      //Zwischenspeicher der Welt, in der die Entitaet existiert
    protected Handler handler;                  //Zwischenspeicher des Handlers
    protected Animation activeAnimation;        //hier wird die Animation der Entitaet gespeichert, die dargestellt wird, solange die Entitaet aktiv ist

    //these constructors initialize the values
    public Entity(float posX, float posY, int posZ, Handler handler, World world) {             //im Konstruktor werden die Position und die Groesse der Entitaet initialisiert
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.handler = handler;
    }
    public Entity(float posX, float posY, int posZ, float dir, Handler handler, World world) {  //im Konstruktor werden die Position und die Groesse der Entitaet initialisiert
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dir = dir;
        this.handler = handler;
    }


    public boolean checkLineOfSight(Line2D.Float line){                                         //eine Methode, die errechnet, ob der Gegner eine freie Schusslinie zum Spieler hat
        for(int x=0;x<64*world.getMapsize();x++){
            for(int y=0;y<36*world.getMapsize();y++){
                Tile temptile = world.getTiles(x,y);
                if(line.intersects(temptile.getHitbox())&& temptile.isSolid())
                    return true;
            }
        }
        return false;
    }

    //abstrakte tick und renders zum erben
    public abstract void tick();
    public abstract void render(Graphics g);

    public void move(float amtX, float amtY) {                                                  //eine Methode um die Entitaet einen Schritt zu bewegen
        //System.out.println(amtX +"   "+amtY);
        posX += amtX;
        posY += amtY;
    }
    public void moveAbs(float amtX, float amtY) {                                               //eine Methode um die Entitaet auf eine absolute Position zu setzen
        //System.out.println(amtX +"   "+amtY);
        posX = amtX;
        posY = amtY;
    }
    public void face(float amt) {                                                               //eine Methode um die Blickrichtung der Entitaet auf einen bestimmten Wert zu setzen
        dir = amt;
    }

    //Getters und Setters
    public void setInActive() { active = false; }
    public void setActive() { active = true; }
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

    public abstract String getData();
}
