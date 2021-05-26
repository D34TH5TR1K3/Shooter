package shooter.entities;


import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Particle{

    private int x, y, speed, yNew, xNew;
    private int timeLeftToLive = 1000;
    private BufferedImage[] frames;
    private BufferedImage frame;
    private Animation AnimParticle;
    private Handler handler;
    private World world;
    private int width = 0, height = 0;
    private float dir;
    private int type = 0;
    private int speedFactor = 10;

    public Particle(int x, int y, int speed, BufferedImage[] frames, Handler handler, World world){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.frames = frames;
        this.handler = handler;
        this.world = world;
        AnimParticle = new Animation(frames,speed);
    }
    public Particle(int x, int y, int speed, float dir, BufferedImage frame, Handler handler, World world){
        this.x = x;
        this.y = y;
        this.xNew = x;
        this.yNew = y;
        this.dir = dir;
        this.speed = speed;
        this.frame = frame;
        this.handler = handler;
        this.world = world;
        type = 1;
    }
    public Particle(int x, int y, int speed, float dir, BufferedImage frame, Handler handler, World world, int timeLeftToLive){
        this.x = x;
        this.y = y;
        this.xNew = x;
        this.yNew = y;
        this.dir = dir;
        this.speed = speed;
        this.frame = frame;
        this.handler = handler;
        this.world = world;
        this.timeLeftToLive = timeLeftToLive;
        type = 1;
    }
    public Particle(int x, int y, int width, int height, int speed, BufferedImage[] frames, Handler handler, World world){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.frames = frames;
        this.handler = handler;
        this.world = world;
        AnimParticle = new Animation(frames,speed);
    }
    public void tick(){
        timeLeftToLive--;
        if(type == 0) {
            AnimParticle.tick();
        }else if(type == 1){
            if(!world.collisionCheck(new Rectangle(x+27, y+21+speedFactor, 3, 3)))
                yNew += speedFactor;  //TODO: random shells
            if(speedFactor > 0)
                speedFactor--;
        }
    }
    public void render(Graphics g){
        if(type == 0) {
            if (width == 0)
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), null);
            else
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), width, height, null);
        }else if(type == 1) {
            AffineTransform reset = new AffineTransform();
            reset.rotate(0, 0, 0);  //save before rotation
            Graphics2D g2 = (Graphics2D)g;  // cast Graphics to Graphics 2d

            g2.rotate(Math.toRadians(dir), (int) (x - handler.getGameCamera().getxOffset()), (int) (y -handler.getGameCamera().getyOffset()));   //rotate graphics object
            g2.drawImage(frame, ((int) (xNew +27- handler.getxOffset())), ((int) (yNew +9- handler.getyOffset())), null);

            g2.setTransform(reset); //reset rotation
        }
    }

    public Animation getAnimParticle() {
        return AnimParticle;
    }
    public int getType() {
        return type;
    }
    public int getTimeLeftToLive(){
        return timeLeftToLive;
    }
}
