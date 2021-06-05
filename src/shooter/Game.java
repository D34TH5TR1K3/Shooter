package shooter;

import shooter.gfx.Assets;
import shooter.gfx.Display;
import shooter.gfx.GameCamera;
import shooter.gfx.LoadingImage;
import shooter.input.KeyManager;
import shooter.input.MouseManager;
import shooter.utils.Sound;
import shooter.states.GameState;
import shooter.states.MenuState;
import shooter.states.State;
import shooter.utils.Writer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    //display is responsible for displaying the game to the user
    private final Display display;
    //thread is responsible for running the game efficiently
    private Thread thread;
    //running indicates whether the game is running
    private boolean running;
    //game and menuState are States of the game with different functions
    public State gameState, menuState;
    //handler is responsible for variable distribution
    private final Handler handler;
    //key and mouseManagers are responsible for user input
    private final KeyManager keyManager;
    private final MouseManager mouseManager;
    //gameCamera is responsible for the users viewport
    private final GameCamera gameCamera;
    //sound is responsible for playing sound
    private final Sound sound;

    //this constructor initializes the values
    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        display = new Display(keyManager,mouseManager);
        LoadingImage.initialRender(display);
        Assets.init();
        handler = new Handler(this);
        gameState = new GameState(this,handler);
        handler.setWorld(((GameState)gameState).getWorld());
        menuState = new MenuState(this,handler);
        State.setState(menuState);
        gameCamera = new GameCamera(0,0);
        sound = new Sound();
        if(new Writer().GetSettingValue("VolumeToggle") == 1)
            sound.playBackgroundMusic();
        float volume = new Writer().GetSettingValue("Volume");
        sound.setBgVol(sound.getBgVolMin() + (sound.getBgVolMax() - sound.getBgVolMin()) * volume / 100f);
        start();
    }

    //tick is responsible for the logic of the game. all tick methods get called here
    public void tick() {
        keyManager.tick();
        if(handler.getKeyManager().esc)
            State.setState(handler.getGame().menuState);
        if(!handler.getWorld().getActiveLevel().getEntityManager().getPlayer().isAlive()&&!keyManager.reload&&State.getState().equals(gameState))
            return;
        else if(keyManager.reload)
            handler.getWorld().reloadLevels();
        State.getState().tick();
        sound.tick();
        if(keyManager.save)
            Writer.writeGameSave(handler.getWorld().getActiveLevel());
        if(keyManager.load)
            handler.getWorld().setLevel(0);
        if(keyManager.wipe)
            Writer.wipeGame();
        if(keyManager.level1)
            handler.getWorld().setLevel(1);
        if(keyManager.level2)
            handler.getWorld().setLevel(2);
    }
    //render is responsible for the graphics of the game. all render methods get called here
    public void render() {
        if(!handler.getWorld().getActiveLevel().getEntityManager().getPlayer().isAlive()&&State.getState().equals(gameState)){
            LoadingImage.renderDeathScreen();
            return;
        }
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0, 1920, 1080);
        State.getState().render(g);
        bs.show();
        g.dispose();
    }

    //getters and setters
    public Handler getHandler() { return handler; }
    public GameCamera getGameCamera() { return gameCamera; }
    public KeyManager getKeyManager() { return keyManager; }
    public MouseManager getMouseManager() { return mouseManager; }
    public Sound getSound() { return sound; }

    //methods required for the Thread logic to work properly
    public void run() {
        boolean debug = false;

        double timePerTick = 1000000000f / 60;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        short ticks = 0;

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime)  / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                if(debug)
                    now = System.nanoTime();
                tick();
                if(debug) {
                    System.out.println("tick\t"+(System.nanoTime() - now) / 1000000f);
                    now = System.nanoTime();
                }
                render();
                if(debug)
                    System.out.println("render\t"+(System.nanoTime() - now) / 1000000f);
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.print(ticks+"\t");
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }
    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try { thread.join(); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
