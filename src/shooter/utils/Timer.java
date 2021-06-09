package shooter.utils;

public class Timer {

    private final int delay;
    private final long lastTime;
    private long now;

    public Timer(int delay) {
        this.delay = delay;
        lastTime = System.currentTimeMillis();
    }

    public boolean valid() {
        now = System.currentTimeMillis();
        return !(now - lastTime > delay);
    }
}
