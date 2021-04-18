package shooter;

import shooter.entities.EntityManager;
import shooter.gfx.Assets;
import shooter.gfx.Display;
import shooter.gfx.Map;
import shooter.input.*;
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

    private Handler handler;

    private KeyManager keyManager;
    private MouseManager mouseManager;
//TEMP VARIABLES
    private Map map = new Map();
    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        start();
    }

    private void init(){    //init display & assets
        display = new Display();
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();
        map.fillTiles();

        handler = new Handler(this);

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    public void tick() {
        keyManager.tick();
        State.getState().tick();
        map.tick();
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0,0, width, height); // Clear Screen

        //render entity manager

        //g.drawImage(Assets.map_temp,0,0,1920,1080,null);
        map.renderTiles(g);
        map.render(g);
        
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
        long frametime = 0;

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
                System.out.print("TPS & FPS:" + ticks+"\t");
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
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
