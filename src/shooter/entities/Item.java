package shooter.entities;

import java.awt.Graphics;
import java.util.ArrayList;

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

    public Item(float posX, float posY, int type) {
        super(posX, posY);
        setZ(2);
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

    public void fire() {
        //TODO implement different ammotypes
        if(ammo!=0)
            ammo--;
        if(type!=0)
            bullets.add(new Bullet(0,0,0));
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
