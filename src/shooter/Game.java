package shooter;

import shooter.gfx.Display;
import shooter.states.*;

import java.awt.*;
import java.lang.Runnable;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private Display display;
    private final int width = 1920, height = 1080;

    private BufferStrategy bs;
    private Graphics g;

    private Thread thread;
    private boolean running;

    private State gameState;
    private State menuState;

    public Game() { start(); }

    private void init(){    //init display & assets
        display = new Display();
        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    public void tick() {
        if(State.getState() != null)
            State.getState().tick();
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0,0, width, height); // Clear Screen

        if(State.getState() != null)
            State.getState().render(g);

        bs.show();
        g.dispose();
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

        while(running) {    //gameloop
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
                //NOTE: Maximum Frametime : '16666666'
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start() {
        if (running) { return; }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) { return; }
        running = false;
        try { thread.join(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
