package shooter;

import shooter.gfx.Display;
import java.lang.Runnable;

public class Game implements Runnable {
    private Display display;
    private int width, height;
    private String title;

    private Thread thread;

    private boolean running;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){    //init display & assets
        display = new Display();
    }

    public void run() { // run game
        init();

        int fps = 60; //TODO: Change for performance?
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {                            //gameloop
            now = System.nanoTime();
            delta += (now - lastTime)  / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println("Ticks and FPS:" + ticks +"\t"+"\t" + System.nanoTime()/1000000000);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public void tick() {

    }

    public void render() {

    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
