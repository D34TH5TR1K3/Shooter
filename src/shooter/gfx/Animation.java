package shooter.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    //saves the speed of the animation
    private final int speed;
    //logical variables
    private int index = 0;
    private long lastTime;
    //saves the animations textures
    private final BufferedImage[] frames;
    //indicates whether the animation is active
    private boolean active = true;

    //this constructor initializes the values
    public Animation(BufferedImage[] frames, int speed) {
        this.speed = speed;
        this.frames = frames;
        lastTime = System.currentTimeMillis();
    }

    //ticks the animation
    public void tick() {
        if(System.currentTimeMillis() - lastTime > speed) {
            index++;
            lastTime = System.currentTimeMillis();
            if(index >= frames.length)
                index = 0;
        }
    }

    //method to stop the animation
    public void stop() {
        active = false;
    }
    //method to start the animation
    public void start() {
        if(!active)
            index = 0;
        active = true;
    }

    //getters
    public boolean lastFrame() { return index == frames.length - 1; }
    public BufferedImage getCurrentFrame() { return (active) ? frames[index] : frames[0]; }
}
