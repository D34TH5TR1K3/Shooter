package shooter.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    private final int speed;                                //hier wird die Geschwindigkeit der Animation gespeichert
    private int index = 0;                                  //hier wird gespeichert bei welchem Bild der Animation wir uns befinden
    private long lastTime;                                  //hier wird gespeichert, wann sich die Animation das letzte Mal bewegt hat
    private final BufferedImage[] frames;                   //hier werden die Bilder der Animation gespeichert
    private boolean active = true;                          //hier wird gespeichert, ob sich die Animation gaerade bewegt

    //this constructor initializes the values
    public Animation(BufferedImage[] frames, int speed) {   //im Konstruktor werden speed, frames und lastTime initialisiert
        this.speed = speed;
        this.frames = frames;
        lastTime = System.currentTimeMillis();
    }

    public void stop() {                                    //in stop wird die Animation angehalten
        active = false;
    }

    public void start() {                                   //in start wird die Animation gestartet
        if(!active)
            index = 0;
        active = true;
    }

    public void tick() {                                    //in tick wird, wenn nötig das nächste Bild der Animation angezeigt
        if(System.currentTimeMillis() - lastTime > speed) {
            index++;
            lastTime = System.currentTimeMillis();
            if(index >= frames.length) index = 0;
        }
    }

    public boolean lastFrame() {                            //hier wird zurückgegeben ob die Animation den letzten Frame erreicht hat
        return index == frames.length - 1;
    }

    public BufferedImage getCurrentFrame() {                //hier wird der derzeitige Frame ausgegeben
        return (active) ? frames[index] : frames[0];
    }
}
