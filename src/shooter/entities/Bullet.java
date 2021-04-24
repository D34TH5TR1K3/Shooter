package shooter.entities;

import java.awt.Graphics;

import shooter.Handler;
import shooter.gfx.World;

public class Bullet extends Entity {

    public Bullet(float posX, float posY, float dir, Handler handler, World world) {
        super(posX, posY,3, dir, handler, world);
    }
    
    @Override
    public void tick() {
        float amtX = 0;
        float amtY = 0;
        //TODO implement directional movement
        move(amtX, amtY);
    }
    @Override
    public void render(Graphics g) {
        g.drawOval(0,0,0,0);
        //TODO implement rendering for the Bullet
    }
}
