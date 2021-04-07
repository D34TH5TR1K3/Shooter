package shooter.entities;

import java.awt.Graphics;

public class Bullet extends Entity {

    public Bullet(float posX, float posY, float dir) { 
        super(posX, posY, dir);
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
