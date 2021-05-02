package shooter.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }
    public Animation(BufferedImage[] frames, int rpm) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        if(System.currentTimeMillis()	- lastTime > speed) {
            index++;
            lastTime = System.currentTimeMillis();
            if(index >= frames.length)
                index = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
