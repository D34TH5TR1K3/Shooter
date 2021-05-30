package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.World;
import shooter.sound.Sound;

public class Bullet extends Entity {
    private final float speed;                                                                              //hier wird die Geschwindigkeit der Bullet gespeichert
    private final int type;                                                                                 //hier wird die Position der Bullet gespeichert
    Animation animation;                                                                                    //hier wird die Animation der Bullet gespeichert

    //this constructor initializes the values
    public Bullet(float posX, float posY, float dir, int speed, int type, Handler handler, World world) {   //im Konstruktor werden die Position, Geschwindigkeit, etc. initialisiert
        super(posX, posY,3, dir, handler, world);
        this.type = type;
        this.dir = dir;
        this.speed = speed;
        switch(type) {
            case 0:

                break;
            case 1:
                animation = new Animation(Assets.rocket,20);
                //System.out.println(posX+"  "+posY);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void tick() {                                                                                    //in tick wird die Animation, die Bewegung und die Kollision getickt
        if(animation != null)
            animation.tick();

        posX = posX + (float) (Math.cos(Math.toRadians(dir) + Math.PI) * speed);
        posY = posY + (float) (Math.sin(Math.toRadians(dir) + Math.PI) * speed);
        //TODO implement directional movement
        moveAbs(posX, posY);
        if(world.collisionCheck(new Rectangle(((int) posX), ((int) posY), 10, 10))||world.checkEnemyCollision(new Rectangle(((int) posX), ((int) posY), 10, 10))){
            if(type == 1) {
                Sound.play("RocketExplode");
                world.getEntityManager().addParticle(new Particle(((int) posX), ((int) posY), 80, 80, 12, Assets.explosion, handler, world));
            }
            else if(type == 0) {
                world.getEntityManager().addParticle(new Particle(((int) posX), ((int) posY), 20, Assets.particles1, handler, world));
            }
            world.getEntityManager().removeBullet(this);
        }
    }
    @Override
    public void render(Graphics g) {                                                                        //in render wird die Bullet gerendert
        // for debugging
        //g.fillOval((int)(posX - handler.getxOffset()), (int)(posY - handler.getyOffset()), 10, 10);
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);  //save before rotation
        Graphics2D g2 = (Graphics2D)g;  // cast Graphics to Graphics 2d
        switch(type) {
            case 0:
                g2.rotate(Math.toRadians(dir), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));   //rotate graphics object
                g2.drawImage(Assets.Bullet,(int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()),  null);
                break;
            case 1:
                g2.rotate(Math.toRadians(dir + 180), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));   //rotate graphics object
                g.drawImage(animation.getCurrentFrame(), (int) (posX-handler.getxOffset()), (int) (posY-handler.getyOffset()), null);
                //System.out.println(posX+"  "+posY);
                break;
            default:
                break;
        }

        g2.setTransform(reset); //reset rotation
        //TODO implement rendering for the Bullet
    }

    //Getters
    public String getData(){
        return "";
    }
}
