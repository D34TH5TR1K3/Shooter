package shooter.entities;


import shooter.Handler;
import shooter.gfx.Animation;
import shooter.levels.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Particle{
    //saves the particles remaining time
    private int timeLeftToLive = 1000;
    //saves the particles animation or texture
    private BufferedImage texture;
    private Animation AnimParticle;
    //handler and world distribute variables
    private final Handler handler;
    private final Level level;
    //saves the size and direction
    private int width = 0, height = 0;
    private final int x, y;
    private int xNew, yNew;
    private float dir;
    //saves the particles type
    private byte type = 0;
    //saves the particles speed
    private byte speedFactor = 10;

    //these constructors initialize the values
    public Particle(int x, int y, int speed, BufferedImage[] frames, Handler handler, Level level){
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.level = level;
        AnimParticle = new Animation(frames,speed);
    }
    public Particle(int x, int y, float dir, BufferedImage texture, Handler handler, Level level, int timeLeftToLive){
        this.x = x;
        this.y = y;
        this.xNew = x;
        this.yNew = y;
        this.dir = dir;
        this.texture = texture;
        this.handler = handler;
        this.level = level;
        this.timeLeftToLive = timeLeftToLive;
        type = 1;
    }
    public Particle(int x, int y, int width, int height, int speed, BufferedImage[] frames, Handler handler, Level level){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.level = level;
        AnimParticle = new Animation(frames,speed);
    }

    //ticks a particles collision and animation
    public void tick(){
        timeLeftToLive--;
        if(type == 0)
            AnimParticle.tick();
        else if(type == 1){
            if(!level.collisionCheck(new Rectangle(x+27, y+21+speedFactor, 3, 3)))
                yNew += speedFactor;
            if(speedFactor > 0)
                speedFactor--;
        }
    }
    //renders animations or singular textures
    public void render(Graphics g){
        if(type == 0) {
            if (width == 0)
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), null);
            else
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), width, height, null);
        }else if(type == 1) {
            AffineTransform reset = new AffineTransform();
            reset.rotate(0, 0, 0);
            Graphics2D g2 = (Graphics2D)g;

            g2.rotate(Math.toRadians(dir), (int) (x - handler.getGameCamera().getxOffset()), (int) (y -handler.getGameCamera().getyOffset()));   //rotate graphics object
            g2.drawImage(texture, ((int) (xNew +27- handler.getxOffset())), ((int) (yNew +9- handler.getyOffset())), null);

            g2.setTransform(reset);
        }
    }

    //getters and setters
    public Animation getAnimParticle() { return AnimParticle; }
    public byte getType() { return type; }
    public int getTimeLeftToLive(){ return timeLeftToLive; }
}
