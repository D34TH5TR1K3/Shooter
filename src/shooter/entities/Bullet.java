package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.utils.Sound;
import shooter.world.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends Entity {
    //saves the bullets type and speed
    private final float speed;
    private final int type;
    //saves the bullets animation
    Animation animation;

    //this constructor initializes the values
    public Bullet(float posX, float posY, float dir, int speed, int type, Handler handler, Level level) {
        super(posX, posY,3, dir, handler, level);
        this.type = type;
        this.dir = dir;
        this.speed = speed;
        switch(type) {
            case 1,2:

                break;
            case 0:
                animation = new Animation(Assets.rocket,20);
                break;
            default:
                break;
        }
    }

    //ticks the bullets movement, collision and animation
    @Override
    public void tick() {
        if(animation != null)
            animation.tick();

        posX = posX + (float) (Math.cos(Math.toRadians(dir) + Math.PI) * speed);
        posY = posY + (float) (Math.sin(Math.toRadians(dir) + Math.PI) * speed);
        //TODO implement directional movement
        moveAbs(posX, posY);
        if(level.collisionCheck(new Rectangle(((int) posX), ((int) posY), 10, 10))||level.checkEnemyCollision(new Rectangle(((int) posX), ((int) posY), 10, 10))){
            if(type == 0) {
                Sound.play("RocketExplode");
                level.getEntityManager().addEntity(new Particle(((int) posX), ((int) posY), 80, 80, 12, Assets.explosion, handler, level));
            }else if(type > 0)
                level.getEntityManager().addEntity(new Particle(((int) posX), ((int) posY), 20, Assets.particles1, handler, level));
            level.getEntityManager().removeEntity(this);
        }
        //if(type == 2)
        //    level.checkPlayerCollision(new Rectangle(((int) posX), ((int) posY), 10, 10));
    }
    //renders the bullet
    @Override
    public void render(Graphics g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D)g;
        switch(type) {
            case 1,2:
                g2.rotate(Math.toRadians(dir), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));
                g2.drawImage(Assets.Bullet,(int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()),  null);
                break;
            case 0:
                g2.rotate(Math.toRadians(dir + 180), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));
                g.drawImage(animation.getCurrentFrame(), (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), null);
                break;
            default:
                break;
        }

        g2.setTransform(reset);
        //TODO implement rendering for the Bullet
    }

    //getters
    public String getData(){ return ""; }
}
