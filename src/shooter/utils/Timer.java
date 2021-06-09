package shooter.utils;

public class Timer {

    private int delay;
    private long lastTime, now;

    public Timer(int delay) {
        this.delay = delay;
        lastTime = System.currentTimeMillis();
    }

    public boolean valid() {
        now = System.currentTimeMillis();
        return !(now - lastTime > delay);
    }
}
