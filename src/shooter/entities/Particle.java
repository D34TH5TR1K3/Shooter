package shooter.entities;


import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Particle {

    private int x, y, speed;
    private BufferedImage[] frames;
    private Animation AminParticle;
    private Handler handler;
    private World world;
    private int width = 0, height = 0;
    
    public Particle(int x, int y, int speed, BufferedImage[] frames, Handler handler, World world){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.frames = frames;
        this.handler = handler;
        this.world = world;
        AminParticle = new Animation(speed, frames);
        summonParticle();
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
        AminParticle = new Animation(speed, frames);
        summonParticle();
    }
    public void summonParticle(){

    }
    public void tick(){
        AminParticle.tick();
    }
    public void render(Graphics g){
        if(width == 0)
            g.drawImage(AminParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), null);
        else
            g.drawImage(AminParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), width, height, null);
    }

    public Animation getAminParticle() {
        return AminParticle;
    }
}
