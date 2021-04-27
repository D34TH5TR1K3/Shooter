package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Assets;
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
        //g.drawOval((int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()),10,10);

        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D)g;
        g2.rotate(Math.toRadians(dir), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));
        g.drawImage(Assets.Bullet,(int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()),  null);
        g2.setTransform(reset);
        //TODO implement rendering for the Bullet
    }
}
