package shooter.entities;


import shooter.Handler;
import shooter.gfx.Animation;
import shooter.world.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Particle extends Entity{
    //saves the particles remaining time
    private int timeLeftToLive = 1000;
    //saves the particles animation or texture
    private BufferedImage texture;
    private Animation AnimParticle;
    //saves the size and direction
    private int width = 0, height = 0;
    private int xNew, yNew;
    //saves the particles type
    private byte type = 0;
    //saves the particles speed
    private byte speedFactor = 10;
    private int gun;

    //these constructors initialize the values
    public Particle(int x, int y, int speed, BufferedImage[] frames, Handler handler, Level level){
        super(x,y,4,handler, level);
        AnimParticle = new Animation(frames,speed, 666, 666);
    }
    public Particle(int x, int y, float dir, BufferedImage texture, Handler handler, Level level, int timeLeftToLive, int type){
        super(x,y,4,dir,handler, level);
        this.xNew = x;
        this.yNew = y;
        this.texture = texture;
        this.timeLeftToLive = timeLeftToLive;
        this.gun = type;
        this.type = 1;
    }
    public Particle(int x, int y, int width, int height, int speed, BufferedImage[] frames, Handler handler, Level level){
        super(x,y,4,handler, level);
        this.width = width;
        this.height = height;
        AnimParticle = new Animation(frames,speed);
    }

    //ticks a particles collision and animation
    public void tick(){
        timeLeftToLive--;
        if(type == 0)
            AnimParticle.tick();
        else if(type == 1){
            if(!level.collisionCheck(new Rectangle((int)posX+27, (int)posY+21+speedFactor, 3, 3))){
                if(gun == 6 || gun == 7)
                    yNew += speedFactor;
                else
                    yNew -= speedFactor;
            }
            if(speedFactor > 0)
                speedFactor--;
        }
    }
    //renders animations or singular textures
    public void render(Graphics g){
        if(type == 0) {
            if (width == 0)
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (posX - handler.getxOffset())), ((int) (posY - handler.getyOffset())), null);
            else
                g.drawImage(AnimParticle.getCurrentFrame(), ((int) (posX - handler.getxOffset())), ((int) (posY - handler.getyOffset())), width, height, null);
        }else if(type == 1) {
            AffineTransform reset = new AffineTransform();
            reset.rotate(0, 0, 0);
            Graphics2D g2 = (Graphics2D)g;

            g2.rotate(Math.toRadians(dir), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY -handler.getGameCamera().getyOffset()));   //rotate graphics object
            g2.drawImage(texture, ((int) (xNew +27- handler.getxOffset())), ((int) (yNew +9- handler.getyOffset())), null);

            g2.setTransform(reset);
        }
    }

    //getters and setters
    public Animation getAnimParticle() { return AnimParticle; }
    public byte getType() { return type; }
    public int getTimeLeftToLive(){ return timeLeftToLive; }
    @Override
    public String getData() { return ""; }
}
