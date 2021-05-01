package shooter;

import shooter.entities.EntityManager;
import shooter.gfx.*;
import shooter.input.*;
import shooter.sound.Sound;
import shooter.states.*;
import shooter.utils.Writer;

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

    public State gameState;
    public State menuState;

    private Handler handler;

    private KeyManager keyManager;
    private MouseManager mouseManager;
    private GameCamera gameCamera;
    private Writer writer;
    private World world;

    private Sound sound;

    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        writer = new Writer();
        start();
    }

    private void init(){    //init display & assets
        display = new Display(writer);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();
        handler = new Handler(this);

        gameCamera = new GameCamera(handler,0,0);
        world = new World(handler);

        gameState = new GameState(this,handler);
        menuState = new MenuState(this,handler);
        State.setState(gameState);

        sound = new Sound();
        if(writer.GetSettingValue("VolumeToggle") == 1){
            sound.playBackgroundMusic();
        }
        float volume = writer.GetSettingValue("Volume");
        sound.setBackgroundVolume(sound.getBackgroundMinVolume() + (sound.getBackgroundMaxVolume() - sound.getBackgroundMinVolume()) * volume / 100f);
    }

    public void tick() {
        keyManager.tick();
        State.getState().tick();
        sound.tick();
    }
long now;
    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0,0, width, height); // Clear Screen

        //g.drawImage(Assets.map_temp,0,0,1920,1080,null);
        //map1.renderTiles(g);

        State.getState().render(g);
        //g.drawImage(Assets.map_temp,0,0,1920,1080,null);
        //world.renderTiles(g);

        now = System.nanoTime();
        bs.show();
        System.out.println((System.nanoTime() - now) / 1000000f);
        g.dispose();

    }

    public void run() { // run game
        init();

        boolean debug = true;

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
                if(debug) {
                    now = System.nanoTime();
                }
                tick();
                if(debug) {
                    System.out.println("tick");
                    System.out.println((System.nanoTime() - now) / 1000000f);
                    now = System.nanoTime();
                }
                render();
                if(debug) {
                    System.out.println("render");
                    System.out.println((System.nanoTime() - now) / 1000000f);
                }
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println("TPS & FPS:" + ticks+"\t");
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public Handler getHandler() {
        return handler;
    }
    public GameCamera getGameCamera() {
        return gameCamera;
    }
    public KeyManager getKeyManager() {
        return keyManager;
    }
    public MouseManager getMouseManager() {
        return mouseManager;
    }
    public Sound getSound() {
        return sound;
    }
    public Writer getWriter() {
        return writer;
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
