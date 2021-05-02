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
    public void summonParticle(){

    }
    public void tick(){
        AminParticle.tick();
    }
    public void render(Graphics g){
        g.drawImage(AminParticle.getCurrentFrame(), ((int) (x - handler.getxOffset())), ((int) (y - handler.getyOffset())), null);
    }

    public Animation getAminParticle() {
        return AminParticle;
    }
}
