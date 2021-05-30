package shooter;

import shooter.gfx.*;
import shooter.input.*;
import shooter.sound.Sound;
import shooter.states.*;
import shooter.utils.Writer;

import java.awt.*;
import java.lang.Runnable;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private Display display;                    //Das Display stellt ein Fenster bereit, mit dem der Nutzer interagieren kann

    private Thread thread;                      //Der Thread kümmert sich darum, dass das Programm effizient ausgeführt wird
    private boolean running;                    //running ist eine globale Variable, die genutzt wird um das starten und stoppen des Spiels zu organisieren

    public State gameState, menuState;          //GameState und MenuState sind die beiden Zustände des Spiels, die entscheiden, ob man gerade das Menü navigiert oder das Spiel spielt

    private Handler handler;                    //Der Handler kümmert sich um das Verteilen von Objektinstanzen innerhalb der Klassen

    private final KeyManager keyManager;        //Der KeyManager kümmert sich um den Input der Tastatur
    private final MouseManager mouseManager;    //Der MouseManager kümmert sich um den Input der Maus
    private GameCamera gameCamera;              //Die GameCamera sorgt dafür, dass der Spieler immer zentriert ist und dass sich die Welt um ihn herumbewegt
    private final Writer writer;                //Der Writer kümmert sich um das Speichern und Auslesen von Einstellungen und Spielständen

    private Sound sound;                        //Das Sound-objekt kümmert sich um die Wiedergabe von Geräuschen und Musik
    private BufferStrategy bs;
    private Graphics g;

    //this constructor initializes the values
    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        writer = new Writer();
        start();
    }

    //init is responsible for the initialization of variables. all init methods get called here
    private void init(){
        display = new Display(writer);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();
        handler = new Handler(this);

        gameCamera = new GameCamera(0,0);

        gameState = new GameState(this,handler);
        menuState = new MenuState(this,handler);
        State.setState(gameState);

        sound = new Sound();
        if(writer.GetSettingValue("VolumeToggle") == 1){
            sound.playBackgroundMusic();
        }
        float volume = writer.GetSettingValue("Volume");
        sound.setBgVol(sound.getBgVolMin() + (sound.getBgVolMax() - sound.getBgVolMin()) * volume / 100f);
    }
    //tick is responsible for the logic of the game. all tick methods get called here
    public void tick() {
        keyManager.tick();
        State.getState().tick();
        sound.tick();
        if(keyManager.save)
            writer.writeGameSave(((GameState)gameState).getWorld());
    }
    //render is responsible for the graphics of the game. all render methods get called here
    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
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
    public Writer getWriter() { return writer; }

    //methods required for the Thread logic to work properly
    public void run() {
        init();

        boolean debug = false;

        int fps = 60;
        double timePerTick = 1000000000f / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {
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
