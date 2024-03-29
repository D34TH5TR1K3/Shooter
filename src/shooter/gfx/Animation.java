package shooter.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    //saves the speed of the animation
    private final int speed;
    //logical variables
    private short index = 0;
    private long lastTime;
    //saves the animations textures
    private final BufferedImage[] frames;
    //indicates whether the animation is active
    private boolean active = true;
    //center offset of the animation
    private int xOffset, yOffset;
    //image size
    private int width, height;
    //
    private boolean attackAnimation = false;
    private int health = -1;
    //this constructor initializes the values
    public Animation(BufferedImage[] frames, int speed, int xOffset, int yOffset, boolean attackAnimation) {
        this.attackAnimation = attackAnimation;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.speed = speed;
        this.frames = frames;
        this.width = frames[0].getWidth();
        this.height = frames[0].getHeight();
        lastTime = System.currentTimeMillis();
    }

    public Animation(BufferedImage[] frames, int speed, int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.speed = speed;
        this.frames = frames;
        this.width = frames[0].getWidth();
        this.height = frames[0].getHeight();
        lastTime = System.currentTimeMillis();
    }

    public Animation(BufferedImage[] frames, int speed) {
        this.speed = speed;
        this.frames = frames;
        lastTime = System.currentTimeMillis();
    }

    //ticks the animation
    public void tick() {
        if(System.currentTimeMillis() - lastTime > speed) {
            if(health != 0)
                index++;
            lastTime = System.currentTimeMillis();
            if (index >= frames.length) {
                health--;
                index = 0;
            }
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
    public short getCurrentFrameIndex() { return index; }
    public int getxOffset() {
        return xOffset;
    }
    public int getyOffset() {
        return yOffset;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getHealth() {
        return health;
    }
    public void incHealth() {
        if(health < 1)
            health = 1;
        else
            health++;
    }
    public boolean isAttackAnimation() {
        return attackAnimation;
    }
}
