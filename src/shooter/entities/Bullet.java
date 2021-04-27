package shooter.entities;

import java.awt.*;

import shooter.Handler;
import shooter.gfx.World;

public class Bullet extends Entity {
    private float speed = 25;
    private float dir;

    public Bullet(float posX, float posY, float dir, int speed, Handler handler, World world) {
        super(posX, posY,3, dir, handler, world);
        this.dir = dir;
        this.speed = speed;
    }
    
    @Override
    public void tick() {
        posX = posX + (float) (Math.cos(Math.toRadians(dir) + Math.PI) * speed);
        posY = posY + (float) (Math.sin(Math.toRadians(dir) + Math.PI) * speed);
        //TODO implement directional movement
        moveAbs(posX, posY);
        if(collisionCheck(new Rectangle(((int) posX), ((int) posY), 10, 10))){
            world.getEntityManager().removeEntity(this);
        }
    }
    @Override
    public void render(Graphics g) {
        g.drawOval((int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()),10,10);
//        g.drawOval((int) (posX), (int) (posY),10,10);
        //TODO implement rendering for the Bullet
    }
}
